package com.example.instagram;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    ImageView homeBtn, postBtn, photoProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment());

        homeBtn = findViewById(R.id.homeImg);
        postBtn = findViewById(R.id.postImg);
        photoProfileBtn = findViewById(R.id.profilePhotoImg);

        homeBtn.setOnClickListener(v -> replaceFragment(new HomeFragment()));
        postBtn.setOnClickListener(v -> replaceFragment(new PostFragment()));
        photoProfileBtn.setOnClickListener(v -> replaceFragment(new ProfileFragment()));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void notifyProfileAdapter() {
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("profile_fragment");
        if (profileFragment != null) {
            profileFragment.refreshFeed();
        }
    }
}
