package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.myapplication.databinding.FragmentAddBookBinding;

public class AddBookFragment extends Fragment {
    private FragmentAddBookBinding binding;
    private Uri coverUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] genres = {
                "Romance", "Fantasy", "Sci-Fi", "Thriller", "Horror",
                "Drama", "History", "Comedy", "Slice of Life"
        };

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genres
        );
        binding.inputGenre.setAdapter(genreAdapter);
        binding.inputGenre.setOnClickListener(v -> binding.inputGenre.showDropDown());

        binding.editFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        });

        binding.upload.setOnClickListener(v -> {
            String judul = binding.etTitle.getText().toString().trim();
            String penulis = binding.etAuthor.getText().toString().trim();
            String tahun = binding.etPublishDate.getText().toString().trim();
            String genre = binding.inputGenre.getText().toString().trim();
            String blurb = binding.etBlurp.getText().toString().trim();
            String ratingStr = binding.etRating.getText().toString().trim();

            if (judul.isEmpty() || penulis.isEmpty() || tahun.isEmpty()
                    || genre.isEmpty() || blurb.isEmpty() || ratingStr.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            double rating;
            try {
                rating = Double.parseDouble(ratingStr);
                if (rating < 0.0 || rating > 5.0) {
                    Toast.makeText(getContext(), "Rating must be between 0.0 and 5.0", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid rating value", Toast.LENGTH_SHORT).show();
                return;
            }

            if (coverUri == null) {
                Toast.makeText(getContext(), "Please select a cover image", Toast.LENGTH_SHORT).show();
                return;
            }

            Book newBook = new Book(coverUri, judul, penulis, tahun, blurb, genre, false, rating);
            DataBook.books.add(0, newBook);
            Toast.makeText(getContext(), "Book uploaded!", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null) {
            coverUri = data.getData();
            binding.editFoto.setImageURI(coverUri);
        }
    }
}