package com.example.praktikum_8;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.praktikum_8.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SQLiteHelper dbHelper;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<>();
    private List<Note> filteredList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new SQLiteHelper(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(filteredList, note -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("note_id", note.getId());
            startActivity(intent);
        });
        binding.recyclerView.setAdapter(adapter);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(intent);
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        noteList = dbHelper.getAllNotes();
        filterNotes(binding.etSearch.getText().toString());
    }

    private void filterNotes(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(noteList);
        } else {
            for (Note note : noteList) {
                if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        note.getContent().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(note);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}