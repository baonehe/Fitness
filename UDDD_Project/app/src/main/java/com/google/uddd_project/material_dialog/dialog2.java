package com.google.uddd_project.material_dialog;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.uddd_project.MainExerciseActivity;

public final class dialog2 implements MaterialDialog.SingleButtonCallback {


    private final MainExerciseActivity mExc;

    public dialog2(MainExerciseActivity mainExerciseActivity) {
        this.mExc = mainExerciseActivity;
    }

    public final void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
        this.mExc.a(materialDialog, dialogAction);
    }
}
