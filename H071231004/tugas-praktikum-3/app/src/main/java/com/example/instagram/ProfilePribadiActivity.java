package com.example.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.adapter.FeedAdapter;
import com.example.instagram.adapter.ProfileAdapter;
import com.example.instagram.model.FeedModel;
import com.example.instagram.model.PhotoModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePribadiActivity extends AppCompatActivity {
    CircleImageView halamanAkun;
    private RecyclerView recyclerView;
    private FeedAdapter feedAdapter;
    private List<FeedModel> userFeedsOnUpdate = FeedRepository.getFeedList();
    private List<FeedModel> allFeeds;
    private List<FeedModel> userFeeds;

    ProfileAdapter profilePribadiAdapter;

    private ImageView fotoProfil, fotoHighlight, backHome, postingFoto;
    private TextView namaHighlight, tvFollowersCount, tvPostinganCount, tvFollowingCount, bioProfile, tvUsernameProfile, tvUsernameProfile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_pribadi);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backHome = findViewById(R.id.backHome);
        tvUsernameProfile = findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile2 = findViewById(R.id.tvUsernameProfile2);
        fotoProfil = findViewById(R.id.fotoProfil);
        bioProfile = findViewById(R.id.bioProfile);
        fotoHighlight = findViewById(R.id.fotoHighlight);
        namaHighlight = findViewById(R.id.namaHighlight);
        tvFollowersCount = findViewById(R.id.tvFollowersCount);
        tvPostinganCount = findViewById(R.id.tvPostinganCount);
        tvFollowingCount = findViewById(R.id.tvFollowingCount);
        recyclerView = findViewById(R.id.profile_feed);
        halamanAkun = findViewById(R.id.halamanAkun);
        postingFoto = findViewById(R.id.postingFoto);

        postingFoto.setOnClickListener( v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), 101);
        });

        backHome.setOnClickListener( v -> {
            finish();
        });

        allFeeds = getIntent().getParcelableArrayListExtra("feedList");
        userFeeds = new ArrayList<>();

        String username = "maomaoo><";

        for (FeedModel feed : allFeeds) {
            if (feed.getUsername().equals(username)) {
                userFeeds.add(feed);
            }
        }

        if (!userFeeds.isEmpty()) {
            FeedModel firstFeed = userFeeds.get(0);
            tvUsernameProfile.setText(firstFeed.getUsername());
            tvUsernameProfile2.setText(firstFeed.getUsername());
            fotoProfil.setImageResource(firstFeed.getProfileImage());
            bioProfile.setText(firstFeed.getBio());
            halamanAkun.setImageResource(firstFeed.getProfileImage());
            fotoHighlight.setImageResource(firstFeed.getHighlightImage());
            namaHighlight.setText(firstFeed.getHighlightName());
            tvFollowersCount.setText(String.valueOf(formatNumber(firstFeed.getFollowerCount())));
            tvPostinganCount.setText(String.valueOf(formatNumber(firstFeed.getPostCount())));
            tvFollowingCount.setText(String.valueOf(formatNumber(firstFeed.getFollowingCount())));
        } else {
            tvUsernameProfile.setText("maomaoo><");
            fotoProfil.setImageResource(R.drawable.maomao);
            bioProfile.setText("maomaoo\n" +
                    "she/her\n" +
                    "\uD83C\uDF49 Atychiphobia | Undergraduate Student");
            fotoHighlight.setImageResource(R.drawable.ningninghg);
            namaHighlight.setText("Achievement");
            tvFollowersCount.setText("950");
            tvPostinganCount.setText(String.valueOf(formatNumber(0)));
            tvFollowingCount.setText("311");
            halamanAkun.setImageResource(R.drawable.maomao);
        }

        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        profilePribadiAdapter = new ProfileAdapter(this, userFeeds);
        recyclerView.setAdapter(profilePribadiAdapter);

        fotoHighlight.setOnClickListener(v -> {
            ArrayList<PhotoModel> staticPhotos = new ArrayList<>();
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/72/df/54/72df54ac66315cbcd209c475e438301b.jpg")));
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/11/85/99/11859904c1353e43e4fb7f1703a4e738.jpg")));
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/1f/04/df/1f04df4f84738a967b966d6b16927e69.jpg")));
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/9b/ee/41/9bee414a68550ef3c995050a0095ef6c.jpg")));
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/20/68/d2/2068d2c8989b1238f45b5a2a15215669.jpg")));
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/05/28/2b/05282b4dbc03bc32e74b3ce7e06d4695.jpg")));
            staticPhotos.add(new PhotoModel(Uri.parse("https://i.pinimg.com/736x/fb/50/3f/fb503f9f6ffae7df8dfaf90d1224c14a.jpg")));

            Intent intent = new Intent(ProfilePribadiActivity.this, highlightActivity.class);
            intent.putExtra("username", "maomaoo><");
            intent.putExtra("profileImage", R.drawable.maomao);
            intent.putParcelableArrayListExtra("photos", staticPhotos);
            startActivity(intent);
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            ArrayList<Uri> selectedImages = new ArrayList<>();

            if (data.getClipData() != null) {
                int count = Math.min(data.getClipData().getItemCount(), 6); // max 6
                for (int i = 0; i < count; i++) {
                    selectedImages.add(data.getClipData().getItemAt(i).getUri());
                }
            } else if (data.getData() != null) {
                selectedImages.add(data.getData());
            }

            ArrayList<PhotoModel> photoModels = new ArrayList<>();
            for (Uri uri : selectedImages) {
                try {
                    Uri localUri = copyUriToInternalStorage(uri);
                    photoModels.add(new PhotoModel(localUri));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show();
                }
            }
            Intent intent = new Intent(this, CaptionActivity.class);
            intent.putParcelableArrayListExtra("selectedPhotos", photoModels);
            startActivityForResult(intent, 102);
        } else if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
            ArrayList<PhotoModel> selectedPhotos = data.getParcelableArrayListExtra("selectedPhotos");
            String caption = data.getStringExtra("caption");

            FeedModel newFeed = new FeedModel(
                    R.drawable.maomao,
                    "maomao><",
                    selectedPhotos,
                    caption,
                    0,
                    0,
                    "maomao\n" +
                            "she/her\n" +
                            "\uD83C\uDF49 Atychiphobia | Undergraduate Student",
                    "Achievement",
                    R.drawable.ningninghg,
                    selectedPhotos,FeedRepository.getPostCount(), 999, 311
            );
            FeedRepository.addFeed(newFeed);
            profilePribadiAdapter.notifyItemInserted(profilePribadiAdapter.getItemCount() - 1);
            recyclerView.scrollToPosition(0);

            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userFeeds.clear();
        allFeeds = FeedRepository.getFeedList(); // ambil ulang
        for (FeedModel feed : allFeeds) {
            if (feed.getUsername().equals("maomaoo><")) {
                userFeeds.add(feed);
            }
        }

        profilePribadiAdapter.notifyDataSetChanged();

    }

    private Uri copyUriToInternalStorage(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        if (inputStream == null) throw new FileNotFoundException("Unable to open input stream");

        File directory = new File(getFilesDir(), "images");
        if (!directory.exists()) directory.mkdirs();

        String fileName = "img_" + System.currentTimeMillis() + ".jpg";
        File outFile = new File(directory, fileName);

        try (OutputStream outputStream = new FileOutputStream(outFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

        return Uri.fromFile(outFile);
    }

    private String formatNumber(int number) {
        if (number >= 1_000_000) {
            return number / 1_000_000 + "M";
        } else if (number >= 1_000) {
            return number / 1_000 + "K";
        } else {
            return String.valueOf(number);
        }
    }

    // Method yang bisa kamu panggil nanti jika postingan baru ditambahkan:
    public void addNewPost(FeedModel newFeed) {
        userFeeds.add(0, newFeed);
        feedAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }
}
