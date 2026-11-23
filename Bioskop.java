package bioskop.model;

import bioskop.adt.AntreanTiket;
import java.util.ArrayList;
import java.util.List;

/** Class Bioskop: mengelola film, studio, tiket, dan antrean loket. */
public class Bioskop {
    private String nama;
    private String alamat;
    private final List<Film> daftarFilm = new ArrayList<>();
    private final List<Studio> daftarStudio = new ArrayList<>();
    private final List<Tiket> daftarTiket = new ArrayList<>();
    private final AntreanTiket antrean;
    private int urutanTiket = 0;

    public Bioskop() {
        this("Bioskop Mini", "-");
    }

    public Bioskop(String nama, String alamat) {
        this(nama, alamat, AntreanTiket.KAPASITAS_DEFAULT);
    }

    public Bioskop(String nama, String alamat, int kapasitasAntrean) {
        setNama(nama);
        setAlamat(alamat);
        this.antrean = new AntreanTiket(kapasitasAntrean);
    }

    // ---- kelola data ----

    public void tambahFilm(Film f) {
        if (cariFilm(f.getKode()) != null)
            throw new IllegalArgumentException("Kode film " + f.getKode() + " sudah dipakai.");
        daftarFilm.add(f);
    }

    public void tambahStudio(Studio s) {
        if (cariStudio(s.getKode()) != null)
            throw new IllegalArgumentException("Kode studio " + s.getKode() + " sudah dipakai.");
        daftarStudio.add(s);
    }

    // cari film berdasarkan kode
    public Film cariFilm(String kode) {
        for (Film f : daftarFilm)
            if (f.getKode().equalsIgnoreCase(kode.trim())) return f;
        return null;
    }

    // cari film berdasarkan sebagian judul (overloading)
    public List<Film> cariFilm(String kataKunci, boolean berdasarkanJudul) {
        List<Film> hasil = new ArrayList<>();
        for (Film f : daftarFilm) {
            String target = berdasarkanJudul ? f.getJudul() : f.getKode();
            if (target.toLowerCase().contains(kataKunci.trim().toLowerCase())) hasil.add(f);
        }
        return hasil;
    }

    public Studio cariStudio(String kode) {
        for (Studio s : daftarStudio)
            if (s.getKode().equalsIgnoreCase(kode.trim())) return s;
        return null;
    }

    public Tiket cariTiket(String kode) {
        for (Tiket t : daftarTiket)
            if (t.getKodeTiket().equalsIgnoreCase(kode.trim())) return t;
        return null;
    }

    // ---- transaksi ----

    // buat pesanan tiket lalu masukkan ke antrean.
    // antrean dicek penuh/tidak SEBELUM kursi dikunci, supaya kursi tidak terkunci sia-sia

    public Tiket buatPesanan(String namaPenonton, String kodeStudio, int nomorKursi) {
        Studio studio = cariStudio(kodeStudio);
        if (studio == null)
            throw new IllegalArgumentException("Studio '" + kodeStudio + "' tidak ditemukan.");
        Film film = studio.getFilmTayang();
        if (film == null)
            throw new IllegalStateException(studio.getNama() + " belum memutar film apa pun.");
        if (antrean.isFull())
            throw new IllegalStateException("Antrean penuh (kapasitas " + antrean.kapasitas() + "). Proses antrean dahulu.");
        Tiket t = new Tiket(String.format("T%03d", urutanTiket + 1), namaPenonton, film, studio, nomorKursi);
        studio.pesanKursi(nomorKursi); // error kalau kursi sudah terisi / di luar kapasitas
        urutanTiket++;
        antrean.enqueue(t);
        daftarTiket.add(t);
        return t;
    }

    // proses tiket paling depan di antrean
    public Tiket prosesAntrean() {
        Tiket t = antrean.dequeue();
        t.terbitkan();
        return t;
    }

    // ---- laporan ----

    public double hitungPendapatan() {
        double total = 0;
        for (Tiket t : daftarTiket)
            if (t.isTerbit()) total += t.hitungTotalBayar();
        return total;
    }

    public int jumlahTiketTerbit() {
        int n = 0;
        for (Tiket t : daftarTiket) if (t.isTerbit()) n++;
        return n;
    }

    // ---- getter & setter ----

    public String getNama() { return nama; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty())
            throw new IllegalArgumentException("Nama bioskop tidak boleh kosong.");
        this.nama = nama.trim();
    }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty())
            throw new IllegalArgumentException("Alamat tidak boleh kosong.");
        this.alamat = alamat.trim();
    }

    public List<Film> getDaftarFilm() { return new ArrayList<>(daftarFilm); }

    public List<Studio> getDaftarStudio() { return new ArrayList<>(daftarStudio); }

    public List<Tiket> getTiketTercatat() { return new ArrayList<>(daftarTiket); }

    public AntreanTiket getAntrean() { return antrean; }
}
