package com.example.MyRefrigerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper_recipe extends SQLiteOpenHelper {

    static final String COLUMN_NAME_ID="_id";
    static final String COLUMN_NAME_NAME="food_name";
    static final String COLUMN_NAME_NUM="food_num";
    static final String COLUMN_NAME_DAYDAY="food_day2";

    Context context;

    public MySQLiteOpenHelper_recipe(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE meat ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+  COLUMN_NAME_DAYDAY+" Text);");
        db.execSQL("CREATE TABLE vegetable ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+ COLUMN_NAME_DAYDAY+" Text);");
        db.execSQL("CREATE TABLE fish ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+  COLUMN_NAME_DAYDAY+" Text);");
        db.execSQL("CREATE TABLE fruit ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+  COLUMN_NAME_DAYDAY+" Text);");
        db.execSQL("CREATE TABLE sauce ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+  COLUMN_NAME_DAYDAY+" Text);");
        db.execSQL("CREATE TABLE drink ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+ " Text,"+ COLUMN_NAME_DAYDAY+" Text);");
        db.execSQL("CREATE TABLE frozen ("+ COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME_NAME+" Text,"+ COLUMN_NAME_NUM+" Text,"+ " Text,"+ COLUMN_NAME_DAYDAY+" Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meat");
        db.execSQL("DROP TABLE IF EXISTS vegetable");
        db.execSQL("DROP TABLE IF EXISTS fish");
        db.execSQL("DROP TABLE IF EXISTS fruit");
        db.execSQL("DROP TABLE IF EXISTS sauce");
        db.execSQL("DROP TABLE IF EXISTS drink");
        db.execSQL("DROP TABLE IF EXISTS frozen");

        onCreate(db);
    }
}
