package com.example.praktikum_6;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.praktikum_6.databinding.ActivityDataBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {
    private ActivityDataBinding binding;
    private int characterId;

    private RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        characterId = getIntent().getIntExtra("character_id", -1);

        // Siapkan animasi rotasi
        rotateAnimation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        // Retry click listener
        View.OnClickListener retryListener = view -> {
            if (isConnectedToInternet()) {
                showLoading();
                startLoadingAnimation(); // putar progressBar
                loadCharacterData(characterId);
            } else {
                showTemporaryRetryAnimation(); // Tampilkan animasi pendek saat offline
            }
        };


        binding.ivRetry.setOnClickListener(retryListener);
        binding.tvErrorMessage.setOnClickListener(retryListener);

        if (characterId != -1) {
            if (isConnectedToInternet()) {
                showLoading();
                startLoadingAnimation();
                loadCharacterData(characterId);
        } else {
                showNoInternetConnection();
            }
        }
    }


    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void loadCharacterData(int characterId) {
        RickApiService apiService = ApiClient.getClient().create(RickApiService.class);
        apiService.getCharacterById(characterId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                hideLoading();
                stopLoadingAnimation(); // stop animasi

                if (response.isSuccessful() && response.body() != null) {
                    Post character = response.body();

                    // Tampilkan data karakter
                    binding.textName.setText(character.getName());
                    binding.textStatus.setText(character.getStatus());
                    binding.textSpecies.setText(character.getSpecies());
                    binding.textGender.setText(character.getGender());

                    Glide.with(DataActivity.this)
                            .load(character.getImage())
                            .into(binding.imageCharacter);

                    binding.mcData.setVisibility(View.VISIBLE);
                    binding.layoutError.setVisibility(View.GONE);
                } else {
                    showNoInternetConnection();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                hideLoading();
                stopLoadingAnimation();
                Log.e("DataActivity", "Gagal memuat data", t);
                showNoInternetConnection(); // ini sudah cukup
            }
        });
    }

    private void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.mcData.setVisibility(View.GONE);
        binding.layoutError.setVisibility(View.GONE);
    }

    private void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showNoInternetConnection() {
        hideLoading();
        binding.mcData.setVisibility(View.GONE);
        binding.layoutError.setVisibility(View.VISIBLE);
        binding.ivRetry.setVisibility(View.VISIBLE);
        binding.tvErrorMessage.setVisibility(View.VISIBLE);
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

    private void stopLoadingAnimation() {
        binding.progressBar.clearAnimation();
    }

    private void showTemporaryRetryAnimation() {
        binding.ivRetry.setVisibility(View.GONE);
        binding.tvErrorMessage.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        startLoadingAnimation();

        // Tampilkan error kembali setelah delay
        binding.progressBar.postDelayed(() -> {
            stopLoadingAnimation();
            binding.progressBar.setVisibility(View.GONE);
            binding.ivRetry.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
        }, 500); // durasi animasi sementara (bisa kamu ubah)
    }


}
