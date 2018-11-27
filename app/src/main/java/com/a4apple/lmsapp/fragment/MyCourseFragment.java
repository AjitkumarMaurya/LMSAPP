package com.a4apple.lmsapp.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.adaptor.MyCourseAdaptor;
import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class MyCourseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.my_course_recycler)
    RecyclerView myCourserecyclerView;
    MyCourseAdaptor myCourseAdaptor;

    List<AllMyCourseResponce.MyCourseList> courses;

    int flag = 0;

    @BindView(R.id.iv_myCourse_noData)
    ImageView imageView;

    @BindView(R.id.swipe_container_my_course)
    SwipeRefreshLayout refreshLayout;

    com.a4apple.lmsapp.utility.PreferenceManager preferenceManager;

    @SuppressLint("ValidFragment")
    public MyCourseFragment() {
        //   this.courses = courses;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        new GetAllCourse().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_course, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Paper.init(getActivity());
        preferenceManager = new com.a4apple.lmsapp.utility.PreferenceManager(getActivity());
        courses = Paper.book().read("mycourse");


        try {
            if (courses.size() > 0) {

                flag = 1;

                myCourserecyclerView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                myCourserecyclerView.setLayoutManager(mLayoutManager);
                myCourserecyclerView.setHasFixedSize(true);
                myCourserecyclerView.setItemAnimator(new DefaultItemAnimator());

                myCourseAdaptor = new MyCourseAdaptor(getActivity(), courses);


                myCourserecyclerView.setAdapter(myCourseAdaptor);


            }



        } catch (Exception e) {
            e.getMessage();


        }


        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        refreshLayout.post(new Runnable() {

            @Override
            public void run() {

                refreshLayout.setRefreshing(true);
                new GetAllCourse().execute();


            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                refreshLayout.setRefreshing(false);

            }
        }, 3000);


    }

    @OnClick(R.id.iv_myCourse_noData)
    public void clickimg() {

        refreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);

            }
        }, 3000);

        new GetAllCourse().execute();


    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);

            }
        }, 3000);

        new GetAllCourse().execute();

    }

    @SuppressLint("StaticFieldLeak")
    public class GetAllCourse extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {

                Call<AllMyCourseResponce> applistResponceCall;
                ApiInterface apiInterface;


                apiInterface = Apiclient.getClient().create(ApiInterface.class);


                applistResponceCall = apiInterface.allMyCourseList(preferenceManager.getKeyValueString("userId"), "");

                applistResponceCall.enqueue(new Callback<AllMyCourseResponce>() {
                    @Override
                    public void onResponse(Call<AllMyCourseResponce> call, Response<AllMyCourseResponce> response) {


                        try {
                            Paper.book().write("mycourse", response.body().getCourseList());
                            Log.e("myCourse", "Updated");

                            preferenceManager.setKeyValueString("baseURL",""+response.body().getBaseUrl());

                            if (response.body().getCourseList().size() > 0) {

                                myCourserecyclerView.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);

                                if (flag == 1) {
                                    myCourseAdaptor.updateData(response.body().getCourseList());
                                    Log.e("myCourse", "Updated method");

                                } else {

                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    myCourserecyclerView.setLayoutManager(mLayoutManager);
                                    myCourserecyclerView.setHasFixedSize(true);
                                    myCourserecyclerView.setItemAnimator(new DefaultItemAnimator());

                                    myCourseAdaptor = new MyCourseAdaptor(getActivity(), response.body().getCourseList());

                                    myCourserecyclerView.setAdapter(myCourseAdaptor);

                                    flag=1;


                                }


                            } else {
                                myCourserecyclerView.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }

                            Log.e("myCourse", "Updated adaptor");


                        } catch (Exception e) {

                            myCourserecyclerView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);

                            e.getMessage();
                        }


                    }

                    @Override
                    public void onFailure(Call<AllMyCourseResponce> call, Throwable t) {

                        t.getMessage();
                    }
                });


            } catch (Exception e) {

                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String temp) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new GetAllCourse().execute();

    }
}
