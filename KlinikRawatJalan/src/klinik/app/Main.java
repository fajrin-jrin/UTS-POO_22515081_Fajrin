package klinik.app;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Antarmuka console aplikasi Klinik Rawat Jalan.
 * Class ini hanya mengurus input dan output; aturan bisnis ada di class model.
 * Input yang salah (huruf pada kolom angka, nilai kosong) tidak membuat program berhenti.
 */
public class Main {

    private Klinik klinik;
    private Scanner scanner;

    public Main() {
        this.klinik = new Klinik("Klinik Sehat Sentosa");
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Main().jalankan();
    }

    /** Perulangan menu utama sampai pengguna memilih keluar atau input berakhir. */
    private void jalankan() {
        boolean berjalan = true;
        try {
            while (berjalan) {
                tampilkanMenu();
                int pilihan = bacaInt("Pilih menu: ");
                switch (pilihan) {
                    case 1: inputPasien(); break;
                    case 2: klinik.getDaftarPasien().tampilkan(); break;
                    case 3: inputDokter(); break;
                    case 4: tampilkanSemuaDokter(); break;
                    case 5: inputKunjungan(); break;
                    case 6: cariPasien(); break;
                    case 7: hapusPasien(); break;
                    case 8: tampilkanIsiAdt(); break;
                    case 9: klinik.tampilkanRingkasan(); break;
                    case 10: mulaiHariBaru(); break;
                    case 11: ubahStatusKunjungan(); break;
                    case 0:
                        System.out.println("Terima kasih. Program selesai.");
                        berjalan = false;
                        break;
                    default:
                        System.out.println("Menu tidak dikenal. Pilih angka 0-11.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Input berakhir. Program ditutup.");
        }
    }

    private void tampilkanMenu() {
        System.out.println();
        System.out.println("===== " + klinik.getNamaKlinik().toUpperCase() + " =====");
        System.out.println(" 1. Tambah pasien");
        System.out.println(" 2. Tampilkan semua pasien");
        System.out.println(" 3. Tambah dokter");
        System.out.println(" 4. Tampilkan semua dokter");
        System.out.println(" 5. Buat kunjungan");
        System.out.println(" 6. Cari pasien berdasarkan ID");
        System.out.println(" 7. Hapus pasien");
        System.out.println(" 8. Lihat isi ADT (size dan kapasitas)");
        System.out.println(" 9. Ringkasan klinik");
        System.out.println("10. Mulai hari baru (reset kuota dokter)");
        System.out.println("11. Ubah status kunjungan");
        System.out.println(" 0. Keluar");
    }

    // ---------- fitur menu ----------

    private void inputPasien() {
        System.out.println("--- Tambah pasien ---");
        PasienSortedList adt = klinik.getDaftarPasien();
        if (adt.isFull()) {
            System.out.println("Gagal: daftar pasien penuh (kapasitas " + adt.getCapacity() + ").");
            return;
        }
        String id = bacaTeks("ID pasien (contoh P001): ");
        String nama = bacaTeks("Nama: ");
        int umur = bacaInt("Umur (0-120): ");
        String gejala = bacaTeks("Gejala: ");
        try {
            Pasien p = new Pasien(id, nama, umur, gejala);
            if (klinik.tambahPasien(p)) {
                System.out.println("Pasien berhasil ditambahkan.");
            } else {
                System.out.println("Gagal: ID pasien " + p.getIdPasien() + " sudah terdaftar.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void inputDokter() {
        System.out.println("--- Tambah dokter ---");
        String id = bacaTeks("ID dokter (contoh D001): ");
        String nama = bacaTeks("Nama: ");
        String spesialis = bacaTeks("Spesialis: ");
        try {
            Dokter d = new Dokter(id, nama, spesialis);
            if (klinik.tambahDokter(d)) {
                System.out.println("Dokter berhasil ditambahkan.");
            } else {
                System.out.println("Gagal: ID dokter " + d.getIdDokter() + " sudah terdaftar.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void tampilkanSemuaDokter() {
        List<Dokter> dokter = klinik.getDaftarDokter();
        if (dokter.isEmpty()) {
            System.out.println("Belum ada dokter.");
            return;
        }
        for (Dokter d : dokter) {
            d.tampilkanInfo();
        }
    }

    private void inputKunjungan() {
        System.out.println("--- Buat kunjungan ---");
        if (klinik.getDaftarPasien().isEmpty()) {
            System.out.println("Gagal: belum ada pasien terdaftar.");
            return;
        }
        if (klinik.getDaftarDokter().isEmpty()) {
            System.out.println("Gagal: belum ada dokter terdaftar.");
            return;
        }
        Pasien pasien = klinik.cariPasien(bacaTeks("ID pasien: "));
        if (pasien == null) {
            System.out.println("Gagal: pasien tidak ditemukan.");
            return;
        }
        Dokter dokter = klinik.cariDokter(bacaTeks("ID dokter: "));
        if (dokter == null) {
            System.out.println("Gagal: dokter tidak ditemukan.");
            return;
        }
        try {
            Kunjungan k = klinik.buatKunjungan(pasien, dokter);
            System.out.println("Kunjungan berhasil dibuat.");
            k.tampilkanInfo();
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void cariPasien() {
        System.out.println("--- Cari pasien ---");
        Pasien p = klinik.cariPasien(bacaTeks("ID pasien: "));
        if (p == null) {
            System.out.println("Pasien tidak ditemukan.");
        } else {
            p.tampilkanInfo();
        }
    }

    private void hapusPasien() {
        System.out.println("--- Hapus pasien ---");
        if (klinik.getDaftarPasien().isEmpty()) {
            System.out.println("Gagal: daftar pasien kosong.");
            return;
        }
        String id = bacaTeks("ID pasien: ");
        if (klinik.hapusPasien(id)) {
            System.out.println("Pasien berhasil dihapus.");
        } else {
            System.out.println("Gagal: pasien tidak ditemukan.");
        }
    }

    private void tampilkanIsiAdt() {
        PasienSortedList adt = klinik.getDaftarPasien();
        System.out.println("--- Isi ADT PasienSortedList ---");
        System.out.println("Size     : " + adt.size());
        System.out.println("Kapasitas: " + adt.getCapacity());
        System.out.println("Penuh    : " + (adt.isFull() ? "ya" : "tidak"));
        System.out.println("Kosong   : " + (adt.isEmpty() ? "ya" : "tidak"));
        adt.tampilkan();
    }

    private void mulaiHariBaru() {
        klinik.mulaiHariBaru();
        System.out.println("Hari baru dimulai. Kuota kunjungan semua dokter direset.");
    }

    private void ubahStatusKunjungan() {
        System.out.println("--- Ubah status kunjungan ---");
        Kunjungan k = klinik.cariKunjungan(bacaTeks("ID kunjungan (contoh K001): "));
        if (k == null) {
            System.out.println("Gagal: kunjungan tidak ditemukan.");
            return;
        }
        String status = bacaTeks("Status baru (MENUNGGU/SELESAI/BATAL): ");
        try {
            k.ubahStatus(status);
            System.out.println("Status berhasil diubah.");
            k.tampilkanInfo();
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // ---------- pembaca input yang aman ----------

    private String bacaBaris(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /** Mengulang sampai pengguna memasukkan teks yang tidak kosong. */
    private String bacaTeks(String prompt) {
        while (true) {
            String teks = bacaBaris(prompt).trim();
            if (!teks.isEmpty()) {
                return teks;
            }
            System.out.println("Input tidak boleh kosong. Coba lagi.");
        }
    }

    /** Mengulang sampai pengguna memasukkan bilangan bulat yang valid. */
    private int bacaInt(String prompt) {
        while (true) {
            String teks = bacaBaris(prompt).trim();
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }
}