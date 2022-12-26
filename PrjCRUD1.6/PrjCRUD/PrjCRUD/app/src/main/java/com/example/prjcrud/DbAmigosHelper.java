package com.example.prjcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAmigosHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Amigos.db";
    private static final int DATABESE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE Amigos (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT NOT NULL, Celular TEXT NOT NULL, Status INTEGER NOT NULL);";

    public DbAmigosHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABESE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}