package com.example.twitter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST = 1;

    private TextView nameTextView;
    private TextView usernameTextView;
    private ImageView profileImageView;
    private LinearLayout diriLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi view
        nameTextView      = findViewById(R.id.name);
        usernameTextView  = findViewById(R.id.username);
        profileImageView  = findViewById(R.id.profile_picture);
        diriLayout        = findViewById(R.id.diri);
        // Klik bagian profil -> buka EditProfileActivity
        diriLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("name", nameTextView.getText().toString());
            intent.putExtra("username", usernameTextView.getText().toString());
            startActivityForResult(intent, EDIT_PROFILE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Ambil data yang dikirim dari EditProfileActivity
            String updatedName      = data.getStringExtra("name");
            String updatedUsername  = data.getStringExtra("username");
            String imageUriString   = data.getStringExtra("imageUri");

            // Update tampilan teks
            if (updatedName     != null) nameTextView.setText(updatedName);
            if (updatedUsername != null) usernameTextView.setText(updatedUsername);

            // Jika ada URI, parse dan tampilkan gambarnya
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                profileImageView.setImageURI(imageUri);
            }
        }
    }
}
