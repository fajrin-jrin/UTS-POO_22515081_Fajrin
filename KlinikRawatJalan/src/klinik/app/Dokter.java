package klinik.app;

/**
 * Dokter klinik rawat jalan.
 * Menyimpan jumlah kunjungan hari ini untuk menegakkan aturan bisnis:
 * satu dokter maksimal 5 kunjungan per hari.
 */
public class Dokter {

    public static final int MAKS_KUNJUNGAN_PER_HARI = 5;

    private String idDokter;
    private String nama;
    private String spesialis;
    private int jumlahKunjunganHariIni;

    /** Konstruktor default: nilai awal aman, jumlah kunjungan 0. */
    public Dokter() {
        this.idDokter = "-";
        this.nama = "-";
        this.spesialis = "-";
        this.jumlahKunjunganHariIni = 0;
    }

    /** Konstruktor berparameter; jumlah kunjungan selalu mulai dari 0. */
    public Dokter(String idDokter, String nama, String spesialis) {
        this();
        setIdDokter(idDokter);
        setNama(nama);
        setSpesialis(spesialis);
    }

    // ---------- getter ----------

    public String getIdDokter() {
        return idDokter;
    }

    public String getNama() {
        return nama;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public int getJumlahKunjunganHariIni() {
        return jumlahKunjunganHariIni;
    }

    // ---------- setter (dengan validasi) ----------

    public void setIdDokter(String idDokter) {
        this.idDokter = bersihkan(idDokter, "ID dokter").toUpperCase();
    }

    public void setNama(String nama) {
        this.nama = bersihkan(nama, "Nama dokter");
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = bersihkan(spesialis, "Spesialis");
    }

    public void setJumlahKunjunganHariIni(int jumlah) {
        if (jumlah < 0 || jumlah > MAKS_KUNJUNGAN_PER_HARI) {
            throw new IllegalArgumentException("Jumlah kunjungan harian harus antara 0 dan "
                    + MAKS_KUNJUNGAN_PER_HARI + ".");
        }
        this.jumlahKunjunganHariIni = jumlah;
    }

    // ---------- method perilaku ----------

    /** @return true jika dokter masih boleh menerima kunjungan hari ini */
    public boolean bisaMenerimaKunjungan() {
        return jumlahKunjunganHariIni < MAKS_KUNJUNGAN_PER_HARI;
    }

    /**
     * Menambah hitungan kunjungan hari ini.
     * Aturan bisnis: ditolak jika dokter sudah mencapai batas harian.
     */
    public void tambahKunjungan() {
        if (!bisaMenerimaKunjungan()) {
            throw new IllegalStateException("Dokter " + nama + " sudah mencapai batas "
                    + MAKS_KUNJUNGAN_PER_HARI + " kunjungan hari ini.");
        }
        jumlahKunjunganHariIni++;
    }

    /** Mengembalikan hitungan ke 0 saat hari berganti. */
    public void resetKunjunganHarian() {
        this.jumlahKunjunganHariIni = 0;
    }

    /** Mencetak data dokter dalam satu baris. */
    public void tampilkanInfo() {
        System.out.printf("ID: %s | Nama: %s | Spesialis: %s | Kunjungan hari ini: %d/%d%n",
                idDokter, nama, spesialis, jumlahKunjunganHariIni, MAKS_KUNJUNGAN_PER_HARI);
    }

    @Override
    public String toString() {
        return idDokter + " - " + nama;
    }

    private static String bersihkan(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
        return nilai.trim();
    }
}