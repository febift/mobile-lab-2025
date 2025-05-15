package com.example.praktikum_4;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.praktikum_4.databinding.ActivityDetailBookBinding;
import com.google.gson.Gson;

import java.util.Objects;

public class DetailBookActivity extends AppCompatActivity {
    private ActivityDetailBookBinding binding;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ambil Book dari JSON
        String bookJson = getIntent().getStringExtra("book");
        book = new Gson().fromJson(bookJson, Book.class);

        // Set data ke tampilan
        binding.titleBook.setText(book.getTitle());
        if (book.getImageUri() != null && !book.getImageUri().isEmpty()) {
            Glide.with(this)
                    .load(book.getImageUri())
                    .into(binding.coverBook);
        } else if (book.getCoverResId() != null){
            binding.coverBook.setImageResource(book.getCoverResId());
        } else {
            Glide.with(this)
                    .load(book.getCoverUrl())
                    .into(binding.coverBook);
        }

        binding.bookWriter.setText(book.getAuthors());
        binding.categories.setText(book.getCategories());

        // Tampilkan fragment Info sebagai default
        loadFragment(new InfoFragment());

        // Bottom navigation
        binding.navigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.info) {
                selectedFragment = new InfoFragment();
            } else if (item.getItemId() == R.id.reviews) {
                selectedFragment = new ReviewsFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
        binding.ratingBar.setRating(book.getRating());


        // Tombol favorite
        binding.favorite.setOnClickListener(v -> {
            boolean isAlreadyFavorite = false;
            Favorite foundFavorite = null;

            for (Favorite f : FavoriteFragment.favoriteList) {
                if (f.getBook().getTitle().equals(book.getTitle()) &&
                        Objects.equals(f.getBook().getCoverResId(), book.getCoverResId())) {
                    isAlreadyFavorite = true;
                    foundFavorite = f;
                    break;
                }
            }

            if (isAlreadyFavorite) {
                FavoriteFragment.favoriteList.remove(foundFavorite);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                FavoriteFragment.favoriteList.add(new Favorite(book));
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }

            updateFavoriteButton();
        });

        updateFavoriteButton();
    }
    private void updateFavoriteButton() {
        boolean isFavorite = false;

        for (Favorite f : FavoriteFragment.favoriteList) {
            if (f.getBook().getTitle().equals(book.getTitle()) &&
                    Objects.equals(f.getBook().getCoverResId(), book.getCoverResId())) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            binding.favorite.setText("Remove from Favorites");
            binding.favorite.setBackgroundTintList(getColorStateList(R.color.gray)); // warna abu-abu
        } else {
            binding.favorite.setText("Add to Favorites");
            binding.favorite.setBackgroundTintList(getColorStateList(R.color.pink)); // warna pink awal
        }
    }


    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("book", new Gson().toJson(book));
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
