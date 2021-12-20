package com.example.studentregister;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentregister.db.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    TableLayout tblStudentInfo = null;

    DBHelper db = null;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView = null;
    CalendarView calendar;
    Switch switchButton;
    private Activity activity;

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

        InfoActivity.tv_info = (TextView) findViewById(R.id.tv_info);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tblStudentInfo = (TableLayout) findViewById(R.id.tblStudentInfo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Main");

        calendar = (CalendarView)
                findViewById(R.id.calendar);



        Button createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToActions = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(redirectToActions);

            }

        });

        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Cursor c = db.getAllStudentInfo();
                    while (c.moveToNext()) {
                        populatetblStudentInfo(c.getString(c.getColumnIndex("Id")),
                                c.getString(c.getColumnIndex("Emri")),
                                c.getString(c.getColumnIndex("Mbiemri")),
                                c.getString(c.getColumnIndex("Gjinia")),
                                c.getString(c.getColumnIndex("Ditelindja")));
                    }
                } else {
                    tblStudentInfo.removeViews(1, Math.max(0, tblStudentInfo.getChildCount() - 1));
                }
            }
        });
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar
                .setOnDateChangeListener(
                new CalendarView
                .OnDateChangeListener() {
        @Override

        public void onSelectedDayChange(
                @NonNull CalendarView view,
        int year,
        int month,
        int dayOfMonth)  {

            String Date
                    = dayOfMonth + "-"
                    + (month + 1) + "-" + year;
        }
    });
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
    public void populatetblStudentInfo(String Id, String Emri, String Mbiemri, String Gjinia, String Ditelindja) {

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int widthColumn = (size.x / 8);

        int maxColumnHeight = 110;
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
        newId.setTextColor(Color.BLACK);
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
        TextView newGender = new TextView(this);
        newGender.setText(Gjinia);
        newGender.setTextColor(Color.BLACK);
        newGender.setMaxHeight(maxColumnHeight);
        newGender.setGravity(Gravity.CENTER);
        newGender.setTextSize(12);
        newGender.setPadding(10, 0, 10, 0);
        newRow.addView(newGender, layoutParamsForGender);

        TableRow.LayoutParams layoutParamsForDate = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForDate.gravity = Gravity.CENTER;
        TextView newDate = new TextView(this);
        newDate.setText(Ditelindja);
        newDate.setTextColor(Color.BLACK);
        newDate.setMaxHeight(maxColumnHeight);
        newDate.setGravity(Gravity.CENTER);
        newDate.setTextSize(12);
        newDate.setPadding(10, 0, 10, 0);
        newRow.addView(newDate, layoutParamsForDate);


        TableRow.LayoutParams layoutParamsForbtn = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForbtn.gravity = Gravity.CENTER;
        TextView editBtn = new TextView(this);
        editBtn.setText("Edit");
        editBtn.setTextColor(Color.BLACK);
        editBtn.setMaxHeight(maxColumnHeight);
        editBtn.setGravity(Gravity.CENTER);
        editBtn.setPadding(0, 0, 10, 0);
        newRow.addView(editBtn, layoutParamsForbtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToActions = new Intent(getApplicationContext(), EditActivity.class);
                redirectToActions.putExtra("Id", newId.getText().toString());
                redirectToActions.putExtra("Emri", newName.getText().toString());
                redirectToActions.putExtra("Mbiemri", newSurname.getText().toString());
                redirectToActions.putExtra("Gjinia", newGender.getText().toString());
                startActivity(redirectToActions);

            }
        });

        TableRow.LayoutParams layoutParamsForbtn2 = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForbtn2.gravity = Gravity.CENTER;
        TextView deleteBtn = new TextView(this);
        deleteBtn.setText("Delete");
        deleteBtn.setTextColor(Color.BLACK);
        deleteBtn.setMaxHeight(maxColumnHeight);
        deleteBtn.setGravity(Gravity.CENTER);
        deleteBtn.setPadding(0, 0, 10, 0);
        newRow.addView(deleteBtn, layoutParamsForbtn2);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = db.deleteRecordFromStdTable(newId.getText().toString());
                if (deleted) {
                    Toast.makeText(getApplicationContext(),
                            "DELETED_FROM_DB",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }
            }
        });

        TableRow.LayoutParams layoutParamsForbtn3 = new TableRow.LayoutParams(widthColumn, maxColumnHeight);
        layoutParamsForbtn3.gravity = Gravity.CENTER;
        TextView infoBtn = new TextView(this);
        infoBtn.setText("Info");
        infoBtn.setTextColor(Color.BLACK);
        infoBtn.setMaxHeight(maxColumnHeight);
        infoBtn.setGravity(Gravity.CENTER);
        infoBtn.setPadding(0, 0, 10, 0);
        newRow.addView(infoBtn, layoutParamsForbtn3);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override  // tek pjesa ku e kemi shtu butonin per info e shofim si i marrim te dhenat kur e popullojme nje tabel
            // i ruajme dhe menpas kur kalojme ne aktivitetin tjeter i perdorim ato vlera te cilat jane te marra nga tabela ne main
            public void onClick(View v) {
                Intent redirectToActions = new Intent(getApplicationContext(), InfoActivity.class);
                redirectToActions.putExtra("Id", newId.getText().toString());
                redirectToActions.putExtra("Emri", newName.getText().toString());
                redirectToActions.putExtra("Mbiemri", newSurname.getText().toString());
                redirectToActions.putExtra("Gjinia", newGender.getText().toString());
                redirectToActions.putExtra("Ditelindja", newDate.getText().toString());
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