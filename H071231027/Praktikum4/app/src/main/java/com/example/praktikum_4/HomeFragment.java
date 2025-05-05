package com.example.praktikum_4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.praktikum_4.databinding.BottomSheetGenreBinding;
import com.example.praktikum_4.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.util.Executors;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    public BookAdapter adapter;
    private SharedViewModel sharedViewModel;

    public static List<Book> bookList = new ArrayList<>(); // Inisialisasi di sini

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Log.d("HomeFragment", "onCreateView");

        binding.filterGenre.setOnClickListener(v -> showGenreBottomSheet());

        binding.rvBooks.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Dummy book data dengan review berbeda-beda
        if (bookList.isEmpty()) {
            bookList.add(new Book("Atomic Habits", R.drawable.atomic, 5.0f,
                    "An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                    "James Clear", "English", "Learn how to form good habits, break bad ones, and master tiny behaviors.",
                    "Self-help", "Penguin", "2018", 320, "Paperback", "9780735211292",
                    Arrays.asList(
                            new Review("kevinardana12", 4.5f, "Sangat membantu membentuk kebiasaan baik."),
                            new Review("dimas29", 5.0f, "Top banget!")
                    )));

            bookList.add(new Book("Filosofi Teras", R.drawable.filosofiteras, 5.0f,
                    "Filsafat Yunani-Romawi untuk Mental Tangguh Masa Kini",
                    "Henry Manampiring", "Indonesian", "Menghadirkan filosofi Stoikisme agar relevan bagi pembaca modern.",
                    "Philosophy", "Kepustakaan Populer Gramedia", "2018", 346, "Paperback", "9786024812357",
                    Arrays.asList(
                            new Review("annajulie", 4.0f, "Membuka perspektif baru dalam berpikir."),
                            new Review("linaaa", 3.5f, "Butuh pemahaman ekstra, tapi layak dibaca.")
                    )));

            bookList.add(new Book("A Brief History of Time", R.drawable.brief, 4.5f,
                    "From the Big Bang to Black Holes",
                    "Stephen Hawking", "English", "Exploring fundamental questions about the universe.",
                    "Science", "Bantam Dell Publishing Group", "1988", 256, "Hardcover", "9780553109535",
                    Arrays.asList(
                            new Review("mario123", 4.0f, "Sulit tapi mengesankan."),
                            new Review("astrophile", 5.0f, "Buku wajib untuk pecinta kosmos.")
                    )));

            bookList.add(new Book("Mood Machine", "https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781668083505/mood-machine-9781668083505_lg.jpg",
                    3.5f,"The Rise of Spotify and the Costs of the Perfect Playlist",
                    "Liz Pelly", "English",
                    "Investigates how Spotify's model impacts artists and the music industry.",
                    "Arts and Entertainment", "Atria/One Signal Publishers", "2025", 288, "Paperback", "9781668083505",
                    Arrays.asList(
                            new Review("kevinardana12", 4.5f, "Membuka mata tentang industri musik digital."),
                            new Review("dimas29", 5.0f, "Analisis mendalam dan kritis.")
                    )));
            bookList.add(new Book("Elon Musk","https://m.media-amazon.com/images/I/81Kaj5++6pL.jpg",
                    5.0f,"The biography of the world's most controversial innovator.",
                    "Walter Isaacson", "English",
                    "An intimate look into Elon Musk's life and ventures.",
                    "Biographies", "Simon & Schuster", "2023", 688, "Hardcover", "9781982181284",
                    Arrays.asList(
                            new Review("kevinardana12", 4.5f, "Sangat informatif dan mendalam."),
                            new Review("dimas29", 5.0f, "Menginspirasi dan lengkap.")
                    )));
            bookList.add(new Book("JFK: The Conspiracy to Kill John F. Kennedy", "https://m.media-amazon.com/images/I/71670ErQRLL.jpg",
                    4.8f,"And the Secret that Failed to Kill a Presidency",
                    "Jerome R. Corsi", "English",
                    "Explores theories and evidence surrounding JFK's assassination.",
                    "History", "Post Hill Press", "2021", 368, "Hardcover", "9781250790576",
                    Arrays.asList(
                            new Review("history_buff88", 4.3f, "Sarat data dan menarik, meskipun agak spekulatif."),
                            new Review("kennedy_files", 4.0f, "Memberikan sudut pandang alternatif atas peristiwa bersejarah.")
                    )));
            bookList.add(new Book("The Psychology of Money (Edisi Revisi)", "https://penerbitbaca.com/wp-content/uploads/2023/10/The-Psychology-of-Money.jpg",
                    4.9f,"Pelajaran tentang kekayaan, keserakahan, dan kebahagiaan.",
                    "Morgan Housel", "Indonesian",
                    "Membahas perilaku manusia dalam mengelola uang dan keputusan finansial.",
                    "Business and Money", "Gramedia Pustaka Utama", "2022", 264, "Paperback", "9786020664798",
                    Arrays.asList(
                            new Review("uangbijak", 4.9f, "Bahasanya ringan tapi sangat bermakna."),
                            new Review("investasiku", 4.6f, "Sangat cocok bagi pemula yang ingin memahami uang.")
                    )));

            bookList.add(new Book("Think Again: Kekuatan Mengetahui Apa yang Tidak Diketahui", "https://m.media-amazon.com/images/I/71dJ09pdRkL.jpg",
                    4.5f,"Mengajarkan pentingnya berpikir ulang dan fleksibilitas mental.",
                    "Adam Grant", "Indonesian",
                    "Mendorong pembaca untuk membuka pikiran dan mempertanyakan asumsi.",
                    "self-help", "Gramedia Pustaka Utama", "2022", 384, "Paperback", "9786020523316",
                    Arrays.asList(
                            new Review("criticalmind", 4.8f, "Buku yang mengajarkan kerendahan hati intelektual."),
                            new Review("mentalshift", 5.0f, "Mengubah cara saya berpikir dan memutuskan.")
                    )));


            bookList.add(new Book("The Subtle Art of Not Giving a F*ck", "https://m.media-amazon.com/images/I/71QKQ9mwV7L.jpg",
                    3.0f,"Pendekatan hidup realistis yang mengajarkan pentingnya prioritas.",
                    "Mark Manson", "Indonesian",
                    "Membantu pembaca fokus pada hal yang benar-benar penting.",
                    "self-help", "Gramedia", "2019", 224, "Paperback", "9786024246946",
                    Arrays.asList(
                            new Review("realistreader", 4.5f, "Gaya bahasa santai tapi mengena."),
                            new Review("prioritizer", 4.7f, "Menyadarkan saya akan nilai-nilai penting dalam hidup.")
                    )));

            bookList.add(new Book("Rich Dad Poor Dad", "https://m.media-amazon.com/images/I/81BE7eeKzAL.jpg",
                    2.0f,"Mengajarkan prinsip keuangan pribadi dan investasi sejak dini.",
                    "Robert T. Kiyosaki", "Indonesian",
                    "Menginspirasi pembaca untuk mandiri secara finansial.",
                    "Bisnis & Investasi", "Gramedia Pustaka Utama", "2018", 336, "Paperback", "9786024246947",
                    Arrays.asList(
                            new Review("moneywise", 4.9f, "Sangat membuka mata tentang pentingnya literasi finansial."),
                            new Review("investorbeginner", 4.6f, "Panduan yang sederhana namun kuat.")
                    )));

            bookList.add(new Book("Grit: Kekuatan Passion dan Kegigihan", "https://m.media-amazon.com/images/I/81WcnNQ-TBL.jpg",
                    4.5f,"Mengungkap rahasia sukses bukan dari bakat, tapi ketekunan.",
                    "Angela Duckworth", "Indonesian",
                    "Menginspirasi untuk membangun ketangguhan dalam meraih tujuan.",
                    "Self-help", "Gramedia", "2017", 352, "Paperback", "9786024246948",
                    Arrays.asList(
                            new Review("perseverancefan", 4.7f, "Menguatkan semangat saya untuk terus berjuang."),
                            new Review("passionhunter", 4.8f, "Mengajarkan pentingnya kegigihan.")
                    )));

            bookList.add(new Book("Start With Why", "https://m.media-amazon.com/images/I/81RCff1NpnL.jpg",
                    4.5f,"Mengajarkan pentingnya memulai dengan 'mengapa' dalam memimpin.",
                    "Simon Sinek", "Indonesian",
                    "Memberi wawasan mendalam tentang kepemimpinan yang menginspirasi.",
                    "Business and Money", "Gramedia", "2018", 256, "Paperback", "9786024246950",
                    Arrays.asList(
                            new Review("leadershipfan", 4.8f, "Menginspirasi dalam memimpin tim."),
                            new Review("whyseeker", 4.7f, "Membantu menemukan alasan di balik tindakan saya.")
                    )));
            bookList.add(new Book("The Boys of Riverside: A Deaf Football Team and a Quest for Glory", "https://m.media-amazon.com/images/I/81s6DUyQCZL.jpg",
                    4.5f,"Kisah inspiratif tim sepak bola tunarungu yang mengejar kejayaan.",
                    "Thomas Fuller", "English",
                    "Menggambarkan semangat dan determinasi dalam menghadapi tantangan.",
                    "Biographies", "Amazon Publishing", "2024", 320, "Hardcover", "9781542031234",
                    Arrays.asList(
                            new Review("sportsfan", 4.9f, "Sangat menginspirasi dan menyentuh hati."),
                            new Review("motivatedreader", 4.8f, "Cerita yang memotivasi untuk tidak menyerah.")
                    )));
            bookList.add(new Book("Meditations", "https://m.media-amazon.com/images/I/81OthjkJBuL.jpg",
                    5.0f,"Kumpulan pemikiran seorang Kaisar Romawi tentang hidup dan kebajikan.",
                    "Marcus Aurelius", "English",
                    "Refleksi filosofis yang mendalam.",
                    "Philosophy", "Modern Library", "2002", 304, "Paperback", "9780812968255",
                    Arrays.asList(
                            new Review("stoicfan", 4.9f, "Bijak dan relevan hingga sekarang."),
                            new Review("philosophymind", 4.7f, "Menginspirasi hidup sederhana.")
                    )));
            bookList.add(new Book("Steve Jobs", "https://m.media-amazon.com/images/I/81VStYnDGrL.jpg",
                    5.0f,"Biografi lengkap sang inovator Apple.",
                    "Walter Isaacson", "English",
                    "Menggali kehidupan, inovasi, dan kontroversinya.",
                    "Biographies", "Simon & Schuster", "2011", 656, "Hardcover", "9781451648539",
                    Arrays.asList(
                            new Review("applefan", 4.9f, "Sangat menginspirasi dan mendalam."),
                            new Review("biographylover", 4.8f, "Potret kompleks seorang jenius.")
                    )));

            bookList.add(new Book("Sapiens: A Brief History of Humankind", "https://m.media-amazon.com/images/I/713jIoMO3UL.jpg",
                    4.7f,"Kisah evolusi manusia dari masa lalu hingga sekarang.",
                    "Yuval Noah Harari", "English",
                    "Menjelaskan sejarah manusia dengan cara menarik.",
                    "History", "Harper", "2015", 464, "Paperback", "9780062316097",
                    Arrays.asList(
                            new Review("historybuff", 4.9f, "Menarik dan membuka wawasan."),
                            new Review("thinkerdeep", 4.8f, "Mengubah cara saya memandang sejarah.")
                    )));

            bookList.add(new Book("The Story of Art", "https://m.media-amazon.com/images/I/710i2Bp9KVL._AC_UF1000,1000_QL80_.jpg",
                    4.7f,"Sejarah seni dari zaman prasejarah hingga modern.",
                    "E.H. Gombrich", "English",
                    "Panduan seni yang klasik dan mendalam.",
                    "Arts and Photography", "Phaidon Press", "1995", 688, "Paperback", "9780714832470",
                    Arrays.asList(
                            new Review("artlover", 4.7f, "Wajib untuk pecinta seni."),
                            new Review("visualenthusiast", 4.6f, "Mudah dipahami, ilustrasi luar biasa.")
                    )));

            bookList.add(new Book("365 Days of Wonder", "https://m.media-amazon.com/images/I/91m8ctIqfVL._AC_UF1000,1000_QL80_.jpg",
                    4.7f,"Kutipan dan kata bijak untuk setiap hari.",
                    "R.J. Palacio", "English",
                    "Kalender inspirasi harian.",
                    "Calendars", "Knopf Books", "2014", 432, "Hardcover", "9780553499049",
                    Arrays.asList(
                            new Review("quotehunter", 4.7f, "Penuh inspirasi."),
                            new Review("dailyreader", 4.6f, "Setiap hari ada motivasi baru.")
                    )));

            bookList.add(new Book("Charlotte's Web", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9Nr6dZZD7PKr2I3j5eVOPCFUB68OwEHpy2A&s",
                    4.7f,"Kisah persahabatan seekor babi dan laba-laba.",
                    "E.B. White", "English",
                    "Klasik anak-anak yang menyentuh hati.",
                    "Children's Books", "HarperCollins", "2012", 192, "Paperback", "9780061124952",
                    Arrays.asList(
                            new Review("childhoodmemory", 4.9f, "Indah dan penuh makna."),
                            new Review("parentreader", 4.8f, "Anak-anak menyukainya.")
                    )));

            bookList.add(new Book("Watchmen", "https://m.media-amazon.com/images/M/MV5BYmJiNTUwYWUtZDllNi00ODdjLWFmNTEtOTVlNmYxYTZhNzYzXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                    4.7f,"Kisah pahlawan super dalam dunia dystopian.",
                    "Alan Moore", "English",
                    "Novel grafis yang revolusioner.",
                    "Comic and Graphic Novels", "DC Comics", "2019", 416, "Paperback", "9781401295426",
                    Arrays.asList(
                            new Review("comicfan", 4.9f, "Mahakarya komik."),
                            new Review("graphicreader", 4.8f, "Ceritanya mendalam dan kompleks.")
                    )));
            bookList.add(new Book("Clean Code", "https://m.media-amazon.com/images/I/41-sN-mzwKL.jpg",
                    4.7f,"Panduan menulis kode bersih dan mudah dipelihara.",
                    "Robert C. Martin", "English",
                    "Prinsip coding yang baik untuk pengembang.",
                    "Computers and Technology", "Prentice Hall", "2008", 464, "Paperback", "9780132350884",
                    Arrays.asList(
                            new Review("devguru", 4.9f, "Wajib baca untuk programmer."),
                            new Review("codecleaner", 4.8f, "Praktis dan detail.")
                    )));

            bookList.add(new Book("Salt, Fat, Acid, Heat", "https://m.media-amazon.com/images/I/718Qpw4miiL.jpg",
                    4.7f,"Empat elemen penting dalam memasak yang lezat.",
                    "Samin Nosrat", "English",
                    "Panduan kuliner dengan ilustrasi indah.",
                    "Cookbooks, Food and Wine", "Simon & Schuster", "2017", 480, "Hardcover", "9781476753836",
                    Arrays.asList(
                            new Review("foodie", 4.9f, "Memahami masakan secara mendalam."),
                            new Review("homechef", 4.8f, "Resep praktis dan enak.")
                    )));

            bookList.add(new Book("The Life-Changing Magic of Tidying Up", "https://m.media-amazon.com/images/I/81QpkIctqPL.jpg",
                    4.7f,"Metode KonMari untuk merapikan rumah.",
                    "Marie Kondo", "English",
                    "Seni berbenah yang memicu kebahagiaan.",
                    "Crafts, Hobbies and Home", "Ten Speed Press", "2014", 224, "Hardcover", "9781607747307",
                    Arrays.asList(
                            new Review("minimalistlife", 4.7f, "Bikin rumah lebih rapi dan lega."),
                            new Review("tidylover", 4.8f, "Inspiratif dan praktis.")
                    )));

            bookList.add(new Book("Make It Stick: The Science of Successful Learning", "https://images.tokopedia.net/img/cache/700/product-1/2021/4/12/8186755/8186755_e4f8cbd8-3342-432b-b29f-b3b8bee70151.jpg",
                    4.7f,"Bagaimana belajar yang efektif menurut riset.",
                    "Peter C. Brown", "English",
                    "Strategi belajar berbasis sains.",
                    "Education and Teaching", "Belknap Press", "2014", 336, "Hardcover", "9780674729018",
                    Arrays.asList(
                            new Review("learningpro", 4.8f, "Mengubah cara saya belajar."),
                            new Review("eduteacher", 4.7f, "Cocok untuk guru dan siswa.")
                    )));

            bookList.add(new Book("The Design of Everyday Things", "https://m.media-amazon.com/images/I/71HMyqG6MRL.jpg",
                    4.7f,"Prinsip desain yang memudahkan pengguna.",
                    "Don Norman", "English",
                    "Rekayasa dan desain produk sehari-hari.",
                    "Engineering and Transportation", "Basic Books", "2013", 384, "Paperback", "9780465050659",
                    Arrays.asList(
                            new Review("uxdesigner", 4.8f, "Wajib untuk desainer produk."),
                            new Review("engineerthinker", 4.7f, "Insightful dan mudah dipahami.")
                    )));

            bookList.add(new Book("Why We Sleep", "https://m.media-amazon.com/images/I/71KilybDOoL.jpg",
                    4.7f,"Ilmu di balik tidur dan pentingnya kesehatan.",
                    "Matthew Walker", "English",
                    "Panduan untuk tidur lebih baik.",
                    "Health, Fitness and Dieting", "Scribner", "2017", 368, "Hardcover", "9781501144318",
                    Arrays.asList(
                            new Review("sleepbetter", 4.9f, "Membuka mata soal pentingnya tidur."),
                            new Review("healthnut", 4.8f, "Sains + praktis, kombinasi sempurna.")
                    )));

            bookList.add(new Book("Bossypants", "https://upload.wikimedia.org/wikipedia/en/7/7c/Bossypants_Cover_%28Tina_Fey%29_-_200px.jpeg",
                    4.7f,"Memoar lucu dari komedian Tina Fey.",
                    "Tina Fey", "English",
                    "Cerita kocak dan menginspirasi.",
                    "Humor and Entertainment", "Reagan Arthur Books", "2011", 277, "Hardcover", "9780316056861",
                    Arrays.asList(
                            new Review("laughoutloud", 4.7f, "Ngakak terus baca ini."),
                            new Review("comedyfan", 4.6f, "Sangat menghibur dan pintar.")
                    )));

            bookList.add(new Book("The Nine", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1560903605l/52322982.jpg",
                    4.7f,"Cerita di balik Mahkamah Agung AS.",
                    "Jeffrey Toobin", "English",
                    "Hukum dan kekuasaan di balik layar.",
                    "Law", "Anchor", "2008", 496, "Paperback", "9781400076170",
                    Arrays.asList(
                            new Review("lawstudent", 4.7f, "Menarik dan mendalam."),
                            new Review("legalnerd", 4.6f, "Mengungkap rahasia pengadilan.")
                    )));

            bookList.add(new Book("Pride and Prejudice", "https://m.media-amazon.com/images/M/MV5BMTA1NDQ3NTcyOTNeQTJeQWpwZ15BbWU3MDA0MzA4MzE@._V1_QL75_UX190_CR0,0,190,281_.jpg",
                    4.7f,"Kisah klasik cinta dan status sosial.",
                    "Jane Austen", "English",
                    "Roman klasik yang memikat.",
                    "Literature and Fiction", "Penguin Classics", "2002", 480, "Paperback", "9780141439518",
                    Arrays.asList(
                            new Review("classicreader", 4.9f, "Selalu jadi favorit."),
                            new Review("literarylover", 4.8f, "Gaya bahasa indah.")
                    )));

            bookList.add(new Book("Gray's Anatomy for Students", "https://images-cdn.ubuy.co.id/65023c4e970d254f4573ae0a-gray-39-s-anatomy-for-students.jpg",
                    4.7f,"Panduan anatomi manusia untuk mahasiswa kedokteran.",
                    "Richard Drake", "English",
                    "Referensi penting untuk bidang medis.",
                    "Medical Book", "Elsevier", "2019", 1180, "Paperback", "9780323393041",
                    Arrays.asList(
                            new Review("medstudent", 4.9f, "Lengkap dan ilustratif."),
                            new Review("anatomyfan", 4.8f, "Jelas dan detail.")
                    )));

            bookList.add(new Book("The Girl with the Dragon Tattoo", "https://m.media-amazon.com/images/I/81D+KJkO4SL.jpg",
                    4.7f,"Thriller misteri yang menegangkan.",
                    "Stieg Larsson", "English",
                    "Investigasi kasus hilangnya pewaris keluarga.",
                    "Mistery and Thriller", "Vintage Crime", "2009", 672, "Paperback", "9780307949486",
                    Arrays.asList(
                            new Review("thrillerlover", 4.8f, "Penuh ketegangan."),
                            new Review("mysteryreader", 4.7f, "Sulit berhenti membacanya.")
                    )));

            bookList.add(new Book("Bringing Up Bébé", "https://m.media-amazon.com/images/I/711Ftgl02jL._AC_UF1000,1000_QL80_.jpg",
                    4.7f,"Rahasia pola asuh ala Prancis.",
                    "Pamela Druckerman", "English",
                    "Mendidik anak dengan elegan dan tegas.",
                    "Parenting and Relationships", "Penguin", "2013", 304, "Paperback", "9780143122968",
                    Arrays.asList(
                            new Review("parentlife", 4.7f, "Menarik dan membuka wawasan."),
                            new Review("momreads", 4.6f, "Tips praktis dan unik.")
                    )));

            bookList.add(new Book("Why Nations Fail", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTi4QfrhLorRjdPe_bT5vUhIC4Hamrt4Iqr_g&s",
                    4.7f,"Analisis mengapa negara berhasil atau gagal.",
                    "Daron Acemoglu", "English",
                    "Kombinasi politik dan ekonomi.",
                    "Politics and Social Science", "Crown", "2012", 560, "Hardcover", "9780307719218",
                    Arrays.asList(
                            new Review("politicaljunkie", 4.8f, "Sangat mendalam."),
                            new Review("econreader", 4.7f, "Wajib baca untuk pengamat politik.")
                    )));

            bookList.add(new Book("The Elements of Style", "https://m.media-amazon.com/images/I/71tbalAHYCL.jpg",
                    4.7f,"Panduan menulis bahasa Inggris yang efektif.",
                    "William Strunk Jr.", "English",
                    "Referensi ringkas dan kuat.",
                    "Reference Books", "Pearson", "1999", 105, "Paperback", "9780205309023",
                    Arrays.asList(
                            new Review("writerlife", 4.8f, "Panduan esensial."),
                            new Review("grammarnerd", 4.7f, "Sangat membantu.")
                    )));

            bookList.add(new Book("The Purpose Driven Life", "https://m.media-amazon.com/images/I/71Qe9OuoejL._AC_UF1000,1000_QL80_.jpg",
                    4.7f,"Menemukan makna hidup berdasarkan iman.",
                    "Rick Warren", "English",
                    "Panduan spiritual selama 40 hari.",
                    "Religion and Spirituality", "Zondervan", "2002", 336, "Hardcover", "9780310205715",
                    Arrays.asList(
                            new Review("faithseeker", 4.8f, "Mengubah hidup saya."),
                            new Review("believer", 4.7f, "Dalam dan menyentuh hati.")
                    )));

            bookList.add(new Book("The Notebook", "https://m.media-amazon.com/images/I/81WIhP1ca9L._AC_UF1000,1000_QL80_.jpg",
                    4.7f,"Roman cinta yang mengharukan.",
                    "Nicholas Sparks", "English",
                    "Cinta sejati yang abadi.",
                    "Romance", "Warner Books", "1996", 224, "Paperback", "9780446605236",
                    Arrays.asList(
                            new Review("romancereader", 4.8f, "Sangat menyentuh."),
                            new Review("lovefan", 4.7f, "Indah dan emosional.")
                    )));

            bookList.add(new Book("Dune", "https://m.media-amazon.com/images/I/91zbi9M+mKL.jpg",
                    4.7f,"Epik fiksi ilmiah tentang politik dan kekuasaan.",
                    "Frank Herbert", "English",
                    "Petualangan di planet Arrakis.",
                    "Science Fiction", "Ace", "1990", 896, "Paperback", "9780441172719",
                    Arrays.asList(
                            new Review("scififan", 4.9f, "Mahakarya genre sci-fi."),
                            new Review("spaceepic", 4.8f, "Sangat kompleks dan memikat.")
                    )));

            bookList.add(new Book("Born to Run", "https://m.media-amazon.com/images/I/81p6pJbLbCL._AC_UF1000,1000_QL80_.jpg",
                    4.7f,"Rahasia pelari jarak jauh suku Tarahumara.",
                    "Christopher McDougall", "English",
                    "Petualangan, sains, dan olahraga.",
                    "Sports and Outdoors", "Vintage", "2011", 304, "Paperback", "9780307279187",
                    Arrays.asList(
                            new Review("runnerlife", 4.7f, "Menginspirasi untuk berlari."),
                            new Review("fitnessjunkie", 4.6f, "Ceritanya seru sekali.")
                    )));

            bookList.add(new Book("Lonely Planet's Ultimate Travel", "https://m.media-amazon.com/images/I/81s0B6NYXML.jpg",
                    4.7f,"500 destinasi terbaik di dunia.",
                    "Lonely Planet", "English",
                    "Panduan perjalanan impian.",
                    "Travel", "Lonely Planet", "2015", 328, "Hardcover", "9781743607473",
                    Arrays.asList(
                            new Review("wanderlust", 4.8f, "Membuat saya ingin traveling."),
                            new Review("tripplanner", 4.7f, "Foto dan deskripsi yang memikat.")
                    )));

            Log.d("HomeFragment", "Dummy data with unique reviews added");
        }

        adapter = new BookAdapter(bookList, book -> {
            Gson gson = new Gson();
            Intent intent = new Intent(getContext(), DetailBookActivity.class);
            intent.putExtra("book", gson.toJson(book));
            startActivity(intent);
        });
        binding.rvBooks.setAdapter(adapter);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getNewBook().observe(getViewLifecycleOwner(), book -> {
            if (book != null) {
                Log.d("HomeFragment", "New book received: " + book.getTitle());

                if (!bookList.contains(book)) {
                    bookList.add(0, book);
                    adapter.notifyItemInserted(0);
                    binding.rvBooks.scrollToPosition(0);
                }

                sharedViewModel.setNewBook(null);
            }
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchBooksBackground(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        return view;
    }


    private void showGenreBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());

        BottomSheetGenreBinding sheetBinding = BottomSheetGenreBinding.inflate(LayoutInflater.from(getContext()));
        bottomSheetDialog.setContentView(sheetBinding.getRoot());

        sheetBinding.btnApply.setOnClickListener(v -> {
            List<String> selectedGenres = new ArrayList<>();

            if (sheetBinding.selfHelp.isChecked()) selectedGenres.add("Self Help");
            if (sheetBinding.scienceAndMathematics.isChecked()) selectedGenres.add("Science and Mathematics");
            if (sheetBinding.philosophy.isChecked()) selectedGenres.add("Philosophy");
            if (sheetBinding.biography.isChecked()) selectedGenres.add("Biographies");
            if (sheetBinding.history.isChecked()) selectedGenres.add("History");
            if (sheetBinding.artsAndPhotography.isChecked()) selectedGenres.add("Arts and Photography");
            if (sheetBinding.businessAndMoney.isChecked()) selectedGenres.add("Business and Money");
            if (sheetBinding.calendars.isChecked()) selectedGenres.add("Calendars");
            if (sheetBinding.childrensBooks.isChecked()) selectedGenres.add("Children's Books");
            if (sheetBinding.comicAndGraphicNovels.isChecked()) selectedGenres.add("Comic and Graphic Novels");
            if (sheetBinding.computersAndTechnology.isChecked()) selectedGenres.add("Computers and Technology");
            if (sheetBinding.cookbooksAndFoodAndWine.isChecked()) selectedGenres.add("Cookbooks, Food and Wine");
            if (sheetBinding.craftsAndHobbiesAndHome.isChecked()) selectedGenres.add("Crafts, Hobbies and Home");
            if (sheetBinding.educationAndTeaching.isChecked()) selectedGenres.add("Education and Teaching");
            if (sheetBinding.engineeringAndTransportation.isChecked()) selectedGenres.add("Engineering and Transportation");
            if (sheetBinding.healthAndFitnessAndDieting.isChecked()) selectedGenres.add("Health, Fitness and Dieting");
            if (sheetBinding.humorAndEntertainment.isChecked()) selectedGenres.add("Humor and Entertainment");
            if (sheetBinding.law.isChecked()) selectedGenres.add("Law");
            if (sheetBinding.literatureAndFiction.isChecked()) selectedGenres.add("Literature and Fiction");
            if (sheetBinding.medicalBook.isChecked()) selectedGenres.add("Medical Book");
            if (sheetBinding.misteryAndThriller.isChecked()) selectedGenres.add("Mistery and Thriller");
            if (sheetBinding.parentingAndRelationships.isChecked()) selectedGenres.add("Parenting and Relationships");
            if (sheetBinding.politicsAndSocialScience.isChecked()) selectedGenres.add("Politics and Social Science");
            if (sheetBinding.referenceBooks.isChecked()) selectedGenres.add("Reference Books");
            if (sheetBinding.religionAndSpirituality.isChecked()) selectedGenres.add("Religion and Spirituality");
            if (sheetBinding.romance.isChecked()) selectedGenres.add("Romance");
            if (sheetBinding.scienceFiction.isChecked()) selectedGenres.add("Science Fiction");
            if (sheetBinding.sportsAndOutdoors.isChecked()) selectedGenres.add("Sports and Outdoors");
            if (sheetBinding.travel.isChecked()) selectedGenres.add("Travel");

            filterBooksByGenres(selectedGenres); // ✅ ini yang benar
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Log.d("HomeFragment", "onDestroyView");
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            Log.d("HomeFragment", "onResume: adapter.notifyDataSetChanged()");
        }else {
            Log.d("HomeFragment", "onResume: adapter is null");
        }
    }
    private void filterBooksByGenres(List<String> selectedGenres) {
        if (selectedGenres.isEmpty()) {
            adapter.updateData(bookList); // Menampilkan semua
        } else {
            List<Book> filtered = new ArrayList<>();
            for (Book book : bookList) {
                if (selectedGenres.contains(book.getCategories())) {
                    filtered.add(book);
                }
            }
            adapter.updateData(filtered);
        }
    }
    private void searchBooksBackground(String keyword) {
        ProgressBar progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);  // Tampilkan ProgressBar

        new Thread(() -> {
            try {
                Thread.sleep(500);  // ⏳ Delay buatan 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> filteredList = new ArrayList<>();
            String lowerKeyword = keyword.toLowerCase();

            for (Book book : bookList) {
                if (book.getTitle().toLowerCase().contains(lowerKeyword) ||
                        book.getAuthors().toLowerCase().contains(lowerKeyword) ||
                        book.getDescription().toLowerCase().contains(lowerKeyword)) {
                    filteredList.add(book);
                }
            }

            requireActivity().runOnUiThread(() -> {
                adapter.updateData(filteredList);
                progressBar.setVisibility(View.GONE);  // Sembunyikan ProgressBar setelah selesai
            });
        }).start();
    }
}