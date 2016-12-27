package com.example.varsha.myfirstandroidstudioproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by varsha on 10/8/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ExpenseManager.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ExpenseConstants.SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(ExpenseConstants.SQL_CREATE_BUDGET);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ExpenseConstants.SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(ExpenseConstants.SQL_DELETE_BUDGET);
        onCreate(sqLiteDatabase);
    }
}
