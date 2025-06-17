package com.example.pertemuan8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_TABLE_STUDENT =
            String.format(
                    "CREATE TABLE %s" +
                            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " created_at TEXT," +
                            " updated_at TEXT)",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.StudentColumns._ID,
                    DatabaseContract.StudentColumns.NAME,
                    DatabaseContract.StudentColumns.NIM,
                    DatabaseContract.StudentColumns.KEY_CREATED_AT,
                    DatabaseContract.StudentColumns.KEY_UDPATED_AT
            );


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            sqLiteDatabase.execSQL("ALTER TABLE " + DatabaseContract.TABLE_NAME +
                    " ADD COLUMN created_at TEXT");
        }
    }
}
