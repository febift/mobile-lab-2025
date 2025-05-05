package com.example.praktikum_4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.praktikum_4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final Fragment homeFragment = new HomeFragment();
    private final Fragment addBookFragment = new AddBookFragment();
    private final Fragment favoriteFragment = new FavoriteFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadFragment(homeFragment, "HomeFragment");
        binding.buttomNav.setSelectedItemId(R.id.explore);
        binding.buttomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            String tag = null;
            if (item.getItemId() == R.id.explore) {
                fragment = homeFragment;
                tag = "HomeFragment";
            } else if (item.getItemId() == R.id.add) {
                fragment = addBookFragment;
                tag = "AddBookFragment";
            } else if (item.getItemId() == R.id.favorite) {
                fragment = favoriteFragment;
                tag = "FavoriteFragment";
            }
            return loadFragment(fragment, tag);
        });
        Log.d("MainActivity", "onCreate selesai");
    }

    private boolean loadFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            // Tampilkan ProgressBar
            binding.fragmentProgressBar.setVisibility(View.VISIBLE);

            // Tentukan delay khusus
            long delayMillis; // default
            if ("FavoriteFragment".equals(tag)) {
                delayMillis = 500; // tambahkan waktu agar loading terlihat
            } else {
                delayMillis = 300;
            }

            // Jalankan pergantian fragment
            binding.fragmentContainer.post(() -> {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), fragment, tag)
                        .commit();

                // Sembunyikan ProgressBar setelah delay
                binding.fragmentContainer.postDelayed(() ->
                        binding.fragmentProgressBar.setVisibility(View.GONE), delayMillis);
            });

            Log.d("MainActivity", "loadFragment: " + fragment.getClass().getSimpleName() + ", tag: " + tag);
            return true;
        }
        return false;
    }
}