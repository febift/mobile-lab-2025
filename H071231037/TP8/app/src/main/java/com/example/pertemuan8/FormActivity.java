package com.example.pertemuan8;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pertemuan8.model.Student;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT = "extra_student";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;

    private StudentHelper studentHelper;
    private Student student;
    private EditText etName, etNim;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etName = findViewById(R.id.et_name);
        etNim = findViewById(R.id.et_nim);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnDelete = findViewById(R.id.btn_delete);

        studentHelper = StudentHelper.getInstance(getApplicationContext());
        studentHelper.open();

        student = getIntent().getParcelableExtra(EXTRA_STUDENT);

        if (student != null) {
            isEdit = true;
        } else {
            student = new Student();
        }

        String actionBarTitle;
        String buttonTitle;

        if (isEdit) {
            actionBarTitle = "Edit Student";
            buttonTitle = "Update";

            etName.setText(student.getName());
            etNim.setText(student.getNim());

            btnDelete.setVisibility(View.VISIBLE);
        } else {
            actionBarTitle = "Add Student";
            buttonTitle = "Save";
        }

        btnSave.setText(buttonTitle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setOnClickListener(view -> saveStudent());
        btnDelete.setOnClickListener(view -> deleteStudent());
    }

    private void saveStudent() {
        String name = etName.getText().toString().trim();
        String nim = etNim.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Please fill this field");
            return;
        }

        if (nim.isEmpty()) {
            etNim.setError("Please fill this field");
            return;
        }

        student.setName(name);
        student.setNim(nim);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_STUDENT, student);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.StudentColumns.NAME, name);
        values.put(DatabaseContract.StudentColumns.NIM, nim);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        if (isEdit) {
            values.put("updated_at", currentTime);
            long result = studentHelper.update(String.valueOf(student.getId()), values);
            if (result > 0) {
                student.setUpdatedAt(currentTime);
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            values.put("created_at", currentTime);
            long result = studentHelper.insert(values);
            if (result > 0) {
                student.setId((int) result);
                student.setCreatedAt(currentTime);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteStudent() {
        if (student != null && student.getId() > 0) {
            long result = studentHelper.deleteById(String.valueOf(student.getId()));
            if (result > 0) {
                setResult(RESULT_DELETE);
                finish();
            } else {
                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid student data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (studentHelper != null) {
            studentHelper.close();
        }
    }
}
