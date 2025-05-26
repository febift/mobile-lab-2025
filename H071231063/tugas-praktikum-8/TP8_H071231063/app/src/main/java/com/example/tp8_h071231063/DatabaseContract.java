package com.example.tp8_h071231063;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "notes";

    public static final class NoteColumns implements BaseColumns {
        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String TANGGAL_WAKTU = "tanggal_waktu";
    }
}
