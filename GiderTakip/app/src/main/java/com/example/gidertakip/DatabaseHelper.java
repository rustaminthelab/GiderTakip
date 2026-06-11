package com.example.gidertakip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String VERITABANI_ADI = "gider_takip.db";
    private static final int VERITABANI_VERSIYONU = 1;

    private static final String TABLO_ADI = "giderler";
    private static final String SUTUN_ID = "id";
    private static final String SUTUN_BASLIK = "baslik";
    private static final String SUTUN_MIKTAR = "miktar";
    private static final String SUTUN_KATEGORI = "kategori";
    private static final String SUTUN_TARIH = "tarih";

    public DatabaseHelper(Context context) {
        super(context, VERITABANI_ADI, null, VERITABANI_VERSIYONU);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tabloOlustur = "CREATE TABLE " + TABLO_ADI + " (" +
                SUTUN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUTUN_BASLIK + " TEXT NOT NULL, " +
                SUTUN_MIKTAR + " REAL NOT NULL, " +
                SUTUN_KATEGORI + " TEXT NOT NULL, " +
                SUTUN_TARIH + " TEXT NOT NULL)";
        db.execSQL(tabloOlustur);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int eskiVersiyon, int yeniVersiyon) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_ADI);
        onCreate(db);
    }

    // Gider ekle
    public long giderEkle(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(SUTUN_BASLIK, expense.getBaslik());
        degerler.put(SUTUN_MIKTAR, expense.getMiktar());
        degerler.put(SUTUN_KATEGORI, expense.getKategori());
        degerler.put(SUTUN_TARIH, expense.getTarih());
        long sonuc = db.insert(TABLO_ADI, null, degerler);
        db.close();
        return sonuc;
    }

    // Tüm giderleri getir
    public List<Expense> tumGiderleriGetir() {
        List<Expense> giderListesi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLO_ADI, null, null, null, null, null, SUTUN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_ID)));
                expense.setBaslik(cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_BASLIK)));
                expense.setMiktar(cursor.getDouble(cursor.getColumnIndexOrThrow(SUTUN_MIKTAR)));
                expense.setKategori(cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_KATEGORI)));
                expense.setTarih(cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_TARIH)));
                giderListesi.add(expense);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return giderListesi;
    }

    // ID'ye göre gider getir
    public Expense giderGetir(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLO_ADI, null,
                SUTUN_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        Expense expense = null;
        if (cursor.moveToFirst()) {
            expense = new Expense();
            expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_ID)));
            expense.setBaslik(cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_BASLIK)));
            expense.setMiktar(cursor.getDouble(cursor.getColumnIndexOrThrow(SUTUN_MIKTAR)));
            expense.setKategori(cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_KATEGORI)));
            expense.setTarih(cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_TARIH)));
        }
        cursor.close();
        db.close();
        return expense;
    }

    // Gider sil
    public int giderSil(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int sonuc = db.delete(TABLO_ADI, SUTUN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return sonuc;
    }

    // Toplam tutarı hesapla
    public double toplamTutarHesapla() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + SUTUN_MIKTAR + ") FROM " + TABLO_ADI, null);
        double toplam = 0;
        if (cursor.moveToFirst()) {
            toplam = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return toplam;
    }
}
