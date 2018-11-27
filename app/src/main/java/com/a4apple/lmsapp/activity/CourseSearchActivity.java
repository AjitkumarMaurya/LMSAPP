package com.a4apple.lmsapp.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.adaptor.AllCourseAdaptor;
import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.fragment.UserProfileFragment;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.CustomItemClickListener;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class CourseSearchActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {


    @BindView(R.id.toolbar_search)
    Toolbar toolbar;

    @BindView(R.id.rely)
    RelativeLayout relativeLayout;

    @BindView(R.id.search_recyclerview)
    RecyclerView recySearchView;

    @BindView(R.id.etsearch)
    MaterialEditText searchText;

    @BindView(R.id.userProfilesearch)
    CircularImageView profilepic;

    AllCourseAdaptor allCourseAdaptor;
    List<AllMyCourseResponce.MyCourseList> courses;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Paper.init(this);
        checkConnection();


    }

    @OnClick(R.id.userProfilesearch)
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

    @OnClick(R.id.iv_back_home)
    public void clickBack(){
        finish();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (isConnected) {
            preferenceManager = new PreferenceManager(this);


            courses = Paper.book().read("allcourse");

            if (courses.size()<1){

                recySearchView.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);


            }
            else {


                recySearchView.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);

                allCourseAdaptor = new AllCourseAdaptor(this, courses, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(int position) {


                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recySearchView.setLayoutManager(mLayoutManager);
                recySearchView.setItemAnimator(new DefaultItemAnimator());
                recySearchView.setAdapter(allCourseAdaptor);

                try {
                    Glide.with(this).load(preferenceManager.getKeyValueString("profilePic")).apply(new RequestOptions().placeholder(R.drawable.ic_account).diskCacheStrategy(DiskCacheStrategy.ALL)).into(profilepic);
                } catch (Exception e) {

                    e.getMessage();
                }

            }

            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    allCourseAdaptor.filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



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


}
