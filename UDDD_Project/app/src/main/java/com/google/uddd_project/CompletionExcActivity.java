package com.google.uddd_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.uddd_project.adapters.WorkoutData;
import com.google.uddd_project.database.DatabaseOperations;
import com.google.uddd_project.utils.Constants;

import java.util.List;

public class CompletionExcActivity extends AppCompatActivity {

    public TextView gz_complete;
    public int d;
    public int daysCompletionCounter = 0;
    public int e;
    public TextView f;
    public TextView g;
    public TextView h;
    public ImageView i;
    public Context j;
    public int l;
    public SharedPreferences launchDataPreferences;
    public int m;
    
    public List<WorkoutData> workoutDataList;
    

    private void getScreenHeightWidth() {
        this.j = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.l = displayMetrics.heightPixels;
        this.m = displayMetrics.widthPixels;
    }
    
    public void allCompletionDialogCreate() {
        String str = "OK";
        String str2 = "Cancel";
        new AlertDialog.Builder(this).setTitle((CharSequence) "Congratulations !").setMessage((CharSequence) "You have completed all 30 days workouts. To achieve consistency, please repeat the exercise from day one.").setPositiveButton((CharSequence) str, (DialogInterface.OnClickListener) null).setNegativeButton((CharSequence) str2, (DialogInterface.OnClickListener) null).setPositiveButton((CharSequence) str, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(CompletionExcActivity.this.getApplicationContext()).edit();
                edit.putBoolean("thirtyday", true);
                edit.apply();
                CompletionExcActivity.this.finish();
                Constants.TOTAL_DAYS = 30;
                Intent intent = new Intent(CompletionExcActivity.this, WorkoutsFragment.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
            }
        }).setNegativeButton((CharSequence) str2, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(CompletionExcActivity.this, WorkoutsFragment.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
                dialogInterface.dismiss();
            }
        }).show();
    }

    public void onBackPressed() {
        String str = "TAG";
        if (Constants.TOTAL_DAYS - this.daysCompletionCounter == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("daysCompletionCounter on backpress if");
            sb.append(Constants.TOTAL_DAYS - this.daysCompletionCounter);
            Log.d(str, sb.toString());
            allCompletionDialogCreate();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("daysCompletionCounter on backpress else");
        sb2.append(Constants.TOTAL_DAYS - this.daysCompletionCounter);
        Log.d(str, sb2.toString());
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(603979776);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onCreate(@Nullable Bundle bundle) {
        StringBuilder sb;
        String str;
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.launchDataPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.exc_completion_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Congratulation");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.e = getIntent().getIntExtra("totalExc", 0);
        this.d = getIntent().getIntExtra("totalTime", 0);
        this.f = (TextView) findViewById(R.id.gz_duration);
        this.g = (TextView) findViewById(R.id.gz_ExcNo);
        this.gz_complete = (TextView) findViewById(R.id.gz_complete);
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        String str2 = "";
        String string = sharedPreferences.getString("json_string", str2);


        int i2 = this.d;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String str3 = "0";
        if (i3 < 10) {
            sb = new StringBuilder();
            sb.append(str3);
        } else {
            sb = new StringBuilder();
            sb.append(str2);
        }
        sb.append(i3);
        String sb2 = sb.toString();
        if (i4 < 10) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str3);
            sb3.append(i4);
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(i4);
            str = sb4.toString();
        }
        TextView textView = this.f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append(":");
        sb5.append(str);
        textView.setText(sb5.toString());
        TextView textView2 = this.g;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(str2);
        sb6.append(this.e);
        textView2.setText(sb6.toString());
        TextView textView3 = this.gz_complete;
        StringBuilder sb7 = new StringBuilder();
        Bundle extras = getIntent().getExtras();
        extras.getClass();
        sb7.append(extras.getString("day"));
        sb7.append(" Completed");
        textView3.setText(sb7.toString());
        
        getScreenHeightWidth();
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = new DatabaseOperations(this).getAllDaysProgress();
        for (int i5 = 0; i5 < Constants.TOTAL_DAYS; i5++) {
            if (((WorkoutData) this.workoutDataList.get(i5)).getProgress() >= 99.0f) {
                this.daysCompletionCounter++;
            }
        }
        int i6 = this.daysCompletionCounter;
        this.daysCompletionCounter = i6 + (i6 / 3);
        if (this.daysCompletionCounter % 5 == 0) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("day completion");
            sb8.append(this.daysCompletionCounter);
            Log.i("debashish", sb8.toString());
            Bundle bundle2 = new Bundle();
            bundle2.putInt("open_time", this.daysCompletionCounter);
            FirebaseAnalytics instance = FirebaseAnalytics.getInstance(this);
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Days_completed_beginner_");
            sb9.append(this.daysCompletionCounter);
            instance.logEvent(sb9.toString(), bundle2);
        }
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