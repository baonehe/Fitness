package com.google.uddd_project.material_dialog;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public final class dialog1 implements MaterialDialog.SingleButtonCallback {


    public static final  dialog1 dl = new dialog1();

    private dialog1() {
    }

    public final void onClick(MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
        materialDialog.dismiss();
    }
}
