package com.google.uddd_project;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import com.google.uddd_project.adapters.WorkoutData;
import com.google.uddd_project.database.DatabaseOperations;
import com.google.uddd_project.material_dialog.dialog1;
import com.google.uddd_project.material_dialog.dialog2;
import com.google.uddd_project.material_dialog.dialog3;
import com.google.uddd_project.utils.AppUtilss;
import com.google.uddd_project.utils.Constants;

import java.util.ArrayList;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class MainExerciseActivity extends AppCompatActivity {
    public boolean C;
    public Toolbar D;

    public Boolean F;
    public Boolean G;
    public FAImageView animImageFull;

    public TextView count, countRestTimer;
    public DatabaseOperations databaseOperations;
    public String day;
    public TextView eachSideTextView;
    public int excCounter;
    public TextView excDescInReadyToGo, excName, excNameInReadyToGo;
    public CountDownTimer exerciseTimer;
    public int i;
    public LayoutInflater inflater;
    public float k = 1.0f;
    public long l;

    public long m = 25000;
    public int mainExcCounter = 1;
    public ArrayList<WorkoutData> n;
    public TextView o;
    public FAImageView p;
    public ImageView pauseMainExercise, pauseRestTime, playPause, previous_exc;
    public double prog, progress;
    public RoundCornerProgressBar progressbar;
    public TextView q;
    public LinearLayout r;
    public CountDownTimer readyToGoTimer;
    public CoordinatorLayout readytogo_layout, restScreen;
    public CountDownTimer restTimer;
    public ProgressBar restTimerprogress;
    public ImageView resumeRestTime, resumeMainExercise;
    public ProgressBar s;
    public long s1;
    public TextView skip, skipRestTime;
    public ImageView skip_exc;
    public Context t;
    public TextView timerTop;
    public ProgressBar timerprogress;
    public TextView tvProgress, tvProgressMax;
    public int u, v, w;
    public ImageView volume;
    public MyApplication x;

    public MainExerciseActivity() {
        Boolean valueOf = Boolean.valueOf(false);
        this.i = 0;
        this.w = 0;
        this.C = false;
        this.F = valueOf;
        this.G = valueOf;
    }

    private void getScreenHeightWidth() {
        this.t = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.u = displayMetrics.heightPixels;
        this.v = displayMetrics.widthPixels;
    }

    public void mainExcTimer(long j, int i2, float f) {
        WorkoutData workoutData = (WorkoutData) this.n.get(this.excCounter);
        this.animImageFull.reset();
        for (int addImageFrame : workoutData.getImageIdList()) {
            this.animImageFull.addImageFrame(addImageFrame);
        }
        this.restScreen.setVisibility(View.GONE);
        this.animImageFull.startAnimation();
        StringBuilder sb = new StringBuilder();
        sb.append("progressbar: ");
        sb.append(this.l / 1000);
        Log.i("setMax", sb.toString());
        this.progressbar.setMax((float) ((this.l / 1000) - 1));
        this.s = (ProgressBar) this.r.findViewById(this.excCounter);
        this.s.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        this.s.setMax((((int) this.l) / 1000) - 1);
        this.x.addEarCorn();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("setMax: ");
        sb2.append(j / 1000);
        Log.i("mainExcTimer", sb2.toString());
        final float f2 = f;
        final int i3 = i2;
        CountDownTimer r2 = new CountDownTimer(j, 1000) {


            public float f1448a = f2;
            public int b = i3;
            public int c;

            public void onFinish() {
                RoundCornerProgressBar h = MainExerciseActivity.this.progressbar;
                float f2 = this.f1448a;
                this.f1448a = f2 + 1.0f;
                h.setProgress(f2);
                StringBuilder sb = new StringBuilder();
                sb.append("count: ");
                sb.append(this.b);
                sb.append("f ");
                sb.append(this.f1448a);
                Log.i("onTick onFinish", sb.toString());
                MainExerciseActivity.this.excCounter = MainExerciseActivity.this.excCounter + 1;
                MainExerciseActivity.this.animImageFull.stopAnimation();
                String str = "MainExerciseActivity";
                if (MainExerciseActivity.this.excCounter < MainExerciseActivity.this.n.size()) {
                    MainExerciseActivity.this.restScreen.setVisibility(View.VISIBLE);
                    RoundCornerProgressBar h2 = MainExerciseActivity.this.progressbar;
                    MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                    h2.setMax((float) ((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCounter)).getExcCycles());
                    TextView g = MainExerciseActivity.this.tvProgress;
                    StringBuilder sb2 = new StringBuilder();
                    int i = this.b;
                    this.b = i + 1;
                    sb2.append(i);
                    String str2 = "";
                    sb2.append(str2);
                    g.setText(sb2.toString());
                    TextView m = MainExerciseActivity.this.tvProgressMax;
                    StringBuilder sb3 = new StringBuilder();
                    MainExerciseActivity mainExcerciseActivity2 = MainExerciseActivity.this;
                    sb3.append(((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCounter)).getExcCycles());
                    sb3.append(str2);
                    m.setText(sb3.toString());
                    this.f1448a = 1.0f;
                    this.b = 1;
                    MainExerciseActivity mainExcerciseActivity3 = MainExerciseActivity.this;
                    double size = (double) ((float) mainExcerciseActivity3.n.size());
                    Double.isNaN(size);
                    mainExcerciseActivity3.progress = 100.0d / size;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("progress: ");
                    sb4.append(MainExerciseActivity.this.progress);
                    Log.i(str, sb4.toString());
                    float excDayProgress = MainExerciseActivity.this.databaseOperations.getExcDayProgress(MainExerciseActivity.this.day);
                    MainExerciseActivity mainExcerciseActivity4 = MainExerciseActivity.this;
                    double d2 = (double) excDayProgress;
                    double o = mainExcerciseActivity4.progress;
                    Double.isNaN(d2);
                    mainExcerciseActivity4.progress = d2 + o;
                    if (MainExerciseActivity.this.progress <= 100.0d) {
                        MainExerciseActivity.this.databaseOperations.insertExcDayData(MainExerciseActivity.this.day, (float) MainExerciseActivity.this.progress);
                    }
                    try {
                        Intent intent = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
                        intent.putExtra(AppUtilss.KEY_PROGRESS, MainExerciseActivity.this.progress);
                        MainExerciseActivity.this.sendBroadcast(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e("Failed update progress", e2.getMessage());
                    }
                    MainExerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                    MainExerciseActivity.this.resumeRestTime.setVisibility(View.GONE);
                    MainExerciseActivity mainExcerciseActivity5 = MainExerciseActivity.this;
                    mainExcerciseActivity5.a(mainExcerciseActivity5.m);
                } else {
                    MainExerciseActivity mainExcerciseActivity6 = MainExerciseActivity.this;
                    double size2 = (double) ((float) mainExcerciseActivity6.n.size());
                    Double.isNaN(size2);
                    mainExcerciseActivity6.progress = 100.0d / size2;
                    float excDayProgress2 = MainExerciseActivity.this.databaseOperations.getExcDayProgress(MainExerciseActivity.this.day);
                    MainExerciseActivity mainExcerciseActivity7 = MainExerciseActivity.this;
                    double d3 = (double) excDayProgress2;
                    double o2 = mainExcerciseActivity7.progress;
                    Double.isNaN(d3);
                    mainExcerciseActivity7.progress = d3 + o2;
                    if (((int) MainExerciseActivity.this.progress) <= 100) {
                        MainExerciseActivity.this.databaseOperations.insertExcDayData(MainExerciseActivity.this.day, (float) MainExerciseActivity.this.progress);
                    }
                    try {
                        Intent intent2 = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
                        intent2.putExtra(AppUtilss.KEY_PROGRESS, MainExerciseActivity.this.progress);
                        MainExerciseActivity.this.sendBroadcast(intent2);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    MainExerciseActivity.this.restScreen.setVisibility(View.GONE);
                    Intent intent3 = new Intent(MainExerciseActivity.this, CompletionExcActivity.class);
                    intent3.putExtra("day", MainExerciseActivity.this.day);
                    int i2 = 0;
                    int i3 = 0;
                    for (int i4 = 0; i4 < MainExerciseActivity.this.n.size(); i4++) {
                        i2 += ((WorkoutData) MainExerciseActivity.this.n.get(i4)).getExcCycles();
                        i3 = i3 + ((WorkoutData) MainExerciseActivity.this.n.get(i4)).getImageIdList().length + Constants.REST_TIME;
                    }
                    intent3.putExtra("totalExc", i2);
                    intent3.putExtra("totalTime", i3);
                    MainExerciseActivity.this.startActivity(intent3);
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("excCounter: ");
                sb5.append(MainExerciseActivity.this.excCounter);
                Log.i(str, sb5.toString());
                MainExerciseActivity mainExcerciseActivity8 = MainExerciseActivity.this;
                mainExcerciseActivity8.k = 1.0f;
                mainExcerciseActivity8.mainExcCounter = 1;
            }


            public void onTick(long j) {
                int i;
                int i2;
                String str;
                MyApplication absWomenApplication;
                String upperCase;
                TextView m = null;
                String sb = null;
                TextView g = null;
                String valueOf = null;
                String str2 = " ";
                String str3 = "";
                StringBuilder sb2 = new StringBuilder();
                String str4 = "millisUntilFinished: ";
                sb2.append(str4);
                sb2.append(j);
                Log.i("millisUntilFinished", sb2.toString());
                try {
                    if (((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getImageIdList().length <= 2) {
                        if (this.b <= ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcCycles()) {
                            g = MainExerciseActivity.this.tvProgress;
                            StringBuilder sb3 = new StringBuilder();
                            int i3 = this.b;
                            this.b = i3 + 1;
                            sb3.append(i3);
                            sb3.append(str3);
                            valueOf = String.valueOf(sb3.toString());
                        }
                        RoundCornerProgressBar h = MainExerciseActivity.this.progressbar;
                        float f2 = this.f1448a;
                        this.f1448a = 1.0f + f2;
                        h.setProgress(f2);
                        MainExerciseActivity.this.s.setProgress((int) this.f1448a);
                        TextView i4 = MainExerciseActivity.this.timerTop;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(this.b);
                        sb4.append(str3);
                        i4.setText(sb4.toString());
                        upperCase = ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcName().replace("_", str2).toUpperCase();
                        MainExerciseActivity.this.excName.setText(upperCase);
                        if (!upperCase.equalsIgnoreCase("swimmer and superman")) {
                        }
                    } else if (this.f1448a == 1.0f) {
                        g = MainExerciseActivity.this.tvProgress;
                        valueOf = "1";
                    } else {
                        if (this.f1448a % ((float) ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getImageIdList().length) == 0.0f) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str4);
                            sb5.append(this.f1448a % ((float) ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getImageIdList().length));
                            Log.i("mainExce", sb5.toString());
                            g = MainExerciseActivity.this.tvProgress;
                            StringBuilder sb6 = new StringBuilder();
                            int i5 = this.b;
                            this.b = i5 + 1;
                            sb6.append(i5);
                            sb6.append(str3);
                            valueOf = String.valueOf(sb6.toString());
                        }
                        RoundCornerProgressBar h2 = MainExerciseActivity.this.progressbar;
                        float f22 = this.f1448a;
                        this.f1448a = 1.0f + f22;
                        h2.setProgress(f22);
                        MainExerciseActivity.this.s.setProgress((int) this.f1448a);
                        TextView i42 = MainExerciseActivity.this.timerTop;
                        StringBuilder sb42 = new StringBuilder();
                        sb42.append(this.b);
                        sb42.append(str3);
                        i42.setText(sb42.toString());
                        upperCase = ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcName().replace("_", str2).toUpperCase();
                        MainExerciseActivity.this.excName.setText(upperCase);
                        if (!upperCase.equalsIgnoreCase("standing bicycle crunches") && !upperCase.equalsIgnoreCase("clapping crunches") && !upperCase.equalsIgnoreCase("heel touch") && !upperCase.equalsIgnoreCase("bicycle crunches") && !upperCase.equalsIgnoreCase("flutter kicks") && !upperCase.equalsIgnoreCase("adductor strect in standing") && !upperCase.equalsIgnoreCase("lunges") && !upperCase.equalsIgnoreCase("side lunges")) {
                            if (!upperCase.equalsIgnoreCase("swimmer and superman")) {
                                MainExerciseActivity.this.eachSideTextView.setText(str2);
                                String str5 = "/";
                                if (!upperCase.equalsIgnoreCase("plank")) {
                                    m = MainExerciseActivity.this.tvProgressMax;
                                    StringBuilder sb7 = new StringBuilder();
                                    sb7.append(str5);
                                    sb7.append(((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcCycles());
                                    sb7.append("s");
                                    sb = sb7.toString();
                                } else {
                                    m = MainExerciseActivity.this.tvProgressMax;
                                    StringBuilder sb8 = new StringBuilder();
                                    sb8.append(str5);
                                    sb8.append(((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcCycles());
                                    sb = sb8.toString();
                                }
                                m.setText(sb);
                                MainExerciseActivity.this.s1 = j;
                                MainExerciseActivity.this.mainExcCounter = this.b;
                                MainExerciseActivity.this.k = this.f1448a;
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append("onTick: ");
                                sb9.append(this.b);
                                sb9.append("      ");
                                sb9.append(this.c);
                                sb9.append("           ");
                                sb9.append(i3);
                                Log.d("mycount", sb9.toString());
                                i = this.c;
                                i2 = this.b;
                                if (i != i2) {
                                    MainExerciseActivity.this.x.playEarCorn();
                                    return;
                                }
                                this.c = i2;
                                int i6 = this.c;
                                if (i6 != 1) {
                                    MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                                    if (i6 <= ((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCounter)).getExcCycles()) {
                                        absWomenApplication = MainExerciseActivity.this.x;
                                        StringBuilder sb10 = new StringBuilder();
                                        sb10.append(str3);
                                        sb10.append(this.c - 1);
                                        str = sb10.toString();
                                    } else {
                                        absWomenApplication = MainExerciseActivity.this.x;
                                        StringBuilder sb11 = new StringBuilder();
                                        sb11.append(str3);
                                        MainExerciseActivity mainExcerciseActivity2 = MainExerciseActivity.this;
                                        sb11.append(((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCounter)).getExcCycles());
                                        str = sb11.toString();
                                    }
                                    absWomenApplication.speak(str);
                                    return;
                                }
                                return;
                            }
                        }
                        TextView l = MainExerciseActivity.this.eachSideTextView;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("Each Side x ");
                        sb12.append(((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcCycles() / 2);
                        l.setText(sb12.toString());
                        String str52 = "/";
                        if (!upperCase.equalsIgnoreCase("plank")) {
                        }
                        m.setText(sb);
                        MainExerciseActivity.this.s1 = j;
                        MainExerciseActivity.this.mainExcCounter = this.b;
                        MainExerciseActivity.this.k = this.f1448a;
                        StringBuilder sb92 = new StringBuilder();
                        sb92.append("onTick: ");
                        sb92.append(this.b);
                        sb92.append("      ");
                        sb92.append(this.c);
                        sb92.append("           ");
                        sb92.append(i3);
                        Log.d("mycount", sb92.toString());
                        i = this.c;
                        i2 = this.b;
                        if (i != i2) {
                        }
                    }
                    g.setText(valueOf);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RoundCornerProgressBar h22 = MainExerciseActivity.this.progressbar;
                float f222 = this.f1448a;
                this.f1448a = 1.0f + f222;
                h22.setProgress(f222);
                MainExerciseActivity.this.s.setProgress((int) this.f1448a);
                TextView i422 = MainExerciseActivity.this.timerTop;
                StringBuilder sb422 = new StringBuilder();
                sb422.append(this.b);
                sb422.append(str3);
                i422.setText(sb422.toString());
                try {
                    upperCase = ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcName().replace("_", str2).toUpperCase();
                    MainExerciseActivity.this.excName.setText(upperCase);
                    if (!upperCase.equalsIgnoreCase("swimmer and superman")) {
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        };
        this.exerciseTimer = r2.start();
    }

    public void a(long j) {
        String upperCase = ((WorkoutData) this.n.get(this.excCounter)).getExcName().replace("_", " ").toUpperCase();
        this.o.setText(upperCase);
        TextView textView = this.q;
        StringBuilder sb = new StringBuilder();
        sb.append("x");
        sb.append(((WorkoutData) this.n.get(this.excCounter)).getExcCycles());
        textView.setText(sb.toString());
        this.p.reset();
        for (int addImageFrame : ((WorkoutData) this.n.get(this.excCounter)).getImageIdList()) {
            this.p.addImageFrame(addImageFrame);
        }
        this.p.startAnimation();
        this.restTimerprogress.setMax((int) (this.m / 1000));
        if (j == this.m) {
            this.x.speak("Take rest");
            MyApplication absWomenApplication = this.x;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("the next exercise is ");
            sb2.append(upperCase);
            absWomenApplication.speak(sb2.toString());
        }
        CountDownTimer r2 = new CountDownTimer(j, 1000) {
            public void onFinish() {
                MainExerciseActivity.this.restScreen.setVisibility(View.GONE);
                MainExerciseActivity.this.F = Boolean.valueOf(false);
                try {
                    long length = (long) (((((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getImageIdList().length > 2 ? ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getImageIdList().length * ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcCycles() : ((WorkoutData) MainExerciseActivity.this.n.get(MainExerciseActivity.this.excCounter)).getExcCycles()) + 1) * 1000);
                    MainExerciseActivity.this.l = length;
                    MainExerciseActivity.this.pauseMainExercise.setVisibility(View.VISIBLE);
                    MainExerciseActivity.this.resumeMainExercise.setVisibility(View.GONE);
                    MainExerciseActivity.this.mainExcTimer(length, MainExerciseActivity.this.mainExcCounter, MainExerciseActivity.this.k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint({"SetTextI18n"})
            public void onTick(long j) {
                long j2 = (j - 1000) / 1000;
                MainExerciseActivity.this.restTimerprogress.setProgress((int) j2);
                TextView s = MainExerciseActivity.this.countRestTimer;
                StringBuilder sb = new StringBuilder();
                sb.append(j2);
                sb.append("");
                s.setText(sb.toString());
                MainExerciseActivity.this.s1 = j;
                if (j2 < 4) {
                    if (j2 == 3) {
                        MainExerciseActivity.this.x.speak("three ");
                    }
                    if (j2 == 2) {
                        MainExerciseActivity.this.x.speak("two ");
                    }
                    if (j2 == 1) {
                        MainExerciseActivity.this.x.speak("one ");
                    }
                    if (j2 == 0 && !MainExerciseActivity.this.F.booleanValue()) {
                        MainExerciseActivity.this.x.speak("start");
                        MainExerciseActivity.this.F = Boolean.valueOf(true);
                    }
                } else if (!MainExerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExerciseActivity.this.x.playEarCorn();
                }
            }
        };
        this.restTimer = r2.start();
    }

    public void a(View view) {
        if (this.x.isSpeaking().booleanValue()) {
            this.x.stop();
        }
        if (this.excCounter > 0) {
            this.s.setProgress(0);
            this.exerciseTimer.cancel();
            this.excCounter--;
            this.progressbar.setMax((float) ((WorkoutData) this.n.get(this.excCounter)).getExcCycles());
            this.tvProgressMax.setText(String.valueOf(((WorkoutData) this.n.get(this.excCounter)).getExcCycles()));
            long calculateExTime = calculateExTime(this.excCounter);
            this.l = calculateExTime;
            this.pauseMainExercise.setVisibility(View.VISIBLE);
            this.resumeMainExercise.setVisibility(View.GONE);
            double excDayProgress = (double) this.databaseOperations.getExcDayProgress(this.day);
            double size = (double) ((float) this.n.size());
            Double.isNaN(size);
            double d = 100.0d / size;
            Double.isNaN(excDayProgress);
            this.progress = excDayProgress - d;
            dataBaseProgressUpdate(this.progress);
            mainExcTimer(calculateExTime, 1, 1.0f);
            return;
        }
        Toast.makeText(this.x, "This is first exercise", Toast.LENGTH_SHORT).show();
    }

    public void a(MaterialDialog materialDialog, DialogAction dialogAction) {
        try {
            materialDialog.dismiss();
            if (this.readyToGoTimer != null) {
                this.readyToGoTimer.cancel();
            }
            if (this.exerciseTimer != null) {
                this.exerciseTimer.cancel();
            }
            if (this.restTimer != null) {
                this.restTimer.cancel();
            }
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(603979776);
            startActivity(intent);
            onSuperBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public void b() {
        new MaterialDialog.Builder(this).title((CharSequence) "Confirm!").titleColor(ContextCompat.getColor(this, R.color.black))
                .content((CharSequence) "Would you like to quit the workout?").contentColor(ContextCompat.getColor(this, R.color.textColor))
                .positiveText((CharSequence) "Yes").positiveColor(ContextCompat.getColor(this, R.color.colorAccent))
                .onPositive(new dialog2(this)).negativeText((CharSequence) "No").negativeColor(ContextCompat.getColor(this, R.color.textColor)).onNegative(dialog1.dl).show();
    }

    public long calculateExTime(int i2) {
        return ((WorkoutData) this.n.get(i2)).getImageIdList().length > 2
                ? (long) ((((long) ((WorkoutData) this.n.get(i2)).getImageIdList().length * ((WorkoutData) this.n.get(i2)).getExcCycles()) + 1) * 1000)
                : (long) ((((WorkoutData) this.n.get(i2)).getExcCycles() + 1) * 1000L);
    }

    public void readyToGoFun(long j) {
        this.excDescInReadyToGo.setText(((WorkoutData) this.n.get(this.excCounter)).getExcDescResId());
        String upperCase = ((WorkoutData) this.n.get(this.excCounter)).getExcName().replace("_", " ").toUpperCase();
        this.excNameInReadyToGo.setText(upperCase);
        String lowerCase = upperCase.toLowerCase();
        if (j == 10000) {
            this.x.speak("ready to go ");
            MyApplication absWomenApplication = this.x;
            StringBuilder sb = new StringBuilder();
            sb.append("the exercise is ");
            sb.append(lowerCase);
            absWomenApplication.speak(sb.toString());
        }
        this.timerprogress.setMax(10);
        CountDownTimer r2 = new CountDownTimer(j, 1000) {
            public void onFinish() {
                int i;
                Log.i("readyToGoTimer", "onFinish: ");
                MainExerciseActivity.this.G = Boolean.valueOf(false);
                MainExerciseActivity.this.timerprogress.setProgress(0);
                MainExerciseActivity.this.readytogo_layout.setVisibility(View.GONE);
                MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                if (((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCounter)).getImageIdList().length > 2) {
                    MainExerciseActivity mainExcerciseActivity2 = MainExerciseActivity.this;
                    int length = ((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCounter)).getImageIdList().length;
                    MainExerciseActivity mainExcerciseActivity3 = MainExerciseActivity.this;
                    i = length * ((WorkoutData) mainExcerciseActivity3.n.get(mainExcerciseActivity3.excCounter)).getExcCycles();
                } else {
                    MainExerciseActivity mainExcerciseActivity4 = MainExerciseActivity.this;
                    i = ((WorkoutData) mainExcerciseActivity4.n.get(mainExcerciseActivity4.excCounter)).getExcCycles();
                }
                long j = (long) ((i + 1) * 1000);
                MainExerciseActivity mainExcerciseActivity5 = MainExerciseActivity.this;
                mainExcerciseActivity5.l = j;
                mainExcerciseActivity5.pauseMainExercise.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.resumeMainExercise.setVisibility(View.GONE);
                MainExerciseActivity mainExcerciseActivity6 = MainExerciseActivity.this;
                mainExcerciseActivity6.mainExcTimer(j, mainExcerciseActivity6.mainExcCounter, MainExerciseActivity.this.k);
            }

            public void onTick(long j) {
                StringBuilder sb = new StringBuilder();
                sb.append("progressbar: ");
                sb.append(((int) j) / 1000);
                Log.i("readyToGoTimer", sb.toString());
                long j2 = j - 1000;
                MainExerciseActivity.this.timerprogress.setProgress(((int) j2) / 1000);
                TextView u = MainExerciseActivity.this.count;
                StringBuilder sb2 = new StringBuilder();
                long j3 = j2 / 1000;
                sb2.append(j3);
                sb2.append("");
                u.setText(sb2.toString());
                MainExerciseActivity.this.s1 = j;
                if (j3 < 4) {
                    if (j3 == 3) {
                        MainExerciseActivity.this.x.speak("three ");
                    }
                    if (j3 == 2) {
                        MainExerciseActivity.this.x.speak("two ");
                    }
                    if (j3 == 1) {
                        MainExerciseActivity.this.x.speak("one ");
                    }
                    if (j3 == 0 && !MainExerciseActivity.this.G.booleanValue()) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("onTick: ");
                        sb3.append(j);
                        Log.d("TAG", sb3.toString());
                        MainExerciseActivity.this.x.speak("let's start ");
                        MainExerciseActivity.this.G = Boolean.valueOf(true);
                    }
                } else if (!MainExerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExerciseActivity.this.x.playEarCorn();
                }
            }
        };
        this.readyToGoTimer = r2.start();
    }

    public void dataBaseProgressUpdate(double d) {
        this.databaseOperations.insertExcDayData(this.day, (float) d);
        this.databaseOperations.insertExcCounter(this.day, this.excCounter);
        StringBuilder sb = new StringBuilder();
        sb.append(this.excCounter);
        sb.append("");
        Log.d("CounterValue", sb.toString());
        try {
            Intent intent = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
            intent.putExtra(AppUtilss.KEY_PROGRESS, d);
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        b();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        setContentView((int) R.layout.main_exercise_layout);
        this.databaseOperations = new DatabaseOperations(this);
        Toolbar toolbar = findViewById(R.id.ready_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.n = (ArrayList) getIntent().getExtras().getSerializable("workoutDataList");

        Bundle extras = getIntent().getExtras();
        extras.getClass();
        this.day = extras.getString("day");
        this.prog = (double) getIntent().getExtras().getFloat("progress");
        this.x = MyApplication.getInstance();
        this.excCounter = ((int) this.prog) / (100 / this.n.size());
        this.progressbar = (RoundCornerProgressBar) findViewById(R.id.progress_one);
        this.animImageFull = (FAImageView) findViewById(R.id.animImageFull);
        this.tvProgress = (TextView) findViewById(R.id.tv_progress);
        this.tvProgressMax = (TextView) findViewById(R.id.tv_progress_max);
        this.timerTop = (TextView) findViewById(R.id.timerTop);
        this.restScreen = (CoordinatorLayout) findViewById(R.id.restScreen);
        this.excName = (TextView) findViewById(R.id.excName);
        this.pauseMainExercise = (ImageView) findViewById(R.id.pauseMainExercise);
        this.resumeMainExercise = (ImageView) findViewById(R.id.resumeMainExercise);
        this.skip = (TextView) findViewById(R.id.skip);
        this.skipRestTime = (TextView) findViewById(R.id.skipRestTime);
        this.timerprogress = (ProgressBar) findViewById(R.id.timer);
        this.volume = (ImageView) findViewById(R.id.volumeOption);
        this.count = (TextView) findViewById(R.id.counting);
        this.playPause = (ImageView) findViewById(R.id.floatingPlay);
        this.eachSideTextView = (TextView) findViewById(R.id.eachSideTextView);
        this.excDescInReadyToGo = (TextView) findViewById(R.id.excDescInReadyToGo);
        this.excNameInReadyToGo = (TextView) findViewById(R.id.excNameInReadyToGo);
        this.skip_exc = (ImageView) findViewById(R.id.skip_exc);
        this.previous_exc = (ImageView) findViewById(R.id.previous_exc);
        this.pauseRestTime = (ImageView) findViewById(R.id.pauseRestTime);
        this.resumeRestTime = (ImageView) findViewById(R.id.resumeRestTime);
        this.restTimerprogress = (ProgressBar) findViewById(R.id.rest_timer);
        this.countRestTimer = (TextView) findViewById(R.id.rest_counting);
        this.o = (TextView) findViewById(R.id.nextExerciseName);
        this.q = (TextView) findViewById(R.id.nextExerciseCycles);
        this.p = (FAImageView) findViewById(R.id.nextExerciseAnimation);
        this.D = (Toolbar) findViewById(R.id.rest_toolbar);
        this.r = (LinearLayout) findViewById(R.id.Layoutprogress);


        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);

        String string = sharedPreferences.getString("json_string", "");

        this.readytogo_layout = (CoordinatorLayout) findViewById(R.id.readytogo_layout);
        this.progressbar.setMax(10.0f);
        this.animImageFull.setInterval(1000);
        this.animImageFull.setLoop(true);
        this.animImageFull.reset();
        try {
            for (int addImageFrame : ((WorkoutData) this.n.get(this.excCounter)).getImageIdList()) {
                this.animImageFull.addImageFrame(addImageFrame);
            }
        } catch (IndexOutOfBoundsException unused) {
            this.excCounter = this.n.size() - 1;
            for (int addImageFrame2 : ((WorkoutData) this.n.get(this.excCounter)).getImageIdList()) {
                this.animImageFull.addImageFrame(addImageFrame2);
            }
        }
        this.animImageFull.startAnimation();
        this.skipRestTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainExerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExerciseActivity.this.x.stop();
                }
                MainExerciseActivity.this.restTimer.cancel();
                MainExerciseActivity.this.restTimer.onFinish();
                MainExerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.resumeRestTime.setVisibility(View.GONE);
            }
        });
        this.skip.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainExerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExerciseActivity.this.x.stop();
                }
                MainExerciseActivity.this.readyToGoTimer.cancel();
                MainExerciseActivity.this.readyToGoTimer.onFinish();
            }
        });
        this.skip_exc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExerciseActivity.this.mainExcCounter);
                Log.i("mainexc", sb.toString());
                if (MainExerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExerciseActivity.this.x.stop();
                }
                MainExerciseActivity.this.exerciseTimer.cancel();
                MainExerciseActivity mainExerciseActivity = MainExerciseActivity.this;
                ProgressBar progressBar = mainExerciseActivity.s;
                int length = ((WorkoutData) mainExerciseActivity.n.get(mainExerciseActivity.excCounter)).getImageIdList().length;
                MainExerciseActivity mainExerciseActivity2 = MainExerciseActivity.this;
                progressBar.setProgress(length * ((WorkoutData) mainExerciseActivity2.n.get(mainExerciseActivity2.excCounter)).getExcCycles());
                MainExerciseActivity.this.exerciseTimer.onFinish();
            }
        });
        this.previous_exc.setOnClickListener(new dialog3(this));
        this.playPause.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainExerciseActivity.this.i % 2 == 0) {
                    MainExerciseActivity.this.playPause.setBackgroundResource(R.drawable.play);
                    MainExerciseActivity mainExerciseActivity = MainExerciseActivity.this;
                    mainExerciseActivity.C = true;
                    mainExerciseActivity.readyToGoTimer.cancel();
                } else {
                    MainExerciseActivity mainExerciseActivity2 = MainExerciseActivity.this;
                    mainExerciseActivity2.C = false;
                    mainExerciseActivity2.playPause.setBackgroundResource(R.drawable.pause);
                    MainExerciseActivity mainExerciseActivity3 = MainExerciseActivity.this;
                    mainExerciseActivity3.readyToGoFun(mainExerciseActivity3.s1);
                }
                MainExerciseActivity.this.i = MainExerciseActivity.this.i + 1;
            }
        });
        this.pauseRestTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainExerciseActivity.this.pauseRestTime.setVisibility(View.GONE);
                MainExerciseActivity.this.resumeRestTime.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.restTimer.cancel();
            }
        });
        this.resumeRestTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainExerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.resumeRestTime.setVisibility(View.GONE);
                MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                mainExcerciseActivity.a(mainExcerciseActivity.s1);
            }
        });
        this.pauseMainExercise.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExerciseActivity.this.mainExcCounter);
                sb.append("mainExcProgress ");
                sb.append(MainExerciseActivity.this.k);
                Log.i("pauseMainExercise", sb.toString());
                MainExerciseActivity.this.pauseMainExercise.setVisibility(View.GONE);
                MainExerciseActivity.this.resumeMainExercise.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.exerciseTimer.cancel();
                MainExerciseActivity.this.animImageFull.stopAnimation();
            }
        });
        this.resumeMainExercise.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExerciseActivity.this.mainExcCounter);
                sb.append("mainExcProgress ");
                sb.append(MainExerciseActivity.this.k);
                Log.i("resumeMainExercise", sb.toString());
                MainExerciseActivity.this.pauseMainExercise.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.resumeMainExercise.setVisibility(View.GONE);
                MainExerciseActivity mainExerciseActivity = MainExerciseActivity.this;
                mainExerciseActivity.mainExcTimer(mainExerciseActivity.s1 - 1000, MainExerciseActivity.this.mainExcCounter, MainExerciseActivity.this.k);
            }
        });
        readyToGoFun(10000);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        for (int i2 = 0; i2 < this.n.size(); i2++) {
            this.s = new ProgressBar(this, null, 16842872);
            layoutParams2.rightMargin = 2;
            layoutParams2.leftMargin = 2;
            this.s.setPadding(0, 0, 0, -8);
            this.s.setLayoutParams(layoutParams2);
            this.s.setId(i2);
            this.s.setScaleY(2.5f);
            this.r.addView(this.s);
        }
        for (int i3 = 0; i3 < this.excCounter; i3++) {
            this.s = (ProgressBar) this.r.findViewById(i3);
            this.s.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
            this.s.setMax(((WorkoutData) this.n.get(this.excCounter)).getImageIdList().length * ((WorkoutData) this.n.get(this.excCounter)).getExcCycles());
            this.s.setProgress(((WorkoutData) this.n.get(this.excCounter)).getImageIdList().length * ((WorkoutData) this.n.get(this.excCounter)).getExcCycles());
        }
        getScreenHeightWidth();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        CountDownTimer countDownTimer = this.readyToGoTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = this.exerciseTimer;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        CountDownTimer countDownTimer3 = this.restTimer;
        if (countDownTimer3 != null) {
            countDownTimer3.cancel();
        }
        if (!this.C) {
            this.i--;
        }
        this.playPause.setBackgroundResource(R.drawable.play);
        this.resumeMainExercise.setVisibility(View.VISIBLE);
        this.pauseMainExercise.setVisibility(View.GONE);
        this.resumeRestTime.setVisibility(View.VISIBLE);
        this.pauseRestTime.setVisibility(View.GONE);
        this.animImageFull.stopAnimation();
    }

    public void onResume() {
        super.onResume();
        this.pauseMainExercise.setVisibility(View.GONE);
        this.resumeMainExercise.setVisibility(View.VISIBLE);
    }

    public void onSuperBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            b();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
