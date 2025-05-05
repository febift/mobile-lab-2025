package com.example.instagram;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StrangeProfileFragment extends Fragment {

    private CircleImageView profilePicture;
    private TextView username, name, bio, following, follower;
    private RecyclerView rvProfileFeed;
    private FeedProfileAdapter feedProfileAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strange_profile, container, false);

        profilePicture = view.findViewById(R.id.profilePicture);
        username = view.findViewById(R.id.username);
        name = view.findViewById(R.id.name);
        bio = view.findViewById(R.id.bio);
        following= view.findViewById(R.id.following);
        follower= view.findViewById(R.id.follower);

        rvProfileFeed = view.findViewById(R.id.rv_profile_feed);
        rvProfileFeed.setHasFixedSize(true);

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String username = getArguments().getString("username");
            String bio = getArguments().getString("bio");
            String following = getArguments().getString("following");
            String follower = getArguments().getString("follower");
            int profilePictureResId = getArguments().getInt("profileImage", 0);
            int contentResId = getArguments().getInt("content", 0);

            this.name.setText(name);
            this.username.setText(username);
            this.bio.setText(bio);
            this.following.setText(following);
            this.follower.setText(follower);
            Glide.with(this)
                    .load(profilePictureResId)
                    .into(profilePicture);

            String imageUri = "android.resource://" + requireContext().getPackageName() + "/" + contentResId;

            ArrayList<FeedProfile> singlePost = new ArrayList<>();
            singlePost.add(new FeedProfile(
                    imageUri,
                    "My caption",
                    "350", "250", "150"
            ));
            feedProfileAdapter = new FeedProfileAdapter(singlePost, null);
            rvProfileFeed.setAdapter(feedProfileAdapter);
            rvProfileFeed.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        return view;
    }
}
