package com.example.instagram;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FeedProfileAdapter extends RecyclerView.Adapter<FeedProfileAdapter.ViewHolder> {
private List<FeedProfile> feedProfiles;
private ItemClickListener listener;


public interface ItemClickListener {
    void onItemClick(FeedProfile feedProfile);

}


public FeedProfileAdapter(ArrayList<FeedProfile> feedProfiles, ItemClickListener listener) {
    this.feedProfiles = feedProfiles;
    this.listener = listener;
}


@NonNull
@Override
public FeedProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_profile_item, parent, false);
    return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull FeedProfileAdapter.ViewHolder holder, int position) {
    FeedProfile feedProfile = feedProfiles.get(position);
    Glide.with(holder.itemView.getContext())
            .load(Uri.parse(feedProfile.getImageUri()))
            .into(holder.banner);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(feedProfile);
            }
        }
    });
}

@Override
public int getItemCount() {
    return feedProfiles.size();
}

public class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView banner;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        banner = itemView.findViewById(R.id.banner);
    }
    }
}
