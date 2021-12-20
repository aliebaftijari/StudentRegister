package com.example.studentregister;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentregister.db.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    FirebaseAuth mFirebaseAuth;
    DBHelper db = null;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    private static final int CAMERA_REQUEST = 1888;
    ImageView iv_userCamera;
    Button cameraBtn;
    Button galleryBtn;
    public static String currentPhotoPath;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView = null;
    DatePicker picker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        db = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        EditText et_name = findViewById(R.id.et_name);
        EditText et_surname = findViewById(R.id.et_surname);
        iv_userCamera = findViewById(R.id.iv_userCamera);
        cameraBtn = findViewById(R.id.cameraBtn);
        picker=(DatePicker)findViewById(R.id.datePicker1);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register Student");


        EditText ev_id = findViewById(R.id.ev_id);
        ev_id.setEnabled(false);


        Spinner chooseGender = findViewById(R.id.chooseGender);
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("");
        arrayList2.add("Male");
        arrayList2.add("Female");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseGender.setAdapter(arrayAdapter2);


        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_ = et_name.getText().toString();
                String surname_ = et_surname.getText().toString();
                String gender_ = chooseGender.getSelectedItem().toString().trim();
                //procedura per ruajte te dates nepermjet nje date picker kur deklarojme variablat per get vitin,mujin dhe diten
                int day = picker.getDayOfMonth();
                int month = picker.getMonth();
                int year = picker.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);   //kjo form eshte per ruajtje te dates si text
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");// ne baze te kesaj ne e percaktojme formatin se si
                                                                                                // do te na ruhet data
                String formatedDate = simpleDateFormat.format(calendar.getTime());
//
//                ndersa kur e ruajm si date ather e kemi metoden tjeter
//                Date date = null;
//                try {
//                     date = simpleDateFormat.parse(formatedDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                File f;
                if(name_.isEmpty()){
                    et_name.setError("Name field is Empty");
                    et_name.requestFocus();
                }
                else  if(surname_.isEmpty()){
                    et_surname.setError("Surname field is Empty");
                    et_surname.requestFocus();
                }
                else if (chooseGender.getSelectedItem().toString().trim().equals("")) {
                        ((TextView) chooseGender.getSelectedView()).setError("No selected item");
                }// ketu eshte shtuar pjesa per me validu nese pathi i fotos se ruajtur eshte null ose string i zbrazet
                else  if (((currentPhotoPath == null) ? "" : currentPhotoPath).equalsIgnoreCase(""))  {
                    Toast.makeText(getApplicationContext(), "Could not insert the photo, photopath is null ", Toast.LENGTH_LONG).show();

                    }
                else  if(name_.isEmpty() && surname_.isEmpty() && gender_.isEmpty()){

                }
                else  if(!(name_.isEmpty() && surname_.isEmpty() && gender_.isEmpty())) {//shtohet variabla ku ruhet pathi, dhe thirret kur t bejme insert per path, si dhe per daten e lindjes
                    boolean insertedToDb = db.insertstdInfo( et_name.getText().toString(), et_surname.getText().toString(), chooseGender.getSelectedItem().toString(),currentPhotoPath, formatedDate);

                    if (insertedToDb) {
                        Toast.makeText(getApplicationContext(),
                                "INSERTED_TO_DB",
                                Toast.LENGTH_SHORT).show();
                        Intent redirectToActions = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(redirectToActions);

                    }
                }
            }
        });
            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST  && resultCode==RESULT_OK) {
            try {
                Bitmap mImageBitmap;
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(currentPhotoPath));

                iv_userCamera.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private File createImageFile() throws IOException {

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "example",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        db.path = currentPhotoPath;
        currentPhotoPath = "file:" +image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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

            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
            alertDialog.setTitle("Logout Confirmation");
            alertDialog.setMessage("Are you sure you want to log out?");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intToMain = new Intent(com.example.studentregister.RegisterActivity.this, com.example.studentregister.LoginActivity.class);
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
