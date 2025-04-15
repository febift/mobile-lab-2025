package com.example.tpintent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ImageView menutiga;
    CircleImageView circleImageView;
    TextView usernameMain, bioChanged, companyChanged, locationChanged, genderChanged;
    Button openMediaAccount, openMediaAccount2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menutiga = findViewById(R.id.menutiga);
        circleImageView = findViewById(R.id.circleImageView);
        usernameMain = findViewById(R.id.username);
        bioChanged = findViewById(R.id.bio);
        companyChanged = findViewById(R.id.textView);
        locationChanged = findViewById(R.id.textView2);
        genderChanged = findViewById(R.id.titikshe);
        openMediaAccount = findViewById(R.id.btn_linkedin);
        openMediaAccount2 = findViewById(R.id.btn_instagram);

        menutiga.setOnClickListener(this::showPopupMenu);

        openMediaAccount.setOnClickListener(view -> openLinkFromPrefs("MediaAccount"));
        openMediaAccount2.setOnClickListener(view -> openLinkFromPrefs("MediaAccount2"));

        loadProfileData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfileData();
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        // Hindari NullPointerException
        usernameMain.setText(sharedPreferences.getString("username", "Your Name"));
        bioChanged.setText(sharedPreferences.getString("bio", "Your bio here"));
        companyChanged.setText(sharedPreferences.getString("company", "Company"));
        locationChanged.setText(sharedPreferences.getString("location", "Location"));
        genderChanged.setText(sharedPreferences.getString("gender", "Don't specify"));

        // Periksa apakah gambar profil tersimpan
        String profileImagePath = sharedPreferences.getString("profileImage", null);
        if (profileImagePath != null && new File(profileImagePath).exists()) {
            circleImageView.setImageURI(Uri.fromFile(new File(profileImagePath)));
        } else {
            circleImageView.setImageResource(R.drawable.friren); // Gambar default
        }

        // Periksa apakah link media sosial tersimpan
        String media1 = sharedPreferences.getString("MediaAccount", "");
        openMediaAccount.setText(media1.isEmpty() ? "LinkedIn" : "LinkedIn");

        String media2 = sharedPreferences.getString("MediaAccount2", "");
        openMediaAccount2.setText(media2.isEmpty() ? "Instagram" : "Instagram");
    }

    private void openLinkFromPrefs(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        String url = sharedPreferences.getString(key, "");

        if (!url.isEmpty() && url.startsWith("http")) {
            try {
                Uri uri = Uri.parse(url);
                Intent openLink = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(openLink);
            } catch (Exception e) {
                Toast.makeText(this, "Gagal membuka link", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Link tidak tersedia atau tidak valid", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add(0, 1, 0, "Settings");
        popup.getMenu().add(0, 2, 1, "Edit Profile");

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == 2) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
        popup.show();
    }
}