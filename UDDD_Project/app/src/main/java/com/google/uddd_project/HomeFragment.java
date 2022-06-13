package com.google.uddd_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.Entry;
import com.google.uddd_project.adapters.WorkoutData;
import com.google.uddd_project.database.DatabaseOperations;
import com.google.uddd_project.helpers.SqliteHelper;
import com.google.uddd_project.utils.AppUtils;
import com.google.uddd_project.utils.AppUtilss;
import com.google.uddd_project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final  int FRAGEMENT_HOME=0;
    private static final  int FRAGEMENT_MYACCOUNT=1;
    private static final  int FRAGEMENT_FEEDBACK=2;
    private static final  int FRAGEMENT_CALCULATOR=3;
    private static final  int FRAGEMENT_WORKOUTS=4;
    private static final  int FRAGEMENT_WALKSTEP=5;
    private static final  int FRAGEMENT_REMINDER=6;

    private int currentFragment = FRAGEMENT_HOME;

    TextView tvKM;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar, progressBar1;
    private TextView txtProgress, percentScore1,dayleft;
    public double k = 0.0d;
    public SharedPreferences launchDataPreferences;
    public DatabaseOperations databaseOperations;
    public List<WorkoutData> workoutDataList;
    public int workoutPosition = -1;
    public int daysCompletionCounter = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPref;
    private SqliteHelper sqliteHelper;
    com.mikhaellopez.circularfillableloaders.CircularFillableLoaders waterLevelView;
    TextView percentWater;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtilss.KEY_PROGRESS, 0.0d);
            try {
                ((WorkoutData) workoutDataList.get(workoutPosition)).setProgress((float) doubleExtra);
                HomeFragment Workout = HomeFragment.this;
                k = 0.0d;
                daysCompletionCounter = 0;
                for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
                    HomeFragment Workout2 = HomeFragment.this;
                    double d = Workout2.k;
                    double progress = (double) ((WorkoutData) Workout2.workoutDataList.get(i)).getProgress();
                    Double.isNaN(progress);
                    Workout2.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
                    if (((WorkoutData) HomeFragment.this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                        HomeFragment.this.daysCompletionCounter = HomeFragment.this.daysCompletionCounter + 1;
                    }
                }
                HomeFragment Workout3 = HomeFragment.this;
                Workout3.daysCompletionCounter = Workout3.daysCompletionCounter + (HomeFragment.this.daysCompletionCounter / 3);
                HomeFragment.this.progressBar1.setProgress((int) HomeFragment.this.k);
                TextView g = HomeFragment.this.percentScore1;
                StringBuilder sb = new StringBuilder();
                sb.append((int) HomeFragment.this.k);
                sb.append("%");
                g.setText(sb.toString());
                TextView h = HomeFragment.this.dayleft;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Constants.TOTAL_DAYS - HomeFragment.this.daysCompletionCounter);
                sb2.append(" Days left");
                h.setText(sb2.toString());
//                HomeFragment.this.adapter.notifyDataSetChanged();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(doubleExtra);
                Log.i("progress broadcast", sb3.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = sharedPreferences.edit();
        tvKM = view.findViewById(R.id.tvDis);

        if(!sharedPreferences.getString("DistancesKM", "").isEmpty()){
            String KM = sharedPreferences.getString("DistancesKM", "");
            tvKM.setText(KM);
        }

        txtProgress = view.findViewById(R.id.txtProgress);
        progressBar1 = view.findViewById(R.id.progress);

        percentScore1 = view.findViewById(R.id.percentScore);
        dayleft = view.findViewById(R.id.daysLeft);

        this.launchDataPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.databaseOperations = new DatabaseOperations(getActivity());
        final String str = "thirtyday";
        boolean z = this.launchDataPreferences.getBoolean(str, false);
        String str2 = "daysInserted";
        boolean z2 = this.launchDataPreferences.getBoolean(str2, false);
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
        this.workoutDataList = databaseOperations.getAllDaysProgress();
//        this.progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar_drawable));

        for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
            double d = this.k;
            double progress = (double) ((WorkoutData) this.workoutDataList.get(i)).getProgress();
            Double.isNaN(progress);
            this.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
            if (((WorkoutData) this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                this.daysCompletionCounter++;
            }
        }


        int i2 = this.daysCompletionCounter;
        this.daysCompletionCounter = i2 + (i2 / 3);
        this.progressBar1.setProgress((int) this.k);
        TextView textView = this.percentScore1;
        StringBuilder sb = new StringBuilder();
        sb.append((int) this.k);
        sb.append("%");
        textView.setText(sb.toString());
        TextView textView2 = this.dayleft;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Constants.TOTAL_DAYS - this.daysCompletionCounter);
        sb2.append(" Days left");
        textView2.setText(sb2.toString());

        view.findViewById(R.id.cardViewWalkStep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment != FRAGEMENT_WALKSTEP){
                    replaceFragment(new SummaryFragment());
                    currentFragment = FRAGEMENT_WALKSTEP;
                }
            }
        });

        view.findViewById(R.id.cardViewWater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DailyDrinkTargetActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.cardViewExcercises).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment != FRAGEMENT_WORKOUTS){
                    replaceFragment(new WorkoutsFragment());
                    currentFragment = FRAGEMENT_WORKOUTS;
                }
            }
        });

        waterLevelView = view.findViewById(R.id.waterLevelView);
        percentWater = view.findViewById(R.id.percentWater);

        SharedPreferences var10001 = this.getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        Intrinsics.checkExpressionValueIsNotNull(var10001, "getSharedPreferences(App…F, AppUtils.PRIVATE_MODE)");
        this.sharedPref = var10001;
        this.sqliteHelper = new SqliteHelper(getActivity());
        ArrayList entries = new ArrayList();
        ArrayList dateArray = new ArrayList();
        SqliteHelper var10000 = this.sqliteHelper;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sqliteHelper");
        }
        Cursor cursor = var10000.getAllStats();
        if (cursor.moveToFirst()) {

            int i = 0;

            for (int var6 = cursor.getCount(); i < var6; ++i) {
                dateArray.add(cursor.getString(1));
                float percent = (float) cursor.getInt(2) / (float) cursor.getInt(3) * (float) 100;
                double number1 = percent;
                double number2 = (int) (Math.round(number1 * 100)) / 100.0;


                percentWater.setText((number2) + " %");
                waterLevelView.setProgress(100-(int)number2);

                entries.add(new Entry((float) i, percent));
                cursor.moveToNext();
            }
        } else {
//            Toast.makeText(getActivity(), (CharSequence) "Empty", 1).show();
        }

        return  view;
    }
    private  void replaceFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), fragment, "find").addToBackStack(null).commit();
    }

    private SharedPreferences getSharedPreferences(String users_shared_pref, int private_mode) {
        return getContext().getSharedPreferences(users_shared_pref, private_mode);
    }
}