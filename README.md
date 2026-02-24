
# Modul 1
### Refleksi Exercise 1

1. **Implementasi Edit dan Delete**

   Saya sendiribelajar menerapkan clean code dalam mengimplementasi fitur edit dan delete ini. Saya menggunakan variable
   yang cukup menjelaskan fungsi, tidak terlalu detail namun tetap berarti. Saya juga mengoptimalkan reusability. Dari sisi
   secure coding, saya telah menggunakan enkapsulasi data dan menggunakan UUID untuk id sebuah barang. Setelah saya
   pelajari lebih lanjut, source code saya terdapat celah keaamanan pada penggunaan @GetMapping untuk opereasi delete dan
   penggunaan ArrayList yang tidak thread safe. Perbaikannya adalah dengan mengubah metode delete menjadi @PostMapping, beralih
   ke struktur data yang mendukung concurrency seperti @CopyOnWriteArrayList, serta menerapkan validasi input Spring
   untuk memastikan integritas data aplikasi.

### Refleksi Exercise 2

1. **Unit Test**

   Setelah menyelesaikan unit test, saya merasa _satisfied_ karena testnya berhasil semua. Untuk unit testnya sendiri,
   seharusnya bisa mengcover keseluruhan logika yang dilakukan dalam program tersebut. Dengan itu, kita bisa memperbaiki
   kesalahan logika lebih awal dan meningkatkan tingkat kepuasan dalam program yang kita buat. Untuk unit test yang
   kita harus cover juga seharusnya menggunakan skenario positif, negatif, dan juga edge cases yang mungkin. 
   Metrik code coverage ini tentunya sangat membantu untuk tracking seberapa besar coverage hal yang sudah diuji.
   Namun, code coverage 100% artinya bukan aplikasi tersebut tidak ada bug sama sekali, melainkan indikator bahwa
   seluruh baris kode telah dieksekusi.

3. **Functional Test**

   Untuk kasus saya, setelah membuat `CreateProductFunctionalTest.java`, saya tersadar bahwa implementasinya 
sangat mirip dengan implementasi Functional Test yang saya buat untuk tutorial `HomePageFunctionalTest.java`.
Hal tersebut bisa mengarah ke code duplication pada bagian setup danjuga konfigurasi Selenium. Selain itu,
terdapat redundancy dalam mendefinisikan variabel serverPort dan juga baseUrl, serta Pengulangan logika
penggabungan URL di setiap @BeforeEach Oleh karena itu, untuk memperbaikinya adalah kita bisa menerapkan konsep
Inheritance dengan memnbuat sebuah class baru yang berisikan konfigurasi Spring Boot Test dan Selenium. Dengan 
solusi tersebut, kita tinggal menggunakan konsep iheritance dan dapat meningkatkan maintainability.


# Modul 2

### Code Quality Issues

**1. Penggunaan Modifier yang Tidak Perlu**
   
   Beberapa kali ditemukan penggunaan modifier 'public' yang unnecessary untuk digunakan, tidak sesuai dengan standar encapsulation. Fix yang saya lakukan adalah dengan menhapus modifier 'public' tersebut pada class dan juga unit test yang tidak perlu.

**2. Pengelompokkan dependensi di `build.gradle.kts'**

   SonarCloud saya mendeteksi bagian dependencies saya di 'build.gradle.kts' tidak tersusun dengan rapi. Oleh karena itu saya perbaiki dengan mengelompokkan library berdasarkan tujuannya (seperti menempatkan semua testImplementation dalam satu blok yang sama) untuk meningkatkan readability dan maintanability.

**3. Permasalahan Penulisan**

   Terjadi error saat deployment karena terdapat permaslahan case-sensitivity antara Windows dan Linux. Hal yang saya lakukan  adalah memastikan nama file di folder templates (misal: productList.html) cocok secara persis dengan string yang dikembalikan oleh Controller atau sebalikanya agar aplikasi dapat berjalan di production (Linux/Koyeb).


### Implementasi CI/CD

Menurut saya, implementasi saat ini sudah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD.

Pertama, aspek Continuous Integration terpenuhi karena setiap kali ada perubahan kode yang dicommit ke repositori, GitHub Actions otomatis menjalankan unit test dan static analysis menggunakan SonarCloud untuk memastikan kode baru tidak merusak fitur yang sudah ada.

Kedua, aspek Continuous Deployment telah tercapai melalui integrasi dengan Koyeb menggunakan pendekatan pull-based, sehingga setiap kode yang sudah pass test dan dimerge ke branch main akan langsung di-build dan dideploy ke server produksisecara otomatis.

Ketiga, alur ini menciptakan siklus pengembangan yang cepat dan aman, di mana kesalahan konfigurasi (seperti masalah verifikasi dependensi) dapat segera dideteksi dan diperbaiki, sesuai dengan tujuan utama dari CI/CD.



