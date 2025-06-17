package com.projeku.tuprak4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookDetail extends AppCompatActivity {

    private ImageView bookCoverImage;
    private TextView bookTitle, bookAuthor, bookRatingText, bookYear, bookBlurb, bookReview;
    private Chip bookGenreChip;
    private RatingBar ratingBar;
    private FloatingActionButton fabLike;
    private boolean isLiked = false;
    String currentBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout Edge to Edge
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_book_detail);

        // Atur padding untuk menghindari status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookCoverImage = findViewById(R.id.book_cover_image);
        bookTitle = findViewById(R.id.book_title);
        bookAuthor = findViewById(R.id.book_author);
        bookRatingText = findViewById(R.id.book_rating_text);
        bookYear = findViewById(R.id.book_year);
        bookBlurb = findViewById(R.id.book_blurb);
        bookReview = findViewById(R.id.book_review);
        bookGenreChip = findViewById(R.id.book_genre_chip);
        ratingBar = findViewById(R.id.book_rating_bar);
        fabLike = findViewById(R.id.fab_like);

        // Ambil data yang dikirim dari intent
        Bundle extras = getIntent().getExtras();

        // Tampilkan detail buku berdasarkan data intent
        populateBookDetails(extras);

        // Atur fungsi tombol like dan submit review
        setupReviewSubmission();
        setupLikeButton();
    }

    // Menampilkan semua data buku dari Bundle intent ke tampilan
    private void populateBookDetails(Bundle extras) {
        // Ambil data dari extras
        String title = extras.getString("title", "");
        String author = extras.getString("author", "");
        String blurb = extras.getString("blurb", "");
        String genre = extras.getString("genre", "");
        String review = extras.getString("review", "");
        String imageUri = extras.getString("imageUri", "");
        int year = extras.getInt("year", 0);
        isLiked = extras.getBoolean("liked", false);
        int rating = extras.getInt("rating", 0);

        // Set data ke tampilan
        bookTitle.setText(title);
        bookAuthor.setText("By " + author);
        bookRatingText.setText(rating + "/5");
        bookYear.setText("Release Year : " + String.valueOf(year));
        currentBookTitle = title; // Simpan judul buku saat ini
        bookBlurb.setText(blurb);
        bookReview.setText(review);
        bookGenreChip.setText(genre);
        ratingBar.setRating(rating);

        updateLikeButtonIcon(); // Update icon like sesuai status

        // Tampilkan gambar cover buku
        Glide.with(this)
                .load(imageUri)
                .placeholder(R.drawable.placeholder_image)
                .into(bookCoverImage);
    }

    // Atur aksi klik untuk tombol like
    private void setupLikeButton() {
        fabLike.setOnClickListener(v -> {
            isLiked = !isLiked; // Toggle status like
            updateLikeButtonIcon(); // Perbarui icon

            // Perbarui data like di list buku
            for (Book book : DataBook.bookList) {
                if (book.title.equals(currentBookTitle)) {
                    book.isLiked = isLiked;
                    break;
                }
            }
        });
    }

    // Menampilkan icon love sesuai status like
    private void updateLikeButtonIcon() {
        if (isLiked) {
            fabLike.setImageResource(R.drawable.ic_favorite);
        } else {
            fabLike.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    // Atur aksi submit review dari pengguna
    private void setupReviewSubmission() {
        // Inisialisasi komponen rating dan review input dari user
        RatingBar userRatingBar = findViewById(R.id.user_rating_bar);
        EditText userReviewInput = findViewById(R.id.user_review_input);
        Button submitReviewButton = findViewById(R.id.submit_review_button);

        // Aksi saat tombol submit review ditekan
        submitReviewButton.setOnClickListener(v -> {
            int userRating = (int) userRatingBar.getRating(); // Ambil nilai rating
            String userReview = userReviewInput.getText().toString(); // Ambil isi review

            // Validasi inputan
            if (userRating == 0 || userReview.isEmpty()) {
                Toast.makeText(this, "Please provide a rating and review", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update data rating dan review di objek buku
            for (Book book : DataBook.bookList) {
                if (book.title.equals(currentBookTitle)) {
                    book.rating = userRating;
                    book.review = userReview;
                    break;
                }
            }

            // Tampilkan rating dan review baru di tampilan
            ratingBar.setRating(userRating);
            bookReview.setText(userReview);

            // Reset form setelah submit
            userRatingBar.setRating(0);
            userReviewInput.setText("");

            // Tampilkan notifikasi berhasil
            Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
        });
    }
}
