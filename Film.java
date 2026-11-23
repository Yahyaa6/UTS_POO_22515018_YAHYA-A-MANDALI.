package bioskop.model;

/** Class Film: data film yang diputar di bioskop. */
public class Film {
    private String kode;
    private String judul;
    private int durasiMenit;
    private double rating;
    private boolean sedangTayang;

    // konstruktor default
    public Film() {
        this("F000", "Tanpa Judul", 90, 0);
    }

    // konstruktor berparameter
    public Film(String kode, String judul, int durasiMenit, double rating) {
        setKode(kode);
        setJudul(judul);
        setDurasiMenit(durasiMenit);
        setRating(rating);
        this.sedangTayang = true;
    }

    public void ubahStatusTayang(boolean tayang) {
        this.sedangTayang = tayang;
    }

    // contoh: 135 menit jadi "2 jam 15 menit"
    public String formatDurasi() {
        int jam = durasiMenit / 60;
        int menit = durasiMenit % 60;
        if (jam == 0) {
            return menit + " menit";
        }
        if (menit == 0) {
            return jam + " jam";
        }
        return jam + " jam " + menit + " menit";
    }

    public void tampilkanInfo() {
        String status = "Turun layar";
        if (sedangTayang) {
            status = "Tayang";
        }
        System.out.printf("%-6s | %-26s | %-12s | Rating %.1f | %s%n",
                kode, judul, formatDurasi(), rating, status);
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode film tidak boleh kosong.");
        }
        this.kode = kode.trim().toUpperCase();
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul film tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public void setDurasiMenit(int durasiMenit) {
        if (durasiMenit <= 0 || durasiMenit > 300) {
            throw new IllegalArgumentException("Durasi film harus 1-300 menit.");
        }
        this.durasiMenit = durasiMenit;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating film harus 0.0-10.0.");
        }
        this.rating = rating;
    }

    public boolean isSedangTayang() {
        return sedangTayang;
    }
}
