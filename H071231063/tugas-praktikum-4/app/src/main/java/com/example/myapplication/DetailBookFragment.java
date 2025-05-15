package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentDetailBookBinding;

public class DetailBookFragment extends Fragment {
    private FragmentDetailBookBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).hideBottomNav();

        binding.back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        Book book = getArguments().getParcelable("book");
        if (book != null) {
            Uri coverUri = book.getCoverUri();
            int coverDrawable = book.getCover();

            if (coverUri != null) {
                try {
                    binding.cover.setImageURI(coverUri);
                    if (binding.cover.getDrawable() == null) {
                        binding.cover.setImageResource(coverDrawable);
                    }
                } catch (Exception e) {
                    binding.cover.setImageResource(coverDrawable);
                }
            } else {
                binding.cover.setImageResource(coverDrawable);
            }
            binding.judul.setText(book.getJudul());
            binding.penulis.setText(book.getPenulis());
            binding.tahun.setText(String.valueOf(book.getTahun()));
            binding.genre.setText(book.getGenre());
            binding.blurb.setText(book.getBlurb());
            updateLikeIcon(book.isFavorite());
            binding.rating.setText(String.valueOf(book.getRating()));

            binding.favorites.setOnClickListener(v -> {
                book.setFavorite(!book.isFavorite());
                updateLikeIcon(book.isFavorite());

                if (book.isFavorite()) {
                    BookRepository.addToFavorites(book);
                } else {
                    BookRepository.removeFromFavorites(book);
                }
            });
        }

        return view;
    }

    private void updateLikeIcon(boolean isFavorited) {
        if (isFavorited) {
            binding.favorites.setImageResource(R.drawable.heart2);
        } else {
            binding.favorites.setImageResource(R.drawable.heart1);
        }
    }
}