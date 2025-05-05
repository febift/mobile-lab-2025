package com.example.praktikum_4;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.praktikum_4.databinding.FragmentFavoriteBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    public static List<Favorite> favoriteList = new ArrayList<>();

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
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.favorite.postDelayed(() -> {
                    searchFavorites(s.toString());
                    binding.progressBar.setVisibility(View.GONE);
                }, 300);
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
            binding.tvEmptyMessage.setVisibility(View.VISIBLE);
            binding.favorite.setVisibility(View.GONE);
        } else {
            binding.tvEmptyMessage.setVisibility(View.GONE);
            binding.favorite.setVisibility(View.VISIBLE);
        }
    }

    private void searchFavorites(String keyword) {
        if (keyword.isEmpty()) {
            favoriteAdapter.updateData(favoriteList);
            return;
        }

        List<Favorite> filteredList = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Favorite fav : favoriteList) {
            if (fav.getBook().getTitle().toLowerCase().contains(lowerKeyword) ||
                    fav.getBook().getAuthors().toLowerCase().contains(lowerKeyword) ||
                    fav.getBook().getDescription().toLowerCase().contains(lowerKeyword)) {
                filteredList.add(fav);
            }
        }

        favoriteAdapter.updateData(filteredList);
    }
}
