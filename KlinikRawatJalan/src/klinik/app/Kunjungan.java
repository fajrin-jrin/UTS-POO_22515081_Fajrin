package klinik.app;

/**
 * Kunjungan: peristiwa seorang pasien diperiksa oleh seorang dokter.
 * Class ini menjadi penghubung (asosiasi) antara Pasien dan Dokter.
 */
public class Kunjungan {

    public static final String STATUS_MENUNGGU = "MENUNGGU";
    public static final String STATUS_SELESAI = "SELESAI";
    public static final String STATUS_BATAL = "BATAL";

    private String idKunjungan;
    private Pasien pasien;
    private Dokter dokter;
    private double biaya;
    private String status;

    /** Konstruktor default: belum terhubung ke pasien dan dokter, status MENUNGGU. */
    public Kunjungan() {
        this.idKunjungan = "-";
        this.pasien = null;
        this.dokter = null;
        this.biaya = 0;
        this.status = STATUS_MENUNGGU;
    }

    /** Konstruktor berparameter: menautkan pasien dan dokter, status awal MENUNGGU. */
    public Kunjungan(String idKunjungan, Pasien pasien, Dokter dokter) {
        this();
        setIdKunjungan(idKunjungan);
        setPasien(pasien);
        setDokter(dokter);
    }

    // ---------- getter ----------

    public String getIdKunjungan() {
        return idKunjungan;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public double getBiaya() {
        return biaya;
    }

    public String getStatus() {
        return status;
    }

    // ---------- setter (dengan validasi) ----------

    public void setIdKunjungan(String idKunjungan) {
        if (idKunjungan == null || idKunjungan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID kunjungan tidak boleh kosong.");
        }
        this.idKunjungan = idKunjungan.trim().toUpperCase();
    }

    public void setPasien(Pasien pasien) {
        if (pasien == null) {
            throw new IllegalArgumentException("Pasien pada kunjungan tidak boleh kosong.");
        }
        this.pasien = pasien;
    }

    public void setDokter(Dokter dokter) {
        if (dokter == null) {
            throw new IllegalArgumentException("Dokter pada kunjungan tidak boleh kosong.");
        }
        this.dokter = dokter;
    }

    public void setBiaya(double biaya) {
        if (biaya < 0) {
            throw new IllegalArgumentException("Biaya tidak boleh negatif.");
        }
        this.biaya = biaya;
    }

    public void setStatus(String status) {
        String baru = (status == null) ? "" : status.trim().toUpperCase();
        if (!baru.equals(STATUS_MENUNGGU) && !baru.equals(STATUS_SELESAI)
                && !baru.equals(STATUS_BATAL)) {
            throw new IllegalArgumentException("Status tidak dikenal. Gunakan "
                    + STATUS_MENUNGGU + ", " + STATUS_SELESAI + ", atau " + STATUS_BATAL + ".");
        }
        this.status = baru;
    }

    // ---------- method perilaku ----------

    /**
     * Menetapkan biaya kunjungan berdasarkan tarif, lalu mengembalikannya.
     * Aturan sederhana: biaya sama dengan tarif; tarif negatif ditolak.
     */
    public double hitungBiaya(double tarif) {
        if (tarif < 0) {
            throw new IllegalArgumentException("Tarif tidak boleh negatif.");
        }
        setBiaya(tarif);
        return this.biaya;
    }

    /** Mengubah status kunjungan; nilai yang tidak dikenal ditolak oleh setter. */
    public void ubahStatus(String statusBaru) {
        setStatus(statusBaru);
    }

    /**
     * Menampilkan hanya data yang relevan bagi pengguna (abstraksi):
     * detail internal objek pasien dan dokter tidak dibuka seluruhnya.
     */
    public void tampilkanInfo() {
        String namaPasien = (pasien == null) ? "-" : pasien.getNama();
        String namaDokter = (dokter == null) ? "-" : dokter.getNama();
        System.out.printf("Kunjungan %s | Pasien: %s | Dokter: %s | Biaya: Rp%,.0f | Status: %s%n",
                idKunjungan, namaPasien, namaDokter, biaya, status);
    }
}