package com.projeku.tuprak8;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public NoteAdapter(Context context, List<Note> noteList, OnItemClickListener listener) {
        this.context = context;
        this.noteList = noteList != null ? noteList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = noteList.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewContent.setText(currentNote.getContent());

        String createdAtStr = currentNote.getCreatedAt();
        String updatedAtStr = currentNote.getUpdatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

        try {
            Date createdAtDate = sdf.parse(createdAtStr);
            Date updatedAtDate = sdf.parse(updatedAtStr);

            if (createdAtDate != null && updatedAtDate != null) {
                long diffInMillis = Math.abs(updatedAtDate.getTime() - createdAtDate.getTime());
                long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

                if (diffInSeconds > 5) {
                    holder.textViewTimestamp.setText("Updated at " + updatedAtStr);
                } else {
                    holder.textViewTimestamp.setText("Created at " + createdAtStr);
                }
            } else {
                holder.textViewTimestamp.setText("Created at " + createdAtStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.textViewTimestamp.setText("Created at " + createdAtStr);
        }


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentNote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> notes) {
        this.noteList = notes != null ? notes : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewContent;
        TextView textViewTimestamp;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}
