package com.projeku.tuprak4;

import java.util.ArrayList;

public class DataBook {
    public static ArrayList<Book> bookList = new ArrayList<>();
    static {
        if (bookList.isEmpty()) {
            bookList.addAll(getDummyBooks());
        }
    }

    public static ArrayList<Book> getDummyBooks() {
        ArrayList<Book> books = new ArrayList<>();

        // Touhou Project books with working image URLs
        books.add(new Book("Bohemian Archive", "ZUN",
                "Official Touhou interviews and articles collection.",
                "Artbook", "First official Touhou print work.",
                "https://img.mandarake.co.jp/webshopimg/04/23/254/0423482254/04234822540.jpg",  // Placeholder Touhou art
                2005, false, 5));

        books.add(new Book("Perfect Memento", "ZUN",
                "Comprehensive Touhou character guidebook.",
                "Guide", "Essential for Touhou lore.",
                "https://img.mandarake.co.jp/webshopimg/04/04/140/0404040140/04040401400.jpg",  // Placeholder Touhou art
                2006, false, 5));

        books.add(new Book("Curiosities of Lotus", "ZUN",
                "Novel about mysterious artifacts in Gensokyo.",
                "Novel", "Rinnosuke's adventures.",
                "https://img.mandarake.co.jp/webshopimg/04/04/664/0404006664/s_04040066643.jpg",  // Placeholder Touhou art
                2003, false, 5));

        books.add(new Book("Grimoire of Marisa", "ZUN",
                "Marisa's spell card collection with commentary.",
                "Guide", "Fun look at spell mechanics.",
                "https://img.mandarake.co.jp/webshopimg/04/30/483/0430091483/s_04300914830.jpg",  // Placeholder Touhou art
                2009, false, 5));

        books.add(new Book("Forbidden Scrollery", "ZUN/Mato",
                "Manga about dangerous books in Gensokyo.",
                "Manga", "Kosuzu's adventures.",
                "data:image/jpeg;base64,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",  // Placeholder Touhou art
                2012, false, 5));

        books.add(new Book("Wild and Horned Hermit", "ZUN/Mato",
                "Manga about Kasen Ibaraki's hermit life.",
                "Manga", "Youkai society insights.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTURZVLmdgcoOfB6asTIl333PuStTaAGSqnpg&s",  // Placeholder Touhou art
                2010, false, 5));

        books.add(new Book("Alternative Facts", "ZUN",
                "Sequel to Bohemian Archive with new interviews.",
                "Artbook", "More Gensokyo insights.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRtO_b_dt02IqLbtGBDwSBJF7YUk_PrWXCrXg&s",  // Placeholder Touhou art
                2017, false, 5));

        books.add(new Book("Symposium of Post-mysticism", "ZUN",
                "Artbook featuring newer Touhou characters.",
                "Artbook", "Beautiful official artwork.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSM5juFPMtk6XdtR6HHB3cZ0DLVTdf_HYghYw&s",  // Placeholder Touhou art
                2014, false, 5));

        books.add(new Book("Touhou Sangetsusei", "ZUN",
                "Manga about the Three Fairies of Light.",
                "Manga", "Cute and comedic stories.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4CrvQ5mh5o20Am7c0-jxixfTV-rAl-wMjzg&s",  // Placeholder Touhou art
                2005, false, 5));

        books.add(new Book("Touhou Bougetsushou", "ZUN",
                "Cirno and Friends",
                "Novel", "Eientei and Lunarian lore.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdndy4BaPzIP6RfShVFbAIMGvexZi3aJbrSw&s",  // Placeholder Touhou art
                2007, false, 5));

        return books;
    }
}