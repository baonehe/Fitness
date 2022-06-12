package com.google.uddd_project.material_dialog;

import android.view.View;

import com.google.uddd_project.MainExerciseActivity;

public final class dialog3 implements View.OnClickListener {


    private final MainExerciseActivity mExc;

    public dialog3(MainExerciseActivity mainExerciseActivity) {
        this.mExc = mainExerciseActivity;
    }

    public final void onClick(View view) {
        this.mExc.a(view);
    }
}
