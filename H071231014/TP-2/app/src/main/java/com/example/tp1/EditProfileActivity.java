package com.example.tp1;

import static com.example.tp1.R.id.edit_profile_image;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private EditText nameEdit;
    private EditText bioEdit;
    private EditText universityEdit;
    private EditText locationEdit;
    private EditText usernameEdit;
    private TextView saveButton;
    private ImageView backButton;
    private TextView changePhotoText;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profileImage = findViewById(edit_profile_image);
        nameEdit = findViewById(R.id.edit_name);
        usernameEdit = findViewById(R.id.edit_username);
        bioEdit = findViewById(R.id.edit_bio);
        universityEdit = findViewById(R.id.edit_university);
        locationEdit = findViewById(R.id.edit_location);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);
        changePhotoText = findViewById(R.id.change_photo_text);

        Intent intent = getIntent();
        if (intent != null) {
            nameEdit.setText(intent.getStringExtra("name"));
            usernameEdit.setText(intent.getStringExtra("username"));
            bioEdit.setText(intent.getStringExtra("bio"));
            universityEdit.setText(intent.getStringExtra("university"));
            locationEdit.setText(intent.getStringExtra("location"));

            String imageUriString = intent.getStringExtra("profile_image_uri");
            if (imageUriString != null) {
                selectedImageUri = Uri.parse(imageUriString);
                profileImage.setImageURI(selectedImageUri);
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close activity without saving
            }
        });

        View.OnClickListener imageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        };
        profileImage.setOnClickListener(imageClickListener);
        changePhotoText.setOnClickListener(imageClickListener);
    }

    private void saveProfileData() {
        if (nameEdit.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usernameEdit.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();

        resultIntent.putExtra("name", nameEdit.getText().toString());
        resultIntent.putExtra("username", usernameEdit.getText().toString());
        resultIntent.putExtra("bio", bioEdit.getText().toString());
        resultIntent.putExtra("university", universityEdit.getText().toString());
        resultIntent.putExtra("location", locationEdit.getText().toString());

        if (selectedImageUri != null) {
            resultIntent.putExtra("profile_image_uri", selectedImageUri.toString());
        }

        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
        }
    }
}