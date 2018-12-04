package com.a4apple.lmsapp.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.apiNetworkResponce.CourseContainResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.fragment.CourseDescriptionFragment;
import com.a4apple.lmsapp.fragment.CourseUnitsFragment;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.PreferenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private String[] PAGE_TITLES;

    private Fragment[] PAGES;

    @BindView(R.id.viewpager_desc)
    ViewPager viewpager_desc;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout_desc)
    TabLayout tab_layout_desc;


    @BindView(R.id.tv_startCourse)
    TextView tv_startCourse;


    @BindView(R.id.tv_unit_duration)
    TextView unitDuration;

    String duration;

    int courseId;
    AlertDialog dialog;
    boolean startCourse = false;

    String completedOrder = "R";

    List<CourseContainResponce.CourseContent> courseContentsData;
    String courseDesc, courseName, courseCatName;


    @BindView(R.id.tv_cdetail_cat_name)
    TextView catNameTv;

    @BindView(R.id.tv_cdetail_course_name)
    TextView courseNameTv;

    PreferenceManager preferenceManager;

    int resumeIndex = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        ButterKnife.bind(this);
        Paper.init(this);
        setSupportActionBar(toolbar);

        preferenceManager = new PreferenceManager(this);

        if (getWiFiInternet()) {
            checkConnection();

        } else {
            checkConnection();

        }


        String str = "s";

        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            courseId = bundle.getInt("courseId", 0);
            courseName = bundle.getString("courseName");
            courseCatName = bundle.getString("courseCatName");
            startCourse = bundle.getBoolean("enrolled", false);

            preferenceManager.setKeyValueInt("resumeCourseId", courseId);

            if (bundle.getBoolean("enrolled", false)) {

                tv_startCourse.setText("Resume Course ");
                str = "r";
            } else {

                tv_startCourse.setText("Start Course ");


            }


            catNameTv.setText(courseCatName);
            courseNameTv.setText(courseName);
            courseNameTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            courseNameTv.setMarqueeRepeatLimit(-1);
            courseNameTv.setSingleLine(true);
            courseNameTv.setSelected(true);

        }

        getCourseDetailsNetworkCall(str, preferenceManager.getKeyValueInt("resumeCourseId"));


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                PAGE_TITLES = new String[]{
                        "UNITS",
                        "COURSE DESCRIPTION"

                };
                PAGES = new Fragment[]{
                        new CourseUnitsFragment(courseContentsData, courseName, courseCatName, courseDesc, courseId, startCourse, completedOrder),
                        new CourseDescriptionFragment(courseDesc)};


                viewpager_desc.setAdapter(new MyPagerAdapter(getFragmentManager()));

                tab_layout_desc.setupWithViewPager(viewpager_desc);

            }
        });


    }


    public boolean getWiFiInternet() {
        boolean val = false;
        if (preferenceManager.getWiFiSession()) {

            final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = null;
            if (connMgr != null) {
                activeNetworkInfo = connMgr.getActiveNetworkInfo();
            }

            if (activeNetworkInfo != null) { // connected to the internet

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                    val = true;

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                    builder.setCancelable(false);
                    builder.setMessage("Please connect to Wi-Fi ");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Go Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(CourseDetailsActivity.this, SettingActivity.class));
                        }
                    });

                    android.app.AlertDialog alert = builder.create();
                    alert.show();

                }
            }


        }
        return val;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        checkConnection();

        getCourseDetailsNetworkCall("str", preferenceManager.getKeyValueInt("resumeCourseId"));


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

    @OnClick(R.id.iv_back_home_desc)
    public void clickback() {

        finish();
    }

    @OnClick(R.id.tv_startCourse)
    public void click() {

        if (startCourse) {
            Intent intent = new Intent(this, CourseStartActivity.class);
            intent.putExtra("courseId", courseId);
            intent.putExtra("courseCatName", courseCatName);
            intent.putExtra("courseName", courseName);
            intent.putExtra("indexCourse", resumeIndex);
            intent.putExtra("completedOrder", completedOrder);

            startActivity(intent);

        } else {

            Toasty.info(this, "Enroll first to start course.", Toast.LENGTH_SHORT).show();

        }

    }

    public void getCourseDetailsNetworkCall(final String str, final int cId) {
        dialog = new SpotsDialog(this, "Loading...", R.style.Custom);

        dialog.show();

        Call<CourseContainResponce> applistResponceCall;
        ApiInterface apiInterface;

        apiInterface = Apiclient.getClient().create(ApiInterface.class);

        applistResponceCall = apiInterface.coursedetails(String.valueOf(cId), preferenceManager.getKeyValueString("userId"));

        applistResponceCall.enqueue(new Callback<CourseContainResponce>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CourseContainResponce> call, Response<CourseContainResponce> response) {

                int totalUnits = 0;
                int remainUnits = 0;

                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        try {

                            completedOrder = response.body().getCourseList().get(0).getCompleteOrder().toString();

                        } catch (Exception e) {
                            e.getMessage();
                        }
                        courseContentsData = response.body().getCourseContent();

                        courseDesc = response.body().getCourseList().get(0).getDescription().toString();

                        Paper.book().write("singleCourse", courseContentsData);

                        for (int position = 0; position < courseContentsData.size(); position++) {


                            if (courseContentsData.get(position).getContentCategory() == 1) {


                            } else {
                                totalUnits++;

                                try {
                                    if (courseContentsData.get(position).getStatus().toString().equalsIgnoreCase("A") || courseContentsData.get(position).getStatus().toString().equalsIgnoreCase("D")) {

                                        remainUnits++;

                                    }

                                } catch (Exception e) {
                                    Log.e("error", e.getMessage());
                                }


                            }
                        }
    /*
                        unit = String.valueOf(courseContentsData.size());
    */
                        duration = response.body().getCourseList().get(0).getTimeLimit().toString();

                        /*  if (str.equalsIgnoreCase("r")){*/


                        unitDuration.setText(remainUnits + " units/ " + totalUnits + " units");

                      /*  }else {
                            unitDuration.setText(totalUnits + " units/ " + duration + " hours");
                        }



    */

                        for (int i = 0; i < courseContentsData.size(); i++) {

                            if (courseContentsData.get(i).getContentCategory() == 1) {

                            } else {

                                try {
                                    if (courseContentsData.get(i).getStatus().toString().equals("A")) {

                                    } else {
                                        resumeIndex = i;


                                        break;
                                    }
                                } catch (Exception e) {
                                    e.getMessage();


                                    resumeIndex = i;
                                    break;

                                }
                            }
                        }


                        try {
                            for (int l = 0; l < courseContentsData.size(); l++) {

                                if (courseContentsData.get(l).getContentCategory() == 1) {

                                } else {

                                    preferenceManager.setKeyValueIntForCourseIndex("courseStartIndex", l);
                                    break;
                                }
                            }

                        } catch (Exception e) {
                            e.getMessage();
                        }
                        /*if (completedOrder.equalsIgnoreCase("N")) {
                            Log.e("resumeIndexAlwa   1  ", "" + resumeIndex);

                            resumeIndex = 1;
                        }*/

                        Log.e("resumeIndex", "" + resumeIndex);

                        preferenceManager.setKeyValueInt("resumeIndex", resumeIndex);

                        dialog.dismiss();


                    } else {
                        dialog.dismiss();
                        Toast.makeText(CourseDetailsActivity.this, "No details available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CourseDetailsActivity.this, "No details available form server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CourseContainResponce> call, Throwable t) {
                dialog.dismiss();
                t.getMessage();
            }
        });


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
    @SuppressLint("SetTextI18n")
    private void showSnack(boolean isConnected) {
        if (isConnected) {


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

        LMSApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

}
