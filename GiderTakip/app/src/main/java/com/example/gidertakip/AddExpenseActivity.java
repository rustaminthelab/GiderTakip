package com.example.gidertakip;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etBaslik, etMiktar;
    private Spinner spinnerKategori;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new DatabaseHelper(this);
        etBaslik = findViewById(R.id.etBaslik);
        etMiktar = findViewById(R.id.etMiktar);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        Button btnKaydet = findViewById(R.id.btnKaydet);
        Button btnIptal = findViewById(R.id.btnIptal);

        // Kategori listesini Spinner'a bağla
        String[] kategoriler = {"Yiyecek", "Ulaşım", "Eğlence", "Sağlık", "Kıyafet", "Faturalar", "Diğer"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, kategoriler);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(spinnerAdapter);

        btnKaydet.setOnClickListener(v -> giderKaydet());
        btnIptal.setOnClickListener(v -> finish());
    }

    private void giderKaydet() {
        String baslik = etBaslik.getText().toString().trim();
        String miktarStr = etMiktar.getText().toString().trim();
        String kategori = spinnerKategori.getSelectedItem().toString();

        if (baslik.isEmpty()) {
            etBaslik.setError("Başlık boş olamaz!");
            return;
        }

        if (miktarStr.isEmpty()) {
            etMiktar.setError("Miktar boş olamaz!");
            return;
        }

        double miktar;
        try {
            miktar = Double.parseDouble(miktarStr);
        } catch (NumberFormatException e) {
            etMiktar.setError("Geçerli bir miktar girin!");
            return;
        }

        String tarih = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Expense yeniGider = new Expense();
        yeniGider.setBaslik(baslik);
        yeniGider.setMiktar(miktar);
        yeniGider.setKategori(kategori);
        yeniGider.setTarih(tarih);

        long sonuc = dbHelper.giderEkle(yeniGider);
        if (sonuc != -1) {
            Toast.makeText(this, "Gider eklendi!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Hata oluştu, tekrar deneyin.", Toast.LENGTH_SHORT).show();
        }
    }
}
