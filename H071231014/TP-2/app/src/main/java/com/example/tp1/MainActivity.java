package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView nameText;
    private TextView usernameText;
    private TextView bioText;
    private TextView universityText;
    private TextView locationText;
    private ImageView moreButton;

    private static final int EDIT_PROFILE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = findViewById(R.id.profile);
        nameText = findViewById(R.id.textView);
        usernameText = findViewById(R.id.textView2);
        bioText = findViewById(R.id.textView4);
        universityText = findViewById(R.id.textView5);
        locationText = findViewById(R.id.lokasi);
        moreButton = findViewById(R.id.more);

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_edit_profile) {
                    openEditProfileActivity();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void openEditProfileActivity() {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

        intent.putExtra("name", nameText.getText().toString());
        intent.putExtra("username", usernameText.getText().toString().split(" • ")[0]);
        intent.putExtra("bio", bioText.getText().toString());
        intent.putExtra("university", universityText.getText().toString());
        intent.putExtra("location", locationText.getText().toString());

        if (profileImage.getTag() != null) {
            intent.putExtra("profile_image_uri", profileImage.getTag().toString());
        }

        startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra("name");
                String username = data.getStringExtra("username");
                String bio = data.getStringExtra("bio");
                String university = data.getStringExtra("university");
                String location = data.getStringExtra("location");

                nameText.setText(name);
                usernameText.setText(username + " • Women");
                bioText.setText(bio);
                universityText.setText(university);
                locationText.setText(location);

                String imageUriString = data.getStringExtra("profile_image_uri");
                if (imageUriString != null) {
                    Uri imageUri = Uri.parse(imageUriString);
                    profileImage.setImageURI(imageUri);
                    profileImage.setTag(imageUriString);
                }
            }
        }
    }
}