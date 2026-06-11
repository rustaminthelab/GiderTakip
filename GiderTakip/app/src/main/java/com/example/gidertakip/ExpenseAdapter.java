package com.example.gidertakip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Gider Adaptörü: Veritabanından gelen gider listesini RecyclerView içerisinde görüntülemek için kullanılır.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.GiderViewHolder> {

    private Context context;
    private List<Expense> giderListesi;
    private OnItemClickListener listener;

    /**
     * Tıklama olaylarını yakalamak için arayüz.
     */
    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    public ExpenseAdapter(Context context, List<Expense> giderListesi, OnItemClickListener listener) {
        this.context = context;
        this.giderListesi = giderListesi;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Her bir liste satırı için item_expense tasarımını yükle
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        return new GiderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiderViewHolder holder, int position) {
        // Belirli pozisyondaki veriyi al ve ilgili görünümlere yerleştir
        Expense expense = giderListesi.get(position);
        holder.tvBaslik.setText(expense.getBaslik());
        holder.tvMiktar.setText(String.format("%.2f ₺", expense.getMiktar()));
        holder.tvKategori.setText(expense.getKategori());
        holder.tvTarih.setText(expense.getTarih());

        // Satıra tıklandığında listener'ı tetikle
        holder.itemView.setOnClickListener(v -> listener.onItemClick(expense));
    }

    @Override
    public int getItemCount() {
        return giderListesi.size();
    }

    /**
     * Liste güncellendiğinde (yeni veri eklendiğinde veya silindiğinde) RecyclerView'ı yeniler.
     */
    public void listeGuncelle(List<Expense> yeniListe) {
        this.giderListesi = yeniListe;
        notifyDataSetChanged();
    }

    /**
     * Görünüm Tutucu: Liste satırındaki görsel bileşenleri tanımlar.
     */
    static class GiderViewHolder extends RecyclerView.ViewHolder {
        TextView tvBaslik, tvMiktar, tvKategori, tvTarih;

        public GiderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBaslik = itemView.findViewById(R.id.tvBaslik);
            tvMiktar = itemView.findViewById(R.id.tvMiktar);
            tvKategori = itemView.findViewById(R.id.tvKategori);
            tvTarih = itemView.findViewById(R.id.tvTarih);
        }
    }
}
