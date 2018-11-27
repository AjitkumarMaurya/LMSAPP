package com.a4apple.lmsapp.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.fragment.AllCourseFragment;
import com.a4apple.lmsapp.fragment.MyCourseFragment;
import com.a4apple.lmsapp.fragment.UserProfileFragment;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private String[] PAGE_TITLES;
    private Fragment[] PAGES;

    AlertDialog dialog;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    @BindView(R.id.userProfile)
    CircularImageView profilepic;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;


    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Paper.init(this);
        preferenceManager = new PreferenceManager(this);

        checkConnection();

    }


    @OnClick(R.id.userProfile)
    public void clickprofile() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = new UserProfileFragment();
        dialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.iv_search)
    public void clickSearch() {

        startActivity(new Intent(this, CourseSearchActivity.class));


    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setCancelable(true);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }

    private void checkConnection() {

        Bundle extra = getIntent().getExtras();

        String str = extra.getString("popup", "no");

        if (str.equalsIgnoreCase("yes")) {


            final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = null;
            if (connMgr != null) {
                activeNetworkInfo = connMgr.getActiveNetworkInfo();
            }

            if (activeNetworkInfo != null) { // connected to the internet

                if (preferenceManager.getWiFiSession()) {
                    if (activeNetworkInfo.getType() != ConnectivityManager.TYPE_WIFI) {

                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                        builder.setCancelable(false);
                        builder.setMessage("Set WiFi Require to Start this Course.");
                        builder.setPositiveButton("Go Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(UserHomeActivity.this, SettingActivity.class));
                            }
                        });
                        builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        android.app.AlertDialog alert = builder.create();
                        alert.show();

                    }
                }
            }


        }

        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (isConnected) {

            try {
                Glide.with(this).load(preferenceManager.getKeyValueString("profilePic")).apply(new RequestOptions().placeholder(R.drawable.ic_account).diskCacheStrategy(DiskCacheStrategy.ALL)).into(profilepic);
            } catch (Exception e) {
                e.getMessage();
            }


            setPager();


        } else {

            showDialogInternet();


        }

    }

    private void showDialogInternet() {
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

        if (!alert.isShowing()) {
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


    public void setPager() {

        PAGE_TITLES = new String[]{
                "ALL",
                "MY COURSE"

        };
        PAGES = new Fragment[]{
                new AllCourseFragment(),
                new MyCourseFragment()
        };


        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));

        tabLayout.setupWithViewPager(mViewPager);

    }
}