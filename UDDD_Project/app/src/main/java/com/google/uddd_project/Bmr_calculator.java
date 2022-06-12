package com.google.uddd_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mikhaellopez.circleview.CircleView;

public class Bmr_calculator extends AppCompatActivity {
    ToggleButton femalefb, malefb;
    Button btncalculate;
    EditText edtheight,edtweight,edtage;
    TextView tv_result_bmr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr_calculator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("BMR");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtheight=findViewById(R.id.edt_numberheight_bmr);
        edtweight=findViewById(R.id.edt_numberweight_bmr);
        edtage=findViewById(R.id.edt_numberage_bmr);
        btncalculate=findViewById(R.id.btn_calculate_bmr);
        tv_result_bmr=findViewById(R.id.tv_result_bmr);
        femalefb = findViewById(R.id.fbfemale_bmr);
        malefb = findViewById(R.id.fbmale_bmr);

        femalefb.setOnCheckedChangeListener(checkedChangeListener);
        malefb.setOnCheckedChangeListener(checkedChangeListener);

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(femalefb.isChecked() || malefb.isChecked()){
                    if (!edtheight.getText().toString().isEmpty() && !edtweight.getText().toString().isEmpty() && !edtage.getText().toString().isEmpty() ){
                        int check_sex ;

                        if (femalefb.isChecked()) check_sex=0;//female = 0
                        else check_sex = 1;//male =1

                        double number = Calculate_bmr(Integer.valueOf(edtheight.getText().toString()),Integer.valueOf(edtweight.getText().toString()),Integer.valueOf(edtage.getText().toString()),check_sex);
                        tv_result_bmr.setText(String.format("%.1f",number));
                    }else
                        Toast.makeText(Bmr_calculator.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(Bmr_calculator.this, "Please choose your sex", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private double Calculate_bmr(Integer height, Integer weight, Integer age, Integer female_male) {
        //female=0, male= 1
        double num;
        if (female_male ==0){
            num=(double) 447.593 + (9.247 * weight)+(3.098 * height) -(4.33 *age);
        }else
            num = (double) 88.362 + (13.397 * weight)+(4.799 * height) -(5.677 *age);
        return num;
    };

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if (buttonView == femalefb) malefb.setChecked(false);
                if (buttonView == malefb) femalefb.setChecked(false);
            }
        }
    };
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