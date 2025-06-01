package com.example.pertemuan8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pertemuan8.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvStudents;
    private StudentAdapter adapter;
    private StudentHelper studentHelper;
    private TextView noData;
    private SearchView searchView;

    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Student List");
        }

        rvStudents = findViewById(R.id.rv_students);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        noData = findViewById(R.id.noData);
        searchView = findViewById(R.id.searchView);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this);
        rvStudents.setAdapter(adapter);

        studentHelper = StudentHelper.getInstance(getApplicationContext());
        studentHelper.open();

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchStudents(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchStudents(newText);
                return true;
            }
        });

        loadStudents();
    }

    private void loadStudents() {
        new LoadStudentsAsync(this, students -> {
            adapter.setStudents(students);
            if (students.isEmpty()) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
            }
        }).execute();
    }

    private void searchStudents(String keyword) {
        if (studentHelper == null) {
            studentHelper = StudentHelper.getInstance(getApplicationContext());
        }
        try {
            studentHelper.open();
        } catch (IllegalStateException ignored) {
        }

        Cursor cursor = studentHelper.searchByName(keyword);
        ArrayList<Student> result = MappingHelper.mapCursorToArrayList(cursor);
        cursor.close();
        adapter.setStudents(result);

        if (result.isEmpty()) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD && resultCode == FormActivity.RESULT_ADD) {
            loadStudents();
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormActivity.RESULT_UPDATE || resultCode == FormActivity.RESULT_DELETE) {
                loadStudents();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (studentHelper != null) {
            studentHelper.close();
        }
    }

    private static class LoadStudentsAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadStudentsCallback> weakCallback;

        private LoadStudentsAsync(Context context, LoadStudentsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    StudentHelper studentHelper = StudentHelper.getInstance(context);
                    studentHelper.open();
                    Cursor cursor = studentHelper.queryAll();
                    ArrayList<Student> students = MappingHelper.mapCursorToArrayList(cursor);
                    cursor.close();
                    handler.post(() -> {
                        LoadStudentsCallback callback = weakCallback.get();
                        if (callback != null) {
                            callback.postExecute(students);
                        }
                    });
                }
            });
        }
    }

    interface LoadStudentsCallback {
        void postExecute(ArrayList<Student> students);
    }
}
