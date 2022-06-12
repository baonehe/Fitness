package com.google.uddd_project.material_dialog;

import android.speech.tts.TextToSpeech.OnInitListener;

import com.google.uddd_project.MyApplication;


public final class dialog4 implements OnInitListener {


    private final  MyApplication mApp;

    public dialog4(MyApplication absWomenApplication) {
        this.mApp = absWomenApplication;
    }

    public final void onInit(int i) {
        this.mApp.a(i);
    }
}
