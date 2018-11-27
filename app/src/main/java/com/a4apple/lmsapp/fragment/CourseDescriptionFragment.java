package com.a4apple.lmsapp.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a4apple.lmsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class CourseDescriptionFragment extends Fragment {


    @BindView(R.id.tv_course_description)
    TextView courseDesc;

    String desc;

    @SuppressLint("ValidFragment")
    public CourseDescriptionFragment(String desc) {

        this.desc=desc;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_description, container, false);

        ButterKnife.bind(this,view);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            courseDesc.setText(Html.fromHtml(""+desc,Html.FROM_HTML_MODE_LEGACY));
        } else {
            courseDesc.setText(Html.fromHtml(""+desc));
        }

        return view;
    }

}
