package com.example.myapplication;
import java.util.ArrayList;

public class DataBook {
    public static ArrayList<Book> books = generateDummyBooks();

    private static ArrayList<Book> generateDummyBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(R.drawable.laut_bercerita,
                "Laut Bercerita",
                "Leila S. Chudori",
                "3 Agustus 2022",
                "Buku Laut Bercerita menceritakan terkait perilaku kekejaman dan kebengisan yang dirasakan oleh kelompok aktivis mahasiswa di masa Orde Baru. Tidak hanya itu, novel ini pun merenungkan kembali akan hilangnya tiga belas aktivis, bahkan sampai saat ini belum juga ada yang mendapatkan petunjuknya. Buku ini juga bertutur tentang kisah keluarga yang kehilangan, sekumpulan sahabat yang merasakan kekosongan di dada, sekelompok orang yang gemar menyiksa dan lancar berkhianat, dan sejumlah keluarga yang mencari kejelasan makam anaknya.",
                "History",
                false,
                4.6));
        books.add(new Book(R.drawable.layangan_putus,
                "Layangan Putus",
                "Mommy ASF",
                "Februari 2010",
                "Seorang gadis remaja polos yang berasal dari daerah, tumbuh, berkembang, dan menemukan cinta di kota besar yang sangat berbeda dengan iklim daerah asalnya. Mimpi sederhananya menyambung pendidikan dan menyelesaikannya tepat waktu, namun berubah setelah ia mengenal sosok lelaki tangguh Lelaki yang mandiri dan berpendirian keras mengenalkannya dengan dunia baru yang belum pernah ia temui. Dunia yang asyik dan menyenangkan yang berbeda total dengan kehidupan remaja di daerah asalnya.",
                "Drama",
                false,
                4.1));
        books.add(new Book(R.drawable.nebula,
                "Nebula",
                "Tere Liye",
                "2020",
                "Novel ini menceritakan petualangan tiga sahabat yaitu Selena, Mata, dan Task yang memiliki kekuatan di zaman modern. Mereka berpetualang ke berbagai tempat seperti kota Tishri, kampus, dan klan Bulan",
                "Fantasy",
                false,
                4.3));
        books.add(new Book(R.drawable.selena,
                "Selena",
                "Tere Liye",
                "Mei 2020",
                "Selena dan Nebula adalah buku ke-8 dan ke-9 yang menceritakan siapa orangtua Raib dalam serial petualangan dunia parallel. Dua buku ini sebaiknya dibaca berurutan.",
                "Fantasy",
                false,
                4.5));
        books.add(new Book(R.drawable.pasta_kacang_merah,
                "Pasta Kacang Merah",
                "Durian Sukegawa",
                "2017",
                "Novel ini merupakan salah satu novel best seller international dan mendapatkan ulasan baik tentang ceritanya yang sangat menyentuh. Pada tahun 2017. buku ini memenangkan Le Prix des Lecteurs du Livre de Poche kategori Pilihan Pembaca. Le Prix des Lecteurs du Livre de Poche merupakan acara penghargaan yang diadakan oleh salah satu penerbit Perancis yaitu Le Livre de Poche.",
                "Romance",
                false,
                4.7));
        books.add(new Book(R.drawable.melangkah,
                "Melangkah",
                "J. S Khairen",
                "23 Mar 2020",
                "Novel karya J. S Khairen yang berjudul Melangkah bertemakan tentang petualangan di Indonesia. Tidak hanya itu, cerita dalam novel ini juga mengutamakan kisah pahlawan. Berbeda dari karya-karya yang sebelumnya, di novel ini Khairen memberi sedikit imajinasi yang ia tanamkan. Terdapat 36 episode dan 5 babak.",
                "Fantasy",
                false,
                4.4));
        books.add(new Book(R.drawable.the_dragon_republik,
                "The Dragon Republik",
                "R.F. Kuang",
                "11 Des 2020",
                "Sekuel dari debut terkenal Kuang, The Poppy War, Dragon Republic menggabungkan sejarah Cina abad ke-20 dengan dunia dewa dan monster yang mencekam, untuk efek yang menghancurkan.",
                "Fantasy",
                false,
                4.7));
        books.add(new Book(R.drawable.peter_pan,
                "Peter Pan",
                "J.M. Barrie",
                "Desember 2024",
                "Peter Pan adalah seorang bocah lelaki yang tidak ingin tumbuh dewasa. Ia tinggal di Neverland, sebuah dunia magis yang dihuni oleh peri, bajak laut, dan makhluk-makhluk lain. Suatu malam, Peter Pan mengunjungi kamar Wendy Darling dan adik-adiknya, John dan Michael, dan mengajak mereka untuk pergi bersamanya ke Neverland.",
                "Fantasy",
                false,
                4.4));
        books.add(new Book(R.drawable.laskar_pelangi,
                "Laskar Pelangi",
                "Andrea Hirata",
                "2005",
                "Sebelas orang anak Melayu Belitong yang luar biasa ini tak menyerah walau keadaan tak bersimpati pada mereka. Tengoklah Lintang, seorang kuli kopra cilik yang genius dan dengan senang hati bersepeda 80 kilometer pulang pergi untuk memuaskan dahaganya akan ilmu—bahkan terkadang hanya untuk menyanyikan Padamu Negeri di akhir jam sekolah.",
                "Drama",
                false,
                4.7));
        books.add(new Book(R.drawable.saint_catherine,
                "Saint Catherine",
                "Anna Meyer",
                "2025",
                "As a recovering Irish American Catholic, she has mostly traded the world of communion and confessionals for the “city-girl” struggle of work-life balance, family, and her relationships. The only thing she has not been able to shake is her fear that something bad will happen if she misses Sunday mass.",
                "Horror",
                false,
                4.2));
        books.add(new Book(R.drawable.a_memoir,
                "The Last American Road Trip: A Memoir",
                "Sarah Kendzior",
                "2025",
                "In 1795, Catherine the Great of Russia was in search of a bride for her grandson Constantine, who stood third in line to her throne. In an eerie echo of her own story, Catherine selected an innocent young German princess, Julie of Saxe-Coburg, aunt of the future Queen Victoria.",
                "History",
                false,
                3.8));
        books.add(new Book(R.drawable.ocean,
                "Ocean: Earth’s Last Wilderness",
                "David Attenborough",
                "2025",
                "Award-winning broadcaster and natural historian David Attenborough and longtime collaborator Colin Butfield present a powerful call to action focused on our planet's oceans, exploring how critical this habitat is for the survival of humanity and the future of Earth. ",
                "History",
                false,
                4.7));
        books.add(new Book(R.drawable.selvish_gene,
                "The Selfish Gene",
                "Richard Dawkins",
                "January 1976",
                "The Selfish Gene caused a wave of excitement among biologists and the general public when it was first published in 1976. Its vivid rendering of a gene's eye view of life, in lucid prose, gathered together the strands of thought about the nature of natural selection into a conceptual framework with far-reaching implications for our understanding of evolution.",
                "Sci-Fi",
                false,
                4.1));
        books.add(new Book(R.drawable.origin_spiecies,
                "The Origin of Species",
                "Charles Darwin",
                "November 1859",
                "Darwin's theory of natural selection issued a profound challenge to orthodox thought and belief: no being or species has been specifically created; all are locked into a pitiless struggle for existence, with extinction looming for those not fitted for the task.",
                "Sci-Fi",
                false,
                4.0));
        books.add(new Book(R.drawable.lying,
                "One of Us Is Lying",
                "Karen M. McManus",
                " May 2017",
                "Only, Simon never makes it out of that classroom. Before the end of detention Simon's dead. And according to investigators, his death wasn't an accident. On Monday, he died. But on Tuesday, he'd planned to post juicy reveals about all four of his high-profile classmates, which makes all four of them suspects in his murder. Or are they the perfect patsies for a killer who's still on the loose?",
                "Thriller",
                false,
                3.9));
        return books;
    }
}