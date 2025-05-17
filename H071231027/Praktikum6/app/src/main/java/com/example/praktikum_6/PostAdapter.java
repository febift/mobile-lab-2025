package com.example.praktikum_6;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum_6.databinding.ItemPostBinding;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW = 0;
    private static final int FOOTER_VIEW = 1;
    private boolean showLoadMore = false;
    private final List<Post> postList;
    private final Context context;  // Context to start DataActivity
    private AdapterView.OnItemClickListener onItemClickListener;
    private OnLoadMoreListener loadMoreListener;

    // Constructor
    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    // Set the listener for item clicks
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Set the listener for loading more items
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    // Hide load more view
    public void hideLoadMore() {
        showLoadMore = false;
        notifyItemRemoved(postList.size());
    }

    // Get the item type (either regular item or footer view for load more)
    @Override
    public int getItemViewType(int position) {
        if (position == postList.size() && showLoadMore) {
            return FOOTER_VIEW;
        }
        return ITEM_VIEW;
    }

    // Get the item count (adding 1 for the footer view)
    @Override
    public int getItemCount() {
        return postList.size() + (showLoadMore ? 1 : 0);
    }

    // Create the view holder based on the item type
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ITEM_VIEW) {
            ItemPostBinding binding = ItemPostBinding.inflate(inflater, parent, false);
            return new PostViewHolder(binding);
        } else {
            View view = inflater.inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    // Bind data to view holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            Post post = postList.get(position);
            ((PostViewHolder) holder).bind(post);

            // Set click listener for item
            holder.itemView.setOnClickListener(v -> {
                // Start DataActivity when item is clicked
                Intent intent = new Intent(context, DataActivity.class);
                intent.putExtra("character_id", post.getId()); // Passing character ID
                context.startActivity(intent);
            });
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).bind(() -> {
                if (isNetworkConnected()) {
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore();
                    }
                } else {
                    // offline → sembunyikan tombol
                    hideLoadMore();
                }
            });
        }
    }

    // Add new data to the list and notify adapter
    public void addData(List<Post> newPosts) {
        int start = postList.size();
        postList.addAll(newPosts);
        notifyItemRangeInserted(start, newPosts.size());
    }

    // ViewHolder for regular post item
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        public PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Post post) {
            binding.textName.setText(post.getName());
            binding.textSpecies.setText(post.getSpecies());

            Glide.with(binding.getRoot().getContext())
                    .load(post.getImage())
                    .into(binding.imageCharacter);
        }
    }

    // ViewHolder for the footer view (load more)
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private final View btn;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn_load_more);
        }

        public void bind(Runnable onClick) {
            btn.setOnClickListener(v -> onClick.run());
        }
    }

    // Interface for load more listener
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
    public void showLoadMore() {
        if (!showLoadMore) {
            showLoadMore = true;
            notifyItemInserted(postList.size());
        }
    }


}
