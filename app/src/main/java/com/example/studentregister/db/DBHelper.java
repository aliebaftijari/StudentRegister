package com.example.studentregister.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //DB Version
    private static final int DB_VERSION = 8;
    //DB NAME
    private static final String DB_NAME = "dbStudent";

    //Table name
    private static final String TABLE_STUDENT_INFO = "std_info";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENT_INFO
                + " ("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Emri TEXT,"
                + "Mbiemri TEXT,"
                + "Gjinia TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertstdInfo(String emri, String mbiemri, String gjinia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Emri", emri);
        contentValues.put("Mbiemri", mbiemri);
        contentValues.put("Gjinia", gjinia);
        long result = db.insert(TABLE_STUDENT_INFO, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllStudentInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("select Id, emri, mbiemri, gjinia  from std_info", null);
        return resultSet;
    }


}
