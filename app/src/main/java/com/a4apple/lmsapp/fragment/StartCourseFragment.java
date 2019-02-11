package com.a4apple.lmsapp.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.activity.CourseStartActivity;
import com.a4apple.lmsapp.activity.CourseTestActivity;
import com.a4apple.lmsapp.activity.FullScreenVideoActivity;
import com.a4apple.lmsapp.adaptor.MatchPairAdaptor;
import com.a4apple.lmsapp.apiNetworkResponce.CommonResponce;
import com.a4apple.lmsapp.apiNetworkResponce.CourseContainResponce;
import com.a4apple.lmsapp.apiNetworkResponce.QuestionResponce;
import com.a4apple.lmsapp.apiNetworkResponce.TestQuestionResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.AnsMatchPair;
import com.a4apple.lmsapp.utility.CustomArrayAdapter;
import com.a4apple.lmsapp.utility.CustomMatchPairClick;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerView;
import com.google.android.flexbox.FlexboxLayout;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class StartCourseFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnDragListener, View.OnLongClickListener {

    @BindView(R.id.start_course_container)
    LinearLayout linearLayout;

    @BindView(R.id.lin6_lay)
    LinearLayout rootlin;

    RelativeLayout relativeLayout;

    @BindView(R.id.nestedView)
    ScrollView nestedScrollView;
    RecyclerView listMatchGivenAns;

    String questionIdTemp = "no";

    List<CourseContainResponce.CourseContent> courseContentsData;
    List<QuestionResponce.QuestionList> questionListsDetails;
    List<QuestionResponce.MCQAnswerList> mcqAnswerLists;
    List<QuestionResponce.OrderingAnswerlist> orderingAnswerlists;
    List<QuestionResponce.MatchingAnswerList> matchingAnswerLists;
    List<QuestionResponce.KeywordsAnswerList> fillBlanckAnswerList;

    List<TestQuestionResponce.TestDetail> testDetailsList;
    List<TestQuestionResponce.QuestionList> testquestionLists;

    ArrayList<String> ansMCQList, tempMCQList, tempMachPair, shufflMachPair, ansOderingList, tempOderingList, ansFillBlankList, tempFillBlankList;
    List<AnsMatchPair> ansMatchPair, tempAnsMatchPair;
    ArrayList<Spinner> spinners;
    TextView next, prev;
    ImageView nextiv, previv;
    int count = 0;
    int courseStartPoint;
    int testRetryCount = 0;
    int testRetryCountTotal = 0;

    Button btnNext;
    boolean ansValue = false;
    String strCheckPoint = "0";
    Context context;

    TextView titletv;

    JcPlayerView jcplayerView;

    @BindView(R.id.tv_unit_title)
    TextView tvTitle;

    String idTest = null;


    private EasyVideoPlayer player;

    PreferenceManager preferenceManager;

    String userId, courseId, completedOrder;

    LinearLayout ll_top_layout;

    int netListSiz=0;

    public StartCourseFragment(Context context, RelativeLayout relativeLayout, ImageView nextiv, TextView next, ImageView previv, TextView prev, List<CourseContainResponce.CourseContent> courseContentsData, Button btnNext, int i, TextView titletv, int courseId, String completedOrder) {
        this.relativeLayout = relativeLayout;
        this.next = next;
        this.nextiv = nextiv;
        this.prev = prev;
        this.previv = previv;
        this.btnNext = btnNext;
        this.count = i;
        this.courseContentsData = courseContentsData;
        this.netListSiz=courseContentsData.size()-1;
        this.context = context;
        this.titletv = titletv;
        this.courseId = String.valueOf(courseId);
        this.completedOrder = completedOrder;
        Paper.init(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_course, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Paper.init(context);
        initFirst();
    }

    public void initFirst() {
        ansMCQList = new ArrayList<>();
        tempMCQList = new ArrayList<>();
        tempMachPair = new ArrayList<>();
        shufflMachPair = new ArrayList<>();
        ansMatchPair = new ArrayList<>();
        tempAnsMatchPair = new ArrayList<>();
        ansOderingList = new ArrayList<>();
        tempOderingList = new ArrayList<>();
        ansFillBlankList = new ArrayList<>();
        tempFillBlankList = new ArrayList<>();
        spinners = new ArrayList<>();

        preferenceManager = new PreferenceManager(getActivity());

        userId = preferenceManager.getKeyValueString("userId");

        courseStartPoint = preferenceManager.getKeyValueIntForCourseIndex("courseStartIndex");

        eableNextBtn();

        clickNext(count, false, "");

        if (count == courseStartPoint) {

            previv.setImageResource(R.drawable.triangle_left_gray);

        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.performClick();
            }
        });
        nextiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (questionIdTemp.equalsIgnoreCase("no")) {
                    if (strCheckPoint.equalsIgnoreCase("1")) {

                        if (ansValue) {
                            ansValue = false;
                            count++;
                            previv.setImageResource(R.drawable.triangle_left_oreng);

                            clickNext(count, false, "");
                            clearArrays();
                        } else {

                            if (getAllAnswer()) {
                                new networkCallForUpdateCourseStatus(courseContentsData.get(count).getCourseContentId().toString(), userId, "", courseId).execute();

                                Toasty.success(getActivity(), "Answer Success!", Toast.LENGTH_SHORT, true).show();
                            }
                        }

                    } else {
                        previv.setImageResource(R.drawable.triangle_left_oreng);
                        count++;
                        clickNext(count, false, "");
                    }
                } else {
                    networkCallForQuestions(questionIdTemp);
                    questionIdTemp = "no";
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextiv.performClick();
            }
        });
        previv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eableNextBtn();

                if (count > courseStartPoint) {
                    count--;
                    clearArrays();
                    if (count == courseStartPoint) {
                        previv.setImageResource(R.drawable.triangle_left_gray);

                    } else {
                        previv.setImageResource(R.drawable.triangle_left_oreng);
                    }
                    nextiv.setImageResource(R.drawable.triangle_right_orange);
                    clickNext(count, true, "");
                } else {


                    previv.setImageResource(R.drawable.triangle_left_gray);

                    Toasty.warning(getActivity(), "No Data Found", Toast.LENGTH_SHORT, true).show();
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previv.performClick();
            }
        });
    }

    public boolean getAllAnswer() {
        ansValue = false;
        if (ansMCQList.size() >= 1) {
            if (ansMCQList.size() == tempMCQList.size()) {
                int counterxy = 0;
                for (int j = 0; j < ansMCQList.size(); j++) {
                    String ansTrue = ansMCQList.get(j);
                    for (int i = 0; i < ansMCQList.size(); i++) {
                        String ansTemp = tempMCQList.get(i);
                        if (ansTemp.equalsIgnoreCase(ansTrue)) {
                            counterxy++;
                        }
                    }
                }
                if (counterxy == ansMCQList.size()) {
                    ansValue = true;
                } else {
                    Toasty.error(getActivity(), "MCQ Answer Wrong.", Toast.LENGTH_SHORT, true).show();
                    ansValue = false;
                }

            } else {
                Toasty.error(getActivity(), "MCQ Answer Wrong.", Toast.LENGTH_SHORT, true).show();
                ansValue = false;
            }
        }


        if (ansOderingList.size() > 1) {
            if (ansOderingList.size() == tempOderingList.size()) {

                if (ansOderingList.equals(tempOderingList)) {
                    ansValue = true;
                } else {
                    Toasty.error(getActivity(), "Wrong Oder Answer.", Toast.LENGTH_SHORT, true).show();
                    ansValue = false;
                }
            } else {
                Toasty.error(getActivity(), "Wrong Oder Answer.", Toast.LENGTH_SHORT, true).show();
                ansValue = false;
            }
        }

        if (ansFillBlankList.size() > 1) {

            try {

                if (spinners.size() > 1) {

                    for (int i = 0; i < spinners.size(); i++) {

                        tempFillBlankList.add(spinners.get(i).getSelectedItem().toString());

                    }

                }
            } catch (Exception e) {
                e.getMessage();
            }


            if (ansFillBlankList.size() == tempFillBlankList.size()) {
                if (ansFillBlankList.equals(tempFillBlankList)) {
                    ansValue = true;
                } else {
                    tempFillBlankList.clear();
                    Toasty.error(getActivity(), "Wrong Answer.", Toast.LENGTH_SHORT, true).show();
                    ansValue = false;
                }

            } else {
                tempFillBlankList.clear();
                Toasty.error(getActivity(), "Wrong Answer.", Toast.LENGTH_SHORT, true).show();
                ansValue = false;
            }

        }

        if (ansMatchPair.size() > 1) {
            if (ansMatchPair.size() == tempAnsMatchPair.size()) {

                int anscount = 0;
                for (int i = 0; i < ansMatchPair.size(); i++) {

                    for (int j = 0; j < tempAnsMatchPair.size(); j++) {

                        if (ansMatchPair.get(i).getLeft().equals(tempAnsMatchPair.get(j).getLeft()) || ansMatchPair.get(i).getLeft().equals(tempAnsMatchPair.get(j).getRight()) && ansMatchPair.get(i).getRight().equals(tempAnsMatchPair.get(j).getLeft()) || ansMatchPair.get(i).getRight().equals(tempAnsMatchPair.get(j).getRight())) {
                            anscount++;
                        }
                    }

                }
                if (anscount == ansMatchPair.size()) {
                    ansValue = true;
                } else {
                    ansValue = false;
                    Toasty.error(getActivity(), "MatchPair Answer Wrong.", Toast.LENGTH_SHORT, false).show();
                    matchPairView("no");
                }
            } else {
                ansValue = false;
                Toasty.error(getActivity(), "Complete the MatchPair", Toast.LENGTH_SHORT, false).show();
            }
        }

        return ansValue;
    }


    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    public void clickNext(final int i, Boolean val, @Nullable String str) {

        Date d = new Date();
        CharSequence sd = DateFormat.format("HH:mm dd/MM/yyyy ", d.getTime());


        try {
            if (jcplayerView.isPlaying()) {
                jcplayerView.kill();
            }
        } catch (Exception e) {
            e.getMessage();
        }


        if (courseContentsData.size() - 1 == i) {
            nextiv.setImageResource(R.drawable.triangle_right_white);
        }

        if (count < 1) {

            previv.setImageResource(R.drawable.triangle_left_gray);

        }


        if (courseContentsData.size() > i) {
            final AlertDialog dialog = new SpotsDialog(getActivity(), "Loading...", R.style.Custom);
            dialog.setCancelable(false);
            dialog.show();
            try {
                if (courseContentsData.get(i).getContentCategory().equals(1)) {
                    if (val) {
                        count--;
                        clickNext(count, true, "");
                        clearArrays();
                    } else {
                        count++;
                        clickNext(count, false, "");
                        clearArrays();
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }

            if (courseContentsData.get(i).getContentCategory().equals(2)) {
                try {
                    enableBottomBtn();

                    if (courseContentsData.get(i).getContentDetail() == null) {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.no_data_found_unit, null);
                        addView(viewVideo);

                    } else {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString(), Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString()));
                        }


                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_course_text_view, null);
                        WebView text = viewVideo.findViewById(R.id.tv_course_into);
                        text.getSettings().setJavaScriptEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            text.loadDataWithBaseURL(null, courseContentsData.get(i).getContentDetail().toString(), "text/html", "utf-8", null);
                        } else {
                            text.loadDataWithBaseURL(null, courseContentsData.get(i).getContentDetail().toString(), "text/html", "utf-8", null);
                        }
                        addView(viewVideo);
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("Q")) {

                            questionIdTemp = String.valueOf(courseContentsData.get(i).getCompletionValue());

                        } else {
                            questionIdTemp = "no";
                        }
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("D")) {
                            new networkCallForUpdateCourseStatus(courseContentsData.get(i).getCourseContentId().toString(), userId, "", courseId).execute();

                        }
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("T")) {
                            try {
                                int time = courseContentsData.get(i).getCompletionValue();
                                delayView(time, courseContentsData.get(i).getCourseContentId().toString());
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            if (courseContentsData.get(i).getContentCategory().equals(3)) {
                try {
                    enableBottomBtn();


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString()));
                    }

                    if (courseContentsData.get(i).getContentDetail() == null) {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.no_data_found_unit, null);
                        addView(viewVideo);

                    } else {
                        String tag = "<iframe width= 100% height= 320 style= border: 1px solid #cccccc; src= " + courseContentsData.get(i).getContentDetail().toString() + " ></iframe>";

                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_web_view, null);
                        WebView webView = viewVideo.findViewById(R.id.webView);
                        addView(viewVideo);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.loadData(tag, "text/html", null);
                        webView.setHorizontalScrollBarEnabled(false);

                        showDialog(true);
                        webView.setWebViewClient(new WebViewClient() {

                            public void onPageFinished(WebView view, String url) {
                                // do your stuff here

                                showDialog(false);

                            }
                        });

                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("D")) {
                            new networkCallForUpdateCourseStatus(courseContentsData.get(i).getCourseContentId().toString(), userId, "", courseId).execute();

                        }

                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("Q")) {
                            questionIdTemp = String.valueOf(courseContentsData.get(i).getCompletionValue());

                        } else {
                            questionIdTemp = "no";
                        }
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("T")) {
                            try {
                                int time = Integer.parseInt(courseContentsData.get(i).getCompletionValue().toString());
                                delayView(time, courseContentsData.get(i).getCourseContentId().toString());
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            if (courseContentsData.get(i).getContentCategory().equals(4)) {
                try {
                    enableBottomBtn();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString()));
                    }
                    if (courseContentsData.get(i).getContentDetail() == null) {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.no_data_found_unit, null);
                        addView(viewVideo);

                    } else {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_course_video_view, null);
                        ImageButton button = viewVideo.findViewById(R.id.click);
                        player = viewVideo.findViewById(R.id.player);
                        try {
                            final String url = preferenceManager.getKeyValueString("baseURL") + courseContentsData.get(i).getContentDetail().toString();
                            player.setSource(Uri.parse(url));
                            player.setCallback(new EasyVideoCallback() {
                                @Override
                                public void onStarted(EasyVideoPlayer player) {

                                }

                                @Override
                                public void onPaused(EasyVideoPlayer player) {

                                }

                                @Override
                                public void onPreparing(EasyVideoPlayer player) {

                                }

                                @Override
                                public void onPrepared(EasyVideoPlayer player) {

                                }

                                @Override
                                public void onBuffering(int percent) {

                                }

                                @Override
                                public void onError(EasyVideoPlayer player, Exception e) {
                                    e.getMessage();
                                }

                                @Override
                                public void onCompletion(EasyVideoPlayer player) {

                                }

                                @Override
                                public void onRetry(EasyVideoPlayer player, Uri source) {

                                }

                                @Override
                                public void onSubmit(EasyVideoPlayer player, Uri source) {

                                }
                            });
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), FullScreenVideoActivity.class);
                                    intent.putExtra("play", player.getCurrentPosition());
                                    intent.putExtra("url", url);
                                    startActivityForResult(intent, 4);
                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();
                        }

                        addView(viewVideo);
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("D")) {
                            new networkCallForUpdateCourseStatus(courseContentsData.get(i).getCourseContentId().toString(), userId, "", courseId).execute();

                        }

                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("Q")) {
                            questionIdTemp = String.valueOf(courseContentsData.get(i).getCompletionValue());

                        } else {
                            questionIdTemp = "no";
                        }
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("T")) {
                            try {
                                int time = Integer.parseInt(courseContentsData.get(i).getCompletionValue().toString());
                                delayView(time, courseContentsData.get(i).getCourseContentId().toString());
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                    }
                } catch (Exception e) {

                    e.getMessage();
                }
            }
            if (courseContentsData.get(i).getContentCategory().equals(5)) {
                try {
                    enableBottomBtn();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString()));
                    }
                    if (courseContentsData.get(i).getContentDetail() == null) {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.no_data_found_unit, null);
                        addView(viewVideo);

                    } else {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_course_audio_view, null);
                        jcplayerView = viewVideo.findViewById(R.id.jcplayer);
                        ArrayList<JcAudio> jcAudios = new ArrayList<>();
                        String url = preferenceManager.getKeyValueString("baseURL") + courseContentsData.get(i).getContentDetail().toString();
                        jcAudios.add(JcAudio.createFromURL(courseContentsData.get(i).getContentTitle().toString(), url));
                        jcplayerView.initPlaylist(jcAudios);
                        jcplayerView.createNotification();


                        addView(viewVideo);
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("D")) {
                            new networkCallForUpdateCourseStatus(courseContentsData.get(i).getCourseContentId().toString(), userId, "", courseId).execute();

                        }

                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("Q")) {
                            questionIdTemp = String.valueOf(courseContentsData.get(i).getCompletionValue());

                        } else {
                            questionIdTemp = "no";
                        }
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("T")) {
                            try {
                                int time = Integer.parseInt(courseContentsData.get(i).getCompletionValue().toString());
                                delayView(time, courseContentsData.get(i).getCourseContentId().toString());
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                    }
                } catch (Exception e) {

                    e.getMessage();
                }
            }
            if (courseContentsData.get(i).getContentCategory().equals(6)) {
                try {
                    enableBottomBtn();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString()));
                    }
                    if (courseContentsData.get(i).getContentDetail() == null) {
                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.no_data_found_unit, null);
                        addView(viewVideo);

                    } else {
                        final String url = preferenceManager.getKeyValueString("baseURL") + courseContentsData.get(i).getContentDetail().toString();

                        StringTokenizer stStr = new StringTokenizer(courseContentsData.get(i).getContentDetail().toString(), ".");

                        stStr.nextToken();

                        Log.e("url...", "" + url);

                        if (stStr.nextToken().equalsIgnoreCase("pdf")) {

                            @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_web_view, null);
                            WebView webView = viewVideo.findViewById(R.id.webView);

                            webView.loadUrl(preferenceManager.getKeyValueString("baseURL")+"web/viewer.html?file=" + url);

                            addView(viewVideo);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.setHorizontalScrollBarEnabled(false);
                            webView.setVerticalScrollBarEnabled(true);

                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            int height = (displayMetrics.heightPixels * 11) / 20;

                            webView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    height));


                            showDialog(true);

                            webView.setWebViewClient(new WebViewClient() {

                                public void onPageFinished(WebView view, String url) {

                                    showDialog(false);

                                }
                            });

                        } else {
                            @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_web_view, null);
                            WebView webView = viewVideo.findViewById(R.id.webView);


                            webView.loadUrl("https://view.officeapps.live.com/op/embed.aspx?src=" + url);

                            addView(viewVideo);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.setHorizontalScrollBarEnabled(false);
                            webView.setVerticalScrollBarEnabled(true);

                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            int height = (displayMetrics.heightPixels * 11) / 20;

                            webView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    height));

                            showDialog(true);

                            webView.setWebViewClient(new WebViewClient() {

                                public void onPageFinished(WebView view, String url) {

                                    showDialog(false);

                                }
                            });
                        }

                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("D")) {
                            new networkCallForUpdateCourseStatus(courseContentsData.get(i).getCourseContentId().toString(), userId, "", courseId).execute();

                        }

                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("Q")) {
                            questionIdTemp = String.valueOf(courseContentsData.get(i).getCompletionValue());

                        } else {
                            questionIdTemp = "no";
                        }
                        if (courseContentsData.get(i).getCompletionType().toString().equalsIgnoreCase("T")) {
                            try {
                                int time = Integer.parseInt(courseContentsData.get(i).getCompletionValue().toString());
                                delayView(time, courseContentsData.get(i).getCourseContentId().toString());
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            if (courseContentsData.get(i).getContentCategory().equals(7)) {
                try {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        titletv.setText(Html.fromHtml(courseContentsData.get(i).getContentTitle().toString()));
                    }


                    idTest = courseContentsData.get(i).getCourseContentId().toString();

                    final int Retrycount = courseContentsData.get(i).getTryCount();

                    testRetryCount = Retrycount;

                    final AlertDialog dialog2 = new SpotsDialog(getActivity(), "Loading...", R.style.Custom);
                    dialog2.setCancelable(false);
                    dialog2.show();
                    Call<TestQuestionResponce> applistResponceCall;
                    ApiInterface apiInterface;
                    apiInterface = Apiclient.getClient().create(ApiInterface.class);
                    applistResponceCall = apiInterface.testquestiondetails(idTest, preferenceManager.getKeyValueString("userId"));
                    applistResponceCall.enqueue(new Callback<TestQuestionResponce>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<TestQuestionResponce> call, Response<TestQuestionResponce> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus().equalsIgnoreCase("1")) {
                                    testDetailsList = response.body().getTestDetails();
                                    testquestionLists = response.body().getQuestionList();

                                    try {
                                        Paper.book().delete("testDetailsList");
                                        Paper.book().delete("testquestionLists");

                                        Paper.book().write("testDetailsList", testDetailsList);
                                        Paper.book().write("testquestionLists", testquestionLists);

                                        dialog2.dismiss();

                                        @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_start_test_course, null);
                                        TextView title = viewVideo.findViewById(R.id.tv_stest_title);
                                        TextView desc = viewVideo.findViewById(R.id.tv_stest_desc);
                                        TextView pass = viewVideo.findViewById(R.id.tv_stest_pass_score);
                                        TextView back = viewVideo.findViewById(R.id.tv_stest_back_main_screen);
                                        Button start = viewVideo.findViewById(R.id.btn_stest_start);
                                        addView(viewVideo);

                                        try {


                                            if (testDetailsList.get(0).getPass().toString().equalsIgnoreCase("A")) {

                                                try {
                                                    title.setText("" + testDetailsList.get(0).getContentTitle().toString());
                                                    desc.setText("Congratulations " + "\n" + "you passed!" + "\n\n" + "Time allowed : " + (int) Math.round((Double) testDetailsList.get(0).getDuration()) + " minutes " + "\n" + "Time used : " + getPerfectTimeString(testDetailsList.get(0).getTime_used().toString()) + " " + "\n" + " Completed at: " + getPerfectTime(testDetailsList.get(0).getCompleted_date().toString()));
                                                    pass.setText(" Score  " + (int) Math.round((Double) testDetailsList.get(0).getScore()) + "%");
                                                } catch (Exception e) {
                                                    Log.e("error", e.getMessage());

                                                }

                                                if (i!=netListSiz){

                                                   enableBottomBtn();

                                                }else {
                                                    diableBottomBtn();

                                                }

                                                start.setVisibility(View.GONE);

                                            } else if (testDetailsList.get(0).getPass().toString().equalsIgnoreCase("D")) {


                                                if (completedOrder.equalsIgnoreCase("N")) {
                                                    dissableNextBtn();
                                                }

                                                if (testDetailsList.get(0).getAllowTryIfFailed().toString().equalsIgnoreCase("C")) {

                                                    try {
                                                        title.setText("" + testDetailsList.get(0).getContentTitle().toString());
                                                        desc.setText("Sorry " + "\n" + "you did't passed!" + "\n\n" + "Time allowed : " + (int) Math.round((Double) testDetailsList.get(0).getDuration()) + " minutes" + "\n" + "Time used : " + getPerfectTimeString(testDetailsList.get(0).getTime_used().toString()) + " " + "\n" + " Completed at: " + getPerfectTime(testDetailsList.get(0).getCompleted_date().toString()));
                                                        pass.setText(" Score  " + (int) Math.round((Double) testDetailsList.get(0).getScore()) + "%");
                                                    } catch (Exception e) {
                                                        e.getMessage();
                                                    }

                                                    if (i!=netListSiz){

                                                        if (completedOrder.equalsIgnoreCase("N")) {

                                                            enableBottomBtn();

                                                        }else {

                                                            diableBottomBtn();                                                        }

                                                    }else {
                                                        diableBottomBtn();

                                                    }


                                                    try {
                                                        testRetryCountTotal = Integer.parseInt(testDetailsList.get(0).getTimesOfRetry().toString());
                                                        if (Retrycount >= Integer.parseInt(testDetailsList.get(0).getTimesOfRetry().toString())) {

                                                            start.setVisibility(View.GONE);

                                                        } else {
                                                            start.setVisibility(View.VISIBLE);

                                                            start.setText("Try Again");

                                                            start.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    Intent i = new Intent(getActivity(), CourseTestActivity.class);
                                                                    startActivityForResult(i, 101);
                                                                }
                                                            });

                                                        }

                                                    } catch (Exception e) {

                                                        start.setVisibility(View.VISIBLE);

                                                        start.setText("Try Again");

                                                        start.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent i = new Intent(getActivity(), CourseTestActivity.class);
                                                                startActivityForResult(i, 101);
                                                            }
                                                        });


                                                    }


                                                } else {

                                                    try {
                                                        title.setText("" + testDetailsList.get(0).getContentTitle().toString());
                                                        desc.setText("Sorry " + "\n" + "you did't passed!" + "\n\n" + "Time allowed : " + (int) Math.round((Double) testDetailsList.get(0).getDuration()) + " minutes" + "\n" + "Time used : " + getPerfectTimeString(testDetailsList.get(0).getTime_used().toString()) + " " + "\n" + " Completed at: " + getPerfectTime(testDetailsList.get(0).getCompleted_date().toString()));
                                                        pass.setText(" Score  " + (int) Math.round((Double) testDetailsList.get(0).getScore()) + "%");
                                                    } catch (Exception e) {
                                                        e.getMessage();
                                                    }



                                                    if (i!=netListSiz){

                                                        if (completedOrder.equalsIgnoreCase("N")) {
                                                            enableBottomBtn();

                                                        }else {
                                                            diableBottomBtn();

                                                        }

                                                    }else {
                                                        diableBottomBtn();

                                                    }

                                                    start.setVisibility(View.GONE);

                                                }

                                            } else {
                                                if (completedOrder.equalsIgnoreCase("N")) {
                                                    dissableNextBtn();
                                                }

                                                title.setText(testDetailsList.get(0).getContentTitle().toString());
                                                desc.setText("This test contains " + testquestionLists.size() + " questions You have " + (int) Math.round((Double) testDetailsList.get(0).getDuration()) + " minute to complete it Once started, the time allowed cannot be stopped");
                                                pass.setText("The passing score is " + (int) Math.round((Double) testDetailsList.get(0).getPassingScore()) + "%");

                                                if (i!=netListSiz){

                                                    if (completedOrder.equalsIgnoreCase("N")) {
                                                        enableBottomBtn();


                                                    }else {
                                                        diableBottomBtn();

                                                    }

                                                }else {
                                                    diableBottomBtn();

                                                }


                                                back.setVisibility(View.VISIBLE);

                                                start.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent i = new Intent(getActivity(), CourseTestActivity.class);
                                                        startActivityForResult(i, 101);
                                                    }
                                                });

                                            }

                                        } catch (Exception e) {
                                            e.getMessage();
                                        }

                                        back.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                ((CourseStartActivity) getActivity()).clickback();

                                            }
                                        });


                                    } catch (Exception e) {
                                        e.getMessage();
                                        Toasty.warning(getActivity(), "No data Available on server..", Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    dialog2.dismiss();
                                    Toasty.warning(getActivity(), "No data Available on server..", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                dialog2.dismiss();

                            }
                        }

                        @Override
                        public void onFailure(Call<TestQuestionResponce> call, Throwable t) {
                            t.getMessage();
                            dialog2.dismiss();
                        }
                    });

                } catch (Exception e) {
                    Log.e("error",e.getMessage());
                }

            }
            dialog.dismiss();
        } else if (i == 88) {

            try {


                diableBottomBtn();
                if (preferenceManager.getKeyValueBoolean("testPass")) {
                    @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_end_test_pass_course, null);

                    TextView title = viewVideo.findViewById(R.id.tv_eptest_title);
                    TextView desc1 = viewVideo.findViewById(R.id.tv_eptest_desc1);
                    TextView desc2 = viewVideo.findViewById(R.id.tv_eptest_desc2);
                    TextView desc3 = viewVideo.findViewById(R.id.tv_eptest_desc3);
                    TextView pass = viewVideo.findViewById(R.id.tv_eptest_pass_score);
                    Button gomainscreen = viewVideo.findViewById(R.id.btn_eptest_start);

                    title.setText(preferenceManager.getKeyValueString("msgTest"));
                    desc1.setText("Time Allowed: " + preferenceManager.getKeyValueString("resultTestTimeAllow") + " Minutes");
                    desc2.setText("Time Used: " + preferenceManager.getKeyValueString("resultTestTimeUsed") + " ");
                    desc3.setText("Completed at: " + sd);
                    pass.setText("Score: " + preferenceManager.getKeyValueString("testResultSocre") + "%");

                    //  Log.e("csbcbs","----"+idTest+"----"+"----"+preferenceManager.getKeyValueString("userId")+"----"+""+preferenceManager.getKeyValueString("resultTestTimeUsed")+"----"+""+preferenceManager.getKeyValueString("testResultSocre")+"");

                    new callNetworkForTestDataSubmit(idTest, preferenceManager.getKeyValueString("userId"), preferenceManager.getKeyValueString("resultTestTimeUsed2"), preferenceManager.getKeyValueString("testResultSocre"), "A").execute();


                    gomainscreen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CourseStartActivity) getActivity()).clickback();
                        }
                    });

                    new networkCallForUpdateCourseStatus(idTest, userId, "", courseId).execute();

                    eableNextBtn();

                    addView(viewVideo);

                } else {
                    @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_end_test_failed_course, null);

                    TextView title = viewVideo.findViewById(R.id.tv_etest_title);
                    TextView desc1 = viewVideo.findViewById(R.id.tv_etest_desc1);
                    TextView desc2 = viewVideo.findViewById(R.id.tv_etest_desc2);
                    TextView desc3 = viewVideo.findViewById(R.id.tv_etest_desc3);
                    TextView pass = viewVideo.findViewById(R.id.tv_etest_pass_score);
                    TextView back = viewVideo.findViewById(R.id.tv_etest_back_main_screen);
                    Button tryagain = viewVideo.findViewById(R.id.btn_etest_start);

                    title.setText(preferenceManager.getKeyValueString("msgTest"));
                    desc1.setText("Time Allowed: " + preferenceManager.getKeyValueString("resultTestTimeAllow") + " Minutes");
                    desc2.setText("Time Used: " + preferenceManager.getKeyValueString("resultTestTimeUsed") + "");
                    desc3.setText("Completed at: " + sd);
                    pass.setText("Score: " + preferenceManager.getKeyValueString("testResultSocre") + "%");

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CourseStartActivity) getActivity()).clickback();
                        }
                    });

                    new callNetworkForTestDataSubmit(idTest, preferenceManager.getKeyValueString("userId"), preferenceManager.getKeyValueString("resultTestTimeUsed2"), preferenceManager.getKeyValueString("testResultSocre"), "D").execute();

                    testRetryCount++;
                    if (testRetryCount >= testRetryCountTotal) {
                        tryagain.setVisibility(View.GONE);

                    } else {
                        tryagain.setVisibility(View.VISIBLE);

                    }

                    tryagain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), CourseTestActivity.class);
                            startActivityForResult(i, 101);
                        }
                    });


                    addView(viewVideo);

                }

            } catch (Exception e) {
                e.getMessage();
            }
        } else if (courseContentsData.size() < i) {
            getActivity().finish();
        }
    }

    public void dissableNextBtn() {
        nextiv.setImageResource(R.drawable.triangle_right_white);
        nextiv.setClickable(false);


    }

    public void eableNextBtn() {

        nextiv.setClickable(true);

    }


    public String getPassScore(String str) {

        String[] parts = str.split(".");

        return parts[0];

    }

    public String getPerfectTime(String str) {

        String[] parts = str.split("T");
        String part1 = parts[0];
        String part2 = parts[1];
        String[] parts2 = part1.split("-");
        String datestr=parts2[2]+"/"+parts2[1]+"/"+parts2[0];
        return part2.substring(0, 5) + "  " + datestr;


    }


    public String getPerfectTimeString(String str) {


        String[] parts = str.split(":");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];

        int hour = Integer.parseInt(part1);
        int minuts = Integer.parseInt(part2);
        String rtn = part1 + " hour " + part2 + " minutes " + part3 + " seconds";
        try {
            if (hour > 0) {
                rtn = part1 + " hour " + part2 + " minutes " + part3 + " seconds";
            } else {

                if (minuts > 0) {
                    rtn = part2 + " minutes " + part3 + " seconds";

                } else {
                    rtn = part3 + " seconds";
                }

            }
        } catch (Exception e) {

            Log.e("eeee", e.getMessage());
        }
        return rtn;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {

            clickNext(88, false, "");
        }

        if (requestCode == 4) {

            if (data != null) {

                player.seekTo(data.getIntExtra("result", 0));

            }
        }

    }

    public void addView(View view) {
        try {
            linearLayout.removeAllViews();
        } catch (Exception e) {
            e.getMessage();
        }
        linearLayout.addView(view, 0);
    }

    public void addView2(View view) {
        try {
            linearLayout.removeViewAt(0);
        } catch (Exception e) {
            e.getMessage();
        }
        linearLayout.addView(view, 0);
    }

    public void delayView(int time, final String unitid) {
        Toasty.info(getActivity(), "You Have stay at least " + time + " sec..", Toast.LENGTH_LONG, true).show();
        diableView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enableView();
                new networkCallForUpdateCourseStatus(unitid, userId, "", courseId).execute();

            }
        }, time * 1000);
    }


    public void diableBottomBtn() {
        btnNext.setClickable(false);
        btnNext.setVisibility(View.GONE);

    }

    public void enableBottomBtn() {
        btnNext.setClickable(true);
        btnNext.setVisibility(View.VISIBLE);

    }

    public void diableView() {
        next.setClickable(false);
        prev.setClickable(false);
        nextiv.setClickable(false);
        previv.setClickable(false);
        next.setVisibility(View.GONE);
        prev.setVisibility(View.GONE);
        nextiv.setVisibility(View.GONE);
        previv.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    public void enableView() {
        next.setClickable(true);
        prev.setClickable(true);
        nextiv.setClickable(true);
        previv.setClickable(true);
        next.setVisibility(View.VISIBLE);
        prev.setVisibility(View.VISIBLE);
        nextiv.setVisibility(View.VISIBLE);
        previv.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
    }


    public void showDialog(boolean var) {

        try {
            final AlertDialog dialog = new SpotsDialog(getActivity(), "Loading...", R.style.Custom);
            dialog.setCancelable(true);

            if (var) {

                dialog.show();

            } else {
                dialog.dismiss();

            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();

                }
            }, 3000);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void networkCallForQuestions(String id) {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Loading...", R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();
        Call<QuestionResponce> applistResponceCall;
        ApiInterface apiInterface;
        apiInterface = Apiclient.getClient().create(ApiInterface.class);
        applistResponceCall = apiInterface.questiondetails(id);
        applistResponceCall.enqueue(new Callback<QuestionResponce>() {
            @Override
            public void onResponse(Call<QuestionResponce> call, Response<QuestionResponce> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        try {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                questionListsDetails = response.body().getQuestionList();
                                if (response.body().getQuestionType().toString().equalsIgnoreCase("1")) {
                                    mcqAnswerLists = response.body().getmCQAnswerList();
                                }
                                if (response.body().getQuestionType().toString().equalsIgnoreCase("2")) {
                                    orderingAnswerlists = response.body().getOrderingAnswerlist();
                                }
                                if (response.body().getQuestionType().toString().equalsIgnoreCase("3")) {

                                }
                                if (response.body().getQuestionType().toString().equalsIgnoreCase("4")) {
                                    fillBlanckAnswerList = response.body().getKeywordsAnswerList();
                                }
                                if (response.body().getQuestionType().toString().equalsIgnoreCase("5")) {
                                    matchingAnswerLists = response.body().getMatchingAnswerList();
                                }
                                dialog.dismiss();
                                strCheckPoint = "1";
                                getQuestionView(response.body().getQuestionType().toString());

                            }
                        } catch (Exception e) {
                            dialog.dismiss();

                            e.getMessage();
                        }
                    } else {

                        Toasty.info(getActivity(), "Sserver is busy at this movement", Toast.LENGTH_LONG, true).show();

                        dialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionResponce> call, Throwable t) {
                t.getMessage();
                dialog.dismiss();
            }
        });
    }

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    public void getQuestionView(String id) {

        if (id.equalsIgnoreCase("1")) {
            @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_mcq_test, null);
            TextView title = viewVideo.findViewById(R.id.tv_mcq_question_title);
            LinearLayout mcqview = viewVideo.findViewById(R.id.lin_mcq);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                title.setText(Html.fromHtml(questionListsDetails.get(0).getQuestion().toString(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                title.setText(Html.fromHtml(questionListsDetails.get(0).getQuestion().toString()));
            }

            for (int i = 0; i < mcqAnswerLists.size(); i++) {
                if (mcqAnswerLists.get(i).getCorrect().toString().equalsIgnoreCase("A")) {
                    ansMCQList.add(mcqAnswerLists.get(i).getAnswer().toString());
                }
                CheckBox ch = new CheckBox(getActivity());
                ch.setId(i);
                ch.setTextColor(Color.BLACK);
                ch.setOnCheckedChangeListener(this);
                ch.setText(mcqAnswerLists.get(i).getAnswer().toString());
                mcqview.addView(ch);
            }
            addView2(viewVideo);
        }
        if (id.equalsIgnoreCase("2")) {
            @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_odaring_test_view, null);
            final DragLinearLayout dragLinearLayout = viewVideo.findViewById(R.id.odaring_container);
            TextView title = viewVideo.findViewById(R.id.tv_drag_title);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                title.setText(Html.fromHtml(questionListsDetails.get(0).getQuestion().toString(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                title.setText(Html.fromHtml(questionListsDetails.get(0).getQuestion().toString()));
            }

            for (int i = 0; i < orderingAnswerlists.size(); i++) {
                ansOderingList.add(orderingAnswerlists.get(i).getAnswer());
            }

            Collections.shuffle(orderingAnswerlists);

            for (int i = 0; i < orderingAnswerlists.size(); i++) {
                @SuppressLint("InflateParams") View child = LayoutInflater.from(getActivity()).inflate(R.layout.column_item, null);
                child.setTag(orderingAnswerlists.get(i).getAnswer());
                TextView textView = child.findViewById(R.id.text_drag);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    textView.setText(Html.fromHtml(orderingAnswerlists.get(i).getAnswer(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    textView.setText(Html.fromHtml(orderingAnswerlists.get(i).getAnswer()));
                }

                textView.setTag(orderingAnswerlists.get(i).getAnswer());
                textView.setTag(orderingAnswerlists.get(i).getOrderId());
                dragLinearLayout.addView(child);// the child is its own drag handle
            }
            for (int i = 0; i < dragLinearLayout.getChildCount(); i++) {
                View child = dragLinearLayout.getChildAt(i);
                dragLinearLayout.setViewDraggable(child, child); // the child is its own drag handle
            }

            dragLinearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    tempOderingList.clear();
                    for (int i = 0; i < dragLinearLayout.getChildCount(); i++) {

                        try {
                            View temp = dragLinearLayout.getChildAt(i);
                            String str = temp.getTag().toString();
                            Log.e("oder", str);
                            tempOderingList.add(str);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }

                    return false;
                }
            });
            dragLinearLayout.setOnViewSwapListener(new DragLinearLayout.OnViewSwapListener() {
                @Override
                public void onSwap(View firstView, int firstPosition, View secondView, int secondPosition) {

                    tempOderingList.clear();
                    for (int i = 0; i < dragLinearLayout.getChildCount(); i++) {

                        try {
                            View temp = dragLinearLayout.getChildAt(i);
                            String str = temp.getTag().toString();
                            Log.e("oder", str);
                            tempOderingList.add(str);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                }
            });

            addView2(viewVideo);
        }
        /*if (id.equalsIgnoreCase("3")) {


        }*/
        if (id.equalsIgnoreCase("4")) {

            @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_fillblank_test_view, null);
            FlexboxLayout linearLayout = viewVideo.findViewById(R.id.liner_container);


            ArrayList<String> spinnerArray = new ArrayList<String>();

            for (int i = 0; i < fillBlanckAnswerList.size(); i++) {
                spinnerArray.add(fillBlanckAnswerList.get(i).getAnswer().toString());
                ansFillBlankList.add(fillBlanckAnswerList.get(i).getAnswer().toString());
            }

            Collections.shuffle(spinnerArray);

            String str = getStringBulder(questionListsDetails.get(0).getQuestion().toString());
            StringTokenizer sTokenize = new StringTokenizer(str, "_");


            while (sTokenize.hasMoreTokens()) {
                TextView textView = new TextView(getActivity());

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    textView.setText(Html.fromHtml(sTokenize.nextToken().trim(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    textView.setText(Html.fromHtml(sTokenize.nextToken().trim()));
                }
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);


                if (sTokenize.hasMoreTokens()) {
                    Spinner spinner = new Spinner(getActivity());

                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    spinner.setLayoutParams(params2);

                    CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(),
                            R.layout.custom_spinner_item, spinnerArray);


                    spinner.setAdapter(adapter);
                    spinners.add(spinner);
                    linearLayout.addView(spinner);


                }

            }


            addView2(viewVideo);
        }
        if (id.equalsIgnoreCase("5")) {

            matchPairView("no");
        }
    }


    public void matchPairView(String rep) {
        try {

            tempAnsMatchPair.clear();
            ansMatchPair.clear();


            @SuppressLint("InflateParams") View viewVideo = LayoutInflater.from(getActivity()).inflate(R.layout.custom_test_match_pair, null);
            TextView title = viewVideo.findViewById(R.id.tv_match_title);
            ll_top_layout = viewVideo.findViewById(R.id.ll_top_layout);
            final LinearLayout ll_left_layout = viewVideo.findViewById(R.id.ll_left_layout);
            listMatchGivenAns = viewVideo.findViewById(R.id.ll_match_view_list);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            listMatchGivenAns.setLayoutManager(mLayoutManager);
            listMatchGivenAns.setItemAnimator(new DefaultItemAnimator());
            listMatchGivenAns.setHasFixedSize(true);

            final MatchPairAdaptor matchPairAdaptor = new MatchPairAdaptor(tempAnsMatchPair, new CustomMatchPairClick() {
                @Override
                public void onItemClickPair(int position, String l, String r) {
                    tempAnsMatchPair.remove(position);
                    tempMachPair.add(l);
                    shufflMachPair.add(r);
                    createMatchPair();
                }
            });

            listMatchGivenAns.setAdapter(matchPairAdaptor);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                title.setText(Html.fromHtml(questionListsDetails.get(0).getQuestion().toString(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                title.setText(Html.fromHtml(questionListsDetails.get(0).getQuestion().toString()));
            }
            AnsMatchPair ansMa;
            for (int i = 0; i < matchingAnswerLists.size(); i++) {
                ansMa = new AnsMatchPair();
                ansMa.setLeft(matchingAnswerLists.get(i).getLeftSide());
                ansMa.setRight(matchingAnswerLists.get(i).getRightSide());
                ansMatchPair.add(ansMa);
                shufflMachPair.add(matchingAnswerLists.get(i).getRightSide());
                tempMachPair.add(matchingAnswerLists.get(i).getLeftSide());
            }
            Collections.shuffle(shufflMachPair);
            Log.e("aj", shufflMachPair.toString());


            ll_left_layout.setOnDragListener(this);
            ll_left_layout.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    if (ll_left_layout.getChildCount() == 2) {
                        new Handler().postDelayed(new Runnable() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void run() {
                                TextView left = (TextView) ll_left_layout.getChildAt(0);
                                TextView right = (TextView) ll_left_layout.getChildAt(1);
                                AnsMatchPair ansMa = new AnsMatchPair();
                                ansMa.setLeft(left.getText().toString());
                                ansMa.setRight(right.getText().toString());
                                tempAnsMatchPair.add(ansMa);
                                matchPairAdaptor.updateData(ansMa);
                                modifyMatchPair(left.getText().toString(), right.getText().toString());
                                ll_left_layout.removeAllViews();
                            }
                        }, 100);

                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                }
            });

            createMatchPair();

            addView2(viewVideo);
        } catch (Exception e) {

            e.getMessage();
        }
    }

    public void modifyMatchPair(String sh, String te) {

        tempMachPair.remove(te);
        shufflMachPair.remove(sh);

        tempMachPair.remove(sh);
        shufflMachPair.remove(te);

        createMatchPair();
    }


    public void createMatchPair() {
        ll_top_layout.removeAllViews();
        for (int i = 0; i < tempMachPair.size(); i++) {
            View childVideo = LayoutInflater.from(getActivity()).inflate(R.layout.single_match_pair_view, null);
            TextView left_tv_dummy = childVideo.findViewById(R.id.left_tv_dummy);
            TextView tv_left_view = childVideo.findViewById(R.id.tv_left_view);
            TextView right_tv_dummy = childVideo.findViewById(R.id.right_tv_dummy);
            TextView tv_right_view = childVideo.findViewById(R.id.tv_right_view);
            left_tv_dummy.setText(tempMachPair.get(i));
            tv_left_view.setText(tempMachPair.get(i));
            right_tv_dummy.setText(shufflMachPair.get(i));
            tv_right_view.setText(shufflMachPair.get(i));
            tv_left_view.setTag("leftview");
            tv_right_view.setTag("rightview");
            tv_left_view.setOnLongClickListener(this);
            tv_right_view.setOnLongClickListener(this);
            ll_top_layout.addView(childVideo);
        }
    }

    public String getStringBulder(String s) {
        //   s ="the quick brown [fox] jumps over the lazy [dog].";
        String[] r = s.split(" ");
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < r.length; i++) {

            if (r[i].contains("[")) {
                str.add("_");
            } else {
                str.add(r[i]);
            }
            Log.e("str", " " + r[i]);
        }

        Log.e("strList", " " + str.toString());

        StringBuilder builder = new StringBuilder();

        for (String string : str) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(string);
        }
        builder.append(".");
        return builder.toString();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // int id = buttonView.getId();
        if (isChecked) {
            tempMCQList.add(buttonView.getText().toString());
        } else {
            tempMCQList.remove(buttonView.getText().toString());
        }
    }

    public void clearArrays() {
        ansMCQList.clear();
        tempMCQList.clear();

        ansMatchPair.clear();
        tempAnsMatchPair.clear();

        tempMachPair.clear();
        shufflMachPair.clear();

        ansFillBlankList.clear();
        tempFillBlankList.clear();
        ansOderingList.clear();
        tempOderingList.clear();
        spinners.clear();
        strCheckPoint = "0";
        ansValue = false;

        try {
            player.pause();
            jcplayerView.pause();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data
                , shadowBuilder
                , v
                , 0
        );
        v.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        int action = event.getAction();
        LinearLayout container = (LinearLayout) view;
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                if (container.getChildCount() != 2) {
                    view.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                    view.invalidate();
                } else {
                    view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    view.invalidate();
                }
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                view.getBackground().clearColorFilter();
                view.invalidate();
                container = (LinearLayout) view;
                String view1temp = null;
                if (container.getChildCount() == 1) {
                    try {
                        view1temp = container.getChildAt(0).getTag().toString();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                if (container.getChildAt(0) == null) {
                    owner.removeView(v);
                    container.addView(v);
                    v.setVisibility(View.VISIBLE);
                }
                if (view1temp != null && !view1temp.equalsIgnoreCase(v.getTag().toString())) {
                    owner.removeView(v);
                    container.addView(v);
                    v.setVisibility(View.VISIBLE);
                }
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                view.getBackground().clearColorFilter();
                view.invalidate();

                return true;
            default:
                break;
        }
        return false;
    }


    @SuppressLint("StaticFieldLeak")
    public class networkCallForUpdateCourseStatus extends AsyncTask<Void, Void, Void> {

        String unitid, userid, status, courseid;

        networkCallForUpdateCourseStatus(String unitid, String userid, String status, String courseid) {

            this.unitid = unitid;
            this.courseid = courseid;
            this.status = status;
            this.userid = userid;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Call<CommonResponce> applistResponceCall;
            ApiInterface apiInterface;
            apiInterface = Apiclient.getClient().create(ApiInterface.class);
            applistResponceCall = apiInterface.updatecoursestatus(unitid, userId, courseId);
            applistResponceCall.enqueue(new Callback<CommonResponce>() {
                @Override
                public void onResponse(Call<CommonResponce> call, Response<CommonResponce> response) {

                    //   response.body();
                   /* if (response.body().getStatus().equalsIgnoreCase("1")) {

                        //  new CompleteCourseAsyncTask(courseId,userId,unitid).execute();

                    }*/
                }

                @Override
                public void onFailure(Call<CommonResponce> call, Throwable t) {
                    t.getMessage();
                }
            });

            return null;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            player.pause();
            jcplayerView.pause();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            player.pause();
            jcplayerView.pause();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            player.pause();
            jcplayerView.pause();

        } catch (Exception e) {
            e.getMessage();
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class callNetworkForTestDataSubmit extends AsyncTask<Void, Void, Void> {

        String testId, userId, time, score, pass;

        callNetworkForTestDataSubmit(String testId, String userId, String time, String score, String pass) {
            this.testId = testId;
            this.userId = userId;
            this.time = time;
            this.score = score;
            this.pass = pass;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Call<CommonResponce> applistResponceCall2;
            ApiInterface apiInterface;
            apiInterface = Apiclient.getClient().create(ApiInterface.class);
            applistResponceCall2 = apiInterface.addTestResult(userId, testId, time, score, pass);
            applistResponceCall2.enqueue(new Callback<CommonResponce>() {
                @Override
                public void onResponse(Call<CommonResponce> call, Response<CommonResponce> response) {


                    response.body();
                }

                @Override
                public void onFailure(Call<CommonResponce> call, Throwable t) {

                }
            });

            return null;


        }
    }

}
