package com.example.tugaspraktikum3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.models.Post;
import com.example.tugaspraktikum3.utils.DataSource;
import com.example.tugaspraktikum3.utils.ImageUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView imageViewBack, imageViewPost;
    private CircleImageView imageViewProfile;
    private TextView textViewUsername, textViewLikes, textViewCaption;

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        imageViewPost = findViewById(R.id.imageViewPost);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewLikes = findViewById(R.id.textViewLikes);
        textViewCaption = findViewById(R.id.textViewCaption);

        int postId = getIntent().getIntExtra("POST_ID", 0);
        if (postId != 0) {
            for (Post p : DataSource.getFeedPostList()) {
                if (p.getId() == postId) {
                    post = p;
                    break;
                }
            }

            // If not found, look in profile posts
            if (post == null) {
                for (Post p : DataSource.getProfilePostList()) {
                    if (p.getId() == postId) {
                        post = p;
                        break;
                    }
                }
            }

            if (post != null) {
                // Set up the UI with post data
                setupPostDetail();
            }
        }

        // Back button click listener
        imageViewBack.setOnClickListener(v -> finish());
    }

    private void setupPostDetail() {
        // Set post image - Cek apakah ada gambar yang diupload
        if (ImageUtils.hasUploadedImage(post.getId())) {
            // Gunakan gambar yang diupload
            Bitmap uploadedImage = ImageUtils.getUploadedImage(post.getId());
            imageViewPost.setImageBitmap(uploadedImage);
        } else {
            // Gunakan gambar default dari resource
            imageViewPost.setImageResource(post.getImageResourceId());
        }

        // Set user profile image and username
        imageViewProfile.setImageResource(post.getUser().getProfileImageResourceId());
        textViewUsername.setText(post.getUser().getUsername());

        // Set likes and caption
        textViewLikes.setText(post.getLikesCount() + " likes");
        textViewCaption.setText(post.getCaption());
    }
}