package com.example.studentregister;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentregister.db.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class InfoActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView = null;
    static TextView tv_info;
    ImageView  iv_userCamera;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);

        tv_info = (TextView) findViewById(R.id.tv_info);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Bundle extrass = getIntent().getExtras();

        //ketu eshte metoda qe na bene concatinate te dhenat e userit, te cilat neper mjet bundle extras jane te ruajtura ne main dhe ne momentin
        // kur i kalojme ne aktivitetin tjeter ato jane te ruajtura si- extrass.get("Emri") dhe jane te bashkuara ne nje text view i cili eshte
        // tv_info
        tv_info.setText("ID : " +extrass.get("Id")+"\n"+ "Emri : " + extrass.get("Emri")+"\n"+ "Mbiemri : "
                +extrass.get("Mbiemri")+"\n"+"Gjinia : "+extrass.get("Gjinia") + "\n" + "Ditelindja : " + extrass.get("Ditelindja"));


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = new DBHelper(this);

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
