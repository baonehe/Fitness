package com.google.uddd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BodyFat_Calculator extends AppCompatActivity {
    ToggleButton femalefb, malefb;
    Button btncalculate;
    EditText edtheight,edtweight,edtage;
    TextView tv_result_bdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_calculator);
        femalefb = findViewById(R.id.fbfemale_bdf);
        malefb = findViewById(R.id.fbmale_bdf);
        edtheight=findViewById(R.id.edt_numberheight_bdf);
        edtweight=findViewById(R.id.edt_numberweight_bdf);
        edtage=findViewById(R.id.edt_numberage_bdf);
        btncalculate=findViewById(R.id.btn_calculate_bdf);
        tv_result_bdf= findViewById(R.id.tv_result_bdf);

        femalefb.setOnCheckedChangeListener(checkedChangeListener);
        malefb.setOnCheckedChangeListener(checkedChangeListener);
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (femalefb.isChecked() || malefb.isChecked()){
                        if (!edtage.getText().toString().isEmpty() && !edtweight.getText().toString().isEmpty() && !edtage.getText().toString().isEmpty()){
                            int check_sex ;
                            if (femalefb.isChecked()) check_sex=0;//female = 0
                            else check_sex = 1;//male =1
                            double bmi =  Calculate_bmi(Integer.valueOf(edtheight.getText().toString()),Integer.valueOf(edtweight.getText().toString()));
                            double bmr = Calculate_bmr(bmi,Integer.valueOf(edtage.getText().toString()),check_sex);
                            tv_result_bdf.setText(String.format("%.0f",bmr));
                        }
                    }
            }
        });
    }

    private double Calculate_bmr(double bmi, Integer age,int female_male) {
        double num;
        if (age >=18){
            //male =1 female =0
            if (female_male == 1)
                num = (1.20 * bmi) + (0.23 *age) - 16.2;
            else
                num = (1.20 * bmi) + (0.23 *age )- 5.4;
        }
        else {
            //male =1 female =0
            if (female_male == 1)
                num = (1.51 * bmi) + (0.7 *age) - 2.2;
            else
                num = (1.51 * bmi) - (0.7 *age) + 1.4;
        }
        return  num;
    };

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