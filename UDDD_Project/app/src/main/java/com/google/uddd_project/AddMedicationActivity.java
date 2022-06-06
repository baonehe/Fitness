package com.google.uddd_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddMedicationActivity extends AppCompatActivity {
    RelativeLayout rltDate, rltTime, rltRepeat, rltType;
    TextView tvDate, tvTime, tvRepeat, tvType,tvRepeatDay;
    Spinner spinner ;
    String[] listdayofweek;
    boolean[] checkday;
    ArrayList<Integer> arrayList_dayofweek = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Reminder");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rltDate = findViewById(R.id.rltDate);
        rltTime = findViewById(R.id.rltTime);
        rltRepeat = findViewById(R.id.rltRepeat);
        rltType = findViewById(R.id.rltType);

        tvDate = findViewById(R.id.set_date);
        tvTime = findViewById(R.id.set_time);
        tvRepeat = findViewById(R.id.set_repeat);
        tvTime = findViewById(R.id.set_time);
        tvRepeatDay= findViewById(R.id.set_typeOfRepetions);
        spinner = findViewById(R.id.spinertimerepeat);
        Update();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Update();
            }
        };
        rltDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMedicationActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                Update();
            }
        };
        rltTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddMedicationActivity.this, time, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(AddMedicationActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.timepicker));
        spinner.setAdapter(myadapter);
        listdayofweek =getResources().getStringArray(R.array.daysofweek);
        checkday = new boolean[listdayofweek.length];
        rltType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AddMedicationActivity.this);
                builder.setTitle("Day");
                builder.setMultiChoiceItems(listdayofweek, checkday, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            if (!arrayList_dayofweek.contains(which))
                                arrayList_dayofweek.add(which);
                        }
                        else
                            if (arrayList_dayofweek.contains(which))
                                arrayList_dayofweek.remove(which);

                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i= 0; i < arrayList_dayofweek.size(); i++){
                            item = item + listdayofweek[arrayList_dayofweek.get(i)];
                            if (i!= arrayList_dayofweek.size()-1){
                                item = item + ",";
                            }
                        }
                        tvRepeatDay.setText(item);
                    }
                });
                builder.setNegativeButton("Dimiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i < checkday.length; i++){
                            checkday[i] = false;
                            arrayList_dayofweek.clear();
                            tvRepeatDay.setText("");
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
//        rltRepeat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new NumberPicker();
//            };
//        });
    }

    private void Update() {
        String FMDate = "dd/MM/yy";
        String FMTime = "hh:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(FMDate, Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat(FMTime, Locale.US);

        tvDate.setText(dateFormat.format(calendar.getTime()));
        tvTime.setText(timeFormat.format(calendar.getTime()));
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