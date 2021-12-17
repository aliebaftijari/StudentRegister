package com.example.studentregister;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentregister.db.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class InfoActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        AlertDialog alertDialog = new AlertDialog.Builder(InfoActivity.this).create();
        alertDialog.setTitle("Logout Confirmation");
        alertDialog.setMessage("Are you sure you want to log out?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(com.example.studentregister.InfoActivity.this, com.example.studentregister.LoginActivity.class);
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
