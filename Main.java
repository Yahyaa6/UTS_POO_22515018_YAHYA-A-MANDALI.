package bioskop.app;

import bioskop.model.Bioskop;
import bioskop.model.Film;
import bioskop.model.Studio;
import bioskop.model.Tiket;
import java.util.List;
import java.util.Scanner;

/**
 * Menu console Sistem Manajemen Bioskop Mini.
 * Argumen opsional: --demo (isi data contoh), --echo (cetak input, untuk pengujian).
 */
public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static boolean echoInput = false;
    private static final Bioskop BIOSKOP = new Bioskop("Cinema Nusantara 22515018", "Jl. Merdeka No. 1");

    public static void main(String[] args) {
        for (String a : args) {
            if (a.equals("--demo")) muatDataDemo();
            if (a.equals("--echo")) echoInput = true;
        }
        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            int pilih = bacaInt("Pilih menu: ");
            try {
                if (pilih == 1) {
                    menuFilm();
                } else if (pilih == 2) {
                    menuStudio();
                } else if (pilih == 3) {
                    menuPesanTiket();
                } else if (pilih == 4) {
                    menuCari();
                } else if (pilih == 5) {
                    menuAntrean();
                } else if (pilih == 6) {
                    menuLaporan();
                } else if (pilih == 7) {
                    jalan = false;
                } else {
                    System.out.println("[ERROR] Menu tidak tersedia (pilih 1-7).");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // kalau ada aturan yang dilanggar, tampilkan pesan lalu kembali ke menu
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        System.out.println("Terima kasih. Program selesai.");
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== " + BIOSKOP.getNama() + " ===");
        System.out.println("1. Kelola Film (tambah & tampilkan)");
        System.out.println("2. Kelola Studio (tambah & tampilkan)");
        System.out.println("3. Pesan Tiket (masuk antrean)");
        System.out.println("4. Cari Data (film / tiket)");
        System.out.println("5. Antrean Loket (ADT Queue)");
        System.out.println("6. Laporan Ringkasan");
        System.out.println("7. Keluar");
    }

    // ===== Menu 1 =====
    private static void menuFilm() {
        System.out.println("-- Tambah Film --");
        String kode = bacaString("Kode film      : ");
        String judul = bacaString("Judul film     : ");
        int durasi = bacaInt("Durasi (menit)  : ");
        double rating = bacaDouble("Rating (0-10)   : ");
        BIOSKOP.tambahFilm(new Film(kode, judul, durasi, rating));
        System.out.println("[OK] Film ditambahkan.");
        System.out.println("-- Daftar Film --");
        for (Film f : BIOSKOP.getDaftarFilm()) f.tampilkanInfo();
    }

    // ===== Menu 2 =====
    private static void menuStudio() {
        System.out.println("-- Tambah Studio --");
        String kode = bacaString("Kode studio          : ");
        String nama = bacaString("Nama studio          : ");
        int kap = bacaInt("Kapasitas kursi (1-100): ");
        double harga = bacaDouble("Harga tiket (Rp)       : ");
        Studio s = new Studio(kode, nama, kap, harga);
        String kodeFilm = bacaString("Kode film yang diputar (ketik - untuk kosong): ");
        if (!kodeFilm.equals("-")) {
            Film f = BIOSKOP.cariFilm(kodeFilm);
            if (f == null) throw new IllegalArgumentException("Film '" + kodeFilm + "' tidak ditemukan.");
            s.pasangFilm(f);
        }
        BIOSKOP.tambahStudio(s);
        System.out.println("[OK] Studio ditambahkan.");
        System.out.println("-- Daftar Studio --");
        for (Studio x : BIOSKOP.getDaftarStudio()) x.tampilkanInfo();
    }

    // ===== Menu 3 (enqueue ke antrean) =====
    private static void menuPesanTiket() {
        System.out.println("-- Pesan Tiket --");
        for (Studio x : BIOSKOP.getDaftarStudio()) x.tampilkanInfo();
        String kodeStudio = bacaString("Kode studio    : ");
        String nama = bacaString("Nama penonton  : ");
        int kursi = bacaInt("Nomor kursi    : ");
        Tiket t = BIOSKOP.buatPesanan(nama, kodeStudio, kursi);
        System.out.println("[OK] Pesanan masuk antrean (enqueue). Posisi antrean: " + BIOSKOP.getAntrean().size());
        t.tampilkanInfo();
    }

    // ===== Menu 4 =====
    private static void menuCari() {
        System.out.println("-- Cari Data --");
        System.out.println("1. Film (kode)  2. Film (judul)  3. Tiket (kode)");
        int j = bacaInt("Pilih: ");
        String kunci = bacaString("Kata kunci: ");
        switch (j) {
            case 1:
                Film f = BIOSKOP.cariFilm(kunci);
                if (f == null) System.out.println("[INFO] Film tidak ditemukan.");
                else f.tampilkanInfo();
                break;
            case 2:
                List<Film> hasil = BIOSKOP.cariFilm(kunci, true);
                if (hasil.isEmpty()) System.out.println("[INFO] Film tidak ditemukan.");
                for (Film x : hasil) x.tampilkanInfo();
                break;
            case 3:
                Tiket t = BIOSKOP.cariTiket(kunci);
                if (t == null) System.out.println("[INFO] Tiket tidak ditemukan.");
                else t.tampilkanInfo();
                break;
            default:
                System.out.println("[ERROR] Pilihan pencarian tidak valid.");
        }
    }

    // ===== Menu 5 (operasi antrean) =====
    private static void menuAntrean() {
        System.out.println("-- Antrean Loket --");
        System.out.println("1. Proses tiket terdepan (dequeue)  2. Lihat terdepan (front)");
        System.out.println("3. Tampilkan antrean  4. Status (isEmpty/isFull/size)");
        int p = bacaInt("Pilih: ");
        switch (p) {
            case 1:
                Tiket t = BIOSKOP.prosesAntrean();
                System.out.println("[OK] Tiket diproses & diterbitkan (dequeue):");
                t.tampilkanInfo();
                break;
            case 2:
                System.out.println("Terdepan (front):");
                BIOSKOP.getAntrean().front().tampilkanInfo();
                break;
            case 3:
                BIOSKOP.getAntrean().tampilkan();
                break;
            case 4:
                System.out.println("isEmpty = " + BIOSKOP.getAntrean().isEmpty()
                        + ", isFull = " + BIOSKOP.getAntrean().isFull()
                        + ", size = " + BIOSKOP.getAntrean().size()
                        + "/" + BIOSKOP.getAntrean().kapasitas());
                break;
            default:
                System.out.println("[ERROR] Pilihan antrean tidak valid.");
        }
    }

    // ===== Menu 6 =====
    private static void menuLaporan() {
        System.out.println("-- Laporan Ringkasan --");
        System.out.println("Jumlah film            : " + BIOSKOP.getDaftarFilm().size());
        System.out.println("Jumlah studio          : " + BIOSKOP.getDaftarStudio().size());
        System.out.println("Tiket menunggu (antre) : " + BIOSKOP.getAntrean().size());
        System.out.println("Tiket terbit           : " + BIOSKOP.jumlahTiketTerbit());
        System.out.printf("Total pendapatan       : Rp%,.0f%n", BIOSKOP.hitungPendapatan());
    }

    // ===== Data demo =====
    private static void muatDataDemo() {
        Film f1 = new Film("F01", "Laskar Pelangi Reborn", 125, 8.2);
        Film f2 = new Film("F02", "Samudra Biru", 98, 7.5);
        BIOSKOP.tambahFilm(f1);
        BIOSKOP.tambahFilm(f2);
        Studio s1 = new Studio("S1", "Studio 1", 10, 40000);
        s1.pasangFilm(f1);
        Studio s2 = new Studio("S2", "Studio 2", 10, 35000);
        BIOSKOP.tambahStudio(s1);
        BIOSKOP.tambahStudio(s2); // S2 sengaja belum diberi film
    }

    // ===== Baca input (diulang kalau salah) =====
    private static String bacaString(String prompt) {
        System.out.print(prompt);
        return bacaBaris();
    }

    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = bacaBaris();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan bilangan bulat yang valid.");
            }
        }
    }

    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = bacaBaris().replace(',', '.');
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan angka yang valid.");
            }
        }
    }

    private static String bacaBaris() {
        String s = SC.nextLine().trim();
        if (echoInput) System.out.println(s);
        return s;
    }
}
