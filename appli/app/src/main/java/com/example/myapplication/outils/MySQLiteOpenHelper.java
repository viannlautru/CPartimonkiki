package com.example.myapplication.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    //propriétés
    private String creation="create table photo ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,liennomphoto STRING NOT NULL, Adresse STRING NOT NULL, codepostal STRING NOT NULL);";
    private String creatiopromo="create table photopromo ("
            + "idPhotopromo INTEGER PRIMARY KEY AUTOINCREMENT,numPhotopromo INTEGER NOT NULL, codoPhotopromo STRING NOT NULL, datePhotopromo STRING NOT NULL);";
    /**
     * constructeur
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Si changement de BD
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creation);
        sqLiteDatabase.execSQL(creatiopromo);
    }


    /**
     * Si changement de version
     * @param sqLiteDatabase
     * @param i ancienne version
     * @param i1 nouvelle version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
