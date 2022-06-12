package com.google.uddd_project.material_dialog;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.uddd_project.WorkoutsFragment;

public final  class dialog6 implements MaterialDialog.SingleButtonCallback {


    private final WorkoutsFragment mWF;
    private final  int a;

    public /* synthetic */ dialog6(WorkoutsFragment mainActivity, int i) {
        this.mWF= mainActivity;
        this.a = i;
    }

    public final void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
        this.mWF.dialog(this.a, materialDialog, dialogAction);
    }
}
