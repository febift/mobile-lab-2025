package com.projeku.tuprak8; // Sesuaikan dengan package Anda

import android.content.Context; // Import Context
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {

    private RecyclerView recyclerViewNotes;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private DatabaseHelper dbHelper;
    private FloatingActionButton fabAddNote;
    private EditText editTextSearch;
    private ImageButton buttonClearSearch;
    private TextView textViewNoData;
    private Toolbar toolbarMain;

    private ActivityResultLauncher<Intent> addEditNoteLauncher;

    private Handler noDataMessageHandler;
    private Runnable hideNoDataMessageRunnable;
    private static final long NO_DATA_MESSAGE_DISPLAY_DURATION = 2000;
    private static final long FADE_OUT_DURATION = 500;
    private Animation fadeOutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.notes_toolbar_title));
        }

        dbHelper = new DatabaseHelper(this);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        fabAddNote = findViewById(R.id.fab_add_note);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonClearSearch = findViewById(R.id.buttonClearSearch);
        textViewNoData = findViewById(R.id.textViewNoData);

        fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeOutAnimation.setDuration(FADE_OUT_DURATION);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                if (textViewNoData != null) {
                    textViewNoData.setVisibility(View.GONE);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        noDataMessageHandler = new Handler(Looper.getMainLooper());
        hideNoDataMessageRunnable = () -> {
            if (textViewNoData != null && textViewNoData.getVisibility() == View.VISIBLE) {
                textViewNoData.startAnimation(fadeOutAnimation);
            }
        };

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, noteList, this);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(noteAdapter);

        loadNotes();

        addEditNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadNotes();
                        preventKeyboardFromShowing();
                    }
                }
        );

        fabAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            addEditNoteLauncher.launch(intent);
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noDataMessageHandler.removeCallbacks(hideNoDataMessageRunnable);
                if (textViewNoData.getAnimation() != null) {
                    textViewNoData.clearAnimation();
                }
                searchNotes(s.toString());
                if (s.length() > 0) {
                    buttonClearSearch.setVisibility(View.VISIBLE);
                } else {
                    buttonClearSearch.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        buttonClearSearch.setOnClickListener(v -> {
            editTextSearch.setText("");
        });

        preventKeyboardFromShowing();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preventKeyboardFromShowing();
    }

    private void preventKeyboardFromShowing() {
        if (editTextSearch != null) {
            editTextSearch.clearFocus();
        }


        if (toolbarMain != null) {
            toolbarMain.requestFocus();
        } else {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                decorView.requestFocus();
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getCurrentFocus();
        if (imm != null) {
            if (currentFocusedView != null) {
                imm.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
            } else {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }


    private void loadNotes() {
        String currentSearchQuery = editTextSearch.getText().toString().trim();
        noDataMessageHandler.removeCallbacks(hideNoDataMessageRunnable);
        if (textViewNoData.getAnimation() != null) {
            textViewNoData.clearAnimation();
        }

        if (currentSearchQuery.isEmpty()) {
            noteList.clear(); //
            noteList.addAll(dbHelper.getAllNotes());
            noteAdapter.setNotes(noteList);
            checkIfNoData(null);
        } else {
            searchNotes(currentSearchQuery);
        }
    }

    private void searchNotes(String query) {
        noDataMessageHandler.removeCallbacks(hideNoDataMessageRunnable);
        if (textViewNoData.getAnimation() != null) {
            textViewNoData.clearAnimation();
        }
        noteList.clear();
        if (query.isEmpty()) {
            noteList.addAll(dbHelper.getAllNotes());
        } else {
            noteList.addAll(dbHelper.searchNotesByTitle(query));
        }
        noteAdapter.setNotes(noteList); // Perbarui Adapter
        checkIfNoData(query); // Memeriksa apakah hasil kosong
    }

    private void checkIfNoData(String searchQuery) {
        noDataMessageHandler.removeCallbacks(hideNoDataMessageRunnable);
        if (textViewNoData.getAnimation() != null) { // Batalkan tidak ada data dan hentikan animasi
            textViewNoData.clearAnimation(); // Hentikan animasi saat ini
            if (!noteList.isEmpty()) {
                textViewNoData.setVisibility(View.GONE);
            }
        }

        if (noteList.isEmpty()) {
            recyclerViewNotes.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
            textViewNoData.setAlpha(1.0f);

            if (searchQuery != null && !searchQuery.isEmpty()) {
                textViewNoData.setText(String.format(getString(R.string.no_search_results), searchQuery));
            } else {
                textViewNoData.setText(getString(R.string.no_data_available));
            }
            noDataMessageHandler.postDelayed(hideNoDataMessageRunnable, NO_DATA_MESSAGE_DISPLAY_DURATION);
        } else {
            if (textViewNoData.getVisibility() == View.VISIBLE && textViewNoData.getAnimation() == null) {
                textViewNoData.setVisibility(View.GONE);
            }
            recyclerViewNotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra("note_id", note.getId());
        addEditNoteLauncher.launch(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noDataMessageHandler != null) {
            noDataMessageHandler.removeCallbacks(hideNoDataMessageRunnable);
        }
        if (textViewNoData != null && textViewNoData.getAnimation() != null) {
            textViewNoData.clearAnimation();
        }
    }
}
