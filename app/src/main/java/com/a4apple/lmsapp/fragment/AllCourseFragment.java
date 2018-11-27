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
import com.a4apple.lmsapp.adaptor.AllCourseAdaptor;
import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.CustomItemClickListener;
import com.a4apple.lmsapp.utility.PreferenceManager;

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
public class AllCourseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.all_course_recycler)
    RecyclerView allCourserecyclerView;
    AllCourseAdaptor allCourseAdaptor;
    List<AllMyCourseResponce.MyCourseList> courses;

    @BindView(R.id.swipe_container_all_course)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.iv_allCourse_noData)
    ImageView imageView;

    com.a4apple.lmsapp.utility.PreferenceManager preferenceManager;

    int flag = 0;


    @SuppressLint("ValidFragment")
    public AllCourseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_course, container, false);
        ButterKnife.bind(this, rootView);
        Paper.init(getActivity());
        preferenceManager = new PreferenceManager(getActivity());
        return rootView;
    }


    @OnClick(R.id.iv_allCourse_noData)
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        courses = Paper.book().read("allcourse");


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


        try {
            if (courses.size() > 0) {

                flag = 1;

                allCourserecyclerView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                allCourseAdaptor = new AllCourseAdaptor(getActivity(), courses, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        new GetAllCourse().execute();


                    }
                });


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                allCourserecyclerView.setLayoutManager(mLayoutManager);
                allCourserecyclerView.setHasFixedSize(true);
                allCourserecyclerView.setItemAnimator(new DefaultItemAnimator());
                allCourserecyclerView.setAdapter(allCourseAdaptor);

            }

        } catch (Exception e) {
            allCourserecyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);


            e.getMessage();
        }


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


                applistResponceCall = apiInterface.allCourseList(preferenceManager.getKeyValueString("userId"), "");

                applistResponceCall.enqueue(new Callback<AllMyCourseResponce>() {
                    @Override
                    public void onResponse(Call<AllMyCourseResponce> call, Response<AllMyCourseResponce> response) {


                        try {
                            Paper.book().write("allcourse", response.body().getCourseList());
                            Log.e("allCourse", "Updated");

                            preferenceManager.setKeyValueString("baseURL",""+response.body().getBaseUrl());

                            if (response.body().getCourseList().size() > 0) {


                                allCourserecyclerView.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);

                                if (flag == 1) {

                                    allCourseAdaptor.updateData(response.body().getCourseList());
                                    Log.e("allCourse", "Updated method");


                                } else {

                                    allCourseAdaptor = new AllCourseAdaptor(getActivity(), response.body().getCourseList(), new CustomItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {

                                            new GetAllCourse().execute();


                                        }
                                    });


                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    allCourserecyclerView.setLayoutManager(mLayoutManager);
                                    allCourserecyclerView.setHasFixedSize(true);
                                    allCourserecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    allCourserecyclerView.setAdapter(allCourseAdaptor);

                                    flag=1;

                                }
                                Log.e("allCourse", "Updated adaptor");

                            } else {
                                allCourserecyclerView.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);

                            }

                        } catch (Exception e) {
                            e.getMessage();
                            allCourserecyclerView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);

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
}
