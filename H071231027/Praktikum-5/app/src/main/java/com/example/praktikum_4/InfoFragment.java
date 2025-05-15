package com.example.praktikum_4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.praktikum_4.databinding.FragmentInfoBinding;
import com.google.gson.Gson;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String bookJson = getArguments().getString("book");
        Gson gson = new Gson();
        Book book = gson.fromJson(bookJson, Book.class);

        binding.titleBook.setText(book.getTitle());
        binding.subtitleBook.setText(book.getSubtitle());
        binding.authors.setText(book.getAuthors());
        binding.language.setText(book.getLanguage());
        binding.description.setText(book.getDescription());
        binding.categories.setText(book.getCategories());
        binding.publisher.setText(book.getPublisher());
        binding.publishDate.setText(book.getPublishDate());
        binding.pages.setText(String.valueOf(book.getPages()));
        binding.formate.setText(book.getFormat());
        binding.isbn.setText(book.getIsbn());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // hindari memory leak
    }
}

