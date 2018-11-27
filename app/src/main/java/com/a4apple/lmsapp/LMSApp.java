package com.a4apple.lmsapp;

import android.app.Application;
import android.content.Context;

import com.a4apple.lmsapp.utility.ConnectivityReceiver;

import io.paperdb.Paper;

public class LMSApp extends Application {

    private static LMSApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        Paper.init(this);

    }

    public static synchronized LMSApp getInstance() {
        return mInstance;
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
