package com.example.studentregister;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentregister.db.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {


    DBHelper db = null;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView = null;

    ImageView iv_userCamera;
    Button cameraBtn;
    Button btn_Save, btn_cancel;
    EditText ev_id, et_name, et_surname;
    String id, _emri, _mbiemri, _gjinia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit");

        db = new DBHelper(this);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EditText et_name = findViewById(R.id.et_name);
        EditText et_surname = findViewById(R.id.et_surname);
        iv_userCamera = findViewById(R.id.iv_userCamera);
        btn_cancel = findViewById(R.id.btn_cancel);


        EditText ev_id = findViewById(R.id.ev_id);
        ev_id.setEnabled(false);
        Bundle extrass = getIntent().getExtras();
           id = extrass.getString("Id");
        _emri = extrass.getString("Emri");
        _mbiemri = extrass.getString("Mbiemri");
        _gjinia = extrass.getString("Gjinia");


        ev_id.setText(id);
        et_name.setText(_emri);
        et_surname.setText(_mbiemri);

        Spinner chooseGender = findViewById(R.id.chooseGender);
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("");
        arrayList2.add("Male");
        arrayList2.add("Female");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseGender.setAdapter(arrayAdapter2);

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }

        });

        Button btn_Save = findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean updated = db.updateRecordForStdInfo(id, et_name.getText().toString(),
                        et_surname.getText().toString(), chooseGender.getSelectedItem().toString());
                if (updated) {
                    Toast.makeText(getApplicationContext(),
                            "UPDATED",
                            Toast.LENGTH_SHORT).show();
                    Intent redirectToActions = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(redirectToActions);

                }
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

            AlertDialog alertDialog = new AlertDialog.Builder(EditActivity.this).create();
            alertDialog.setTitle("Logout Confirmation");
            alertDialog.setMessage("Are you sure you want to log out?");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intToMain = new Intent(com.example.studentregister.EditActivity.this, com.example.studentregister.LoginActivity.class);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

