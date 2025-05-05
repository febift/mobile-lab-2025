package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements FeedProfileAdapter.ItemClickListener, StoryAdapter.ItemclickListener {

    private RecyclerView rvProfileFeed, rvProfileStory;
    private FeedProfileAdapter feedProfileAdapter;
    private StoryAdapter storyAdapter;
    private TextView jpost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        jpost = view.findViewById(R.id.jpost);
        int jumlahPost = DataDummy.feedProfiles.size();
        jpost.setText(jumlahPost+"");
        rvProfileFeed = view.findViewById(R.id.rv_profile_feed);
        rvProfileFeed.setHasFixedSize(true);

        feedProfileAdapter = new FeedProfileAdapter(DataDummy.feedProfiles, this);
        rvProfileFeed.setAdapter(feedProfileAdapter);


        rvProfileStory = view.findViewById(R.id.story_highlight);
        rvProfileStory.setHasFixedSize(true);
        rvProfileStory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        storyAdapter = new StoryAdapter(DataDummy.stories, this);
        rvProfileStory.setAdapter(storyAdapter);



        return view;
    }

    @Override
    public void onItemClick(FeedProfile feedProfile) {
        Bundle bundle = new Bundle();
        bundle.putInt("pfp", R.drawable.diah);
        bundle.putString("imageUri", feedProfile.getImageUri());
        bundle.putString("caption", feedProfile.getCaption());
        bundle.putString("like", feedProfile.getLike());
        bundle.putString("comment", feedProfile.getComment());
        bundle.putString("send", feedProfile.getSend());

        FeedProfileFragment fragment = new FeedProfileFragment();
        fragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void refreshFeed() {
        if (feedProfileAdapter != null) {
            feedProfileAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Story story) {
        Intent intent = new Intent(getActivity(), HighlightActivity.class);
        intent.putExtra("STORY_TITLE", story.getStoryTitle());
        intent.putExtra("STORY_IMAGE", story.getStoryCover());
        startActivity(intent);
    }
}