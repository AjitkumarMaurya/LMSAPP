package com.a4apple.lmsapp.apiNetworkResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginuserResponce implements Serializable {

    @SerializedName("Status")
    @Expose
    private Object status;


    @SerializedName("BaseUrl")
    @Expose
    private Object baseUrl;

    @SerializedName("EmployeeList")
    @Expose
    private List<UserDetailList> employeeList = null;

    public Object getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(Object baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public List<UserDetailList> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<UserDetailList> employeeList) {
        this.employeeList = employeeList;
    }


    public class UserDetailList {

        @SerializedName("User_Id")
        @Expose
        private Integer userId;
        @SerializedName("First_Name")
        @Expose
        private Object firstName;
        @SerializedName("Last_Name")
        @Expose
        private Object lastName;
        @SerializedName("Email")
        @Expose
        private Object email;
        @SerializedName("Username")
        @Expose
        private Object username;
        @SerializedName("Password")
        @Expose
        private Object password;
        @SerializedName("Profile_Pic")
        @Expose
        private Object profilePic;
        @SerializedName("Bio")
        @Expose
        private Object bio;
        @SerializedName("U_Type_Id")
        @Expose
        private Object uTypeId;

        @SerializedName("Time_Zone")
        @Expose
        private Object timeZone;
        @SerializedName("CDate")
        @Expose
        private Object cDate;
        @SerializedName("UDate")
        @Expose
        private Object uDate;
        @SerializedName("Flag")
        @Expose
        private Object flag;
        @SerializedName("CUser")
        @Expose
        private Object cUser;
        @SerializedName("UUser")
        @Expose
        private Object uUser;

        @SerializedName("Credit")
        @Expose
        private Object credit;


        @SerializedName("IncompleteCourse")
        @Expose
        private Object IncompeleteCourse;
        @SerializedName("CompleteCourse")
        @Expose
        private Object CompleteCourse;
        @SerializedName("Certificate")
        @Expose
        private Object Certficate;

        @SerializedName("Last_Login")
        @Expose
        private Object Last_Login;



        @SerializedName("LastLoginDate")
        @Expose
        private Object lastdate;
        @SerializedName("LastLoginTime")
        @Expose
        private Object lasttime;

        public Object getLastdate() {
            return lastdate;
        }

        public void setLastdate(Object lastdate) {
            this.lastdate = lastdate;
        }

        public Object getLasttime() {
            return lasttime;
        }

        public void setLasttime(Object lasttime) {
            this.lasttime = lasttime;
        }

        public Object getIncompeleteCourse() {
            return IncompeleteCourse;
        }

        public void setIncompeleteCourse(Object incompeleteCourse) {
            IncompeleteCourse = incompeleteCourse;
        }

        public Object getCompleteCourse() {
            return CompleteCourse;
        }

        public void setCompleteCourse(Object completeCourse) {
            CompleteCourse = completeCourse;
        }

        public Object getCertficate() {
            return Certficate;
        }

        public void setCertficate(Object certficate) {
            Certficate = certficate;
        }

        public Object getLast_Login() {
            return Last_Login;
        }

        public void setLast_Login(Object last_Login) {
            Last_Login = last_Login;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getFirstName() {
            return firstName;
        }

        public void setFirstName(Object firstName) {
            this.firstName = firstName;
        }

        public Object getLastName() {
            return lastName;
        }

        public void setLastName(Object lastName) {
            this.lastName = lastName;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(Object profilePic) {
            this.profilePic = profilePic;
        }

        public Object getBio() {
            return bio;
        }

        public void setBio(Object bio) {
            this.bio = bio;
        }

        public Object getuTypeId() {
            return uTypeId;
        }

        public void setuTypeId(Object uTypeId) {
            this.uTypeId = uTypeId;
        }

        public Object getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(Object timeZone) {
            this.timeZone = timeZone;
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

        public Object getFlag() {
            return flag;
        }

        public void setFlag(Object flag) {
            this.flag = flag;
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

        public Object getCredit() {
            return credit;
        }

        public void setCredit(Object credit) {
            this.credit = credit;
        }
    }
}
