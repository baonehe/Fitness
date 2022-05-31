package com.google.uddd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OpenCamera extends AppCompatActivity {

    private Button btnopencamera;
    private static  final  int REQUEST_ID_IMAGE_CAPTURE =100;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);
        btnopencamera = (Button) findViewById(R.id.btnopencamera);
        btnopencamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {askpermission();}
        });
    }
    private void askpermission() {
        if (android.os.Build.VERSION.SDK_INT >=23){
            int camerapermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (camerapermission != PackageManager.PERMISSION_GRANTED){
                this.requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_ID_IMAGE_CAPTURE);
                return;
            }
        }
        switchactivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_ID_IMAGE_CAPTURE:{
                if (grantResults .length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    switchactivity();
                }
                else{
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private  void switchactivity(){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}