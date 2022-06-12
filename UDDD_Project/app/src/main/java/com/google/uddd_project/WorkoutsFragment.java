package com.google.uddd_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.uddd_project.adapters.AllDayAdapter;
import com.google.uddd_project.adapters.WorkoutData;
import com.google.uddd_project.database.DatabaseOperations;
import com.google.uddd_project.listeners.RecyclerItemClickListener;
import com.google.uddd_project.listeners.RecyclerItemClickListener.onItemClickListener;
import com.google.uddd_project.material_dialog.dialog1;
import com.google.uddd_project.material_dialog.dialog6;
import com.google.uddd_project.utils.AppUtilss;
import com.google.uddd_project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * create an instance of this fragment.
 */
public class WorkoutsFragment extends Fragment {
    public AllDayAdapter adapter;
    public ArrayList<String> arr;
    public DatabaseOperations databaseOperations;
    public TextView dayleft;
    public int daysCompletionCounter = 0;
    public int height;
    public double k = 0.0d;
    public SharedPreferences launchDataPreferences;
    public GridLayoutManager manager;
    public TextView percentScore1;
    public ProgressBar progressBar;

    public RecyclerView recyclerView;
    public int squareSize;
    public int width;
    public List<WorkoutData> workoutDataList;
    public int workoutPosition = -1;

