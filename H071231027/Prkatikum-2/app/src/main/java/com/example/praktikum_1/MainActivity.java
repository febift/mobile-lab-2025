package com.example.praktikum_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String PROFILE_IMAGE_FILE_NAME = "profile_picture.jpg";
    private static final String TAG = "MainActivity";
    private static final String SAVED_IMAGE_URI_KEY = "image_uri";
    ImageView setting, imageprofil;
    private Uri currentImageUri;
    TextView namaInput, usernameInput;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setting = findViewById(R.id.setting);
        imageprofil = findViewById(R.id.imageprofil);
        namaInput = findViewById(R.id.nama);
        usernameInput = findViewById(R.id.username);

        sharedPreferences = getSharedPreferences(profil.SHARED_PREFS, MODE_PRIVATE);
        loadNama();
        loadUsername();

        if (savedInstanceState != null) {
            String savedImageUriString = savedInstanceState.getString(SAVED_IMAGE_URI_KEY);
            if (savedImageUriString != null) {
                currentImageUri = Uri.parse(savedImageUriString);
                imageprofil.setImageURI(currentImageUri);
            }
        } else {
            loadImageFromIntent();
        }

        setting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });
    }
    private void loadNama(){
        String nama = sharedPreferences.getString(profil.NAMA_KEY, ""); // "" adalah nilai default jika tidak ada nama yang tersimpan
        // Mengubah teks di TextView
        namaInput.setText(nama);
        if(nama.isEmpty()){
            namaInput.setText("Kevin Ardana");
        }else{
            namaInput.setText(nama);
        }
    }

    private void loadUsername(){
        String username = sharedPreferences.getString(profil.USERNAME_KEY, ""); // "" adalah nilai default jika tidak ada nama yang tersimpan
        // Mengubah teks di TextView
        usernameInput.setText(username);
        if(username.isEmpty()){
            usernameInput.setText("KevinArdan3");
        }else{
            usernameInput.setText(username);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current image URI
        if (currentImageUri != null) {
            outState.putString(SAVED_IMAGE_URI_KEY, currentImageUri.toString());
        }
    }
    private void loadImageFromIntent() {
        String imageUriString = getIntent().getStringExtra(profil.IMAGE_URI_KEY);
        if (imageUriString != null) {
            if (imageUriString.equals("no_image")) {
                setDefaultImage();
            }
            else {
                try {
                    Uri imageUri = Uri.parse(imageUriString);
                    currentImageUri = imageUri;
                    imageprofil.setImageURI(imageUri);
                } catch (Exception e) {
                    Log.e(TAG, "Error loading image from URI", e);
                    setDefaultImage();
                }
            }
        } else {
            loadProfileImage();
        }
    }
    private void loadProfileImage() {
        File file = new File(getFilesDir(), PROFILE_IMAGE_FILE_NAME);
        if (file.exists()) {
            try {
                currentImageUri = Uri.fromFile(file);
                imageprofil.setImageURI(currentImageUri);
            } catch (Exception e) {
                Log.e(TAG, "Error loading profile image", e);
                setDefaultImage();
            }
        } else {
            setDefaultImage();
        }
    }
    private void setDefaultImage() {
        imageprofil.setImageResource(R.drawable.profile);
    }

}