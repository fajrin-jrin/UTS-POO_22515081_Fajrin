1. Pasien

Atribut (private)
- idPasien: String
- nama: String
- umur: int
- gejala: String

Method
- Pasien()
- Pasien(idPasien: String, nama: String, umur: int, gejala: String)
- setUmur(umur: int): void : validasi umur 0-120
- tampilkanInfo(): void
- isLansia(): boolean
- perbaruiGejala(gejala: String): void
getter dan setter atribut lain

2. Dokter

Atribut (private)
- idDokter: String
- nama: String
- spesialis: String
- jumlahKunjunganHariIni: int

Method
- Dokter()
- Dokter(idDokter: String, nama: String, spesialis: String)
- bisaMenerimaKunjungan(): boolean : true jika jumlahKunjunganHariIni < 5
- tambahKunjungan(): void
- resetKunjunganHarian(): void
- tampilkanInfo(): void
- getter dan setter atribut lain

3. Kunjungan

Atribut (private)
- idKunjungan: String
- pasien: Pasien
- dokter: Dokter
- biaya: double
- status: String

Method
- Kunjungan()
- Kunjungan(idKunjungan: String, pasien: Pasien, dokter: Dokter)
- hitungBiaya(tarif: double): double
- ubahStatus(status: String): void
- tampilkanInfo(): void
- getter dan setter atribut lain

4. Klinik

Atribut (private)
- namaKlinik: String
- daftarDokter: ArrayList<Dokter>
- daftarPasien: PasienSortedList
- daftarKunjungan: ArrayList<Kunjungan>

Method
- Klinik()
- Klinik(namaKlinik: String)
- tambahPasien(p: Pasien): boolean
- tambahDokter(d: Dokter): void
- buatKunjungan(p: Pasien, d: Dokter): Kunjungan
- cariPasien(idPasien: String): Pasien
- cariDokter(idDokter: String): Dokter
- tampilkanRingkasan(): void

5. PasienSortedList (ADT)

Atribut (private)
- arrayPasien: Pasien[]
- size: int
- capacity: int

Method
- PasienSortedList(capacity: int)
- insert(p: Pasien): boolean : sisip pada posisi yang tepat agar tetap terurut berdasarkan idPasien
- remove(idPasien: String): boolean
- search(idPasien: String): int : mengembalikan index, -1 jika tidak ditemukan
- get(index: int): Pasien
- tampilkan(): void
- size(): int
- isFull(): boolean
- isEmpty(): boolean

6. Main

Atribut (private)
- klinik: Klinik
- scanner: Scanner

Method
- main(args: String[]): void
- tampilkanMenu(): void
- inputPasien(): void
- inputDokter(): void
- inputKunjungan(): void
- cariPasien(): void