package com.example.praktikum_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum_4.databinding.ItemBooksBinding;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private List<Favorite> favoriteList;
    private OnItemClickListener listener;

    public FavoriteAdapter(Context context, List<Favorite> favoriteList, OnItemClickListener listener) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBooksBinding binding = ItemBooksBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);
        holder.binding.title.setText(favorite.getTitle());
        if (favorite.getImageUri() != null && !favorite.getImageUri().isEmpty()) {
            Glide.with(context)
                    .load(favorite.getImageUri())
                    .into(holder.binding.coverBook);
        } else if (favorite.getCoverResId() != null) {
            holder.binding.coverBook.setImageResource(favorite.getCoverResId());
        } else{
            Glide.with(context)
                    .load(favorite.getCoverUrl())
                    .into(holder.binding.coverBook);
        }


        // Tambahkan klik listener
        holder.binding.coverBook.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(favorite.getBook());
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemBooksBinding binding;

        public ViewHolder(ItemBooksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void updateData(List<Favorite> newList) {
        this.favoriteList = newList;
        notifyDataSetChanged();
    }

}
