package com.example.gidertakip;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Detay Ekranı: Seçilen giderin tüm detaylarını gösterir ve silme imkanı tanır.
 */
public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private int giderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Veritabanı bağlantısını kur
        dbHelper = new DatabaseHelper(this);
        
        // MainActivity'den gönderilen gider ID'sini al
        giderId = getIntent().getIntExtra("GIDER_ID", -1);

        // UI bileşenlerini tanımla
        TextView tvDetayBaslik = findViewById(R.id.tvDetayBaslik);
        TextView tvDetayMiktar = findViewById(R.id.tvDetayMiktar);
        TextView tvDetayKategori = findViewById(R.id.tvDetayKategori);
        TextView tvDetayTarih = findViewById(R.id.tvDetayTarih);
        Button btnSil = findViewById(R.id.btnSil);
        Button btnGeriDon = findViewById(R.id.btnGeriDon);

        // ID geçersizse ekranı kapat
        if (giderId == -1) {
            Toast.makeText(this, "Gider bulunamadı!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Veritabanından gider detaylarını çek ve ekrana yazdır
        Expense expense = dbHelper.giderGetir(giderId);
        if (expense != null) {
            tvDetayBaslik.setText(expense.getBaslik());
            tvDetayMiktar.setText(String.format("%.2f ₺", expense.getMiktar()));
            tvDetayKategori.setText(expense.getKategori());
            tvDetayTarih.setText(expense.getTarih());
        }

        // Silme butonu için tıklama olayı
        btnSil.setOnClickListener(v -> silOnay());
        
        // Geri dön butonu
        btnGeriDon.setOnClickListener(v -> finish());
    }

    /**
     * Kullanıcıdan silme işlemi için onay alan bir diyalog penceresi gösterir.
     */
    private void silOnay() {
        new AlertDialog.Builder(this)
                .setTitle("Gideri Sil")
                .setMessage("Bu gideri silmek istediğinize emin misiniz?")
                .setPositiveButton("Sil", (dialog, which) -> {
                    // Veritabanından sil
                    int sonuc = dbHelper.giderSil(giderId);
                    if (sonuc > 0) {
                        Toast.makeText(this, "Gider silindi.", Toast.LENGTH_SHORT).show();
                        finish(); // Silme başarılıysa ekranı kapat ve listeye dön
                    } else {
                        Toast.makeText(this, "Silme işlemi başarısız!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("İptal", null) // İptal edilirse hiçbir şey yapma
                .show();
    }
}
