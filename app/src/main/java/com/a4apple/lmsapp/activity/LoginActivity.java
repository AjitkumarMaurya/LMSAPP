package com.a4apple.lmsapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.apiNetworkResponce.LoginuserResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements Validator.ValidationListener, ConnectivityReceiver.ConnectivityReceiverListener {

    @NotEmpty
    @BindView(R.id.etLoginUserEmail)
    MaterialEditText email;

    @NotEmpty
    @BindView(R.id.etLoginUserPassword)
    MaterialEditText pass;

    PreferenceManager preferenceManager;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        ButterKnife.bind(this);

        checkConnection();

        validator = new Validator(this);
        validator.setValidationListener(this);

        preferenceManager = new PreferenceManager(this);

    }

    @OnClick(R.id.btn_login)
    public void clicklogin() {

        validator.validate();


    }


    @Override
    public void onValidationSucceeded() {

        callNetwork();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof MaterialEditText) {
                ((MaterialEditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }


    }

    public void callNetwork() {


        final AlertDialog dialog = new SpotsDialog(this, "Loading...", R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();

        Call<LoginuserResponce> applistResponceCall;
        ApiInterface apiInterface;


        apiInterface = Apiclient.getClient().create(ApiInterface.class);


        applistResponceCall = apiInterface.userLogin(email.getText().toString(), pass.getText().toString());

        applistResponceCall.enqueue(new Callback<LoginuserResponce>() {
            @Override
            public void onResponse(Call<LoginuserResponce> call, Response<LoginuserResponce> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().getStatus().toString().equals("1")) {

                            try {


                                preferenceManager.setLoginSession();
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


                                dialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                                intent.putExtra("popup","yes");
                                startActivity(intent);


                            } catch (Exception e) {
                                dialog.dismiss();
                                e.getMessage();
                            }
                        } else {

                            dialog.dismiss();
                            Toasty.error(LoginActivity.this, "User login failed", Toast.LENGTH_LONG).show();
                        }
                    }else {

                        Toasty.error(LoginActivity.this, "User login failed", Toast.LENGTH_LONG).show();

                    }

                }catch (Exception e){

                    Toasty.error(LoginActivity.this, "User login failed   "+e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginuserResponce> call, Throwable t) {

                dialog.dismiss();
                t.getMessage();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus){
            boolean isConnected = ConnectivityReceiver.isConnected();
            showSnack(isConnected);
        }
        super.onWindowFocusChanged(hasFocus);


    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

}
