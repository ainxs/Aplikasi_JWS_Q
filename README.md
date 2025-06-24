# 🕌 Aplikasi_JWS_Q

<img width="207" alt="SS" src="https://github.com/user-attachments/assets/927229dc-16dd-4a8b-b760-03973f81df40" />


Aplikasi Android untuk mengatur **Jam Sholat Digital** berbasis **ESP8266/ESP32**.  
Mendukung pengaturan waktu sholat, tampilan LED Matrix, kontrol tartil via DFPlayer Mini, dan konfigurasi penuh melalui Web Server.

---

## 📱 Fitur Aplikasi Android

✅ Sinkronisasi waktu ke RTC DS3231 di ESP  
✅ Pengaturan lokasi (Latitude, Longitude, Altitude)  
✅ Pengaturan jadwal Iqomah dan Adzan  
✅ Kontrol warna & tampilan LED Matrix DMD  
✅ Pemutaran tartil manual (play, pause, stop) via DFPlayer  
✅ Komunikasi HTTP ke WebServer ESP  
✅ Tampilan modern berbasis Jetpack Compose

---

## 🔌 Dukungan Hardware

- ✅ **ESP8266** (NodeMCU / Wemos D1 Mini)
- ✅ **ESP32** (DevKit v1 / ESP32-CAM)
- ⏱️ RTC: DS3231
- 📻 DFPlayer Mini (MP3)
- 🟩 DMD LED Matrix P10 / P6
- 📁 SPIFFS untuk menyimpan konfigurasi `.json`

---

## 🛠️ Teknologi yang Digunakan

### Aplikasi Android
- Kotlin + Jetpack Compose
- OkHttp untuk komunikasi HTTP
- Accompanist Permissions
- Material 3 UI

### ESP8266/ESP32 Firmware
- Mode WiFi STA + AP
- NTP Client + RTC DS3231
- WebServer dengan REST API
- JSON Config (SPIFFS)
- Library: `DFRobotDFPlayerMini`, `RTClib`, `DMD3`, dll.

---

## 🚀 Build APK

Untuk membuat APK rilis:

```bash
./gradlew assembleRelease

Jika ingin upload ke Play Store, pastikan Anda telah men-sign APK dengan release-key.jks.

🔗 Komunikasi ESP ⇆ Android
ESP menyediakan endpoint seperti:

http
Copy code
http://192.168.4.1/atur.json
http://192.168.4.1/lokasi.json
http://192.168.4.1/tartil?file=003&cmd=play
http://192.168.4.1/rtc-set?jam=14&menit=30
Android mengirim data via HTTP menggunakan OkHttp client.

📂 Struktur Proyek
yaml
Copy code
Aplikasi_JWS_Q/
├── app/
│   ├── components/       ← Komponen UI Reusable
│   ├── screens/          ← Layar: Iqomah, Lokasi, Tartil, dsb
│   ├── utils/            ← Fungsi komunikasi HTTP
├── ESP_Firmware/         ← (Tambahkan jika ingin simpan firmware ESP juga)
├── build.gradle.kts
├── README.md
└── .gitignore

📝 Lisensi
Proyek ini bersifat Open Source untuk keperluan non-komersial / edukatif.
Dibuat oleh @ainxs (UjangNtod) — 2025

🤝 Kontribusi
Jika ingin berkontribusi:
Fork & Pull Request
Ajukan issue untuk saran/perbaikan
Atau kirim pesan via GitHub
<img width="207" alt="SSJADWAL" src="https://github.com/user-attachments/assets/44e7f30e-d9b9-4d59-ae0b-0a7f64976f8f" />
