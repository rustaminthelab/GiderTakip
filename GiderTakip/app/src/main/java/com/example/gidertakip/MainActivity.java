package com.example.gidertakip;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvToplamTutar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        tvToplamTutar = findViewById(R.id.tvToplamTutar);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listeYenile();
    }

    private void listeYenile() {
        List<Expense> giderler = dbHelper.tumGiderleriGetir();
        double toplam = dbHelper.toplamTutarHesapla();
        tvToplamTutar.setText(String.format("Toplam: %.2f ₺", toplam));

        if (adapter == null) {
            adapter = new ExpenseAdapter(this, giderler, expense -> {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("GIDER_ID", expense.getId());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.listeGuncelle(giderler);
        }
    }
}
