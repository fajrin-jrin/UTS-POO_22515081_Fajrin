package klinik.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klinik: mengoordinasikan data pasien, dokter, dan kunjungan serta menegakkan
 * aturan bisnis (satu dokter maksimal 5 kunjungan per hari).
 * Pasien disimpan di ADT PasienSortedList; dokter dan kunjungan di ArrayList.
 */
public class Klinik {

    public static final int KAPASITAS_PASIEN = 10;
    public static final double TARIF_KONSULTASI = 50000.0;

    private String namaKlinik;
    private ArrayList<Dokter> daftarDokter;
    private PasienSortedList daftarPasien;
    private ArrayList<Kunjungan> daftarKunjungan;
    private int urutanKunjungan;

    /** Konstruktor default: memakai nama klinik bawaan. */
    public Klinik() {
        this("Klinik Rawat Jalan");
    }

    /** Konstruktor berparameter: membuat daftar kosong, ADT berkapasitas KAPASITAS_PASIEN. */
    public Klinik(String namaKlinik) {
        setNamaKlinik(namaKlinik);
        this.daftarDokter = new ArrayList<>();
        this.daftarPasien = new PasienSortedList(KAPASITAS_PASIEN);
        this.daftarKunjungan = new ArrayList<>();
        this.urutanKunjungan = 0;
    }

    // ---------- getter dan setter ----------

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        if (namaKlinik == null || namaKlinik.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama klinik tidak boleh kosong.");
        }
        this.namaKlinik = namaKlinik.trim();
    }

    public PasienSortedList getDaftarPasien() {
        return daftarPasien;
    }

    /** Daftar dokter hanya bisa dibaca dari luar; penambahan lewat tambahDokter. */
    public List<Dokter> getDaftarDokter() {
        return Collections.unmodifiableList(daftarDokter);
    }

    /** Daftar kunjungan hanya bisa dibaca dari luar; penambahan lewat buatKunjungan. */
    public List<Kunjungan> getDaftarKunjungan() {
        return Collections.unmodifiableList(daftarKunjungan);
    }

    // ---------- pengelolaan pasien ----------

    /** @return false jika pasien null, ADT penuh, atau idPasien sudah terdaftar */
    public boolean tambahPasien(Pasien p) {
        return daftarPasien.insert(p);
    }

    /** @return pasien dengan id tersebut, atau null jika tidak ditemukan */
    public Pasien cariPasien(String idPasien) {
        int indeks = daftarPasien.search(idPasien);
        return (indeks == -1) ? null : daftarPasien.get(indeks);
    }

    /** @return false jika idPasien tidak ditemukan */
    public boolean hapusPasien(String idPasien) {
        return daftarPasien.remove(idPasien);
    }

    // ---------- pengelolaan dokter ----------

    /** @return false jika dokter null atau idDokter sudah terdaftar */
    public boolean tambahDokter(Dokter d) {
        if (d == null || cariDokter(d.getIdDokter()) != null) {
            return false;
        }
        daftarDokter.add(d);
        return true;
    }

    /** @return dokter dengan id tersebut, atau null jika tidak ditemukan */
    public Dokter cariDokter(String idDokter) {
        if (idDokter == null) {
            return null;
        }
        for (Dokter d : daftarDokter) {
            if (d.getIdDokter().equalsIgnoreCase(idDokter.trim())) {
                return d;
            }
        }
        return null;
    }

    // ---------- pengelolaan kunjungan ----------

    /**
     * Membuat kunjungan baru dan mencatatnya.
     * Aturan bisnis: dokter yang sudah menerima 5 kunjungan hari ini menolak kunjungan baru.
     * @throws IllegalArgumentException jika pasien atau dokter null
     * @throws IllegalStateException jika dokter sudah mencapai batas harian
     */
    public Kunjungan buatKunjungan(Pasien p, Dokter d) {
        if (p == null || d == null) {
            throw new IllegalArgumentException("Pasien dan dokter harus dipilih.");
        }
        if (!d.bisaMenerimaKunjungan()) {
            throw new IllegalStateException("Dokter " + d.getNama() + " sudah mencapai batas "
                    + Dokter.MAKS_KUNJUNGAN_PER_HARI + " kunjungan hari ini.");
        }
        urutanKunjungan++;
        String id = String.format("K%03d", urutanKunjungan);
        Kunjungan k = new Kunjungan(id, p, d);
        k.hitungBiaya(TARIF_KONSULTASI);
        d.tambahKunjungan();
        daftarKunjungan.add(k);
        return k;
    }

    /** @return kunjungan dengan id tersebut, atau null jika tidak ditemukan */
    public Kunjungan cariKunjungan(String idKunjungan) {
        if (idKunjungan == null) {
            return null;
        }
        for (Kunjungan k : daftarKunjungan) {
            if (k.getIdKunjungan().equalsIgnoreCase(idKunjungan.trim())) {
                return k;
            }
        }
        return null;
    }

    /** Simulasi pergantian hari: kuota semua dokter dikembalikan ke 0. */
    public void mulaiHariBaru() {
        for (Dokter d : daftarDokter) {
            d.resetKunjunganHarian();
        }
    }

    // ---------- laporan ----------

    /** Menampilkan jumlah data dan total biaya (kunjungan BATAL tidak dihitung). */
    public void tampilkanRingkasan() {
        double totalBiaya = 0;
        for (Kunjungan k : daftarKunjungan) {
            if (!Kunjungan.STATUS_BATAL.equals(k.getStatus())) {
                totalBiaya += k.getBiaya();
            }
        }
        System.out.println("=== Ringkasan " + namaKlinik + " ===");
        System.out.println("Jumlah pasien    : " + daftarPasien.size() + " dari " + daftarPasien.getCapacity());
        System.out.println("Jumlah dokter    : " + daftarDokter.size());
        System.out.println("Jumlah kunjungan : " + daftarKunjungan.size());
        System.out.printf("Total biaya      : Rp%,.0f%n", totalBiaya);
        for (Kunjungan k : daftarKunjungan) {
            k.tampilkanInfo();
        }
    }
}
