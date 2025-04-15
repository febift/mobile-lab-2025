package com.example.praktikum_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class profil extends AppCompatActivity {
    private static final String PROFILE_IMAGE_FILE_NAME = "profile_picture.jpg";
    public static final String IMAGE_URI_KEY = "image_uri";
    public static final String NAMA_KEY = "nama";
    public static final String USERNAME_KEY = "username";
    public static final String SHARED_PREFS = "sharedPrefs";
    MaterialCardView back;
    ImageView imageView1;
    TextView gantifoto;
    TextInputEditText inputNama, inputUsername;
    private SharedPreferences sharedPreferences;
    ActivityResultLauncher<Intent> openGalleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back = findViewById(R.id.back);
        gantifoto = findViewById(R.id.ganti);
        imageView1 = findViewById(R.id.imageView1);
        inputNama = findViewById(R.id.inputnama);
        inputUsername = findViewById(R.id.inputnamapengguna);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        loadFromInternalStorage(); // Memuat gambar saat activity dibuat
        loadNama();
        loadUsername();

        back.setOnClickListener(v -> {
            Intent intent = new Intent(profil.this, MainActivity2.class);
            File file = new File(getFilesDir(), PROFILE_IMAGE_FILE_NAME);
            if (file.exists()) {
                intent.putExtra(IMAGE_URI_KEY, Uri.fromFile(file).toString());
            } else {
                intent.putExtra(IMAGE_URI_KEY, "no_image");
            }
            String nama = inputNama.getText().toString();
            String username = inputUsername.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NAMA_KEY, nama);
            editor.putString(USERNAME_KEY, username);
            editor.apply();
            startActivity(intent);
            finish();
        });

        openGalleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            saveToInternalStorage(imageUri);
                            loadFromInternalStorage();
                            Intent intent = new Intent(profil.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

        gantifoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            openGalleryLauncher.launch(intent);
        });

    }
    private void loadNama(){
        String nama = sharedPreferences.getString(NAMA_KEY,"");
        if(nama.isEmpty()){
            inputNama.setText("Kevin Ardana");
        }else{
            inputNama.setText(nama);

        }
    }
    private void loadUsername(){
        String username = sharedPreferences.getString(USERNAME_KEY,"");
        if(username.isEmpty()){
            inputUsername.setText("KevinArdan3");
        }else{
            inputUsername.setText(username);
        }
    }

    private String saveToInternalStorage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            File file = new File(getFilesDir(), "profile_picture.jpg"); // Nama file
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return file.getAbsolutePath(); // Kembalikan path gambar
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadFromInternalStorage() {
        File file = new File(getFilesDir(), "profile_picture.jpg");
        if (file.exists()) {
            imageView1.setImageURI(Uri.fromFile(file));
        }
    }
}