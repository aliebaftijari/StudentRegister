package com.example.studentregister;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentregister.db.DBHelper;

public class EditActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit");
    }
}