package com.example.praktikum_4;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.praktikum_4.databinding.FragmentFavoriteBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {

    public static List<Favorite> favoriteList = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private FavoriteAdapter favoriteAdapter;
    private FragmentFavoriteBinding binding;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Tampilkan loading ProgressBar saat mulai
        binding.progressBar.setVisibility(View.VISIBLE);

        // Simulasikan loading dengan delay 500ms
        binding.favorite.postDelayed(() -> {
            binding.favorite.setLayoutManager(new GridLayoutManager(getContext(), 3));

            favoriteAdapter = new FavoriteAdapter(getContext(), favoriteList, book -> {
                Intent intent = new Intent(getContext(), DetailBookActivity.class);
                intent.putExtra("book", new Gson().toJson(book));
                startActivity(intent);
            });
            binding.favorite.setAdapter(favoriteAdapter);

            updateEmptyMessage();

            // Sembunyikan ProgressBar setelah selesai memuat
            binding.progressBar.setVisibility(View.GONE);

        }, 500);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Tampilkan loading saat pencarian
                searchFavorites(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (favoriteAdapter != null) {
            favoriteAdapter.notifyDataSetChanged();
            updateEmptyMessage();
        }
    }


    private void updateEmptyMessage() {
        if (favoriteList.isEmpty()) {
            binding.progressBar.setVisibility(View.GONE);
            binding.tvEmptyMessage.setVisibility(View.VISIBLE);
            binding.favorite.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tvEmptyMessage.setVisibility(View.GONE);
            binding.favorite.setVisibility(View.VISIBLE);
        }
    }

    private void searchFavorites(String keyword) {
        ProgressBar progressBar = binding.progressBar;
        requireActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Favorite> filteredList;

            if (keyword.isEmpty()) {
                filteredList = favoriteList; // tampilkan semua jika kosong
            } else {
                filteredList = new ArrayList<>();
                String lowerKeyword = keyword.toLowerCase();

                for (Favorite fav : favoriteList) {
                    Book book = fav.getBook();
                    if (book != null &&
                            (book.getTitle().toLowerCase().contains(lowerKeyword) ||
                                    book.getAuthors().toLowerCase().contains(lowerKeyword) ||
                                    book.getDescription().toLowerCase().contains(lowerKeyword))) {
                        filteredList.add(fav);
                    }
                }
            }

            requireActivity().runOnUiThread(() -> {
                favoriteAdapter.updateData(filteredList);
                progressBar.setVisibility(View.GONE);
            });
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
