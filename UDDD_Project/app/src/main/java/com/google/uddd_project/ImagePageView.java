package com.google.uddd_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

public class ImagePageView extends AppCompatActivity {

    ViewPager mViewPager;

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
        toolbar.setTitle("Menu food");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}