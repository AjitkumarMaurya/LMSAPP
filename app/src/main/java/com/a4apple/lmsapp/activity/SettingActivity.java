package com.a4apple.lmsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.PreferenceManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SettingActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @BindView(R.id.switch1)
    Switch aSwitch;

    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        checkConnection();

        preferenceManager = new PreferenceManager(this);

        if (preferenceManager.getWiFiSession()){

            aSwitch.setChecked(true);
        }
        else {
            aSwitch.setChecked(false);
        }

    }

    @OnClick(R.id.iv_back_home_setting)
    public void clickback() {
        finish();
    }

    @OnCheckedChanged(R.id.switch1)
    public void onCheckedChanged(Switch buttonView, boolean isChecked) {
        if (isChecked) {

            preferenceManager.setWiFiSession(true);
            Toasty.success(this, "On", Toast.LENGTH_SHORT, true).show();



        } else {

            preferenceManager.setWiFiSession(false);
            Toasty.warning(this, "Off", Toast.LENGTH_SHORT, true).show();

        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus){
            boolean isConnected = ConnectivityReceiver.isConnected();
            showSnack(isConnected);
        }
        super.onWindowFocusChanged(hasFocus);


    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (isConnected) {

        } else {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
            builder.setCancelable(false);
            builder.setMessage("Internet Connection Required");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        LMSApp.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
