package com.a4apple.lmsapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ajit on 22/3/17.
 */

public class PreferenceManager {

    public static PreferenceManager preferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static PreferenceManager getInstance() {
        return preferenceManager;
    }

    public PreferenceManager(Context context) {
        preferenceManager = this;
        try {
            mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
    }

    public void removePref(Context context, String keyToRemove) {
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(keyToRemove);
        mEditor.commit();
    }

    /*set preference for registration*/

    public String getRegisteredUserId() {
        String strUserId = mSharedPreferences.getString(VariableBag.USER_ID, "1");
        return strUserId;
    }

    public void setRegisteredUserId(String strUserId) {
        mEditor.putString(VariableBag.USER_ID, strUserId).commit();
    }

    public void setLoginSession() {
        mEditor.putBoolean(VariableBag.LOGIN, true);
    }

    public boolean getLoginSession() {
        boolean login = mSharedPreferences.getBoolean(VariableBag.LOGIN, false);
        return login;
    }

    public void setWiFiSession(boolean wiFiSession) {
        mEditor.putBoolean("wifi", wiFiSession).commit();
    }

    public boolean getWiFiSession() {
        boolean login = mSharedPreferences.getBoolean("wifi", false);
        return login;
    }


    public void setKeyValueString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public void setKeyValueInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void setKeyValueBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }




    public void setKeyValueIntForCourseIndex(String key, int value) {
        mEditor.putInt(key, value).commit();
    }



    public int getKeyValueIntForCourseIndex(String key) {
        int getValue = mSharedPreferences.getInt(key, 1);
        return getValue;
    }



    public String getKeyValueString(String key) {
        String getValue = mSharedPreferences.getString(key, "Not Mentioned");
        return getValue;
    }

    public int getKeyValueInt(String key) {
        int getValue = mSharedPreferences.getInt(key, 0);
        return getValue;
    }

    public boolean getKeyValueBoolean(String key) {
        boolean getValue = mSharedPreferences.getBoolean(key, false);
        return getValue;
    }
}


