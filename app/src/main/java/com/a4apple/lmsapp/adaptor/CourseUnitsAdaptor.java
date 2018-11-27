package com.a4apple.lmsapp.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.apiNetworkResponce.CourseContainResponce;
import com.a4apple.lmsapp.utility.AnsMatchPair;
import com.a4apple.lmsapp.utility.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CourseUnitsAdaptor extends BaseAdapter {


    private Context context;
    private List<CourseContainResponce.CourseContent> courseContentsData;
    private List<AnsMatchPair> unitList;

    private PreferenceManager preferenceManager;
    public CourseUnitsAdaptor(Context context, List<CourseContainResponce.CourseContent> courseContentsData) {
        this.context = context;
        this.courseContentsData = courseContentsData;
        preferenceManager = new PreferenceManager(context);
        unitList = new ArrayList<>();
        unitList.clear();
        try {
            Paper.init(context);
            Paper.book().delete("unitListTotal");
        }catch (Exception e){
            e.getMessage();
        }
    }


    @Override
    public int getCount() {
        return courseContentsData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.custom_course_units, parent, false);

        TextView title = convertView.findViewById(R.id.tv_units_title);
        LinearLayout layout = convertView.findViewById(R.id.units_container);
        View viewboder = convertView.findViewById(R.id.border3);


        if (courseContentsData.get(position).getContentCategory()==1){

            title.setText(courseContentsData.get(position).getContentTitle().toString());
            convertView.setTag("no");
            layout.setClickable(true);

        }
        else {
            AnsMatchPair ansMatchPair = new AnsMatchPair();

            convertView.setTag("noTick");

            layout.setClickable(false);

            title.setVisibility(View.GONE);
            viewboder.setVisibility(View.GONE);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.custom_units_name, null);
            ImageView tick = view.findViewById(R.id.iv_tick);
            TextView subtitle = view.findViewById(R.id.tv_chaptername);

            try {
                if (courseContentsData.get(position).getStatus().toString().equalsIgnoreCase("A")) {

                    tick.setVisibility(View.VISIBLE);
                    tick.setImageResource(R.drawable.tick);
                    ansMatchPair.setLeft(courseContentsData.get(position).getCourseContentId().toString());
                    ansMatchPair.setRight("d");
                    subtitle.setText(courseContentsData.get(position).getContentTitle().toString());
                    convertView.setTag("yes");

                } else {

                    if(courseContentsData.get(position).getStatus().toString().equalsIgnoreCase("D")){

                        tick.setVisibility(View.VISIBLE);
                        tick.setImageResource(R.drawable.ic_x_icon);
                        ansMatchPair.setLeft(courseContentsData.get(position).getCourseContentId().toString());
                        ansMatchPair.setRight("d");
                        subtitle.setText(courseContentsData.get(position).getContentTitle().toString());
                        convertView.setTag("yes");


                    }else {

                        preferenceManager.setKeyValueInt("nextPos",position);
                        tick.setVisibility(View.GONE);
                        ansMatchPair.setLeft(courseContentsData.get(position).getCourseContentId().toString());
                        ansMatchPair.setRight("p");
                        subtitle.setText(courseContentsData.get(position).getContentTitle().toString());
                        convertView.setTag("noTick");
                    }

                }
                unitList.add(ansMatchPair);
                Paper.book().write("unitListTotal",unitList);

            }catch (Exception e){
                e.getMessage();
                tick.setVisibility(View.GONE);
                ansMatchPair.setLeft(courseContentsData.get(position).getCourseContentId().toString());
                ansMatchPair.setRight("p");
                subtitle.setText(courseContentsData.get(position).getContentTitle().toString());

            }

            subtitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            subtitle.setMarqueeRepeatLimit(-1);
            subtitle.setSingleLine(true);
            subtitle.setSelected(true);


            layout.addView(view);

        }

        return convertView;


    }
}
