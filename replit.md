# KADİRİ TARİKATI EL KİTABI — Android Uygulaması

## Proje Genel Bakışı

**Paket:** `com.kadiri.elkitabi`  
**Min SDK:** 26 (Android 8.0)  
**Target SDK:** 36  
**Sürüm:** 2.0.0 (versionCode = 2)  
**Teknoloji Yığını:** Kotlin 2.1.x + Jetpack Compose + Material3 Expressive + Hilt + Room + DataStore

---

## Mimari

Clean Architecture + MVVM:
```
features/
  <özellik>/
    data/       → Repository impl, Local/Remote data sources, DAO, Entity
    domain/     → Repository arayüzü, Model, UseCase
    presentation/ → ViewModel, Screen, Components
core/
  data/         → Database, DataStore
  designsystem/ → Components, Theme, Icons
  di/           → Hilt modülleri
  navigation/   → NavGraph, Routes
  utils/        → HijriUtils, PrayerTimeUtils, DateUtils, ...
```

---

## Özellikler

| Ekran | Açıklama |
|-------|----------|
| **Ana Sayfa** | Hicrî tarih, namaz vakti, günlük zikir sayacı, özellik grid |
| **ZikirMatik** | Çark sayaç, arabesque halka animasyonu, özel zikir, istatistik, geçmiş |
| **Kitap** | 4 el kitabı (tohid, adap, silsile, zikirname), bölüm okuma, yer imi |
| **Dualar** | Kategori bazlı dualar, Türkçe/Arapça |
| **Namaz Vakitleri** | Adhan kütüphanesi ile gerçek hesaplama |
| **Kıble** | Cihaz sensörü ile kıble yönü |
| **Esmâ-ül Hüsnâ** | 99 İsim tam liste + Arapça |
| **Silsile** | 15 halkadan oluşan Kadiri silsilesi |
| **Hicrî Takvim** | Ay navigasyonu, mübarek günler |
| **İlahiler** | 15 ilahi metni + oynatıcı servisi |
| **Amel Defteri** | Günlük amel ekleme/tamamlama |
| **Ayarlar** | Koyu tema, font boyutu, bildirimler |

---

## Android Studio'da Açmak

1. Projeyi klonlayın / indirin
2. Android Studio'da `File → Open → proje kökü` seçin
3. Gradle sync bekleyin
4. `Run → Run 'app'` veya `Shift+F10`

---

## Font Dosyaları (Geliştirici Notu)

`app/src/main/res/font/` klasöründeki XML dosyaları **placeholder** dur.
Gerçek fontları buraya ekleyin:

| Dosya | İndirme |
|-------|---------|
| `amiri_regular.ttf` | https://fonts.google.com/specimen/Amiri |
| `noto_naskh_arabic.ttf` | https://fonts.google.com/noto/specimen/Noto+Naskh+Arabic |

TTF dosyalarını ekledikten sonra placeholder XML dosyalarını silin.

---

## Ses Dosyaları

`app/src/main/res/raw/README.md` dosyasına bakın.

---

## Bağımlılıklar (app/build.gradle.kts)

- `androidx.compose.*` — UI
- `com.google.dagger:hilt-android` — DI
- `androidx.room:room-runtime` — Veritabanı
- `androidx.datastore:datastore-preferences` — Preferences
- `com.batoulapps.adhan:adhan-android` — Namaz vakti hesaplama
- `androidx.navigation:navigation-compose` — Navigasyon

---

## User Preferences

(Yok — geliştirici notları buraya eklenir)
