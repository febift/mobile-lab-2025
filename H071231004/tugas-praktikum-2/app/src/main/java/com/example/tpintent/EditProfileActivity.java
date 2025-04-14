package com.example.tpintent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    Spinner editGender;
    ImageView backedit;
    CircleImageView cirleImageViewEdit;
    Uri profileImageUri;
    MaterialButton saveButton, cancelButton;
    EditText editMediaAccount, editMediaAccount2;
    TextView editUsername, editBio, editCompany, editLocation;

    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        backedit = findViewById(R.id.backedit);
        backedit.setOnClickListener(v -> finish());

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> finish());

        cirleImageViewEdit = findViewById(R.id.circleImageViewEdit);
        editUsername = findViewById(R.id.namamu);
        editBio = findViewById(R.id.bioEditText);
        editGender = findViewById(R.id.gender);
        String[] options = {"Don't specify", "They", "She", "He", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        editGender.setAdapter(adapter);
        editCompany = findViewById(R.id.company);
        editLocation = findViewById(R.id.location);
        editMediaAccount = findViewById(R.id.mediaAccount1);
        editMediaAccount2 = findViewById(R.id.mediaAccount2);
        saveButton = findViewById(R.id.saveButton);

        // Register image picker
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        cirleImageViewEdit.setImageURI(uri);
                        String path = saveImageToInternalStorage(uri);
                        profileImageUri = Uri.fromFile(new File(path));
                    }
                }
        );

        cirleImageViewEdit.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        saveButton.setOnClickListener(v -> saveProfileData());
        loadProfileData();

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(!s.toString().trim().isEmpty());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        editUsername.setText(sharedPreferences.getString("username", ""));
        editBio.setText(sharedPreferences.getString("bio", ""));
        String savedGender = sharedPreferences.getString("gender", "");
        if (!savedGender.isEmpty()) {
            ArrayAdapter adapter = (ArrayAdapter) editGender.getAdapter();
            int position = adapter.getPosition(savedGender);
            editGender.setSelection(position);
        }
        editCompany.setText(sharedPreferences.getString("company", ""));
        editLocation.setText(sharedPreferences.getString("location", ""));
        editMediaAccount.setText(sharedPreferences.getString("MediaAccount", ""));
        editMediaAccount2.setText(sharedPreferences.getString("MediaAccount2", ""));

        String profileImagePath = sharedPreferences.getString("profileImage", null);
        if (profileImagePath != null) {
            cirleImageViewEdit.setImageURI(Uri.fromFile(new File(profileImagePath)));
        } else {
            cirleImageViewEdit.setImageResource(R.drawable.friren); // Default image
        }
    }

    private void saveProfileData() {
        String name = editUsername.getText().toString().trim();

        if (name.isEmpty()) {
            editUsername.setError("Name is required");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", name);
        editor.putString("bio", editBio.getText().toString().trim());
        String selectedGender = editGender.getSelectedItem().toString();
        editor.putString("gender", selectedGender);
        editor.putString("company", editCompany.getText().toString().trim());
        editor.putString("location", editLocation.getText().toString().trim());
        editor.putString("MediaAccount", editMediaAccount.getText().toString().trim());
        editor.putString("MediaAccount2", editMediaAccount2.getText().toString().trim());

        if (profileImageUri != null) {
            editor.putString("profileImage", profileImageUri.getPath());
        }

        editor.apply();
        Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File file = new File(getFilesDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}