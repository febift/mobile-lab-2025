package com.example.instagram;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private ArrayList<Story> stories;
    private ItemclickListener listener;

    public interface ItemclickListener{
        void onItemClick(Story story);
    }

    public StoryAdapter(ArrayList<Story> stories, ItemclickListener listener){
        this.stories = stories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.story_cover.setImageResource(story.getStoryCover());
        holder.story_title.setText(story.getStoryTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HighlightActivity.class);
            intent.putExtra("STORY_TITLE", story.getStoryTitle());
            intent.putExtra("STORY_IMAGE", story.getStoryCover());
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView story_cover;
        private TextView story_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            story_cover = itemView.findViewById(R.id.story_cover);
            story_title = itemView.findViewById(R.id.story_title);
        }
    }
}