package com.example.tp08sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp08sqlite.database.DatabaseContract;
import com.example.tp08sqlite.database.NoteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE = "extra_note";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;
    private NoteHelper noteHelper;
    private Note note;
    private EditText etName, etNim;
    private boolean isEdit = false;
    private String initialJudul = "";
    private String initialDeskripsi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> onBackPressed());

        etName = findViewById(R.id.et_name);
        etNim = findViewById(R.id.et_nim);
        Button btnSave = findViewById(R.id.btn_save);
        ImageView btnDelete = findViewById(R.id.btn_delete);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        note = getIntent().getParcelableExtra(EXTRA_NOTE);

        if (note != null) {
            isEdit = true;
            initialJudul = note.getJudul();
            initialDeskripsi = note.getDeskripsi();
            etName.setText(initialJudul);
            etNim.setText(initialDeskripsi);
        } else {
            note = new Note();
        }

        String actionBarTitle = isEdit ? "Edit Note" : "Add Note";
        String buttonTitle = isEdit ? "Edit" : "Simpan";

        btnSave.setText(buttonTitle);
        btnDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setOnClickListener(view -> saveNote());
        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Yakin ingin menghapus data ini?")
                    .setPositiveButton("Hapus", (dialog, which) -> deleteNote())
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    private void saveNote() {
        String judul = etName.getText().toString().trim();
        String deskripsi = etNim.getText().toString().trim();

        if (judul.isEmpty()) {
            etName.setError("Please fill this field");
            return;
        }

        if (deskripsi.isEmpty()) {
            etNim.setError("Please fill this field");
            return;
        }

        note.setJudul(judul);
        note.setDeskripsi(deskripsi);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOTE, note);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NoteColumn.JUDUL, judul);
        values.put(DatabaseContract.NoteColumn.DESKRIPSI, deskripsi);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        if (isEdit) {
            values.put(DatabaseContract.NoteColumn.UPDATED_AT, currentDate);
            note.setUpdatedAt(currentDate);

            long result = noteHelper.update(String.valueOf(note.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            values.put(DatabaseContract.NoteColumn.CREATED_AT, currentDate);
            note.setCreatedAt(currentDate);

            long result = noteHelper.insert(values);
            if (result > 0) {
                note.setId((int) result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteNote() {
        if (note != null && note.getId() > 0) {
            long result = noteHelper.deleteById(String.valueOf(note.getId()));
            if (result > 0) {
                setResult(RESULT_DELETE);
                finish();
            } else {
                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid note data", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFormChanged() {
        String currentJudul = etName.getText().toString().trim();
        String currentDeskripsi = etNim.getText().toString().trim();

        if (isEdit) {
            return !currentJudul.equals(initialJudul) || !currentDeskripsi.equals(initialDeskripsi);
        } else {
            return !currentJudul.isEmpty() || !currentDeskripsi.isEmpty();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFormChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle("Keluar tanpa menyimpan?")
                    .setMessage("Yakin ingin keluar? Perubahan tidak akan disimpan.")
                    .setPositiveButton("Ya", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Batal", null)
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Tidak menambahkan data?")
                    .setMessage("Apakah kamu yakin tidak ingin menambahkan data?")
                    .setPositiveButton("Ya", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Batal", null)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }
}
