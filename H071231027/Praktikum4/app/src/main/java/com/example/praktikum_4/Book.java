package com.example.praktikum_4;

import java.util.List;

public class Book {
    private String title;
    private Integer coverResId;
    private float rating;
    private String subtitle;
    private String authors;
    private String language;
    private String description;
    private String categories;
    private String publisher;
    private String publishDate;
    private int pages;
    private String format;
    private String isbn;
    private List<Review> reviews;
    private String imageUri; // Tambahkan ini
    private String coverUrl;   // untuk link internet


    public Book(String title, Integer coverResId,float rating, String subtitle, String authors, String language,
                String description, String categories, String publisher, String publishDate,
                int pages, String format, String isbn, List<Review> reviews) {
        this.title = title;
        this.coverResId = coverResId;
        this.subtitle = subtitle;
        this.authors = authors;
        this.language = language;
        this.description = description;
        this.categories = categories;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pages = pages;
        this.format = format;
        this.isbn = isbn;
        this.reviews = reviews;
        this.rating = rating;
    }
    public Book(String title, String coverUrl, float rating, String subtitle, String authors, String language,
                String description, String categories, String publisher, String publishDate,
                int pages, String format, String isbn, List<Review> reviews) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.subtitle = subtitle;
        this.authors = authors;
        this.language = language;
        this.description = description;
        this.categories = categories;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pages = pages;
        this.format = format;
        this.isbn = isbn;
        this.reviews = reviews;
        this.rating = rating;

    }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public String getSubtitle() { return subtitle; }
    public String getAuthors() { return authors; }
    public String getLanguage() { return language; }
    public String getDescription() { return description; }
    public String getCategories() { return categories; }
    public String getPublisher() { return publisher; }
    public String getPublishDate() { return publishDate; }
    public int getPages() { return pages; }
    public String getFormat() { return format; }
    public String getIsbn() { return isbn; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public String getCoverUrl() { return coverUrl; }
    public String getTitle() { return title; }
    public Integer getCoverResId() { return coverResId;} // default image; }
    public String getImageUri() { return imageUri; } // Tambahkan ini
    public void setImageUri(String imageUri) { this.imageUri = imageUri; } // Tambahkan ini
}

