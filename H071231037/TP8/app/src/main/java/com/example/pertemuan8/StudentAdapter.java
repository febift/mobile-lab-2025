package com.example.pertemuan8;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pertemuan8.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private final ArrayList<Student> students = new ArrayList<>();
    private final Activity activity;

    public StudentAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students.clear();
        if (students.size() > 0) {
            this.students.addAll(students);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvNim, tvTimestamp;
        final CardView cardView;

        StudentViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvTimestamp = itemView.findViewById(R.id.tv_item_timestamp);
            cardView = itemView.findViewById(R.id.card_view);
        }

        void bind(Student student) {
            tvName.setText(student.getName());
            tvNim.setText(student.getNim());
            tvTimestamp.setText(student.getCreatedAt());

            String createdAt = student.getCreatedAt();
            String updatedAt = student.getUpdatedAt();






            if (updatedAt != null) {
                tvTimestamp.setText("Updated at " + updatedAt);
            } else if (createdAt != null) {
                tvTimestamp.setText("Created at " + createdAt);
            } else {
                tvTimestamp.setText("Kosong");
            }

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_STUDENT, student);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            });
        }
    }
}