    public BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtilss.KEY_PROGRESS, 0.0d);
            try {
                ((WorkoutData) WorkoutsFragment.this.workoutDataList.get(WorkoutsFragment.this.workoutPosition)).setProgress((float) doubleExtra);
                WorkoutsFragment Workout = WorkoutsFragment.this;
                Workout.k = 0.0d;
                Workout.daysCompletionCounter = 0;
                for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
                    WorkoutsFragment Workout2 = WorkoutsFragment.this;
                    double d = Workout2.k;
                    double progress = (double) ((WorkoutData) Workout2.workoutDataList.get(i)).getProgress();
                    Double.isNaN(progress);
                    Workout2.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
                    if (((WorkoutData) WorkoutsFragment.this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                        WorkoutsFragment.this.daysCompletionCounter = WorkoutsFragment.this.daysCompletionCounter + 1;
                    }
                }
                WorkoutsFragment Workout3 = WorkoutsFragment.this;
                Workout3.daysCompletionCounter = Workout3.daysCompletionCounter + (WorkoutsFragment.this.daysCompletionCounter / 3);
                WorkoutsFragment.this.progressBar.setProgress((int) WorkoutsFragment.this.k);
                TextView g = WorkoutsFragment.this.percentScore1;
                StringBuilder sb = new StringBuilder();
                sb.append((int) WorkoutsFragment.this.k);
                sb.append("%");
                g.setText(sb.toString());
                TextView h = WorkoutsFragment.this.dayleft;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Constants.TOTAL_DAYS - WorkoutsFragment.this.daysCompletionCounter);
                sb2.append(" Days left");
                h.setText(sb2.toString());
                WorkoutsFragment.this.adapter.notifyDataSetChanged();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(doubleExtra);
                Log.i("progress broadcast", sb3.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void dialog(int i, MaterialDialog materialDialog, DialogAction dialogAction) {
        TextView textView = null;
        String str = null;
        try {
            materialDialog.dismiss();
            String str2 = (String) this.arr.get(i);
            if (this.workoutDataList != null) {
                this.workoutDataList.clear();
            }
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
            this.workoutDataList = this.databaseOperations.getAllDaysProgress();
            this.adapter = new AllDayAdapter(getActivity(), this.workoutDataList);
            this.recyclerView.getRecycledViewPool().clear();
            this.recyclerView.setAdapter(this.adapter);
            this.daysCompletionCounter--;
            TextView textView2 = this.dayleft;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.TOTAL_DAYS - this.daysCompletionCounter);
            sb.append(" Days left");
            textView2.setText(sb.toString());
            if (this.daysCompletionCounter > 0) {
                this.progressBar.setProgress((int) (this.k - 4.348d));
                textView = this.percentScore1;
                StringBuilder sb2 = new StringBuilder();
                sb2.append((int) (this.k - 4.348d));
                sb2.append("%");
                str = sb2.toString();
            } else {
                if (this.daysCompletionCounter == 0) {
                    this.progressBar.setProgress(0);
                    textView = this.percentScore1;
                    str = "0%";
                }
                Intent intent = new Intent(getActivity(), DayActivity.class);
                intent.putExtra("day", str2);
                intent.putExtra("day_num", i);
                intent.putExtra("progress", ((WorkoutData) this.workoutDataList.get(i)).getProgress());
                startActivity(intent);
            }
            textView.setText(str);
            Intent intent2 = new Intent(getActivity(), DayActivity.class);
            intent2.putExtra("day", str2);
            intent2.putExtra("day_num", i);
            intent2.putExtra("progress", ((WorkoutData) this.workoutDataList.get(i)).getProgress());
            startActivity(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void show(int i) {
        new MaterialDialog.Builder(getActivity()).title((CharSequence) "Confirm!").titleColor(ContextCompat.getColor(getActivity(), R.color.black)).content((CharSequence) "Would you like to repeat this workout?").contentColor(ContextCompat.getColor
                (getActivity(), R.color.textColor)).positiveText((CharSequence) "Yes").positiveColor
                (ContextCompat.getColor(getActivity(), R.color.colorAccent))
                .onPositive(new dialog6(this, i)).negativeText((CharSequence) "No")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.textColor))
                .onNegative(dialog1.dl).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_outs, container, false);
        view.findViewById(R.id.menufood7day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ImagePageView.class);
                startActivity(intent);
            }
        });

        this.percentScore1 = view.findViewById(R.id.percentScore);
        this.dayleft = view.findViewById(R.id.daysLeft);
        this.launchDataPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.databaseOperations = new DatabaseOperations(getActivity());
        String str = "thirtyday";
        boolean z = this.launchDataPreferences.getBoolean(str, false);
        String str2 = "daysInserted";
        boolean z2 = this.launchDataPreferences.getBoolean(str2, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
        if (!z2 && this.databaseOperations.CheckDBEmpty() == 0) {
            this.databaseOperations.insertExcALLDayData();
            SharedPreferences.Editor edit = this.launchDataPreferences.edit();
            edit.putBoolean(str2, true);
            edit.apply();
        }
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = this.databaseOperations.getAllDaysProgress();
        this.progressBar = view.findViewById(R.id.progress);
//        this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        for (int i = 0; i < 30; i++) {
            double d = this.k;
            double progress = this.workoutDataList.get(i).getProgress();
            Double.isNaN(progress);
            this.k = d + ((progress * 4.348d) / 100.0d);
            if (this.workoutDataList.get(i).getProgress() >= 99.0f) {
                this.daysCompletionCounter++;
            }
        }
        int i2 = this.daysCompletionCounter;
        this.daysCompletionCounter = i2 + (i2 / 3);
        this.progressBar.setProgress((int) this.k);
        TextView textView = this.percentScore1;
        StringBuilder sb = new StringBuilder();
        sb.append((int) this.k);
        sb.append("%");
        textView.setText(sb.toString());
        TextView textView2 = this.dayleft;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(30 - this.daysCompletionCounter);
        sb2.append(" Days left");
        textView2.setText(sb2.toString());

        this.recyclerView = view.findViewById(R.id.recycler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        this.manager = new GridLayoutManager(getActivity(), 3);

        this.adapter = new AllDayAdapter(container.getContext(), this.workoutDataList);
        this.arr = new ArrayList<>();
        this.recyclerView.getRecycledViewPool().clear();
        for (int i3 = 1; i3 <= 30; i3++) {
            ArrayList<String> arrayList = this.arr;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Day ");
            sb3.append(i3);
            arrayList.add(sb3.toString());
        }
        if (z) {
            SharedPreferences.Editor edit2 = this.launchDataPreferences.edit();
            edit2.putBoolean(str, false);
            edit2.apply();
            restartExercise();
            this.daysCompletionCounter = 0;
        }
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new onItemClickListener() {
            public void OnItem(View view, int i) {
                Intent intent;
                WorkoutsFragment.this.workoutPosition = i;
                if ((WorkoutsFragment.this.workoutPosition + 1) % 4 == 0) {
                    intent = new Intent(getContext(), RestDayActivity.class);
                } else if (WorkoutsFragment.this.workoutDataList.get(i).getProgress() < 99.0f) {
                    intent = new Intent(getContext(), DayActivity.class);
                    intent.putExtra("day", WorkoutsFragment.this.arr.get(i));
                    intent.putExtra("day_num", i);
                    intent.putExtra("progress", WorkoutsFragment.this.workoutDataList.get(i).getProgress());
                } else {
                    WorkoutsFragment.this.show(i);
                    return;
                }
                WorkoutsFragment.this.startActivity(intent);
            }
        }));
        getActivity().registerReceiver(this.progressReceiver, new IntentFilter(AppUtilss.WORKOUT_BROADCAST_FILTER));
        if (this.daysCompletionCounter > 4) {
        }
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        BroadcastReceiver broadcastReceiver = this.progressReceiver;
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    public void restartExercise() {
        SharedPreferences.Editor edit = this.launchDataPreferences.edit();
        String str = "daysInserted";
        edit.putBoolean(str, false);
        edit.apply();
        for (int i = 0; i < 30; i++) {
            String str2 = this.arr.get(i);
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
        }
        edit.putBoolean(str, true);
        edit.apply();
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = this.databaseOperations.getAllDaysProgress();
        this.adapter = new AllDayAdapter(getActivity(), this.workoutDataList);
        this.recyclerView.getRecycledViewPool().clear();
        this.recyclerView.setAdapter(this.adapter);
        this.progressBar.setProgress(0);
        this.percentScore1.setText("0%");
        TextView textView = this.dayleft;
        StringBuilder sb = new StringBuilder();
        sb.append(30);
        sb.append(" Days left");
        textView.setText(sb.toString());
    }
}
