package bioskop.adt;

import bioskop.model.Tiket;

/**
 * ADT Queue (FIFO) untuk antrean tiket di loket.
 * Pakai array biasa dengan kapasitas tetap; elemen paling depan selalu di indeks 0.
 */
public class AntreanTiket {
    public static final int KAPASITAS_DEFAULT = 5;

    private Tiket[] data;
    private int jumlah;

    public AntreanTiket() {
        this(KAPASITAS_DEFAULT);
    }

    public AntreanTiket(int kapasitas) {
        if (kapasitas < 1) {
            throw new IllegalArgumentException("Kapasitas antrean minimal 1.");
        }
        this.data = new Tiket[kapasitas];
        this.jumlah = 0;
    }

    // masukkan tiket ke belakang antrean
    public void enqueue(Tiket tiket) {
        if (tiket == null) {
            throw new IllegalArgumentException("Tiket tidak boleh null.");
        }
        if (isFull()) {
            throw new IllegalStateException("Antrean penuh (kapasitas " + data.length + "). Proses antrean dahulu.");
        }
        data[jumlah] = tiket;
        jumlah++;
    }

    // ambil tiket paling depan, sisanya digeser maju satu langkah
    public Tiket dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Antrean kosong. Tidak ada tiket untuk diproses.");
        }
        Tiket depan = data[0];
        for (int i = 0; i < jumlah - 1; i++) {
            data[i] = data[i + 1];
        }
        data[jumlah - 1] = null;
        jumlah--;
        return depan;
    }

    // lihat tiket paling depan tanpa mengeluarkannya
    public Tiket front() {
        if (isEmpty()) {
            throw new IllegalStateException("Antrean kosong. Tidak ada tiket di depan.");
        }
        return data[0];
    }

    public boolean isEmpty() {
        return jumlah == 0;
    }

    public boolean isFull() {
        return jumlah == data.length;
    }

    public int size() {
        return jumlah;
    }

    public int kapasitas() {
        return data.length;
    }

    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("(antrean kosong)");
            return;
        }
        System.out.println("Isi antrean (depan ke belakang), " + jumlah + "/" + data.length + ":");
        for (int i = 0; i < jumlah; i++) {
            System.out.print((i + 1) + ". ");
            data[i].tampilkanInfo();
        }
    }
}
