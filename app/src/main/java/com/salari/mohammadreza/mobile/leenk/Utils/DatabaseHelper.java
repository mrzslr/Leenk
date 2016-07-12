package com.salari.mohammadreza.mobile.leenk.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MohammadReza on 29/01/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LeenkUrl";

    // Contacts table name
    public static final String TABLE_LEENKS = "leenks";

    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_LONG_URL = "longUrl";
    public static final String KEY_SHORT_URL = "shortUrl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LEENKS_TABLE = "CREATE TABLE " + TABLE_LEENKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_LONG_URL + " TEXT," + KEY_SHORT_URL + " TEXT" + ")";
        db.execSQL(CREATE_LEENKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEENKS);

        // Create tables again
        onCreate(db);
    }
}