package com.example.twitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editName;
    private EditText editUsername;
    private ImageView profileImageView;
    private Button saveButton;
    private Uri selectedImageUri;  // menyimpan pilihan dari galeri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi view
        editName          = findViewById(R.id.edit_name);
        editUsername      = findViewById(R.id.edit_username);
        profileImageView  = findViewById(R.id.edit_profile_image);
        saveButton        = findViewById(R.id.save_button);

        // Pre-fill jika ada data dari MainActivity
        Intent intent = getIntent();
        editName.setText(intent.getStringExtra("name"));
        editUsername.setText(intent.getStringExtra("username"));

        // Klik gambar → buka galeri
        profileImageView.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pick.setType("image/*");
            startActivityForResult(pick, PICK_IMAGE_REQUEST);
        });

        // Klik Simpan → kirim data kembali
        saveButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", editName.getText().toString());
            resultIntent.putExtra("username", editUsername.getText().toString());
            if (selectedImageUri != null) {
                // Kirim URI sebagai String
                resultIntent.putExtra("imageUri", selectedImageUri.toString());
            }
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Tangani hasil dari galeri
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            // Tampilkan langsung di UI edit page
            profileImageView.setImageURI(selectedImageUri);
        }
    }
}
