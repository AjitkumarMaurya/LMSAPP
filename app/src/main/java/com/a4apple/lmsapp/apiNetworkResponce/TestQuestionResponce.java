package com.a4apple.lmsapp.apiNetworkResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestQuestionResponce implements Serializable {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("BaseUrl")
    @Expose
    private String baseUrl;
    @SerializedName("TestDetails")
    @Expose
    private List<TestDetail> testDetails = null;
    @SerializedName("QuestionList")
    @Expose
    private List<QuestionList> questionList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<TestDetail> getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(List<TestDetail> testDetails) {
        this.testDetails = testDetails;
    }

    public List<QuestionList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionList> questionList) {
        this.questionList = questionList;
    }

    public class QuestionList {

        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("Question")
        @Expose
        private Object question;
        @SerializedName("Question_Type")
        @Expose
        private Integer questionType;
        @SerializedName("Course_Id")
        @Expose
        private Integer courseId;
        @SerializedName("Test_id")
        @Expose
        private Integer testId;
        @SerializedName("Questionweight")
        @Expose
        private Object questionweight;
        @SerializedName("QuestionIndex")
        @Expose
        private Object questionIndex;
        @SerializedName("Flag")
        @Expose
        private Object flag;
        @SerializedName("CDate")
        @Expose
        private Object cDate;
        @SerializedName("CUser")
        @Expose
        private Object cUser;
        @SerializedName("UDate")
        @Expose
        private Object uDate;
        @SerializedName("UUser")
        @Expose
        private Object uUser;
        @SerializedName("Questiontype")
        @Expose
        private Object questiontype;
        @SerializedName("QuestionCategory")
        @Expose
        private Object questionCategory;

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public Object getQuestion() {
            return question;
        }

        public void setQuestion(Object question) {
            this.question = question;
        }

        public Integer getQuestionType() {
            return questionType;
        }

        public void setQuestionType(Integer questionType) {
            this.questionType = questionType;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public Integer getTestId() {
            return testId;
        }

        public void setTestId(Integer testId) {
            this.testId = testId;
        }

        public Object getQuestionweight() {
            return questionweight;
        }

        public void setQuestionweight(Object questionweight) {
            this.questionweight = questionweight;
        }

        public Object getQuestionIndex() {
            return questionIndex;
        }

        public void setQuestionIndex(Object questionIndex) {
            this.questionIndex = questionIndex;
        }

        public Object getFlag() {
            return flag;
        }

        public void setFlag(Object flag) {
            this.flag = flag;
        }

        public Object getcDate() {
            return cDate;
        }

        public void setcDate(Object cDate) {
            this.cDate = cDate;
        }

        public Object getcUser() {
            return cUser;
        }

        public void setcUser(Object cUser) {
            this.cUser = cUser;
        }

        public Object getuDate() {
            return uDate;
        }

        public void setuDate(Object uDate) {
            this.uDate = uDate;
        }

        public Object getuUser() {
            return uUser;
        }

        public void setuUser(Object uUser) {
            this.uUser = uUser;
        }

        public Object getQuestiontype() {
            return questiontype;
        }

        public void setQuestiontype(Object questiontype) {
            this.questiontype = questiontype;
        }

        public Object getQuestionCategory() {
            return questionCategory;
        }

        public void setQuestionCategory(Object questionCategory) {
            this.questionCategory = questionCategory;
        }
    }


    public class TestDetail {

        @SerializedName("TestOptionId")
        @Expose
        private Integer testOptionId;
        @SerializedName("Test_Id")
        @Expose
        private Integer testId;
        @SerializedName("Duration")
        @Expose
        private Object duration;
        @SerializedName("Passing_score")
        @Expose
        private Object passingScore;
        @SerializedName("Random_question_order")
        @Expose
        private Object randomQuestionOrder;
        @SerializedName("Shuffle_answers")
        @Expose
        private Object shuffleAnswers;
        @SerializedName("Allow_try_if_failed")
        @Expose
        private Object allowTryIfFailed;
        @SerializedName("Times_of_retry")
        @Expose
        private Object timesOfRetry;
        @SerializedName("Show_correct_answers")
        @Expose
        private Object showCorrectAnswers;
        @SerializedName("Show_users_answers")
        @Expose
        private Object showUsersAnswers;
        @SerializedName("Message_when_passed")
        @Expose
        private Object messageWhenPassed;
        @SerializedName("Message_when_failed")
        @Expose
        private Object messageWhenFailed;
        @SerializedName("Flag")
        @Expose
        private Object flag;
        @SerializedName("CDate")
        @Expose
        private Object cDate;
        @SerializedName("UDate")
        @Expose
        private Object uDate;
        @SerializedName("CUser")
        @Expose
        private Object cUser;
        @SerializedName("UUser")
        @Expose
        private Object uUser;
        @SerializedName("CourseContentId")
        @Expose
        private Integer courseContentId;
        @SerializedName("Course_Id")
        @Expose
        private Integer courseId;
        @SerializedName("ContentCategory")
        @Expose
        private Object contentCategory;
        @SerializedName("ContentTitle")
        @Expose
        private Object contentTitle;
        @SerializedName("ContentClass")
        @Expose
        private Object contentClass;
        @SerializedName("ContentIndex")
        @Expose
        private Object contentIndex;
        @SerializedName("CompletionType")
        @Expose
        private Object completionType;
        @SerializedName("CompletionValue")
        @Expose
        private Integer completionValue;
        @SerializedName("ContentDetail")
        @Expose
        private Object contentDetail;
        @SerializedName("Flag1")
        @Expose
        private Object flag1;
        @SerializedName("CDate1")
        @Expose
        private Object cDate1;
        @SerializedName("CUser1")
        @Expose
        private Object cUser1;
        @SerializedName("UDate1")
        @Expose
        private Object uDate1;
        @SerializedName("UUser1")
        @Expose
        private Object uUser1;

        @SerializedName("Pass")
        @Expose
        private Object Pass;

        @SerializedName("time_used")
        @Expose
        private Object time_used;

        @SerializedName("completed_date")
        @Expose
        private Object completed_date;

        @SerializedName("score")
        @Expose
        private Object score;

        public Object getTime_used() {
            return time_used;
        }

        public void setTime_used(Object time_used) {
            this.time_used = time_used;
        }

        public Object getCompleted_date() {
            return completed_date;
        }

        public void setCompleted_date(Object completed_date) {
            this.completed_date = completed_date;
        }

        public Object getScore() {
            return score;
        }

        public void setScore(Object score) {
            this.score = score;
        }

        public Object getPass() {
            return Pass;
        }

        public void setPass(Object pass) {
            Pass = pass;
        }

        public Integer getTestOptionId() {
            return testOptionId;
        }

        public void setTestOptionId(Integer testOptionId) {
            this.testOptionId = testOptionId;
        }

        public Integer getTestId() {
            return testId;
        }

        public void setTestId(Integer testId) {
            this.testId = testId;
        }

        public Object getDuration() {
            return duration;
        }

        public void setDuration(Object duration) {
            this.duration = duration;
        }

        public Object getPassingScore() {
            return passingScore;
        }

        public void setPassingScore(Object passingScore) {
            this.passingScore = passingScore;
        }

        public Object getRandomQuestionOrder() {
            return randomQuestionOrder;
        }

        public void setRandomQuestionOrder(Object randomQuestionOrder) {
            this.randomQuestionOrder = randomQuestionOrder;
        }

        public Object getShuffleAnswers() {
            return shuffleAnswers;
        }

        public void setShuffleAnswers(Object shuffleAnswers) {
            this.shuffleAnswers = shuffleAnswers;
        }

        public Object getAllowTryIfFailed() {
            return allowTryIfFailed;
        }

        public void setAllowTryIfFailed(Object allowTryIfFailed) {
            this.allowTryIfFailed = allowTryIfFailed;
        }

        public Object getTimesOfRetry() {
            return timesOfRetry;
        }

        public void setTimesOfRetry(Object timesOfRetry) {
            this.timesOfRetry = timesOfRetry;
        }

        public Object getShowCorrectAnswers() {
            return showCorrectAnswers;
        }

        public void setShowCorrectAnswers(Object showCorrectAnswers) {
            this.showCorrectAnswers = showCorrectAnswers;
        }

        public Object getShowUsersAnswers() {
            return showUsersAnswers;
        }

        public void setShowUsersAnswers(Object showUsersAnswers) {
            this.showUsersAnswers = showUsersAnswers;
        }

        public Object getMessageWhenPassed() {
            return messageWhenPassed;
        }

        public void setMessageWhenPassed(Object messageWhenPassed) {
            this.messageWhenPassed = messageWhenPassed;
        }

        public Object getMessageWhenFailed() {
            return messageWhenFailed;
        }

        public void setMessageWhenFailed(Object messageWhenFailed) {
            this.messageWhenFailed = messageWhenFailed;
        }

        public Object getFlag() {
            return flag;
        }

        public void setFlag(Object flag) {
            this.flag = flag;
        }

        public Object getcDate() {
            return cDate;
        }

        public void setcDate(Object cDate) {
            this.cDate = cDate;
        }

        public Object getuDate() {
            return uDate;
        }

        public void setuDate(Object uDate) {
            this.uDate = uDate;
        }

        public Object getcUser() {
            return cUser;
        }

        public void setcUser(Object cUser) {
            this.cUser = cUser;
        }

        public Object getuUser() {
            return uUser;
        }

        public void setuUser(Object uUser) {
            this.uUser = uUser;
        }

        public Integer getCourseContentId() {
            return courseContentId;
        }

        public void setCourseContentId(Integer courseContentId) {
            this.courseContentId = courseContentId;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public Object getContentCategory() {
            return contentCategory;
        }

        public void setContentCategory(Object contentCategory) {
            this.contentCategory = contentCategory;
        }

        public Object getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(Object contentTitle) {
            this.contentTitle = contentTitle;
        }

        public Object getContentClass() {
            return contentClass;
        }

        public void setContentClass(Object contentClass) {
            this.contentClass = contentClass;
        }

        public Object getContentIndex() {
            return contentIndex;
        }

        public void setContentIndex(Object contentIndex) {
            this.contentIndex = contentIndex;
        }

        public Object getCompletionType() {
            return completionType;
        }

        public void setCompletionType(Object completionType) {
            this.completionType = completionType;
        }

        public Integer getCompletionValue() {
            return completionValue;
        }

        public void setCompletionValue(Integer completionValue) {
            this.completionValue = completionValue;
        }

        public Object getContentDetail() {
            return contentDetail;
        }

        public void setContentDetail(Object contentDetail) {
            this.contentDetail = contentDetail;
        }

        public Object getFlag1() {
            return flag1;
        }

        public void setFlag1(Object flag1) {
            this.flag1 = flag1;
        }

        public Object getcDate1() {
            return cDate1;
        }

        public void setcDate1(Object cDate1) {
            this.cDate1 = cDate1;
        }

        public Object getcUser1() {
            return cUser1;
        }

        public void setcUser1(Object cUser1) {
            this.cUser1 = cUser1;
        }

        public Object getuDate1() {
            return uDate1;
        }

        public void setuDate1(Object uDate1) {
            this.uDate1 = uDate1;
        }

        public Object getuUser1() {
            return uUser1;
        }

        public void setuUser1(Object uUser1) {
            this.uUser1 = uUser1;
        }
    }

    }
