package com.example.gidertakip;

public class Expense {
    private int id;
    private String baslik;
    private double miktar;
    private String kategori;
    private String tarih;

    public Expense() {}

    public Expense(int id, String baslik, double miktar, String kategori, String tarih) {
        this.id = id;
        this.baslik = baslik;
        this.miktar = miktar;
        this.kategori = kategori;
        this.tarih = tarih;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }

    public double getMiktar() { return miktar; }
    public void setMiktar(double miktar) { this.miktar = miktar; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getTarih() { return tarih; }
    public void setTarih(String tarih) { this.tarih = tarih; }
}
