// MainActivity.java
package com.example.praktikum_6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.praktikum_6.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PostAdapter adapter;
    private List<Post> characterList = new ArrayList<>();
    private boolean isLoading = false;
    private int currentPage = 1;
    private int totalPages = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up RecyclerView and Adapter
        adapter = new PostAdapter(this, characterList);
        binding.rvData.setLayoutManager(new LinearLayoutManager(this));
        binding.rvData.setAdapter(adapter);

        // Klik error message atau ikon retry = refresh
        View.OnClickListener retryListener = view -> {
            if (isConnectedToInternet()) {
                // Sembunyikan error layout, tampilkan loading
                binding.tvErrorMessage.setVisibility(View.GONE);
                binding.ivRetry.setVisibility(View.GONE);
                binding.layoutError.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                startLoadingAnimation();

                binding.rvData.setVisibility(View.VISIBLE);
                loadCharacterData(currentPage);
            } else {
                // Sembunyikan error dulu, tampilkan animasi
                binding.tvErrorMessage.setVisibility(View.GONE);
                binding.ivRetry.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                startLoadingAnimation();

                // Setelah 1.5 detik, tampilkan lagi error dan stop animasi
                binding.progressBar.postDelayed(() -> {
                    binding.progressBar.clearAnimation();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                    binding.ivRetry.setVisibility(View.VISIBLE);
                }, 500);
            }
        };

        // Pasang listener ke kedua komponen
        binding.ivRetry.setOnClickListener(retryListener);
        binding.tvErrorMessage.setOnClickListener(retryListener);

        // Cek koneksi
        if (isConnectedToInternet()) {
            loadCharacterData(currentPage);
        } else {
            showNoInternetConnection();
        }

        // Setelah setAdapter dan setOnLoadMoreListener
        adapter.setOnLoadMoreListener(() -> {
            if (isLoading || currentPage >= totalPages) return;

            if (isConnectedToInternet()) {
                // masih online → load page berikutnya
                currentPage++;
                loadCharacterData(currentPage);
            } else {
                // offline → sembunyikan tombol Load More dan beri tahu user
                adapter.hideLoadMore();
                Toast.makeText(MainActivity.this,
                        "Tidak ada koneksi internet. Tombol Load More dihilangkan.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to check internet connection
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void loadCharacterData(int page) {
        isLoading = true;
        binding.progressBar.setVisibility(View.VISIBLE); // Show loading

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RickApiService api = retrofit.create(RickApiService.class);
        Call<ApiResponse> call = api.getCharacters(page);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                isLoading = false;
                binding.progressBar.clearAnimation();
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    // Update totalPages
                    if (response.body().getInfo() != null) {
                        totalPages = response.body().getInfo().getPages();
                    }

                    // Tambah data ke adapter
                    List<Post> newData = response.body().getResults();
                    int start = characterList.size();
                    adapter.addData(newData);

                    // Scroll positioning
                    if (currentPage == 1) {
                        binding.rvData.scrollToPosition(0);
                    } else {
                        binding.rvData.scrollToPosition(start);
                    }

                    // Setelah data berhasil ditambahkan, atur footer:
                    if (currentPage < totalPages) {
                        adapter.showLoadMore();   // tampilkan tombol load more
                    } else {
                        adapter.hideLoadMore();   // sudah halaman terakhir → sembunyikan
                    }

                    binding.layoutError.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                isLoading = false;
                binding.progressBar.clearAnimation();
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "onFailure: ", t);

                // Menampilkan pesan error dan tombol retry jika gagal
                binding.layoutError.setVisibility(View.VISIBLE);
                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                binding.ivRetry.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showNoInternetConnection() {
        binding.progressBar.setVisibility(View.GONE);       // Sembunyikan loading
        binding.rvData.setVisibility(View.GONE);            // Sembunyikan RecyclerView
        binding.layoutError.setVisibility(View.VISIBLE);    // Tampilkan layout error
        binding.tvErrorMessage.setVisibility(View.VISIBLE); // Tampilkan pesan error
        binding.ivRetry.setVisibility(View.VISIBLE);        // Tampilkan ikon retry
    }

    // Handle the retry button click
    public void onRetryClick(View view) {
        if (isConnectedToInternet()) {
            // Tampilkan animasi loading
            binding.progressBar.setVisibility(View.VISIBLE);
            startLoadingAnimation(); // Mulai animasi

            // Sembunyikan error UI
            binding.tvErrorMessage.setVisibility(View.GONE);
            binding.rvData.setVisibility(View.VISIBLE);
            binding.ivRetry.setVisibility(View.GONE);

            loadCharacterData(currentPage); // Muat ulang data
        } else {
            Toast.makeText(MainActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoadingAnimation() {
        if (binding.progressBar.getAnimation() == null) {
            RotateAnimation rotate = new RotateAnimation(
                    0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotate.setDuration(1000);
            rotate.setRepeatCount(Animation.INFINITE);
            binding.progressBar.startAnimation(rotate);
        }
    }

}
