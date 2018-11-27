package com.a4apple.lmsapp.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.activity.CourseStartActivity;
import com.a4apple.lmsapp.adaptor.CourseUnitsAdaptor;
import com.a4apple.lmsapp.apiNetworkResponce.CourseContainResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.PreferenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class CourseUnitsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.courseUnits_recycler)
    ListView recyclerView;

    CourseUnitsAdaptor unitsAdaptor;

    @BindView(R.id.swipe_refresh_layout_unit)
    SwipeRefreshLayout refreshLayout;

    List<CourseContainResponce.CourseContent> courseContentsData;
    String courseDesc, courseName, courseCatName;
    int courseId;
    boolean startCourse;
    String completedOrder;
    PreferenceManager preferenceManager;

    @SuppressLint("ValidFragment")
    public CourseUnitsFragment(List<CourseContainResponce.CourseContent> courseContentsData, String courseName, String courseCatName, String courseDesc, int courseId, boolean startCourse, String completedOrder) {
        this.courseContentsData = courseContentsData;
        this.courseName = courseName;
        this.courseCatName = courseCatName;
        this.courseDesc = courseDesc;
        this.courseId = courseId;
        this.startCourse = startCourse;
        this.completedOrder = completedOrder;

        Log.e("#### courseId" , ""+courseId);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_units, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preferenceManager = new PreferenceManager(getActivity());
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        try {
            if (courseContentsData.size() > 0) {
                unitsAdaptor = new CourseUnitsAdaptor(getActivity(), courseContentsData);
                recyclerView.setAdapter(unitsAdaptor);

            }
        } catch (Exception e) {
            e.getMessage();
        }


        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (!view.getTag().equals("no")) {

                    if (view.getTag().equals("yes")) {

                        Intent intent = new Intent(getActivity(), CourseStartActivity.class);

                        intent.putExtra("courseId", courseId);
                        intent.putExtra("courseCatName", courseCatName);
                        intent.putExtra("courseName", courseName);
                        intent.putExtra("indexCourse", position);
                        intent.putExtra("completedOrder",completedOrder);

                        Log.e("resumeIndexOnClick", "" + preferenceManager.getKeyValueInt("resumeIndex"));

                        getActivity().startActivity(intent);




                    } else {

                        if (startCourse) {

                            if (completedOrder.equalsIgnoreCase("N")) {
                                if (position == preferenceManager.getKeyValueInt("nextPos") || position == preferenceManager.getKeyValueInt("resumeIndex")) {
                                    Intent intent = new Intent(getActivity(), CourseStartActivity.class);
                                    intent.putExtra("courseId", courseId);
                                    intent.putExtra("courseCatName", courseCatName);
                                    intent.putExtra("courseName", courseName);
                                    intent.putExtra("indexCourse", position);
                                    intent.putExtra("completedOrder",completedOrder);

                                    Log.e("resumeIndexOnClick", "" + preferenceManager.getKeyValueInt("resumeIndex"));

                                    getActivity().startActivity(intent);
                                } else {

                                    Toasty.info(getActivity(), "This course must be done sequentially", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Intent intent = new Intent(getActivity(), CourseStartActivity.class);
                                intent.putExtra("courseId", courseId);
                                intent.putExtra("courseCatName", courseCatName);
                                intent.putExtra("courseName", courseName);
                                intent.putExtra("indexCourse", position);
                                intent.putExtra("completedOrder",completedOrder);

                                getActivity().startActivity(intent);


                            }


                        } else {
                            Toasty.info(getActivity(), "Enroll first to start course.", Toast.LENGTH_SHORT).show();

                        }


                    }

                }

            }
        });

    }

    @Override
    public void onRefresh() {

        refreshLayout.setRefreshing(true);
        new GetAllCourseContain().execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);

            }
        }, 3000);


    }

    @Override
    public void onResume() {
        super.onResume();

        new GetAllCourseContain().execute();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        new GetAllCourseContain().execute();

        Log.e("^^^^", "hintUpdate");

    }

    @SuppressLint("StaticFieldLeak")
    public class GetAllCourseContain extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                Call<CourseContainResponce> applistResponceCall;
                ApiInterface apiInterface;

                apiInterface = Apiclient.getClient().create(ApiInterface.class);

                applistResponceCall = apiInterface.coursedetails(String.valueOf(courseId), preferenceManager.getKeyValueString("userId"));

                applistResponceCall.enqueue(new Callback<CourseContainResponce>() {
                    @Override
                    public void onResponse(Call<CourseContainResponce> call, Response<CourseContainResponce> response) {


                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("1")) {

                            courseContentsData = response.body().getCourseContent();

                            courseDesc = response.body().getCourseList().get(0).getDescription().toString();

                            try {
                                completedOrder = response.body().getCourseList().get(0).getCompleteOrder().toString();
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            Paper.book().write("singleCourse", courseContentsData);

                            CourseUnitsAdaptor unitsAdaptor2 = new CourseUnitsAdaptor(getActivity(), courseContentsData);
                            recyclerView.setAdapter(unitsAdaptor2);
                            unitsAdaptor2.notifyDataSetChanged();

                            Log.e("refreshUnitData", "dataAdaptor");

                        }
                    }

                    @Override
                    public void onFailure(Call<CourseContainResponce> call, Throwable t) {
                        t.getMessage();
                    }
                });


            } catch (Exception e) {

                e.getMessage();
            }

            return null;
        }
    }
}