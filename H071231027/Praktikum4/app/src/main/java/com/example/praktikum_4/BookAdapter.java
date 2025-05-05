package com.example.praktikum_4;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum_4.databinding.ItemBooksBinding;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(List<Book> books, OnItemClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private final ItemBooksBinding binding;

        public BookViewHolder(ItemBooksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Book book, OnItemClickListener listener) {
            if (book.getImageUri() != null) {
                Glide.with(binding.coverBook.getContext())
                        .load(book.getImageUri())
                        .into(binding.coverBook);
            } else if (book.getCoverResId() != null) {
                binding.coverBook.setImageResource(book.getCoverResId());
            } else {
                Glide.with(binding.coverBook.getContext())
                        .load(book.getCoverUrl())
                        .into(binding.coverBook);
            }

            String title = book.getTitle();
            if (title.length() > 13) {
                title = title.substring(0, 13) + "...";
            }
            binding.title.setText(title);

            binding.getRoot().setOnClickListener(v -> listener.onItemClick(book));
        }

    }
    public void updateData(List<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBooksBinding binding = ItemBooksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(books.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }



}

