package com.a4apple.lmsapp.adaptor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.activity.CourseDetailsActivity;
import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.apiNetworkResponce.CourseEnrollResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.CustomItemClickListener;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCourseAdaptor extends RecyclerView.Adapter<AllCourseAdaptor.MyViewHolder> {


    private PreferenceManager preferenceManager;
    private List<AllMyCourseResponce.MyCourseList> listCourse;
    private List<AllMyCourseResponce.MyCourseList> copylistCourse;

    private Context context;
    private String baseurl;

    private CustomItemClickListener listener;


    public AllCourseAdaptor(Context context, List<AllMyCourseResponce.MyCourseList> listCourse, CustomItemClickListener listener) {

        this.context = context;
        this.listCourse = listCourse;
        copylistCourse = new ArrayList<>();
        copylistCourse.addAll(listCourse);

        this.listener = listener;

        preferenceManager = new PreferenceManager(context);

        baseurl = preferenceManager.getKeyValueString("baseURL");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_all_course, parent, false);

        return new MyViewHolder(itemView);

    }


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        final int pos = position;

        holder.catname.setText(listCourse.get(position).getCatName().toString());
        holder.cname.setText(listCourse.get(position).getCourseName().toString());

        try {

            String url = baseurl + listCourse.get(position).getImage().toString();

            Glide.with(context).load(url).apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.courseimg);

        } catch (Exception e) {

            e.getMessage();
        }

        holder.courseId.setText(String.valueOf(listCourse.get(position).getCourseId()));

        if (listCourse.get(position).getStatus().toString().equalsIgnoreCase("Unenroll")) {


            holder.enroll.setText("Enrolled");
            holder.enroll.setBackgroundResource(R.drawable.enrolled_button_round_coner);
            holder.enroll.setTag("enrolled");


        } else if (listCourse.get(position).getStatus().toString().equalsIgnoreCase("Enroll")) {
            if (listCourse.get(position).getPrice().equals("null")) {

                holder.enroll.setText("Enroll");
                holder.enroll.setBackgroundResource(R.drawable.enroll_button_round_coner);

            } else if (listCourse.get(position).getPrice().toString().equalsIgnoreCase("0.0")) {
                holder.enroll.setText("Enroll");
                holder.enroll.setBackgroundResource(R.drawable.enroll_button_round_coner);

            } else {

                try {
                    holder.enroll.setText("Enroll" + "(" + (int) Math.round((Double) listCourse.get(position).getPrice()) + ")");
                    holder.enroll.setBackgroundResource(R.drawable.enroll_button_round_coner);
                } catch (Exception e) {
                    e.getMessage();
                }
            }


        }


        holder.enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.enroll.getText().toString().equalsIgnoreCase("Enrolled")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
                    builder.setCancelable(true);
                    builder.setMessage("The course is allready enrolled");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
                    builder.setCancelable(true);
                    builder.setMessage("Do you want to enroll this course ?");
                    builder.setPositiveButton("Enroll", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            new EnrollCourse(listCourse.get(pos).getCourseId()).execute();

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });

        holder.courseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);

                if (holder.enroll.getText().toString().equalsIgnoreCase("Enrolled")) {
                    intent.putExtra("enrolled", true);
                    intent.putExtra("courseId", listCourse.get(pos).getCourseId());
                    intent.putExtra("courseName", listCourse.get(pos).getCourseName().toString());
                    intent.putExtra("courseCatName", listCourse.get(pos).getCatName().toString());


                } else {
                    intent.putExtra("enrolled", false);
                    intent.putExtra("courseId", listCourse.get(pos).getCourseId());
                    intent.putExtra("courseName", listCourse.get(pos).getCourseName().toString());
                    intent.putExtra("courseCatName", listCourse.get(pos).getCatName().toString());
                }

                context.startActivity(intent);
            }
        });
        holder.cname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);

                if (holder.enroll.getText().toString().equalsIgnoreCase("Enrolled")) {
                    intent.putExtra("enrolled", true);
                    intent.putExtra("courseId", listCourse.get(pos).getCourseId());
                    intent.putExtra("courseName", listCourse.get(pos).getCourseName().toString());
                    intent.putExtra("courseCatName", listCourse.get(pos).getCatName().toString());

                } else {
                    intent.putExtra("enrolled", false);
                    intent.putExtra("courseId", listCourse.get(pos).getCourseId());
                    intent.putExtra("courseName", listCourse.get(pos).getCourseName().toString());
                    intent.putExtra("courseCatName", listCourse.get(pos).getCatName().toString());
                }

                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listCourse.size();
    }



    public void updateData(List<AllMyCourseResponce.MyCourseList> ulistCourse) {

        listCourse.clear();
        listCourse = ulistCourse;
        notifyDataSetChanged();

    }

    public void filter(CharSequence sequence) {
        List<AllMyCourseResponce.MyCourseList> temp = new ArrayList<>();
        if (!TextUtils.isEmpty(sequence)) {
            for (int i = 0; i < listCourse.size(); i++) {
                if (listCourse.get(i).getCourseName().toString().contains(sequence) || listCourse.get(i).getCourseName().toString().toLowerCase().contains(sequence) || listCourse.get(i).getCourseName().toString().toUpperCase().contains(sequence) || listCourse.get(i).getCourseName().toString().toUpperCase().contains(sequence.toString().toUpperCase()) || listCourse.get(i).getCourseName().toString().toLowerCase().contains(sequence.toString().toLowerCase())) {
                    temp.add(listCourse.get(i));
                } else if (listCourse.get(i).getCatName().toString().contains(sequence) || listCourse.get(i).getCatName().toString().toLowerCase().contains(sequence) || listCourse.get(i).getCatName().toString().toUpperCase().contains(sequence) || listCourse.get(i).getCatName().toString().toUpperCase().contains(sequence.toString().toUpperCase()) || listCourse.get(i).getCatName().toString().toLowerCase().contains(sequence.toString().toLowerCase())) {
                    temp.add(listCourse.get(i));
                }
            }
        } else {
            temp.addAll(copylistCourse);
        }
        listCourse.clear();
        listCourse.addAll(temp);
        notifyDataSetChanged();
        temp.clear();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.course_imageView)
        ImageView courseimg;

        @BindView(R.id.textViewCourseName)
        TextView cname;

        @BindView(R.id.tv_Allcategory_name)
        TextView catname;

        @BindView(R.id.enroll)
        TextView enroll;

        @BindView(R.id.tv_courseId_all_course)
        TextView courseId;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }


    @SuppressLint("StaticFieldLeak")
    public class EnrollCourse extends AsyncTask<String, Void, String> {

        int courseId;

        EnrollCourse(int courseId) {

            this.courseId = courseId;

        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                Call<CourseEnrollResponce> applistResponceCall;
                ApiInterface apiInterface;


                apiInterface = Apiclient.getClient().create(ApiInterface.class);


                applistResponceCall = apiInterface.enrollcourse(preferenceManager.getKeyValueString("userId"), String.valueOf(courseId));

                applistResponceCall.enqueue(new Callback<CourseEnrollResponce>() {
                    @Override
                    public void onResponse(Call<CourseEnrollResponce> call, Response<CourseEnrollResponce> response) {


                        try {
                            if (response.body().getStatus().equals("1")) {

                                try {


                                    listener.onItemClick(Integer.parseInt(String.valueOf(courseId)));


                                } catch (Exception e) {

                                    e.getMessage();
                                }

                            } else {

                                Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.getMessage();


                        }
                    }

                    @Override
                    public void onFailure(Call<CourseEnrollResponce> call, Throwable t) {

                        t.getMessage();

                    }
                });


            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String temp) {

        }
    }

}
