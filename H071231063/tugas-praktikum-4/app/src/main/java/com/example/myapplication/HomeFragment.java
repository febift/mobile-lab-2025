package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.SearchView;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private String currentGenre = "All Genres";

    private FragmentHomeBinding binding;
    private ArrayList<Book> allBooks = new ArrayList<>(DataBook.books);
    private BookAdapter adapter;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).showBottomNav();

        binding.bookRecycler.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requireActivity().runOnUiThread(() -> {
                adapter = new BookAdapter(allBooks, book -> {
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
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                binding.bookRecycler.setLayoutManager(layoutManager);
                binding.bookRecycler.setAdapter(adapter);
                setupGenreButtons();
                binding.bookRecycler.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            });
        }).start();
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooksInBackground(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> searchBooksInBackground(newText);
                return true;
            }
        });
        return view;
    }

    private void searchBooksInBackground(String query) {
        binding.progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> filtered = new ArrayList<>();
            for (Book book : allBooks) {
                boolean matchesQuery = book.getJudul().toLowerCase().contains(query.toLowerCase());
                boolean matchesGenre = currentGenre.equals("All Genres") ||
                        book.getGenre().equalsIgnoreCase(currentGenre);

                if (matchesQuery && matchesGenre) {
                    filtered.add(book);
                }
            }

            mainHandler.post(() -> {
                adapter.filterList(filtered);
                binding.progressBar.setVisibility(View.GONE);

                if (filtered.isEmpty()) {
                    binding.emptyGenre.setVisibility(View.VISIBLE);
                    binding.bookRecycler.setVisibility(View.GONE);
                } else {
                    binding.emptyGenre.setVisibility(View.GONE);
                    binding.bookRecycler.setVisibility(View.VISIBLE);
                }
            });
        });
    }



    private void setupGenreButtons() {
        MaterialButton[] buttons = {
                binding.allGenres, binding.romance, binding.fantasy, binding.scifi,
                binding.thriller, binding.horror, binding.drama, binding.history,
                binding.comedy, binding.sliceOfLife
        };

        String[] genres = {
                "All Genres", "Romance", "Fantasy", "Sci-Fi", "Thriller",
                "Horror", "Drama", "History", "Comedy", "Slice of Life"
        };

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(v -> {
                for (int j = 0; j < buttons.length; j++) {
                    changeButtonStyle(buttons[j], j == index);
                }

                currentGenre = genres[index];
                String query = binding.search.getQuery().toString();
                searchBooksInBackground(query);
            });
        }
    }

    private void changeButtonStyle(MaterialButton button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.yellow));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        } else {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pastel_yellow));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (searchRunnable != null) {
            searchHandler.removeCallbacks(searchRunnable);
        }
    }
}
