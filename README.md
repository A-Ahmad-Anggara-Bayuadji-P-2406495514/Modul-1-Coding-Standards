
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


# Modul 3 (Maintanability & OO)
### Refleksi Exercise

1Tentu, Bayu. Berikut adalah draf jawaban untuk bagian Reflection di README.md kamu. Saya menyusunnya dalam format dua paragraf singkat per poin agar tetap padat dan informatif, sesuai dengan progres proyek ESHOP yang baru saja kita kerjakan.

**1. Principles Applied to the Project**

   Dalam proyek ini, saya menerapkan SOLID untuk meningkatkan kualitas kode. Berikut penerapan saya:
   
   Single Responsibility Principle (SRP) diterapkan dengan memisahkan CarController dari ProductController agar masing-masing hanya menangani satu domain. 
   
   Interface Segregation Principle (ISP) diterapkan dengan memecah CarService dan CarRepository menjadi interface Read dan Write, sehingga klien tidak terpaksa bergantung pada metode yang tidak mereka gunakan.
   
   Dependency Inversion Principle (DIP) dan Open-Closed Principle (OCP) saya terapkan dengan mengubah ketergantungan high-level module (Service) ke low-level module (Repository) menjadi ketergantungan pada abstraksi (interface) menggunakan Constructor Injection. Hal ini secara otomatis mendukung OCP, di mana sistem sekarang terbuka untuk ekstensi (seperti menambah SqlCarRepository) namun tertutup untuk modifikasi pada kode Service yang sudah ada.  
   
   Liskov Substitution Principle (LSP) dipenuhi dengan menghapus inheritance yang kurang tepat antara controller, memastikan setiap kelas dapat berdiri sendiri tanpa merusak logika parentnya.

**2. Advantages of Applying SOLID Principles**

   Penerapan SOLID membuat kode menjadi lebih maintanable dan fleksibel terhadap perubahan. Sebagai contoh, dengan adanya DIP, jika saya ingin mengganti penyimpanan data dari ArrayList ke database SQL, saya hanya perlu untuk membuat implementation baru dari CarRepository tanpa perlu mengubah logika bisnis di CarServiceImpl. Ini juga mengurangi risiko munculnya bug baru saat melakukan pembaruan sistem.
   
   Selain itu, pengujian kode (unit testing) menjadi lebih efisien dan terisolasi. Dengan ISP, saya bisa membuat mock yang sangat spesifik, misalnya saat menguji fitur pencarian, saya hanya perlu melakukan mocking pada CarReadRepository tanpa mempedulikan metode hapus atau edit. Struktur ini memastikan bahwa setiap komponen dapat diuji secara mandiri, yang pada akhirnya meningkatkan keandalan aplikasi secara keseluruhan.

 **3. Disadvantages of Not Applying SOLID Principles**

 Jika kita tidak menerapkan SOLID principle, kode cenderung menjadi "kaku" dan sulit untuk dikembangkan. Sebagai contoh, sebelum refactoring, CarController yang melakukan extends pada ProductController menciptakan ketergantungan yang tidak perlu (LSP Violation). Jika ada perubahan pada cara ProductController menangani pemetaan URL, hal tersebut bisa secara tidak sengaja merusak fungsi di CarController.

Selain itu, tanpa SRP dan OCP, kelas-kelas akan menjadi terlalu besar dan kompleks (God Objects). Jika CarServiceImpl bergantung langsung pada concrete class CarRepository, maka setiap kali kita ingin mengubah cara data disimpan, kita harus membongkar dan memodifikasi isi Service tersebut. Hal ini tidak hanya membuang waktu, tetapi juga membuat kode sulit dibaca oleh developer lainnya dan meningkatkan kemungkinan terjadinya bug yang awalnya fitur berjawal normal.
