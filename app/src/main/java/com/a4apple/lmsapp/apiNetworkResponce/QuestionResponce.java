package com.a4apple.lmsapp.apiNetworkResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QuestionResponce implements Serializable {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("BaseUrl")
    @Expose
    private String baseUrl;


    @SerializedName("QuestionType")
    @Expose
    private Object questionType;


    @SerializedName("QuestionList")
    @Expose
    private List<QuestionList> questionList = null;

    @SerializedName("MCQAnswerList")
    @Expose
    private List<MCQAnswerList> mCQAnswerList = null;

    @SerializedName("MatchingAnswerList")
    @Expose
    private List<MatchingAnswerList> matchingAnswerList = null;


    @SerializedName("OrderingAnswerlist")
    @Expose
    private List<OrderingAnswerlist> orderingAnswerlist = null;

    @SerializedName("KeywordsAnswerList")
    @Expose
    private List<KeywordsAnswerList> keywordsAnswerList = null;


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

    public Object getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Object questionType) {
        this.questionType = questionType;
    }

    public List<QuestionList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionList> questionList) {
        this.questionList = questionList;
    }

    public List<MCQAnswerList> getmCQAnswerList() {
        return mCQAnswerList;
    }

    public void setmCQAnswerList(List<MCQAnswerList> mCQAnswerList) {
        this.mCQAnswerList = mCQAnswerList;
    }

    public List<MatchingAnswerList> getMatchingAnswerList() {
        return matchingAnswerList;
    }

    public void setMatchingAnswerList(List<MatchingAnswerList> matchingAnswerList) {
        this.matchingAnswerList = matchingAnswerList;
    }

    public List<OrderingAnswerlist> getOrderingAnswerlist() {
        return orderingAnswerlist;
    }

    public void setOrderingAnswerlist(List<OrderingAnswerlist> orderingAnswerlist) {
        this.orderingAnswerlist = orderingAnswerlist;
    }

    public List<KeywordsAnswerList> getKeywordsAnswerList() {
        return keywordsAnswerList;
    }

    public void setKeywordsAnswerList(List<KeywordsAnswerList> keywordsAnswerList) {
        this.keywordsAnswerList = keywordsAnswerList;
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
    }

    public class MCQAnswerList {

        @SerializedName("MCQId")
        @Expose
        private Integer mCQId;
        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("Answer")
        @Expose
        private Object answer;
        @SerializedName("Correct")
        @Expose
        private Object correct;
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

        public Integer getmCQId() {
            return mCQId;
        }

        public void setmCQId(Integer mCQId) {
            this.mCQId = mCQId;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public Object getCorrect() {
            return correct;
        }

        public void setCorrect(Object correct) {
            this.correct = correct;
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
    }


    public class MatchingAnswerList {

        @SerializedName("MatchPairId")
        @Expose
        private Integer matchPairId;
        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("LeftSide")
        @Expose
        private String leftSide;
        @SerializedName("RightSide")
        @Expose
        private String rightSide;
        @SerializedName("Flag")
        @Expose
        private String flag;
        @SerializedName("CDate")
        @Expose
        private String cDate;
        @SerializedName("UDate")
        @Expose
        private Object uDate;
        @SerializedName("CUser")
        @Expose
        private Integer cUser;
        @SerializedName("UUser")
        @Expose
        private Object uUser;

        public Integer getMatchPairId() {
            return matchPairId;
        }

        public void setMatchPairId(Integer matchPairId) {
            this.matchPairId = matchPairId;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public String getLeftSide() {
            return leftSide;
        }

        public void setLeftSide(String leftSide) {
            this.leftSide = leftSide;
        }

        public String getRightSide() {
            return rightSide;
        }

        public void setRightSide(String rightSide) {
            this.rightSide = rightSide;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getcDate() {
            return cDate;
        }

        public void setcDate(String cDate) {
            this.cDate = cDate;
        }

        public Object getuDate() {
            return uDate;
        }

        public void setuDate(Object uDate) {
            this.uDate = uDate;
        }

        public Integer getcUser() {
            return cUser;
        }

        public void setcUser(Integer cUser) {
            this.cUser = cUser;
        }

        public Object getuUser() {
            return uUser;
        }

        public void setuUser(Object uUser) {
            this.uUser = uUser;
        }
    }

    public class OrderingAnswerlist {

        @SerializedName("OrderQue_id")
        @Expose
        private Integer orderQueId;
        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("Answer")
        @Expose
        private String answer;
        @SerializedName("Order_id")
        @Expose
        private Integer orderId;
        @SerializedName("Flag")
        @Expose
        private String flag;
        @SerializedName("CDate")
        @Expose
        private String cDate;
        @SerializedName("CUser")
        @Expose
        private Integer cUser;
        @SerializedName("UDate")
        @Expose
        private Object uDate;
        @SerializedName("UUser")
        @Expose
        private Object uUser;

        public Integer getOrderQueId() {
            return orderQueId;
        }

        public void setOrderQueId(Integer orderQueId) {
            this.orderQueId = orderQueId;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getcDate() {
            return cDate;
        }

        public void setcDate(String cDate) {
            this.cDate = cDate;
        }

        public Integer getcUser() {
            return cUser;
        }

        public void setcUser(Integer cUser) {
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
    }


    public class KeywordsAnswerList {

        @SerializedName("KeywordQue_id")
        @Expose
        private Integer keywordQueId;
        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("Answer")
        @Expose
        private Object answer;
        @SerializedName("Order_id")
        @Expose
        private Integer orderId;
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

        public Integer getKeywordQueId() {
            return keywordQueId;
        }

        public void setKeywordQueId(Integer keywordQueId) {
            this.keywordQueId = keywordQueId;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
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
    }
}
