package com.example.myapplication;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {
    private int cover;
    private Uri coverUri;
    private String judul;
    private String penulis;
    private String tahun;
    private String blurb;
    private String genre;
    private boolean isFavorite;
    private double rating;

    public Book(Uri coverUri, String judul, String penulis,
                String tahun, String blurb, String genre, boolean isFavorite, double rating) {
        this.coverUri = coverUri;
        this.judul = judul;
        this.penulis = penulis;
        this.tahun = tahun;
        this.blurb = blurb;
        this.genre = genre;
        this.isFavorite = isFavorite;
        this.rating = rating;
    }

    public Book(int cover, String judul, String penulis,
                String tahun, String blurb, String genre, boolean isFavorite, double rating) {
        this.cover = cover;
        this.judul = judul;
        this.penulis = penulis;
        this.tahun = tahun;
        this.blurb = blurb;
        this.genre = genre;
        this.isFavorite = isFavorite;
        this.rating = rating;
    }

    protected Book(Parcel in) {
        cover = in.readInt();
        coverUri = in.readParcelable(Uri.class.getClassLoader());
        judul = in.readString();
        penulis = in.readString();
        tahun = in.readString();
        blurb = in.readString();
        genre = in.readString();
        isFavorite = in.readByte() != 0;
        rating = in.readDouble();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(cover);
        dest.writeParcelable(coverUri, flags);
        dest.writeString(judul);
        dest.writeString(penulis);
        dest.writeString(tahun);
        dest.writeString(blurb);
        dest.writeString(genre);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeDouble(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter dan Setter
    public int getCover() {
        return cover;
    }

    public void setCover(int coverResource) {
        this.cover = coverResource;
    }

    public Uri getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(Uri coverUri) {
        this.coverUri = coverUri;
    }

    public boolean isFromGallery() {
        return coverUri != null;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}