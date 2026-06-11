package com.example.gidertakip;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Ana Ekran: Giderlerin listelendiği ve toplam tutarın gösterildiği ana aktivite.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvToplamTutar, tvDovizKuru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Veritabanı yardımcısını başlat
        dbHelper = new DatabaseHelper(this);

        // UI bileşenlerini bağla
        recyclerView = findViewById(R.id.recyclerView);
        tvToplamTutar = findViewById(R.id.tvToplamTutar);
        tvDovizKuru = findViewById(R.id.tvDovizKuru);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Yeni gider ekleme sayfasına yönlendir
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        // Döviz kurlarını API'den çek (Gider uygulaması için daha mantıklı bir entegrasyon)
        dovizKuruGetir();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Sayfa her açıldığında veya geri dönüldüğünde listeyi yenile
        listeYenile();
    }

    /**
     * Veritabanından verileri çekerek listeyi ve toplam tutarı günceller.
     */
    private void listeYenile() {
        List<Expense> giderler = dbHelper.tumGiderleriGetir();
        double toplam = dbHelper.toplamTutarHesapla();
        tvToplamTutar.setText(String.format("Toplam: %.2f ₺", toplam));

        if (adapter == null) {
            adapter = new ExpenseAdapter(this, giderler, expense -> {
                // Listedeki bir öğeye tıklandığında detay sayfasına git
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("GIDER_ID", expense.getId());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.listeGuncelle(giderler);
        }
    }

    /**
     * Dış servis (API) kullanarak güncel döviz kurlarını çeker.
     * Frankfurter API kullanılmıştır (Ücretsiz ve finansal temaya uygundur).
     */
    private void dovizKuruGetir() {
        String url = "https://api.frankfurter.app/latest?from=USD&to=TRY";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("rates");
                        double usdRate = rates.getDouble("TRY");
                        tvDovizKuru.setText(String.format("USD/TRY: %.2f", usdRate));
                    } catch (JSONException e) {
                        tvDovizKuru.setText("Hata!");
                    }
                },
                error -> tvDovizKuru.setText("Bağlantı Yok"));

        queue.add(request);
    }
}
