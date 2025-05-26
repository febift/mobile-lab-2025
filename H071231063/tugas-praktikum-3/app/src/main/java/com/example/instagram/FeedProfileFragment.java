package com.example.instagram;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
public class FeedProfileFragment extends Fragment {
    CircleImageView pfp;

    ImageView content;
    TextView caption, like, comment, send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed_profile, container, false);

        pfp = view.findViewById(R.id.pfp);
        content = view.findViewById(R.id.content);
        caption = view.findViewById(R.id.caption);
        like = view.findViewById(R.id.like);
        comment = view.findViewById(R.id.comment);
        send = view.findViewById(R.id.send);

        if (getArguments() != null) {
            String imageUri = getArguments().getString("imageUri");
            String captionText = getArguments().getString("caption");
            String likeText = getArguments().getString("like");
            String commentText = getArguments().getString("comment");
            String sendText = getArguments().getString("send");
            int pfpid = getArguments().getInt("pfp", R.drawable.diah);
            pfp.setImageResource(pfpid);
            Glide.with(this).load(Uri.parse(imageUri)).into(content);
            caption.setText(captionText);
            like.setText(likeText);
            comment.setText(commentText);
            send.setText(sendText);

        }

        return view;
    }
}
