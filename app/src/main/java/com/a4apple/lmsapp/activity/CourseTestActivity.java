package com.a4apple.lmsapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a4apple.lmsapp.LMSApp;
import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.adaptor.MatchPairAdaptor;
import com.a4apple.lmsapp.apiNetworkResponce.QuestionResponce;
import com.a4apple.lmsapp.apiNetworkResponce.TestQuestionResponce;
import com.a4apple.lmsapp.apiRetrofit.ApiInterface;
import com.a4apple.lmsapp.apiRetrofit.Apiclient;
import com.a4apple.lmsapp.utility.AnsMatchPair;
import com.a4apple.lmsapp.utility.ConnectivityReceiver;
import com.a4apple.lmsapp.utility.CustomArrayAdapter;
import com.a4apple.lmsapp.utility.CustomMatchPairClick;
import com.a4apple.lmsapp.utility.PreferenceManager;
import com.google.android.flexbox.FlexboxLayout;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTestActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnLongClickListener, View.OnDragListener, ConnectivityReceiver.ConnectivityReceiverListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_course_test_num)
    TextView courseIndex;

    @BindView(R.id.tv_course_test_time_num)
    TextView courseTimer;

    @BindView(R.id.btn_ans_test)
    Button ansBtn;

    String serverTime;

    @BindView(R.id.container_test)
    LinearLayout linearLayoutContainer;

    List<TestQuestionResponce.TestDetail> testDetails;
    List<TestQuestionResponce.QuestionList> questionLists;

    List<QuestionResponce.QuestionList> questionListsDetails;
    List<QuestionResponce.MCQAnswerList> mcqAnswerLists;
    List<QuestionResponce.OrderingAnswerlist> orderingAnswerlists;
    List<QuestionResponce.MatchingAnswerList> matchingAnswerLists;
    List<QuestionResponce.KeywordsAnswerList> fillBlanckAnswerList;

    ArrayList<String> ansMCQList, tempMCQList, tempMachPair, shufflMachPair, ansOderingList, tempOderingList, ansFillBlankList, tempFillBlankList;
    List<AnsMatchPair> ansMatchPair, tempAnsMatchPair;
    ArrayList<Spinner> spinners;

    PreferenceManager preferenceManager;
    int i = 0;
    int j = 1;
    int quesResult = 0;
    int quesTotal = 0;
    RecyclerView listMatchGivenAns;
    LinearLayout ll_top_layout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_test);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Paper.init(this);

        preferenceManager = new PreferenceManager(this);

        testDetails = Paper.book().read("testDetailsList");


        questionLists = Paper.book().read("testquestionLists");


        if (testDetails.get(0).getRandomQuestionOrder().toString().equalsIgnoreCase("A") || testDetails.get(0).getRandomQuestionOrder().toString().equalsIgnoreCase("C")) {
            Collections.shuffle(questionLists);
        }

        checkConnection();

        for (int i = 0; i < questionLists.size(); i++) {
            quesTotal = quesTotal + (int) Math.round((Double) questionLists.get(i).getQuestionweight());
        }


        int time = (int) Math.round((Double) testDetails.get(0).getDuration());


        serverTime = "" + time;

        new CountDownTimer(time * 60000, 1000) {

            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                int hours = seconds / (60 * 60);
                seconds = seconds % 60;

                courseTimer.setText(String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            @SuppressLint("ResourceAsColor")
            public void onFinish() {
                courseTimer.setText("Finish!");
                ansBtn.setBackgroundColor(R.color.appTheme);
                ansBtn.setClickable(false);
                clearAllArray();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "finished");
                passfail();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }.start();

        initfirst();
        clearAllArray();
        setQuestionView(i);
        courseIndex.setText(j + "/" + questionLists.size());

        ansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllAnswer(i);
                i++;
                j++;
                if (j <= questionLists.size()) {

                    courseIndex.setText(j + "/" + questionLists.size());
                } else {
                    Toasty.success(CourseTestActivity.this, "Successfully all Test Completed.", Toast.LENGTH_SHORT, true).show();
                    clearAllArray();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "finished");
                    passfail();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                }
                setQuestionView(i);

            }
        });
    }


    public boolean getAllAnswer(int iq) {

        if (ansMCQList.size() >= 1) {
            if (ansMCQList.size() == tempMCQList.size()) {
                int counter = 0;
                for (int j = 0; j < ansMCQList.size(); j++) {
                    String ansTrue = ansMCQList.get(j);
                    for (int i = 0; i < ansMCQList.size(); i++) {
                        String ansTemp = tempMCQList.get(i);
                        if (ansTemp.equalsIgnoreCase(ansTrue)) {
                            counter++;
                        }
                    }
                }
                if (counter == ansMCQList.size()) {
                    quesResult = quesResult + (int) Math.round((Double) questionLists.get(iq).getQuestionweight());
                    clearAllArray();

                }
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
                    quesResult = quesResult + (int) Math.round((Double) questionLists.get(iq).getQuestionweight());
                    clearAllArray();

                }
            }
        }

        if (ansOderingList.size() > 1) {
            if (ansOderingList.size() == tempOderingList.size()) {

                if (ansOderingList.equals(tempOderingList)) {
                    quesResult = quesResult + (int) Math.round((Double) questionLists.get(iq).getQuestionweight());
                    clearAllArray();

                }
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

                    quesResult = quesResult + (int) Math.round((Double) questionLists.get(iq).getQuestionweight());

                } else {
                    tempFillBlankList.clear();
                }

            } else {
                tempFillBlankList.clear();
            }

        }
        return true;
    }


    public void initfirst() {

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

    }

    public void clearAllArray() {
        ansMCQList.clear();
        tempMCQList.clear();
        ansOderingList.clear();
        tempOderingList.clear();
        ansMatchPair.clear();
        tempAnsMatchPair.clear();
        tempMachPair.clear();
        shufflMachPair.clear();
        ansFillBlankList.clear();
        tempFillBlankList.clear();
        spinners.clear();

    }

    public void setQuestionView(int i) {

        if (questionLists.size() > i) {
            networkCallForQuestions(questionLists.get(i).getQuestionId().toString());
        } else if (questionLists.size() < i) {
            Toasty.success(this, "Successfully all Test Completed.", Toast.LENGTH_SHORT, true).show();
            clearAllArray();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "finished");
            passfail();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    public void passfail() {

        preferenceManager = new PreferenceManager(this);

        int score = 100 * quesResult / quesTotal;
        int testDuration = (int) Math.round((Double) testDetails.get(0).getDuration());

        String testTimeTake = getTimeUsered(serverTime, courseTimer.getText().toString());

        // String testTimeTake = courseTimer.getText().toString();

        int passScore = (int) Math.round((Double) testDetails.get(0).getPassingScore());

        if (passScore < score) {

            preferenceManager.setKeyValueBoolean("testPass", true);
            preferenceManager.setKeyValueString("msgTest", testDetails.get(0).getMessageWhenPassed().toString());

        } else {
            preferenceManager.setKeyValueBoolean("testPass", false);
            preferenceManager.setKeyValueString("msgTest", testDetails.get(0).getMessageWhenFailed().toString());


        }

        preferenceManager.setKeyValueString("testTitle", testDetails.get(0).getContentTitle().toString());
        preferenceManager.setKeyValueString("resultTestCount", String.valueOf(quesTotal));
        preferenceManager.setKeyValueString("testResultSocre", String.valueOf(score));
        preferenceManager.setKeyValueString("resultTestTimeAllow", String.valueOf(testDuration));
        preferenceManager.setKeyValueString("resultTestTimeUsed", getPerfectTimeString(testTimeTake));


    }


    private String getPerfectTimeString(String str) {


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


    @SuppressLint("DefaultLocale")
    private String convertTime(long time) {
        String finalTime = "";
        long hour = (time % (24 * 60)) / 60;
        long minutes = (time % (24 * 60)) % 60;
        long seconds = time / (24 * 3600);

        finalTime = String.format("%02d:%02d:%02d",
                TimeUnit.HOURS.toHours(hour),
                TimeUnit.MINUTES.toMinutes(minutes),
                TimeUnit.SECONDS.toSeconds(seconds));
        return finalTime;
    }

    public String getTimeUsered(String total, String left) {

        long difference = 0;
        long second=1 , minute=0 , hour = 0;
        int tInt = Integer.parseInt(total);

        String leftTiming = convertTime(tInt);

        if (!left.contains("Finish!")){

            try {


                Log.e("#### converted time", "   " + convertTime(tInt));
                Log.e("#### left timer", "   " + left);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date date1 = format.parse(convertTime(tInt));
                Date date2 = format.parse(left);
                difference = date1.getTime() - date2.getTime();


                Log.e("#### diffrence time", "   " + difference);


                second = (difference / 1000) % 60;
                minute = (difference / (1000 * 60)) % 60;
                hour = (difference / (1000 * 60 * 60)) % 24;


                Log.e("#### diff time final", "   " + hour + ":" + minute + ":" + second);

                leftTiming = hour + ":" + minute + ":" + second;

            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }

        Log.e("#### diffrence time", "   " + leftTiming);

        preferenceManager.setKeyValueString("resultTestTimeUsed2", "" + leftTiming);


        return String.valueOf(leftTiming);

    }

    public void networkCallForQuestions(String id) {
        final AlertDialog dialog = new SpotsDialog(this, "Loading...", R.style.Custom);
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
                            getQuestionView(response.body().getQuestionType().toString());

                        }
                    } catch (Exception e) {
                        e.getMessage();
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


    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    public void getQuestionView(String id) {

        if (id.equalsIgnoreCase("1")) {
            View viewVideo = LayoutInflater.from(this).inflate(R.layout.custom_mcq_test, null);
            TextView title = viewVideo.findViewById(R.id.tv_mcq_question_title);
            LinearLayout mcqview = viewVideo.findViewById(R.id.lin_mcq);
            title.setText(questionListsDetails.get(0).getQuestion().toString());

            if (testDetails.get(0).getShuffleAnswers().toString().equalsIgnoreCase("C")) {
                Collections.shuffle(mcqAnswerLists);
            }

            for (int i = 0; i < mcqAnswerLists.size(); i++) {
                if (mcqAnswerLists.get(i).getCorrect().toString().equalsIgnoreCase("A")) {
                    ansMCQList.add(mcqAnswerLists.get(i).getAnswer().toString());
                }

                CheckBox ch = new CheckBox(this);
                ch.setId(i);
                ch.setTextColor(Color.BLACK);
                ch.setOnCheckedChangeListener(this);
                ch.setText(mcqAnswerLists.get(i).getAnswer().toString());
                mcqview.addView(ch);
            }
            addView(viewVideo);
        }
        if (id.equalsIgnoreCase("2")) {
            View viewVideo = LayoutInflater.from(this).inflate(R.layout.custom_odaring_test_view, null);
            final DragLinearLayout dragLinearLayout = (DragLinearLayout) viewVideo.findViewById(R.id.odaring_container);
            TextView title = viewVideo.findViewById(R.id.tv_drag_title);
            title.setText(questionListsDetails.get(0).getQuestion().toString());
            for (int i = 0; i < orderingAnswerlists.size(); i++) {
                ansOderingList.add(orderingAnswerlists.get(i).getAnswer());
            }

            Collections.shuffle(orderingAnswerlists);

            for (int i = 0; i < orderingAnswerlists.size(); i++) {
                View child = LayoutInflater.from(this).inflate(R.layout.column_item, null);
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
                            View temp = (View) dragLinearLayout.getChildAt(i);
                            String str = temp.getTag().toString();
                            Log.e("oder", str);
                            tempOderingList.add(str);
                        } catch (Exception e) {
                            Log.e("error",e.getMessage());
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
                            View temp = (View) dragLinearLayout.getChildAt(i);
                            String str = temp.getTag().toString();
                            Log.e("oder", str);
                            tempOderingList.add(str);
                        } catch (Exception e) {
                            Log.e("error",e.getMessage());
                        }
                    }
                }
            });
            addView(viewVideo);
        }
        if (id.equalsIgnoreCase("3")) {

        }
        if (id.equalsIgnoreCase("4")) {
            View viewVideo = LayoutInflater.from(this).inflate(R.layout.custom_fillblank_test_view, null);
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
                TextView textView = new TextView(this);
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
                    Spinner spinner = new Spinner(this);
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    spinner.setLayoutParams(params2);

                    CustomArrayAdapter adapter = new CustomArrayAdapter(this,
                            R.layout.custom_spinner_item, spinnerArray);

                    // ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);

                    spinner.setAdapter(adapter);
                    spinners.add(spinner);
                    linearLayout.addView(spinner);

                }

            }
            addView(viewVideo);

        }
        if (id.equalsIgnoreCase("5")) {

            try {
                tempAnsMatchPair.clear();
                ansMatchPair.clear();
                View viewVideo = LayoutInflater.from(this).inflate(R.layout.custom_test_match_pair, null);
                TextView title = viewVideo.findViewById(R.id.tv_match_title);
                ll_top_layout = viewVideo.findViewById(R.id.ll_top_layout);
                final LinearLayout ll_left_layout = viewVideo.findViewById(R.id.ll_left_layout);
                listMatchGivenAns = viewVideo.findViewById(R.id.ll_match_view_list);


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
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


                title.setText(questionListsDetails.get(0).getQuestion().toString());
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

                addView(viewVideo);
            } catch (Exception e) {
                e.getMessage();
            }
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
            View childVideo = LayoutInflater.from(this).inflate(R.layout.single_match_pair_view, null);
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


    public void addView(View view) {
        try {
            linearLayoutContainer.removeAllViews();
        } catch (Exception e) {
            e.getMessage();
        }
        linearLayoutContainer.addView(view, 0);
    }


    @OnClick(R.id.iv_back_home_tstart)
    public void clickback() {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(CourseTestActivity.this);
        dialog.setContentView(R.layout.course_test_leave_dialog);
        dialog.setCancelable(false);
        dialog.setTitle("Custom Dialog");
        Button cancel = (Button) dialog.findViewById(R.id.btn_leave_cancel_dialog);
        Button leave = (Button) dialog.findViewById(R.id.btn_leave_dialog);
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                clearAllArray();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "leaved");
                preferenceManager.setKeyValueBoolean("testPass", false);
                passfail();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (isChecked) {
            tempMCQList.add(buttonView.getText().toString());
        } else {
            tempMCQList.remove(buttonView.getText().toString());
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

/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus){
            boolean isConnected = ConnectivityReceiver.isConnected();
            showSnack(isConnected);
        }
        super.onWindowFocusChanged(hasFocus);


    }
*/


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (isConnected) {


        } else {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
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

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

}


