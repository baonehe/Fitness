package com.google.uddd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Home: break;
                    case R.id.reminder:
                        Intent reminder = new Intent(HomeActivity.this, ReminderActivity.class);
                        startActivity(reminder);
                        break;
                    case R.id.workouts:
                        Intent workouts = new Intent(HomeActivity.this, WorkoutsActivity.class);
                        startActivity(workouts);
                        break;
                }
                return true;
            }
        });
    }
}