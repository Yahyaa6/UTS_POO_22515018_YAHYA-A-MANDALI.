package bioskop.model;

/** Class Tiket: pesanan satu kursi untuk satu film di satu studio. */
public class Tiket {
    public static final double BIAYA_LAYANAN = 3000;

    private String kodeTiket;
    private String namaPenonton;
    private Film film;
    private Studio studio;
    private int nomorKursi;
    private double harga;
    private boolean terbit; // false = masih menunggu di antrean

    public Tiket() {
        this.kodeTiket = "T000";
        this.namaPenonton = "Tanpa Nama";
        this.harga = 0;
        this.terbit = false;
    }

    public Tiket(String kodeTiket, String namaPenonton, Film film, Studio studio, int nomorKursi) {
        setKodeTiket(kodeTiket);
        setNamaPenonton(namaPenonton);
        setFilm(film);
        setStudio(studio);
        setNomorKursi(nomorKursi);
        this.harga = studio.getHargaTiket();
        this.terbit = false;
    }

    public double hitungTotalBayar() {
        return harga + BIAYA_LAYANAN;
    }

    public void terbitkan() {
        this.terbit = true;
    }

    public void tampilkanInfo() {
        String status = "MENUNGGU";
        if (terbit) {
            status = "TERBIT";
        }
        System.out.printf("%-5s | %-10s | %-22s | %-9s | Kursi %2d | Rp%,.0f | %s%n",
                kodeTiket, namaPenonton, film.getJudul(), studio.getNama(),
                nomorKursi, hitungTotalBayar(), status);
    }

    public String getKodeTiket() {
        return kodeTiket;
    }

    public void setKodeTiket(String kodeTiket) {
        if (kodeTiket == null || kodeTiket.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode tiket tidak boleh kosong.");
        }
        this.kodeTiket = kodeTiket.trim().toUpperCase();
    }

    public String getNamaPenonton() {
        return namaPenonton;
    }

    public void setNamaPenonton(String namaPenonton) {
        if (namaPenonton == null || namaPenonton.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama penonton tidak boleh kosong.");
        }
        this.namaPenonton = namaPenonton.trim();
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        if (film == null) {
            throw new IllegalArgumentException("Film tidak boleh null.");
        }
        this.film = film;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        if (studio == null) {
            throw new IllegalArgumentException("Studio tidak boleh null.");
        }
        this.studio = studio;
    }

    public int getNomorKursi() {
        return nomorKursi;
    }

    public void setNomorKursi(int nomorKursi) {
        if (nomorKursi < 1) {
            throw new IllegalArgumentException("Nomor kursi harus bilangan positif.");
        }
        this.nomorKursi = nomorKursi;
    }

    public double getHarga() {
        return harga;
    }

    public boolean isTerbit() {
        return terbit;
    }
}
