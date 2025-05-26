package com.example.instagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class HighlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_highlight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String title = getIntent().getStringExtra("STORY_TITLE");
        int imageRes = getIntent().getIntExtra("STORY_IMAGE", R.drawable.story1);

        CircleImageView storyCover = findViewById(R.id.story_cover);
        TextView storyTitle = findViewById(R.id.story_title);
        ImageView mainImage = findViewById(R.id.main_image);

        storyCover.setImageResource(imageRes);
        storyTitle.setText(title);
        mainImage.setImageResource(imageRes);
        findViewById(R.id.btn_close).setOnClickListener(v -> finish());
    }
}
