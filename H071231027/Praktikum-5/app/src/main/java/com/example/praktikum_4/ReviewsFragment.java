package com.example.praktikum_4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.praktikum_4.databinding.FragmentReviewsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {
    private FragmentReviewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle args = getArguments();
        if (args != null && args.containsKey("book")) {
            String bookJson = args.getString("book");
            if (bookJson != null) {
                Book book = new Gson().fromJson(bookJson, Book.class);

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                ReviewAdapter adapter = new ReviewAdapter(book.getReviews());
                binding.recyclerView.setAdapter(adapter);

                // Tambahkan animasi slide
                Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);

                binding.btnAddReview.setOnClickListener(v -> {
                    if (binding.reviewFormLayout.getVisibility() == View.GONE) {
                        binding.reviewFormLayout.setVisibility(View.VISIBLE);
                        binding.reviewFormLayout.startAnimation(slideUp);
                    } else {
                        binding.reviewFormLayout.startAnimation(slideDown);
                        binding.reviewFormLayout.setVisibility(View.GONE);
                    }
                });

                binding.btnSubmitReview.setOnClickListener(v -> {
                    String reviewText = binding.etReview.getText().toString();
                    float ratingValue = binding.ratingBar.getRating();

                    if (reviewText.isEmpty()) {
                        Toast.makeText(getContext(), "Review tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        // Misalnya username default sementara "Pengguna"
                        Review newReview = new Review("Pengguna", ratingValue, reviewText);

                        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        List<Review> temporaryReviews = sharedViewModel.getTemporaryReviews();

                        temporaryReviews.add(newReview);
                        sharedViewModel.addTemporaryReview(newReview);

                        book.getReviews().add(newReview);
                        adapter.notifyItemInserted(book.getReviews().size() - 1);

                        // Bersihkan form
                        binding.etReview.setText("");
                        binding.ratingBar.setRating(0);

                        // Sembunyikan form
                        binding.reviewFormLayout.startAnimation(slideDown);
                        binding.reviewFormLayout.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "Review berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Hindari memory leak
    }
}
