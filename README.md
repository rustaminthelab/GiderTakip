# Gider Takip Uygulaması (GiderTakip)

Bu uygulama, kullanıcıların harcamalarını takip etmelerini sağlayan ve güncel finansal verilerle desteklenen bir Android projesidir.

## 🛠 Teknik Gereksinimler & Başarı Kriterleri
Hocamızın belirttiği kriterler doğrultusunda projede şu özellikler yer almaktadır:

*   **En Az 3 Aktivite:** Proje `MainActivity`, `AddExpenseActivity` ve `DetailActivity` ekranlarından oluşur.
*   **Veri Yönetimi (Database):** Kullanıcı verileri **SQLite** kullanılarak yerel veri tabanında kalıcı olarak saklanmaktadır.
*   **Dış Servis Entegrasyonu (API):** `Volley` kütüphanesi kullanılarak bir finans API'sinden güncel **USD/TRY** döviz kuru verisi çekilmektedir.
*   **Modern UI/UX:** Tasarımda `CoordinatorLayout`, `Material Design`, `RecyclerView` ve `CardView` bileşenleri kullanılmıştır.

## 🚀 Özellikler
*   Harcama ekleme, silme ve listeleme.
*   Toplam harcama tutarının anlık hesaplanması.
*   Ana ekranda güncel döviz kuru takibi (Finansal API Entegrasyonu).
*   Harcama detaylarını görüntüleme.

## 📦 Kullanılan Kütüphaneler
*   `com.android.volley:volley:1.2.1` (API İşlemleri için)
*   `androidx.recyclerview:recyclerview:1.3.2`
*   `com.google.android.material:material:1.11.0`
