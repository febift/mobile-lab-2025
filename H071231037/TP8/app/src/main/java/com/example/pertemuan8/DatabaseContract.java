package com.example.pertemuan8;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "student";

    public static final class StudentColumns implements     BaseColumns {
        public static String NAME = "name";
        public static String NIM = "nim";
        public static String KEY_UDPATED_AT = "updated_at";
        public static String KEY_CREATED_AT = "created_at";
    }
}