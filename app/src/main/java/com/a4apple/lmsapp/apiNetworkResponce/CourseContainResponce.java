package com.a4apple.lmsapp.apiNetworkResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CourseContainResponce implements Serializable {



    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("BaseUrl")
    @Expose
    private String baseUrl;
    @SerializedName("CourseList")
    @Expose
    private List<CourseList> courseList = null;
    @SerializedName("CourseContent")
    @Expose
    private List<CourseContent> courseContent = null;

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

    public List<CourseList> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseList> courseList) {
        this.courseList = courseList;
    }

    public List<CourseContent> getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(List<CourseContent> courseContent) {
        this.courseContent = courseContent;
    }

    public class CourseList {

        @SerializedName("Course_Id")
        @Expose
        private Integer courseId;
        @SerializedName("Course_Name")
        @Expose
        private String courseName;
        @SerializedName("Cat_Id")
        @Expose
        private Integer catId;
        @SerializedName("Description")
        @Expose
        private Object description;
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
        @SerializedName("Active")
        @Expose
        private Object active;
        @SerializedName("Hide_From_Catalog")
        @Expose
        private Object hideFromCatalog;
        @SerializedName("Course_Code")
        @Expose
        private Object courseCode;
        @SerializedName("Price")
        @Expose
        private Object price;
        @SerializedName("Time_Limit")
        @Expose
        private Object timeLimit;
        @SerializedName("Certification")
        @Expose
        private Object certification;
        @SerializedName("Certification_Duration")
        @Expose
        private Object certificationDuration;
        @SerializedName("Image")
        @Expose
        private Object image;

        @SerializedName("CompleteOrder")
        @Expose
        private Object CompleteOrder;


        public Object getCompleteOrder() {
            return CompleteOrder;
        }

        public void setCompleteOrder(Object completeOrder) {
            CompleteOrder = completeOrder;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Integer getCatId() {
            return catId;
        }

        public void setCatId(Integer catId) {
            this.catId = catId;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
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

        public Object getActive() {
            return active;
        }

        public void setActive(Object active) {
            this.active = active;
        }

        public Object getHideFromCatalog() {
            return hideFromCatalog;
        }

        public void setHideFromCatalog(Object hideFromCatalog) {
            this.hideFromCatalog = hideFromCatalog;
        }

        public Object getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(Object courseCode) {
            this.courseCode = courseCode;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(Object timeLimit) {
            this.timeLimit = timeLimit;
        }

        public Object getCertification() {
            return certification;
        }

        public void setCertification(Object certification) {
            this.certification = certification;
        }

        public Object getCertificationDuration() {
            return certificationDuration;
        }

        public void setCertificationDuration(Object certificationDuration) {
            this.certificationDuration = certificationDuration;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }
    }

    public class CourseContent {

        @SerializedName("TryCount")
        @Expose
        private Integer TryCount;
        @SerializedName("Status")
        @Expose
        private Object status;

        @SerializedName("CourseContentId")
        @Expose
        private Integer courseContentId;
        @SerializedName("Course_Id")
        @Expose
        private Object courseId;
        @SerializedName("ContentCategory")
        @Expose
        private Integer contentCategory;
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


        public Integer getTryCount() {
            return TryCount;
        }

        public void setTryCount(Integer tryCount) {
            TryCount = tryCount;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Integer getCourseContentId() {
            return courseContentId;
        }

        public void setCourseContentId(Integer courseContentId) {
            this.courseContentId = courseContentId;
        }

        public Object getCourseId() {
            return courseId;
        }

        public void setCourseId(Object courseId) {
            this.courseId = courseId;
        }

        public Integer getContentCategory() {
            return contentCategory;
        }

        public void setContentCategory(Integer contentCategory) {
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
