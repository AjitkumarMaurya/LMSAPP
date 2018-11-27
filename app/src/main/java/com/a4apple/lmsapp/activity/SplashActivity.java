package com.a4apple.lmsapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.apiNetworkResponce.LoginuserResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.PreferenceManager;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity implements ConnectivityReceiver.ConnectivityReceiverListener {


    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_it_works);

        ButterKnife.bind(this);


        checkConnection();



    }


    String Decrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] results = cipher.doFinal(android.util.Base64.decode(text, android.util.Base64.DEFAULT));
        return new String(results, "UTF-8");
    }

    String Encrypt(String text, String key)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        String base64 = android.util.Base64.encodeToString(results, android.util.Base64.DEFAULT);
        return base64;
    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (isConnected) {
            preferenceManager = new PreferenceManager(this);

            if (preferenceManager.getLoginSession()) {

                new userDataUpdate(preferenceManager.getKeyValueString("userId")).execute();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SplashActivity.this, UserHomeActivity.class);
                        intent.putExtra("popup","yes");
                        startActivity(intent);

                    }
                }, 2000);


            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }


        } else {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
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
        finishAffinity();
    }


    @SuppressLint("StaticFieldLeak")
    public class userDataUpdate extends AsyncTask<Void, Void, Void> {

        String userId;

         userDataUpdate(String userId) {
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(Void... voids) {


            Call<LoginuserResponce> applistResponceCall;
            ApiInterface apiInterface;


            apiInterface = Apiclient.getClient().create(ApiInterface.class);


            applistResponceCall = apiInterface.userDetailUpdate(userId);

            applistResponceCall.enqueue(new Callback<LoginuserResponce>() {
                @Override
                public void onResponse(Call<LoginuserResponce> call, Response<LoginuserResponce> response) {

                    if (response.body() != null && response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        try {
                            preferenceManager.setKeyValueString("userId", String.valueOf(response.body().getEmployeeList().get(0).getUserId()));
                            preferenceManager.setKeyValueString("firstName", String.valueOf(response.body().getEmployeeList().get(0).getFirstName()));
                            preferenceManager.setKeyValueString("lastName", String.valueOf(response.body().getEmployeeList().get(0).getLastName()));
                            preferenceManager.setKeyValueString("email", String.valueOf(response.body().getEmployeeList().get(0).getEmail()));
                            preferenceManager.setKeyValueString("userName", String.valueOf(response.body().getEmployeeList().get(0).getUsername()));
                            preferenceManager.setKeyValueString("password", String.valueOf(response.body().getEmployeeList().get(0).getPassword()));
                            preferenceManager.setKeyValueString("profilePic", String.valueOf(response.body().getBaseUrl().toString() + response.body().getEmployeeList().get(0).getProfilePic()));
                            preferenceManager.setKeyValueString("baseURL", String.valueOf(response.body().getBaseUrl().toString()));
                            preferenceManager.setKeyValueString("bio", String.valueOf(response.body().getEmployeeList().get(0).getBio()));
                            preferenceManager.setKeyValueString("uType", String.valueOf(response.body().getEmployeeList().get(0).getuTypeId()));
                            preferenceManager.setKeyValueString("timeZone", String.valueOf(response.body().getEmployeeList().get(0).getTimeZone()));
                            preferenceManager.setKeyValueString("cDate", String.valueOf(response.body().getEmployeeList().get(0).getcDate()));
                            preferenceManager.setKeyValueString("uDate", String.valueOf(response.body().getEmployeeList().get(0).getuDate()));
                            preferenceManager.setKeyValueString("flag", String.valueOf(response.body().getEmployeeList().get(0).getFlag()));
                            preferenceManager.setKeyValueString("cUser", String.valueOf(response.body().getEmployeeList().get(0).getcUser()));
                            preferenceManager.setKeyValueString("uUser", String.valueOf(response.body().getEmployeeList().get(0).getuUser()));
                            preferenceManager.setKeyValueString("credit", String.valueOf(response.body().getEmployeeList().get(0).getCredit()));
                            preferenceManager.setKeyValueInt("incompCourse", (int) Math.round((Double) response.body().getEmployeeList().get(0).getIncompeleteCourse()));
                            preferenceManager.setKeyValueInt("ceri", (int) Math.round((Double) response.body().getEmployeeList().get(0).getCertficate()));
                            preferenceManager.setKeyValueInt("compCourse", (int) Math.round((Double) response.body().getEmployeeList().get(0).getCompleteCourse()));
                            preferenceManager.setKeyValueString("lastLogin", String.valueOf(response.body().getEmployeeList().get(0).getLastdate() + " " + response.body().getEmployeeList().get(0).getLasttime()));

                            Log.e("userUpdate", "Done.....");

                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginuserResponce> call, Throwable t) {

                    t.getMessage();
                }
            });
            return null;
        }
    }


}
