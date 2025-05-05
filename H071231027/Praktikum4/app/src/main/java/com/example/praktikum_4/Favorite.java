package com.example.praktikum_4;

public class Favorite {
    private String title;
    private Integer coverResId;
    private String coverUrl;   // untuk link internet
    private Book book; // Tambahkan ini
    private String imageUri; // Tambahkan ini

    public Favorite(Book book) {
        this.title = book.getTitle();
        this.coverResId = book.getCoverResId();
        this.book = book; // simpan referensinya
        this.imageUri = book.getImageUri();
        this.coverUrl = book.getCoverUrl();
    }

    public String getTitle() {
        return title;
    }

    public Integer getCoverResId() {
        return coverResId;
    }

    public Book getBook() {
        return book;
    }
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public String getCoverUrl() {
        return coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
