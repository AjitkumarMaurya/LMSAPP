package com.a4apple.lmsapp.fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.activity.SettingActivity;
import com.a4apple.lmsapp.activity.SplashActivity;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class UserProfileFragment extends DialogFragment {


    @BindView(R.id.userpic_dilog)
    CircularImageView profilepic;

    @BindView(R.id.tvname_dilog)
    TextView name;

    @BindView(R.id.tvemail_dilog)
    TextView email;

    @BindView(R.id.tvldate_dilog)
    TextView ldate;

    @BindView(R.id.tvdesc_dilog)
    TextView bio;

    @BindView(R.id.tv_compcirti_course)
    TextView cirtificate;

    @BindView(R.id.tv_incomp_course)
    TextView incompCourse;

    @BindView(R.id.tv_comp_course)
    TextView compCourse;


    PreferenceManager preferenceManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        preferenceManager = new PreferenceManager(getActivity());
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Paper.init(getActivity());


        name.setText(preferenceManager.getKeyValueString("firstName") + preferenceManager.getKeyValueString("lastName"));
        email.setText(preferenceManager.getKeyValueString("email"));
        ldate.setText("Last login: "+preferenceManager.getKeyValueString("lastLogin"));
        bio.setText(preferenceManager.getKeyValueString("bio"));


        try {
            Glide.with(this).load(preferenceManager.getKeyValueString("profilePic")).apply(new RequestOptions().placeholder(R.drawable.ic_account).diskCacheStrategy(DiskCacheStrategy.ALL)).into(profilepic);


                cirtificate.setText(String.valueOf(preferenceManager.getKeyValueInt("ceri")));

                compCourse.setText(String.valueOf(preferenceManager.getKeyValueInt("compCourse")));

                incompCourse.setText(String.valueOf(preferenceManager.getKeyValueInt("incompCourse")));

        } catch (Exception e) {

            e.getMessage();


        }
    }

    @OnClick(R.id.btn_setting_dialog)
    public void clicksetting() {

        getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @OnClick(R.id.btn_logout_dialog)
    public void clicklogout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogStyle);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

                preferenceManager.clearPreferences();

                Paper.book().destroy();

                getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
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

    @OnClick(R.id.close_dialog)
    public void clickclose() {
        UserProfileFragment.this.dismiss();
    }
}
