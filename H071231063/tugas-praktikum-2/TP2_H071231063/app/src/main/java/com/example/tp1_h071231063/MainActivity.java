package com.example.tp1_h071231063;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class MainActivity extends AppCompatActivity {

    ImageButton editProfileButton;
    TextView nameTextView, usernameTextView;
    ImageView avatarImageView;

    private final ActivityResultLauncher<Intent> profileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getBooleanExtra("account_deleted", false)) {
                        nameTextView.setText("Amalia Diah Ramadani");
                        usernameTextView.setText("AmaliaDiah");
                        avatarImageView.setImageResource(R.drawable.backround1);

                        // Hapus data lokal juga untuk sync
                        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
                        prefs.edit().clear().apply();
                    } else {
                        String name = result.getData().getStringExtra("name");
                        String username = result.getData().getStringExtra("username");
                        String avatarBase64 = result.getData().getStringExtra("avatar");

                        nameTextView.setText(name);
                        usernameTextView.setText(username);

                        if (avatarBase64 != null) {
                            byte[] decodedBytes = Base64.decode(avatarBase64, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            avatarImageView.setImageBitmap(bitmap);
                        }

                        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("name", name);
                        editor.putString("username", username);
                        editor.putString("avatar", avatarBase64);
                        editor.apply();
                    }
                }


            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatarImageView = findViewById(R.id.avatarImageView);
        editProfileButton = findViewById(R.id.editProfileButton);
        nameTextView = findViewById(R.id.nameTextView);
        usernameTextView = findViewById(R.id.usernameTextView);

        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String username = prefs.getString("username", null);
        String avatarBase64 = prefs.getString("avatar", null);

        if (name == null && username == null && avatarBase64 == null) {
            name = "Amalia Diah Ramadani";
            username = "AmaliaDiah";
            avatarImageView.setImageResource(R.drawable.avatar); // default avatar
        } else {
            if (avatarBase64 != null) {
                byte[] decodedBytes = Base64.decode(avatarBase64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                avatarImageView.setImageBitmap(bitmap);
            }
        }

        nameTextView.setText(name);
        usernameTextView.setText(username);

        editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            profileLauncher.launch(intent);
        });
    }

}
