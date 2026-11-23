package bioskop.model;

/** Class Studio: ruang bioskop dengan jumlah kursi terbatas. */
public class Studio {
    private String kode;
    private String nama;
    private int kapasitas;
    private double hargaTiket;
    private Film filmTayang;
    private boolean[] kursiTerisi; // kursi nomor 1 ada di indeks 0

    public Studio() {
        this("S0", "Studio Baru", 10, 35000);
    }

    public Studio(String kode, String nama, int kapasitas, double hargaTiket) {
        setKode(kode);
        setNama(nama);
        setKapasitas(kapasitas);
        setHargaTiket(hargaTiket);
        this.filmTayang = null;
    }

    public void pasangFilm(Film film) {
        if (film == null) {
            throw new IllegalArgumentException("Film tidak boleh null.");
        }
        if (!film.isSedangTayang()) {
            throw new IllegalArgumentException("Film '" + film.getJudul() + "' sudah turun layar.");
        }
        this.filmTayang = film;
    }

    public boolean isKursiTerisi(int nomor) {
        cekNomor(nomor);
        return kursiTerisi[nomor - 1];
    }

    public void pesanKursi(int nomor) {
        cekNomor(nomor);
        // aturan bisnis: kursi yang sudah terisi tidak boleh dipesan lagi
        if (kursiTerisi[nomor - 1]) {
            throw new IllegalStateException("Kursi " + nomor + " di " + nama + " sudah dipesan.");
        }
        kursiTerisi[nomor - 1] = true;
    }

    public int kursiTersedia() {
        int kosong = 0;
        for (int i = 0; i < kapasitas; i++) {
            if (!kursiTerisi[i]) {
                kosong++;
            }
        }
        return kosong;
    }

    public void tampilkanDenah() {
        System.out.println("Denah " + nama + " ([XX] = terisi):");
        for (int i = 0; i < kapasitas; i++) {
            if (kursiTerisi[i]) {
                System.out.print("[XX] ");
            } else {
                System.out.print(String.format("[%02d] ", i + 1));
            }
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    // nomor kursi harus 1 sampai kapasitas
    private void cekNomor(int nomor) {
        if (nomor < 1 || nomor > kapasitas) {
            throw new IllegalArgumentException("Nomor kursi harus 1-" + kapasitas + " (kapasitas studio).");
        }
    }

    public void tampilkanInfo() {
        String namaFilm = "(belum ada)";
        if (filmTayang != null) {
            namaFilm = filmTayang.getJudul();
        }
        System.out.printf("%-4s | %-10s | Kursi %2d (sisa %2d) | Rp%,.0f | Film: %s%n",
                kode, nama, kapasitas, kursiTersedia(), hargaTiket, namaFilm);
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode studio tidak boleh kosong.");
        }
        this.kode = kode.trim().toUpperCase();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama studio tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        if (kapasitas < 1 || kapasitas > 100) {
            throw new IllegalArgumentException("Kapasitas studio harus 1-100 kursi.");
        }
        this.kapasitas = kapasitas;
        this.kursiTerisi = new boolean[kapasitas]; // denah dibuat ulang
    }

    public double getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(double hargaTiket) {
        if (hargaTiket <= 0) {
            throw new IllegalArgumentException("Harga tiket harus lebih dari 0.");
        }
        this.hargaTiket = hargaTiket;
    }

    public Film getFilmTayang() {
        return filmTayang;
    }
}
