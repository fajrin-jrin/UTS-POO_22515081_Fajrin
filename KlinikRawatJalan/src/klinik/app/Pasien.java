package klinik.app;

/**
 * Pasien klinik rawat jalan.
 * Semua atribut bersifat private. Perubahan nilai hanya lewat setter yang
 * memvalidasi data, termasuk aturan bisnis umur pasien 0-120 tahun.
 */
public class Pasien {

    public static final int UMUR_MIN = 0;
    public static final int UMUR_MAX = 120;
    public static final int UMUR_LANSIA = 60;

    private String idPasien;
    private String nama;
    private int umur;
    private String gejala;

    /** Konstruktor default: mengisi nilai awal yang aman. */
    public Pasien() {
        this.idPasien = "-";
        this.nama = "-";
        this.umur = UMUR_MIN;
        this.gejala = "-";
    }

    /**
     * Konstruktor berparameter. Nilai diteruskan ke setter agar validasi
     * (terutama umur 0-120) selalu berlaku sejak objek dibuat.
     */
    public Pasien(String idPasien, String nama, int umur, String gejala) {
        this(); // mulai dari nilai awal, lalu isi lewat setter
        setIdPasien(idPasien);
        setNama(nama);
        setUmur(umur);
        setGejala(gejala);
    }

    // ---------- getter ----------

    public String getIdPasien() {
        return idPasien;
    }

    public String getNama() {
        return nama;
    }

    public int getUmur() {
        return umur;
    }

    public String getGejala() {
        return gejala;
    }

    // ---------- setter (dengan validasi) ----------

    public void setIdPasien(String idPasien) {
        // ID dinormalisasi ke huruf besar agar urutan di ADT konsisten
        this.idPasien = bersihkan(idPasien, "ID pasien").toUpperCase();
    }

    public void setNama(String nama) {
        this.nama = bersihkan(nama, "Nama pasien");
    }

    /** Validasi aturan bisnis: umur pasien harus berada pada rentang 0-120 tahun. */
    public void setUmur(int umur) {
        if (umur < UMUR_MIN || umur > UMUR_MAX) {
            throw new IllegalArgumentException("Umur pasien harus antara " + UMUR_MIN
                    + " dan " + UMUR_MAX + " tahun (input: " + umur + ").");
        }
        this.umur = umur;
    }

    public void setGejala(String gejala) {
        this.gejala = bersihkan(gejala, "Gejala");
    }

    // ---------- method perilaku ----------

    /** Mencetak data pasien dalam satu baris. */
    public void tampilkanInfo() {
        System.out.printf("ID: %s | Nama: %s | Umur: %d tahun | Gejala: %s%n",
                idPasien, nama, umur, gejala);
    }

    /** @return true jika umur pasien termasuk lansia (60 tahun ke atas) */
    public boolean isLansia() {
        return umur >= UMUR_LANSIA;
    }

    /** Mengganti gejala pasien; nilai kosong ditolak oleh setter. */
    public void perbaruiGejala(String gejalaBaru) {
        setGejala(gejalaBaru);
    }

    @Override
    public String toString() {
        return idPasien + " - " + nama;
    }

    private static String bersihkan(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
        return nilai.trim();
    }
}