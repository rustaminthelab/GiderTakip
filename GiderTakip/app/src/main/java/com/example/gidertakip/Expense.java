package com.example.gidertakip;

/**
 * Model Sınıfı: Her bir gider kaydını temsil eden veri modelidir.
 */
public class Expense {
    private int id;
    private String baslik;
    private double miktar;
    private String kategori;
    private String tarih;

    /**
     * Boş yapıcı metot: Yeni gider eklerken (ID henüz atanmamışken) kullanılır.
     */
    public Expense() {}

    /**
     * Parametreli yapıcı metot: Veritabanından mevcut verileri çekerken kullanılır.
     */
    public Expense(int id, String baslik, double miktar, String kategori, String tarih) {
        this.id = id;
        this.baslik = baslik;
        this.miktar = miktar;
        this.kategori = kategori;
        this.tarih = tarih;
    }

    /**
     * Getter ve Setter metotları: Verilere erişim ve güncelleme sağlar.
     * Not: setId metodu, veritabanı ID'yi otomatik (autoincrement) atadığı için 
     * kullanılmadığından kaldırılmıştır.
     */
    public int getId() { return id; }

    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }

    public double getMiktar() { return miktar; }
    public void setMiktar(double miktar) { this.miktar = miktar; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getTarih() { return tarih; }
    public void setTarih(String tarih) { this.tarih = tarih; }
}
