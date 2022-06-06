package com.google.uddd_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mikhaellopez.circleview.CircleView;

import java.text.DecimalFormat;

public class Bmi_calculator extends AppCompatActivity {
    ToggleButton femalefb, malefb;
    Button btncalculate;
    EditText edtheight,edtweight;
    TextView tv_result_bmi,tv_status_bmi,tv_tiile_bmi;
    CircleView circleView_status;
    private static final DecimalFormat df = new DecimalFormat("0.0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        tv_tiile_bmi=findViewById(R.id.tv_bmi_tiitle);
        femalefb = findViewById(R.id.fbfemale);
        malefb = findViewById(R.id.fbmale);
        btncalculate= findViewById(R.id.btn_calculate_bmi);
        edtheight = findViewById(R.id.edt_numberheight_bmi);
        edtweight=findViewById(R.id.edt_numberweight_bmi);
        circleView_status= findViewById(R.id.cv_bodystate);
        tv_result_bmi = findViewById(R.id.tv_result_bmi);
        tv_status_bmi= findViewById(R.id.tv_status_bmi);

        femalefb.setOnCheckedChangeListener(checkedChangeListener);
        malefb.setOnCheckedChangeListener(checkedChangeListener);
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (femalefb.isChecked() || malefb.isChecked()){
                    if (!edtheight.getText().toString().isEmpty() && !edtweight.getText().toString().isEmpty() ){
                      double number=  Calculate_bmi(Integer.valueOf(edtheight.getText().toString()),Integer.valueOf(edtweight.getText().toString()));
                      tv_result_bmi.setText(String.format("%.1f",number));
                      tv_tiile_bmi.setTextColor(getResources().getColor(R.color.white));
                      if (number <18.5){
                            tv_status_bmi.setText("Underweight");
                            circleView_status.setCircleColor(getResources().getColor(R.color.colorSkyBlue));
                            circleView_status.setShadowColor(getResources().getColor(R.color.colorSkyBlue));
                      }
                      else if(number <=24.9){
                              tv_status_bmi.setText("Normal");
                              circleView_status.setCircleColor(getResources().getColor(R.color.green));
                          circleView_status.setShadowColor(getResources().getColor(R.color.green));
                          }
                      else if (number <= 29.9){
                          tv_status_bmi.setText("Overweight");
                          circleView_status.setCircleColor(getResources().getColor(R.color.weightcolor));
                          circleView_status.setShadowColor(getResources().getColor(R.color.weightcolor));
                      }
                      else if (number <=34.9){
                          tv_status_bmi.setText("Obese");
                          circleView_status.setCircleColor(getResources().getColor(R.color.smoking_risk1));
                          circleView_status.setShadowColor(getResources().getColor(R.color.smoking_risk1));
                      }
                      else {
                          tv_status_bmi.setText("Extremly Obese");
                          circleView_status.setCircleColor(getResources().getColor(R.color.purple));
                          circleView_status.setShadowColor(getResources().getColor(R.color.purple));
                      }

                    } else Toast.makeText(Bmi_calculator.this, "Please fill your height or weight", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(Bmi_calculator.this, "Please choose your sex", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public double Calculate_bmi (int height,int weight){
        double h= (double)height/100;
        double number = (double) weight/(h*h);
        return  number;
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
    
}