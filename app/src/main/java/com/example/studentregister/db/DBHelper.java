package com.example.studentregister.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.studentregister.RegisterActivity;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //DB Version
    private static final int DB_VERSION = 8;
    //DB NAME
    private static final String DB_NAME = "dbStudent818";

    public String path = (RegisterActivity.currentPhotoPath == null) ? "" : RegisterActivity.currentPhotoPath;

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
                + "Gjinia TEXT,"
                + "Path TEXT,"
                + "Ditelindja DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertstdInfo(String emri, String mbiemri, String gjinia, String ditelindja) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Emri", emri);
        contentValues.put("Mbiemri", mbiemri);
        contentValues.put("Gjinia", gjinia);
        contentValues.put("Ditelindja", ditelindja);

        long result = db.insert(TABLE_STUDENT_INFO, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateStudentPhoto(String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Path", path);

        int result = db.update("std_info", contentValues, "Id =" + "(select max(Id) from std_info)", null);

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

    public boolean deleteRecordFromStdTable(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            long deletedRow = db.delete(TABLE_STUDENT_INFO, "Id=" + id, null);

            if (deletedRow > 0) {

                return true;
            }

            return false;
        } catch (SQLException sqlExc) {

            return false;
        }
    }

    public boolean updateRecordForStdInfo(String id,String emri, String  mbiemri, String gjinia) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put("Emri", emri);
            newValues.put("Mbiemri", mbiemri);
            newValues.put("Gjinia", gjinia);
            long rowIndex = db.update(TABLE_STUDENT_INFO, newValues, "Id = " + id, null);

            if (rowIndex > 0) {
                return true;
            }
            return false;
        } catch (SQLException sqlExc) {
            return false;
        }
    }
}
