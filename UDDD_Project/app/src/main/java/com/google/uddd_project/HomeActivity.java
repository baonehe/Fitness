package com.google.uddd_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView imageView;
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

        //set
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView =  findViewById(R.id.bottom_navigation);
        imageView = findViewById(R.id.imageViewAva);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView= findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);

        View headerview =  navigationView.getHeaderView(0);
        TextView nav_gmail = headerview.findViewById(R.id.tv_gmail);
        TextView nav_name = headerview.findViewById(R.id.tv_nameava);
        ImageView nav_image = headerview.findViewById(R.id.imageViewAva);
        String iduser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        nav_gmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(iduser);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountInfo accountInfo = snapshot.getValue(AccountInfo.class);
                if (!accountInfo.getProfileImage().equals("")) Picasso.get().load(accountInfo.getProfileImage()).into(nav_image);
                nav_name.setText(accountInfo.getFirstname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error Loading ", Toast.LENGTH_SHORT).show();
            }
        });

//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String link = snapshot.getValue(String.class);
//                Picasso.get().load(link).into(nav_image);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(HomeActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
//            }
//        });
        nav_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenEditPictureProfle();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.home:{
                        OpenHomeFragment();
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    }
                    case R.id.workouts:{
                        OpenWorkOutsFragment();
                        IsCheckFalseFragment_nav_drawer();
                        break;
                    }
                    case R.id.reminder:{
                        OpenReminderFragment();
                        IsCheckFalseFragment_nav_drawer();
                        break;
                    }
                    case R.id.walkstep:{
                        OpenWalkStepFragment();
                        IsCheckFalseFragment_nav_drawer();
                        break;
                    }
                    case R.id.calculator:{
                        OpenCaLculatorFragment();
                        IsCheckFalseFragment_nav_drawer();
                        break;
                    }
                }
                item.setChecked(true);
                setTitle(item.getTitle());
                return true;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            OpenHomeFragment();
            bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
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
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void OpenEditPictureProfle() {
        Intent intent = new Intent(HomeActivity.this,EditPictureProfile.class);
        startActivity(intent);
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
        public void OpenCaLculatorFragment(){
        if (currentFragment != FRAGEMENT_CALCULATOR){
         replaceFragment(new CalculatorFragment());
           currentFragment = FRAGEMENT_CALCULATOR;
       }
   }
    public  void OpenWorkOutsFragment(){
        if (currentFragment != FRAGEMENT_WORKOUTS){
            replaceFragment(new WorkoutsFragment());
            currentFragment = FRAGEMENT_WORKOUTS;
        }
    }
    public  void OpenWalkStepFragment()
    {
        if (currentFragment != FRAGEMENT_WALKSTEP){
            replaceFragment(new SummaryFragment());
            currentFragment = FRAGEMENT_WALKSTEP;
        }
    }
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
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.workouts).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.calculator).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.walkstep).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.reminder).setChecked(false);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public  void LoadExcersizeFragemnt(Fragment fragment){
        replaceFragment(fragment);
        toolbar.setTitle("workout");
        bottomNavigationView.setSelectedItemId(R.id.workouts);
    }
    public  void LoadWalkStepFragemnt(Fragment fragment){
        replaceFragment(fragment);
        toolbar.setTitle("WalkStep");
        bottomNavigationView.setSelectedItemId(R.id.walkstep);
    }
    private  void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}