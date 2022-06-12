package com.google.uddd_project.material_dialog;


import com.google.uddd_project.MyApplication;

public final class dialog5 implements Runnable {


    private final MyApplication mApp;

    public dialog5(MyApplication absWomenApplication) {
        this.mApp = absWomenApplication;
    }

    public final void run() {
        this.mApp.a();
    }
}
