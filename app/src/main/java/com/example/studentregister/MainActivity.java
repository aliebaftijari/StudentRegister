package com.example.studentregister;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.studentregister.db.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    TableLayout tblStudentInfo = null;

    DBHelper db = null;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView = null;

    @SuppressLint({"Range", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tblStudentInfo = (TableLayout) findViewById(R.id.tblStudentInfo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Main");



        Cursor c = db.getAllStudentInfo();
        while (c.moveToNext()) {
            populatetblStudentInfo(c.getString(c.getColumnIndex("Id")),
                    c.getString(c.getColumnIndex("Emri")),
                    c.getString(c.getColumnIndex("Mbiemri")),
                    c.getString(c.getColumnIndex("Gjinia")));
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_Register) {
            Intent redirectToActions = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(redirectToActions);

        } else if (id == R.id.nav_edit) {
            Intent redirectToActions = new Intent(getApplicationContext(), EditActivity.class);
            startActivity(redirectToActions);

        } else if (id == R.id.nav_info) {
            Intent redirectToActions = new Intent(getApplicationContext(), InfoActivity.class);
            startActivity(redirectToActions);

        } else if (id == R.id.nav_logout) {

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Logout Confirmation");
            alertDialog.setMessage("Are you sure you want to log out?");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intToMain = new Intent(com.example.studentregister.MainActivity.this, com.example.studentregister.LoginActivity.class);
                    startActivity(intToMain);

                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void populatetblStudentInfo(String Id, String Emri, String Mbiemri, String Gjinia) {

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int widthColumn = (size.x / 6);

        int maxColumnHeight = 120;
        TableRow newRow;

        newRow = new TableRow(this);

        newRow.setBackgroundResource(R.drawable.border_bottom_table_row);
        TableRow.LayoutParams layoutParamsForId = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForId.gravity = Gravity.CENTER;
        TextView newId = new TextView(this);
        newId.setText(Id);
        newId.setMaxHeight(maxColumnHeight);
        newId.setGravity(Gravity.CENTER);
        newId.setTextSize(12);
        newId.setTextColor(Color.WHITE);
        newId.setPadding(10, 0, 10, 0);
        newRow.addView(newId, layoutParamsForId);
        newRow.setBackground(getDrawable(R.color.white));


        TableRow.LayoutParams layoutParamsForName = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForName.gravity = Gravity.CENTER;
        TextView newName = new TextView(this);
        newName.setText(Emri);
        newName.setMaxHeight(maxColumnHeight);
        newName.setGravity(Gravity.CENTER);
        newName.setTextSize(12);
        newName.setTextColor(Color.BLACK);
        newName.setPadding(10, 0, 10, 0);
        newRow.addView(newName, layoutParamsForName);


        TableRow.LayoutParams layoutParamsForSurname = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        //layoutParamsForRequestDescription.gravity = Gravity.CENTER;
        TextView newSurname = new TextView(this);
        newSurname.setText(Mbiemri);
        newSurname.setMaxHeight(maxColumnHeight);
        newSurname.setGravity(Gravity.CENTER);
        newSurname.setTextSize(12);
        newSurname.setTextColor(Color.BLACK);
        newSurname.setPadding(10, 0, 10, 0);
        newRow.addView(newSurname, layoutParamsForSurname);


        TableRow.LayoutParams layoutParamsForGender = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForGender.gravity = Gravity.CENTER;
        TextView newPath = new TextView(this);
        newPath.setText(Gjinia);
        newPath.setTextColor(Color.WHITE);
        newPath.setMaxHeight(maxColumnHeight);
        newPath.setGravity(Gravity.CENTER);
        newPath.setTextSize(12);
        newPath.setPadding(10, 0, 10, 0);
        newRow.addView(newPath, layoutParamsForGender);


        TableRow.LayoutParams layoutParamsForbtn = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForbtn.gravity = Gravity.CENTER;
        TextView addBtn = new TextView(this);
        addBtn.setText("Add");
        addBtn.setTextColor(Color.BLACK);
        addBtn.setMaxHeight(maxColumnHeight);
        addBtn.setGravity(Gravity.CENTER);
        addBtn.setPadding(0, 0, 10, 0);
        newRow.addView(addBtn, layoutParamsForbtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToActions = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(redirectToActions);

            }
        });
        tblStudentInfo.addView(newRow);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}