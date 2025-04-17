package com.example.tp1_h071231063;

import android.content.Intent;                      // Untuk berpindah antar activity
import android.content.SharedPreferences;          // Menyimpan data lokal secara persistent
import android.graphics.Bitmap;                    // Untuk menyimpan gambar avatar
import android.graphics.BitmapFactory;             // Mengubah byte[] menjadi Bitmap
import android.net.Uri;                            // Untuk menyimpan lokasi gambar dari galeri
import android.os.Bundle;                          // Data bawaan saat activity dibuat
import android.provider.MediaStore;                // Untuk mengambil gambar dari galeri
import android.util.Base64;                        // Encode/decode gambar ke base64 string
import android.widget.*;                           // Mengakses komponen UI: TextView, EditText, Button, dll
import androidx.annotation.Nullable;               // Anotasi untuk parameter yang bisa null
import androidx.appcompat.app.AppCompatActivity;   // Activity dengan dukungan ActionBar modern
import java.io.ByteArrayOutputStream;              // Untuk mengubah Bitmap ke byte array
import java.io.InputStream;                        // Untuk membaca data dari URI

public class ProfileActivity extends AppCompatActivity {
    ImageButton backButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView avatarImageView;
    private EditText nameEditText, usernameEditText, passwordEditText, emailEditText;
    private Button deleteAccountButton;
    private TextView changeAvatarText;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImageView = findViewById(R.id.avatarImageView);
        changeAvatarText = findViewById(R.id.changeAvatarText);
        nameEditText = findViewById(R.id.nameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
        loadProfile();

        changeAvatarText.setOnClickListener(view -> openGallery());
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> {
            prefs.edit()
                    .putString("name", nameEditText.getText().toString())
                    .putString("username", usernameEditText.getText().toString())
                    .putString("password", passwordEditText.getText().toString())
                    .putString("email", emailEditText.getText().toString())
                    .apply();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", nameEditText.getText().toString());
            resultIntent.putExtra("username", usernameEditText.getText().toString());
            resultIntent.putExtra("avatar", prefs.getString("avatar", null));
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        deleteAccountButton.setOnClickListener(view -> {
            prefs.edit().clear().apply();
            Toast.makeText(this, "Akun dihapus", Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("account_deleted", true);
            setResult(RESULT_OK, resultIntent);
            finish(); // Kembali ke MainActivity
        });

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                avatarImageView.setImageBitmap(selectedImage);
                saveImageToPrefs(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageToPrefs(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        prefs.edit().putString("avatar", imageEncoded).apply();
    }

    private void loadProfile() {
        nameEditText.setText(prefs.getString("name", "Amalia Diah Ramadani"));
        usernameEditText.setText(prefs.getString("username", "AmaliaDiah"));
        passwordEditText.setText(prefs.getString("password", "password"));
        emailEditText.setText(prefs.getString("email", "amaliadiahramadani@gmail.com"));

        String imageString = prefs.getString("avatar", null);
        if (imageString != null) {
            byte[] decoded = Base64.decode(imageString, Base64.DEFAULT);
            avatarImageView.setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.length));
        }
    }
}
