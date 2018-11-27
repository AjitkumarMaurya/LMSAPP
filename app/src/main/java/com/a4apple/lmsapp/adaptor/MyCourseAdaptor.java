package com.a4apple.lmsapp.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.activity.CourseDetailsActivity;
import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCourseAdaptor extends RecyclerView.Adapter<MyCourseAdaptor.MyViewHolder> {

    private PreferenceManager preferenceManager;
    private List<AllMyCourseResponce.MyCourseList> listCourse;
    private Context context;
    private String baseurl;


    public MyCourseAdaptor(Context context, List<AllMyCourseResponce.MyCourseList> listCourse) {

        this.context = context;
        this.listCourse = listCourse;
        preferenceManager = new PreferenceManager(context);

        baseurl = preferenceManager.getKeyValueString("baseURL");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_my_course, parent, false);

        return new MyCourseAdaptor.MyViewHolder(itemView);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.catname.setText(listCourse.get(position).getCatName().toString());
        holder.cname.setText(listCourse.get(position).getCourseName().toString());

        try {

            String url = baseurl + listCourse.get(position).getImage().toString();

            Glide.with(context).load(url).apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.courseimg);


            holder.courseId.setText(String.valueOf(listCourse.get(position).getCourseId()));

            if (listCourse.get(position).getStatus().toString().equalsIgnoreCase("Completed")) {
                holder.status.setText(listCourse.get(position).getStatus().toString());
                holder.status.setTextColor(Color.BLACK);

            } else {
                holder.status.setText(listCourse.get(position).getStatus().toString());
                holder.status.setTextColor(Color.parseColor("#c75d1d"));

            }

            holder.courseimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra("enrolled", true);
                    intent.putExtra("courseId", listCourse.get(position).getCourseId());
                    intent.putExtra("courseName", listCourse.get(position).getCourseName().toString());
                    intent.putExtra("courseCatName", listCourse.get(position).getCatName().toString());

                    context.startActivity(intent);

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra("enrolled", true);
                    intent.putExtra("courseId", listCourse.get(position).getCourseId());
                    intent.putExtra("courseName", listCourse.get(position).getCourseName().toString());
                    intent.putExtra("courseCatName", listCourse.get(position).getCatName().toString());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {

            e.getMessage();
        }

    }

    @Override
    public int getItemCount() {
        return listCourse.size();
    }



    public void updateData(List<AllMyCourseResponce.MyCourseList> ulistCourse) {

        try {
            listCourse.clear();

            listCourse = ulistCourse;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.getMessage();
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView courseimg;

        @BindView(R.id.textViewCourseName)
        TextView cname;

        @BindView(R.id.textViewCategory)
        TextView catname;

        @BindView(R.id.textViewCourseStatus)
        TextView status;

        @BindView(R.id.textViewCourseId)
        TextView courseId;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }

}
