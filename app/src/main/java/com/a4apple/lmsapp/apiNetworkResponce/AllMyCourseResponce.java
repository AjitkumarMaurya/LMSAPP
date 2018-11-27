package com.a4apple.lmsapp.apiNetworkResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllMyCourseResponce implements Serializable {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("BaseUrl")
    @Expose
    private String baseUrl;
    @SerializedName("CourseList")
    @Expose
    private List<MyCourseList> courseList = null;

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

    public List<MyCourseList> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<MyCourseList> courseList) {
        this.courseList = courseList;
    }

    public class MyCourseList {


        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("Course_Id")
        @Expose
        private int courseId;
        @SerializedName("Course_Name")
        @Expose
        private Object courseName;
        @SerializedName("Cat_Id")
        @Expose
        private Object catId;
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

        @SerializedName("Category_name")
        @Expose
        private Object catName;

        public Object getCatName() {
            return catName;
        }

        public void setCatName(Object catName) {
            this.catName = catName;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public Object getCourseName() {
            return courseName;
        }

        public void setCourseName(Object courseName) {
            this.courseName = courseName;
        }

        public Object getCatId() {
            return catId;
        }

        public void setCatId(Object catId) {
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

}
