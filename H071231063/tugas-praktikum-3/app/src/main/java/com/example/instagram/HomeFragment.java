package com.example.instagram;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment implements FeedAdapter.ItemClickListener{
    private RecyclerView rvFeed;
    private FeedAdapter feedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvFeed = view.findViewById(R.id.rv_feed);
        rvFeed.setHasFixedSize(true);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        feedAdapter = new FeedAdapter(DataDummy.feeds, this);
        rvFeed.setAdapter(feedAdapter);

        return view;
    }

    @Override
    public void onItemClick(Feed feed) {
        Bundle bundle = new Bundle();
        bundle.putString("username", feed.getUsername());
        bundle.putString("name", feed.getName());
        bundle.putString("bio", feed.getBio());
        bundle.putInt("profileImage", feed.getPfp());
        bundle.putString("follower",feed.getFollower());
        bundle.putString("comment", feed.getComment());
        bundle.putString("send", feed.getSend());
        bundle.putString("like", feed.getLike());
        bundle.putString("following",feed.getFollowing());
        bundle.putInt("content", feed.getContent());

        StrangeProfileFragment fragment = new StrangeProfileFragment();
        fragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}