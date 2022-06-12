package com.google.uddd_project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ImagePageView extends AppCompatActivity {

    ViewPager mViewPager;
    TextView firstname;
    FirebaseDatabase database;
    DatabaseReference myRef;

    int[] images = {R.drawable.day1, R.drawable.day2, R.drawable.day3, R.drawable.day4,
            R.drawable.day5, R.drawable.day6, R.drawable.day7};

    // Creating Object of ViewPagerAdapter
    ViewPageAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page_view);
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPageAdapter(ImagePageView.this, images);
        mViewPager.setAdapter(mViewPagerAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        firstname= findViewById(R.id.name_7day);
        toolbar.setTitle("Menu food");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String iduser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(iduser);
        ReadData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private  void ReadData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountInfo post = snapshot.getValue(AccountInfo.class);
                firstname.setText(post.getFirstname());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}