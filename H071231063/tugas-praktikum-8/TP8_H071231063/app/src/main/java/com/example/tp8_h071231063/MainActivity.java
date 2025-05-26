package com.example.tp8_h071231063;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView statusMessage;

    private RecyclerView rvNotes;
    private NoteAdapter adapter;
    private NoteHelper noteHelper;
    private ArrayList<Note> notes = new ArrayList<>();

    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Student List");
        }

        rvNotes = findViewById(R.id.rv_notes);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        statusMessage = findViewById(R.id.statusMessage);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this);
        rvNotes.setAdapter(adapter);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        SearchView searchView = findViewById(R.id.search_view);

        int black = ContextCompat.getColor(this, R.color.black);

        int searchIconId = androidx.appcompat.R.id.search_mag_icon;
        int clearIconId = androidx.appcompat.R.id.search_close_btn;

        ImageView searchIcon = searchView.findViewById(searchIconId);
        ImageView clearIcon = searchView.findViewById(clearIconId);

        if (searchIcon != null) searchIcon.setColorFilter(black);
        if (clearIcon != null) clearIcon.setColorFilter(black);

        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(black);
        searchEditText.setHintTextColor(black);
        searchView.setBackgroundColor(ContextCompat.getColor(this, R.color.purpleTertiary));

        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);

        if (clearIcon != null) {
            clearIcon.setOnClickListener(v -> {
                searchView.setQuery("", false);
                searchView.clearFocus();
                loadNotes();
            });
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadNotes();
                } else {
                    filterNotes(newText);
                }
                return true;
            }
        });

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        loadNotes();
    }


    private void loadNotes() {
        new LoadNotesAsync(this, notes -> {
            this.notes.clear();
            this.notes.addAll(notes);
            adapter.setNotes(notes);

            if (notes.isEmpty()) {
                rvNotes.setVisibility(View.GONE);
                statusMessage.setVisibility(View.VISIBLE);
                statusMessage.setText("No data");
            } else {
                rvNotes.setVisibility(View.VISIBLE);
                statusMessage.setVisibility(View.GONE);
            }
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                statusMessage.animate().alpha(0f).setDuration(300).withEndAction(() -> {
                    statusMessage.setVisibility(View.GONE);
                }).start();
            }, 2000);
        }).execute();
    }
    private void filterNotes(String keyword) {
        ArrayList<Note> filteredList = new ArrayList<>();
        for (Note note : notes) {
            if (note.getJudul().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(note);
            }
        }
        adapter.setNotes(filteredList);

        if (filteredList.isEmpty()) {
            rvNotes.setVisibility(View.GONE);
            showMessage("No notes found for \"" + keyword + "\"");
        } else {
            rvNotes.setVisibility(View.VISIBLE);
            statusMessage.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD && resultCode == FormActivity.RESULT_ADD) {
            showMessage("Note added successfully");
            loadNotes();
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormActivity.RESULT_UPDATE) {
                showMessage("Note updated successfully");
                loadNotes();
            } else if (resultCode == FormActivity.RESULT_DELETE) {
                showMessage("Note deleted successfully");
                loadNotes();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }

    private void showMessage(String message) {
        statusMessage.setAlpha(0f);
        statusMessage.setText(message);
        statusMessage.setVisibility(View.VISIBLE);
        statusMessage.animate().alpha(1f).setDuration(300).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            statusMessage.animate().alpha(0f).setDuration(300).withEndAction(() -> {
                statusMessage.setVisibility(View.GONE);
            }).start();
        }, 3000);
    }

    private static class LoadNotesAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNotesAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    NoteHelper noteHelper = NoteHelper.getInstance(context);
                    noteHelper.open();

                    Cursor notesCursor = noteHelper.queryAll();
                    ArrayList<Note> notes = MappingHelper.mapCursorToArrayList(notesCursor);

                    notesCursor.close();

                    handler.post(() -> {
                        LoadNotesCallback callback = weakCallback.get();
                        if (callback != null) {
                            callback.postExecute(notes);
                        }
                    });
                }
            });
        }
    }

    interface LoadNotesCallback {
        void postExecute(ArrayList<Note> notes);
    }
}
