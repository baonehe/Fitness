package com.google.uddd_project;

<<<<<<< Updated upstream
import androidx.appcompat.app.AppCompatActivity;
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
>>>>>>> Stashed changes

import android.os.Bundle;
<<<<<<< Updated upstream
=======
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
>>>>>>> Stashed changes

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private static final  int FRAGEMENT_HOME=0;
    private static final  int FRAGEMENT_MYACCOUNT=1;
    private static final  int FRAGEMENT_FEEDBACK=2;
    private static final  int FRAGEMENT_CALCULATOR=3;
    private static final  int FRAGEMENT_WORKOUTS=4;
    private static final  int FRAGEMENT_WALKSTEP=5;
    private static final  int FRAGEMENT_REMINDER=6;


    private int currentFragment = FRAGEMENT_HOME;
    private  BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
<<<<<<< Updated upstream
=======
        //set
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView =  findViewById(R.id.bottom_navigation);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView= findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.nav_bottom_Home).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.nav_bottom_Home:{
                        OpenHomeFragment();
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    }
                    case R.id.nav_bottom_workouts:{
                        OpenWorkOutsFragment();
                        IsCheckFalseFragment_nav_drawer();
                        break;
                    }
                    case R.id.nav_bottom_reminder:{
                        OpenReminderFragment();
                        IsCheckFalseFragment_nav_drawer();
                        break;
                    }
                    case 4:{}
                    case 5:{}
                }
                item.setChecked(true);
                setTitle(item.getTitle());
                return true;
            }
        });
>>>>>>> Stashed changes
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if (id == R.id.nav_home){
          OpenHomeFragment();
          bottomNavigationView.getMenu().findItem(R.id.nav_bottom_Home).setChecked(true);
       }else
           if (id == R.id.nav_myaccount){
               OpenMyAccountFragment();
               IsCheckFalseFragment_nav_bottom();
           }else
               if (id == R.id.nav_feedback){
                  OpenFeedBackFragment();
                   IsCheckFalseFragment_nav_bottom();
               }
               else
               if (id == R.id.nav_logout){
                   Intent intent = new Intent(this,MainActivity.class);
                   startActivity(intent);
                   finish();
               }

        item.setChecked(true);
               setTitle(item.getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OpenHomeFragment(){
        if (currentFragment != FRAGEMENT_HOME){
            replaceFragment(new HomeFragment());
            currentFragment = FRAGEMENT_HOME;
        }
    }
    public  void OpenMyAccountFragment(){
        if (currentFragment != FRAGEMENT_MYACCOUNT){
            replaceFragment(new MyAccountFragment());
            currentFragment = FRAGEMENT_MYACCOUNT;
        }
    }
    public  void OpenFeedBackFragment(){
        if (currentFragment != FRAGEMENT_FEEDBACK){
            replaceFragment(new FeedbackFragment());
            currentFragment = FRAGEMENT_FEEDBACK;
        }
    }
//    public void OpenCaculatorFragment(){
//        if (currentFragment != FRAGEMENT_CALCULATOR){
//            replaceFragment(new FeedbackFragment());
//            currentFragment = FRAGEMENT_CALCULATOR;
//        }
//    }
    public  void OpenWorkOutsFragment(){
        if (currentFragment != FRAGEMENT_WORKOUTS){
            replaceFragment(new WorkOutFragment());
            currentFragment = FRAGEMENT_WORKOUTS;
        }
    }
    public  void OpenWalkStepFragment(){}
    public void OpenReminderFragment(){
        if (currentFragment != FRAGEMENT_REMINDER){
            replaceFragment(new ReminderFragment());
            currentFragment = FRAGEMENT_REMINDER;
        }
    }
    public void IsCheckFalseFragment_nav_drawer(){
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_feedback).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_myaccount).setChecked(false);
    }
    public void IsCheckFalseFragment_nav_bottom(){
        bottomNavigationView.getMenu().findItem(R.id.nav_bottom_Home).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.nav_bottom_workouts).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.nav_bottom_calculator).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.nav_bottom_walkstep).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.nav_bottom_reminder).setChecked(false);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    private  void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();
    }
}