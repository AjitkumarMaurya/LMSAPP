package com.a4apple.lmsapp.activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.apiNetworkResponce.CourseContainResponce;
import com.a4apple.lmsapp.fragment.StartCourseFragment;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.PreferenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class CourseStartActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    FragmentTransaction transaction;


    @BindView(R.id.btn_next_start_course)
    Button btnNext;

    @BindView(R.id.rely_main2)
    RelativeLayout relymain;


    @BindView(R.id.tv_next_step_course)
    TextView tvnext;

    @BindView(R.id.next_step_course)
    ImageView ivnext;

    List<CourseContainResponce.CourseContent> courseContentsData;
    int courseId,indexCourse;
    String courseName, courseCatName;

    @BindView(R.id.tv_cstart_course_name)
    TextView courseNameTv;

    @BindView(R.id.tv_cstart_cat_name)
    TextView courseCatNameTv;

    @BindView(R.id.tv_prev_step_course)
    TextView prev;

    @BindView(R.id.prev_step_course)
    ImageView previv;

    String completedOrder;

    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_start);

        ButterKnife.bind(this);
        Paper.init(this);
        setSupportActionBar(toolbar);

        preferenceManager = new PreferenceManager(this);

        if (getWiFiInternet()){
            checkConnection();

        }else {
            checkConnection();

        }


    }

    @OnClick(R.id.iv_back_home_cstart)
    public void clickback() {
        CourseStartActivity.this.finish();
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        builder.setCancelable(true);
        builder.setMessage("Do you want Back to Home Screen?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(CourseStartActivity.this,UserHomeActivity.class);
                intent.putExtra("popup","no");
                startActivity(intent);

                finish();
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
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (isConnected) {
            courseContentsData = Paper.book().read("singleCourse");


            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {

                courseId = bundle.getInt("courseId", 0);
                indexCourse = bundle.getInt("indexCourse",0);
                courseName = bundle.getString("courseName");
                courseCatName = bundle.getString("courseCatName");
                completedOrder = bundle.getString("completedOrder");



                courseCatNameTv.setText(courseName);

                courseCatNameTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                courseCatNameTv.setMarqueeRepeatLimit(-1);
                courseCatNameTv.setSingleLine(true);
                courseCatNameTv.setSelected(true);

                courseNameTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                courseNameTv.setMarqueeRepeatLimit(-1);
                courseNameTv.setSingleLine(true);
                courseNameTv.setSelected(true);


                if (indexCourse!=0) {
                    StartCourseFragment myf = new StartCourseFragment(this,relymain, ivnext, tvnext, previv, prev, courseContentsData, btnNext, indexCourse,courseNameTv,courseId,completedOrder);
                    transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.course_container, myf);
                    transaction.commit();
                }else {
                    StartCourseFragment myf = new StartCourseFragment(this,relymain, ivnext, tvnext, previv, prev, courseContentsData, btnNext, 0,courseNameTv,courseId,completedOrder);
                    transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.course_container, myf);
                    transaction.commit();
                }

            }



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

    public boolean getWiFiInternet()
    {
        boolean val =false;
       if(preferenceManager.getWiFiSession()){

           final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

           NetworkInfo activeNetworkInfo = null;
           if (connMgr != null) {
               activeNetworkInfo = connMgr.getActiveNetworkInfo();
           }

           if (activeNetworkInfo != null) { // connected to the internet

               if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                   val=true;

               } else {
                   android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
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

                           startActivity(new Intent(CourseStartActivity.this, SettingActivity.class));
                       }
                   });

                   android.app.AlertDialog alert = builder.create();
                   alert.show();

               }
           }


       }
        return val;
    }


}
