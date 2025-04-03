# WinterHold - Sistem Manajemen Perpustakaan

<h3>README Translation</h3>
<ul>
  <li><a href="https://github.com/fer-nando65/WinterHold-microservice/blob/master/README.md">English</a></li>
  <li><a href="https://github.com/fer-nando65/WinterHold-microservice/blob/master/README.id.md">Indonesia</a></li>
</ul>
<br>

WinterHold adalah Sistem Manajemen Perpustakaan yang modern dan skalabel, dibangun menggunakan Spring Boot dengan arsitektur microservices. Proyek ini menyediakan cara yang efisien dan mulus untuk mengelola buku, peminjam, dan transaksi dengan ketersediaan tinggi serta toleransi kesalahan.

<h2>✨ Fitur</h2>

* Arsitektur Microservices – Layanan independen untuk pengembangan modular dan skalabilitas.

* Message Broker (Kafka) – Komunikasi berbasis event antar layanan.

* Circuit Breaker (Resilience4j) – Menjamin keandalan sistem dengan menangani kegagalan layanan secara elegan.

* API Gateway – Titik masuk terpusat untuk routing dan keamanan.

* Service Discovery (Eureka) – Registrasi dan penemuan layanan secara dinamis.

* Halaman Kesalahan Kustom – Penanganan kesalahan yang ramah pengguna dengan halaman 404 dan 503 kustom.

<h2>🏗️ Teknologi yang Digunakan</h2>

* Backend: Java, Spring Boot, Spring Cloud, Spring Data JPA

* Database: SQL Server

* Message Broker: Apache Kafka

* Service Discovery: Eureka Server

* API Gateway: Spring Cloud Gateway

* Circuit Breaker: Resilience4j

<h2>📜 Ringkasan Microservices</h2>

<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/architecture_microservice.JPG">

* Customer Service – Mengelola akun pengguna, peminjam, dan anggota perpustakaan.

* Library Service – Mengelola buku, penulis, dan kategori.

* Loan Service – Mengelola peminjaman dan pengembalian buku.

* Notification Service – Mendengarkan event dan mengirimkan notifikasi.

* API Gateway – Mengarahkan permintaan ke layanan yang sesuai.

* Eureka - Mendaftarkan dan mengelola alamat dari server/service

📸 Screenshot

Berikut adalah beberapa tangkapan layar dari aplikasi:

* Database Diagram
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/db_full.png">

* Home
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/home.JPG">

* Author Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/author.JPG">

* Category Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/category.JPG">

* Book Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/book.JPG">

* Loan Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/loan.JPG">

* Validation
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/validation.JPG">

* Error Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/errorpage.JPG">

🤝 Kontribusi

Jangan ragu untuk melakukan fork repositori dan berkontribusi melalui pull request.

📜 Lisensi

Proyek ini dibuat untuk tujuan pembelajaran dan bersifat open-source. Anda dapat menggunakannya sesuai kebutuhan.

<h2>Nikmati penggunaan WinterHold – Sistem Manajemen Perpustakaan terbaik untuk Anda! 📚❄️</h2>
