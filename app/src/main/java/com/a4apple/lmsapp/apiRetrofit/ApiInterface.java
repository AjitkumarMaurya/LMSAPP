package com.a4apple.lmsapp.apiRetrofit;

import com.a4apple.lmsapp.apiNetworkResponce.AllMyCourseResponce;
import com.a4apple.lmsapp.apiNetworkResponce.CommonResponce;
import com.a4apple.lmsapp.apiNetworkResponce.CourseContainResponce;
import com.a4apple.lmsapp.apiNetworkResponce.CourseEnrollResponce;
import com.a4apple.lmsapp.apiNetworkResponce.LoginuserResponce;
import com.a4apple.lmsapp.apiNetworkResponce.QuestionResponce;
import com.a4apple.lmsapp.apiNetworkResponce.TestQuestionResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by ajit on 04-01-2018.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST(AppConfig.LOGIN)
    Call<LoginuserResponce> userLogin(@Field("UserName") String username,
                                      @Field("Password") String password);

    @FormUrlEncoded
    @POST(AppConfig.ALLCOURSE)
    Call<AllMyCourseResponce> allCourseList(@Field("UserId") String userId, @Field("SearchName") String searchname);

    @FormUrlEncoded
    @POST(AppConfig.MYCOURSE)
    Call<AllMyCourseResponce> allMyCourseList(@Field("UserId") String userId, @Field("SearchName") String searchname);


    @FormUrlEncoded
    @POST(AppConfig.ENROLLCOURSE)
    Call<CourseEnrollResponce> enrollcourse(@Field("UserId")String userId,@Field("CourseId")String courseId);

    @FormUrlEncoded
    @POST(AppConfig.COURSEDETAILS)
    Call<CourseContainResponce> coursedetails(@Field("CourseId")String courseId,@Field("User_id") String userId);



    @FormUrlEncoded
    @POST(AppConfig.COURSEDETAILSQUESTIONSELECTEDBYID)
    Call<QuestionResponce> questiondetails(@Field("QuestionId")String questionId);

    @FormUrlEncoded
    @POST(AppConfig.COURSEDETAILSTESTQUESTIONSELECTEDBYID)
    Call<TestQuestionResponce> testquestiondetails(@Field("Test_Id")String questionId ,@Field("user_id")String userId);


    @FormUrlEncoded
    @POST(AppConfig.COURSEUPDATESTATUS)
    Call<CommonResponce> updatecoursestatus(@Field("Unit_Id")String unitId,@Field("User_Id")String userId,@Field("Course_Id")String courseId);

    @FormUrlEncoded
    @POST(AppConfig.USERDETAILSUPDATE)
    Call<LoginuserResponce> userDetailUpdate(@Field("User_Id") String userId);


    @FormUrlEncoded
    @POST(AppConfig.TESTRESULTSUBMIT)
    Call<CommonResponce> addTestResult(@Field("User_Id") String User_Id,@Field("Test_id")String Test_id,@Field("Timeused") String Timeused,@Field("score") String score,@Field("Pass") String pass);


}
