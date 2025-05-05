package com.example.praktikum_4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.praktikum_4.databinding.FragmentAddBookBinding;

import java.util.ArrayList;

public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private SharedViewModel sharedViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        Log.d("AddBookFragment", "onCreateView");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("AddBookFragment", "onViewCreated");

        binding.btnPickImage.setOnClickListener(v -> openGallery());

        binding.btnSubmit.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString().trim();
            String author = binding.etAuthor.getText().toString().trim();
            String categorie = binding.etCategorie.getText().toString().trim();
            String subtitle = binding.etSubtitle.getText().toString().trim();
            String language = binding.etLanguage.getText().toString().trim();
            String description = binding.etDescription.getText().toString().trim();
            String publisher = binding.etPublisher.getText().toString().trim();
            String publishDate = binding.etPublishDate.getText().toString().trim();
            String pages = binding.etPages.getText().toString().trim();
            String format = binding.etFormat.getText().toString().trim();
            String isbn = binding.etISBN.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(getContext(), "Judul dan Penulis wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validasi angka untuk halaman
            int pageCount;
            try {
                pageCount = Integer.parseInt(pages.isEmpty() ? "0" : pages);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Jumlah halaman harus berupa angka!", Toast.LENGTH_SHORT).show();
                return;
            }

            sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

            Book newBook = new Book(
                    title, 0,0, subtitle, author, language, description, categorie,
                    publisher, publishDate, pageCount, format, isbn, new ArrayList<>()
            );
            newBook.setImageUri(selectedImageUri != null ? selectedImageUri.toString() : null);

            sharedViewModel.setNewBook(newBook);

            resetForm();
            Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        Log.d("AddBookFragment", "openGallery");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Glide.with(this)
                    .load(selectedImageUri)
                    .into(binding.ivCoverPreview);
            Log.d("AddBookFragment", "onActivityResult: Image selected");
        }else {
            Log.d("AddBookFragment", "onActivityResult: Image not selected");
        }
    }

    private void resetForm() {
        binding.etTitle.setText("");
        binding.etAuthor.setText("");
        binding.etCategorie.setText("");
        binding.etSubtitle.setText("");
        binding.etLanguage.setText("");
        binding.etDescription.setText("");
        binding.etPublisher.setText("");
        binding.etPublishDate.setText("");
        binding.etPages.setText("");
        binding.etFormat.setText("");
        binding.etISBN.setText("");
        binding.ivCoverPreview.setImageResource(R.drawable.ic_launcher_background); // Reset ke placeholder
        selectedImageUri = null;
        Log.d("AddBookFragment", "resetForm");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}