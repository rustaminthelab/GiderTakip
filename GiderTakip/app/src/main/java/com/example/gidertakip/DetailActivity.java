package com.example.gidertakip;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private int giderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DatabaseHelper(this);
        giderId = getIntent().getIntExtra("GIDER_ID", -1);

        TextView tvDetayBaslik = findViewById(R.id.tvDetayBaslik);
        TextView tvDetayMiktar = findViewById(R.id.tvDetayMiktar);
        TextView tvDetayKategori = findViewById(R.id.tvDetayKategori);
        TextView tvDetayTarih = findViewById(R.id.tvDetayTarih);
        Button btnSil = findViewById(R.id.btnSil);
        Button btnGeriDon = findViewById(R.id.btnGeriDon);

        if (giderId == -1) {
            Toast.makeText(this, "Gider bulunamadı!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Expense expense = dbHelper.giderGetir(giderId);
        if (expense != null) {
            tvDetayBaslik.setText(expense.getBaslik());
            tvDetayMiktar.setText(String.format("%.2f ₺", expense.getMiktar()));
            tvDetayKategori.setText(expense.getKategori());
            tvDetayTarih.setText(expense.getTarih());
        }

        btnSil.setOnClickListener(v -> silOnay());
        btnGeriDon.setOnClickListener(v -> finish());
    }

    private void silOnay() {
        new AlertDialog.Builder(this)
                .setTitle("Gideri Sil")
                .setMessage("Bu gideri silmek istediğinize emin misiniz?")
                .setPositiveButton("Sil", (dialog, which) -> {
                    int sonuc = dbHelper.giderSil(giderId);
                    if (sonuc > 0) {
                        Toast.makeText(this, "Gider silindi.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Silme işlemi başarısız!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("İptal", null)
                .show();
    }
}
