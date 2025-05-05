package com.example.praktikum_4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Book> newBookLiveData = new MutableLiveData<>();

    private final List<Review> temporaryReviews = new ArrayList<>();

    public void setNewBook(Book book) {
        newBookLiveData.setValue(book);
    }

    public LiveData<Book> getNewBook() {
        return newBookLiveData;
    }

    public List<Review> getTemporaryReviews() {
        return temporaryReviews;
    }

    public void addTemporaryReview(Review review) {
        temporaryReviews.add(review);
    }

    public void clearTemporaryReviews() {
        temporaryReviews.clear();
    }
}
