package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private BookAdapter adapter;
    private final ArrayList<Book> favoriteBooks = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).showBottomNav();

        adapter = new BookAdapter(favoriteBooks, book -> {
            DetailBookFragment fragment = new DetailBookFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", book);
            fragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_top,
                            R.anim.slide_out_bottom,
                            R.anim.slide_in_bottom,
                            R.anim.slide_out_top
                    )
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        binding.bookRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.bookRecycler.setAdapter(adapter);

        loadFavoriteBooks();

        return view;
    }

    private void loadFavoriteBooks() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.emptyFavorites.setVisibility(View.GONE);

        executor.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> loadedFavorites = new ArrayList<>(BookRepository.getFavoriteBooks());

            mainHandler.post(() -> {
                favoriteBooks.clear();
                favoriteBooks.addAll(loadedFavorites);
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
                binding.emptyFavorites.setVisibility(favoriteBooks.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
