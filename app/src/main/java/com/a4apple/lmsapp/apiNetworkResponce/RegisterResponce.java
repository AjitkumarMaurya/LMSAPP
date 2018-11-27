package com.a4apple.lmsapp.apiNetworkResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterResponce implements Serializable{

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("user_id")
    @Expose
    Object user_id;

    @SerializedName("name")
    @Expose
    Object name;


    @SerializedName("password")
    @Expose
    Object password;

    @SerializedName("propic")
    @Expose
    Object propic;

    @SerializedName("email")
    @Expose
    Object email;

    @SerializedName("mobile")
    @Expose
    Object mobile;

    @SerializedName("fb_id")
    @Expose
    Object fb_id;

    @SerializedName("g_id")
    @Expose
    Object g_id;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("status")
    @Expose
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getUser_id() {
        return user_id;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getPropic() {
        return propic;
    }

    public void setPropic(Object propic) {
        this.propic = propic;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getFb_id() {
        return fb_id;
    }

    public void setFb_id(Object fb_id) {
        this.fb_id = fb_id;
    }

    public Object getG_id() {
        return g_id;
    }

    public void setG_id(Object g_id) {
        this.g_id = g_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
