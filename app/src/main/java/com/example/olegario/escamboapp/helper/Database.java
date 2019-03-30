package com.example.olegario.escamboapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "escamboapp";

    private static final String TABLE_USERS = "Users";

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";


    public Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                                + KEY_ID + " VARCHAR," + KEY_EMAIL + " VARCHAR);";

        db.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;
        db.execSQL(DROP_USERS);

        String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                + KEY_ID + " VARCHAR," + KEY_EMAIL + " VARCHAR);";

        db.execSQL(CREATE_USERS);
    }

    public void addUser(String userId, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, userId);
        cv.put(KEY_EMAIL, email);

        db.insert(TABLE_USERS, null, cv);
        db.close();
    }
}