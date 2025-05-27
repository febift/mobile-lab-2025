package com.example.praktikum_8;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.praktikum_8.databinding.ActivityNoteBinding;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {
    private ActivityNoteBinding binding;
    private SQLiteHelper dbHelper;
    private int noteId = -1;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new SQLiteHelper(this);

        if (getIntent().hasExtra("note_id")) {
            noteId = getIntent().getIntExtra("note_id", -1);
            note = dbHelper.getNote(noteId);
            if (note != null) {
                binding.etTitle.setText(note.getTitle());
                binding.etContent.setText(note.getContent());
                binding.tvTitleBar.setText(getString(R.string.edit));
                binding.btnSave.setText(getString(R.string.update));
                binding.btnDelete.setVisibility(View.VISIBLE);

            }
        } else {
            binding.tvTitleBar.setText(getString(R.string.tambah));
            binding.btnSave.setText(getString(R.string.submit));
            binding.btnDelete.setVisibility(View.GONE);
        }

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSave.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString().trim();
            String content = binding.etContent.getText().toString().trim();

            if (title.isEmpty()) {
                binding.etTitle.setError("Judul tidak boleh kosong");
                return;
            }

            if (noteId == -1) {
                String currentDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
                Note newNote = new Note(0, title, content, currentDateTime);
                dbHelper.addNote(newNote);
            } else {
                note.setTitle(title);
                note.setContent(content);
                String updatedDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
                note.setUpdatedAt(updatedDateTime);
                dbHelper.updateNote(note);
            }
            finish();
        });

        binding.btnDelete.setOnClickListener(v -> {
            if (note != null) {
                dbHelper.deleteNote(note.getId());
            }
            finish();
        });
    }
}
