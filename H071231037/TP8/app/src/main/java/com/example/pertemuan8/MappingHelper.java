package com.example.pertemuan8;

import android.database.Cursor;

import com.example.pertemuan8.model.Student;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Student> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns._ID));
            String name =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.NAME));
            String nim =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.NIM));
            String updatedAt =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.KEY_UDPATED_AT));
            String createdAt =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.KEY_CREATED_AT));
            students.add(new Student(id, name, nim,updatedAt,createdAt));

        }
        return students;
    }
}
