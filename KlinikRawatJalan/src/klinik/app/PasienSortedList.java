package klinik.app;

/**
 * ADT Sorted List (array terurut) untuk menyimpan Pasien.
 * Elemen selalu terurut menaik berdasarkan idPasien. Atribut internal private;
 * pengguna hanya memakai operasi publik. Kondisi penuh, kosong, dan data tidak
 * ditemukan ditangani lewat nilai kembalian, bukan dengan menghentikan program.
 */
public class PasienSortedList {

    private Pasien[] arrayPasien;
    private int size;
    private int capacity;

    /**
     * Membuat list kosong dengan kapasitas tetap.
     * @param capacity kapasitas maksimum, harus lebih dari 0
     */
    public PasienSortedList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Kapasitas harus lebih dari 0.");
        }
        this.capacity = capacity;
        this.arrayPasien = new Pasien[capacity];
        this.size = 0;
    }

    /**
     * Menyisipkan pasien pada posisi yang benar sehingga list tetap terurut.
     * @return false jika pasien null, list penuh, atau idPasien sudah ada
     */
    public boolean insert(Pasien p) {
        if (p == null || isFull()) {
            return false;
        }
        if (search(p.getIdPasien()) != -1) {
            return false; // idPasien harus unik
        }
        // geser elemen yang lebih besar satu langkah ke kanan
        int i = size - 1;
        while (i >= 0 && arrayPasien[i].getIdPasien().compareTo(p.getIdPasien()) > 0) {
            arrayPasien[i + 1] = arrayPasien[i];
            i--;
        }
        arrayPasien[i + 1] = p;
        size++;
        return true;
    }

    /**
     * Menghapus pasien berdasarkan idPasien lalu menggeser elemen sesudahnya ke kiri.
     * @return false jika list kosong atau idPasien tidak ditemukan
     */
    public boolean remove(String idPasien) {
        int indeks = search(idPasien);
        if (indeks == -1) {
            return false;
        }
        for (int i = indeks; i < size - 1; i++) {
            arrayPasien[i] = arrayPasien[i + 1];
        }
        arrayPasien[size - 1] = null;
        size--;
        return true;
    }

    /**
     * Mencari idPasien dengan pencarian biner (list sudah terurut).
     * @return indeks elemen, atau -1 jika tidak ditemukan
     */
    public int search(String idPasien) {
        if (idPasien == null || isEmpty()) {
            return -1;
        }
        String kunci = idPasien.trim().toUpperCase();
        int kiri = 0;
        int kanan = size - 1;
        while (kiri <= kanan) {
            int tengah = (kiri + kanan) / 2;
            int banding = kunci.compareTo(arrayPasien[tengah].getIdPasien());
            if (banding == 0) {
                return tengah;
            } else if (banding < 0) {
                kanan = tengah - 1;
            } else {
                kiri = tengah + 1;
            }
        }
        return -1;
    }

    /**
     * Mengambil elemen pada indeks tertentu.
     * @return pasien, atau null jika indeks di luar rentang 0..size-1
     */
    public Pasien get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return arrayPasien[index];
    }

    /** Mencetak seluruh isi list berurutan; memberi pesan khusus jika kosong. */
    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("Daftar pasien kosong.");
            return;
        }
        System.out.printf("%-3s %-8s %-20s %-6s %s%n", "No", "ID", "Nama", "Umur", "Gejala");
        for (int i = 0; i < size; i++) {
            Pasien p = arrayPasien[i];
            System.out.printf("%-3d %-8s %-20s %-6d %s%n",
                    i + 1, p.getIdPasien(), p.getNama(), p.getUmur(), p.getGejala());
        }
    }

    /** @return jumlah elemen yang tersimpan */
    public int size() {
        return size;
    }

    /** @return kapasitas maksimum list */
    public int getCapacity() {
        return capacity;
    }

    /** @return true jika jumlah elemen sama dengan kapasitas */
    public boolean isFull() {
        return size == capacity;
    }

    /** @return true jika belum ada elemen */
    public boolean isEmpty() {
        return size == 0;
    }
}