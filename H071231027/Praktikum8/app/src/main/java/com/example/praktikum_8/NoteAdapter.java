package com.example.praktikum_8;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikum_8.databinding.ItemNoteBinding;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    private final List<Note> noteList;
    private final OnNoteClickListener listener;

    public NoteAdapter(List<Note> noteList, OnNoteClickListener listener) {
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.bind(note, listener);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoteBinding binding;

        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final Note note, final OnNoteClickListener listener) {
            binding.tvTitle.setText(note.getTitle());
            binding.tvContent.setText(note.getContent());

            // Check if the note has been updated
            if (note.getUpdatedAt() != null && !note.getUpdatedAt().isEmpty()) {
                binding.tvCreated.setText("Updated at " + note.getUpdatedAt());
            } else {
                binding.tvCreated.setText("Created at " + note.getCreatedAt());
            }

            binding.getRoot().setOnClickListener(v -> listener.onNoteClick(note));
        }
    }
}