package com.developeronrent.ferry.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2018-02-09.
 */

public class PrefsManager {

    SharedPreferences preferences;
    Context context;
    private static PrefsManager sInstance;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static PrefsManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PrefsManager(context);
        }
        return sInstance;
    }


    public PrefsManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("ferry", 0);
        editor = preferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public String getUserId() {
        return preferences.getString("user_id", "");
    }

    public void setUserId(String userId) {
        editor.putString("user_id", userId);
        editor.commit();
    }

    public String getPin() {
        return preferences.getString("pin", "");
    }

    public void setPin(String pin) {
        editor.putString("pin", pin);
        editor.commit();
    }

    public String getMobile() {
        return preferences.getString("mobile", "");
    }

    public void setMobile(String mobile) {
        editor.putString("mobile", mobile);
        editor.commit();
    }


    public String getToken() {
        return preferences.getString("token", "");
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }
    public String getPassengerAdult() {
        return preferences.getString("Adult", "");
    }

    public void setPassengerAdult(String Adult) {
        editor.putString("Adult", Adult);
        editor.commit();
    }
    public String getPassengerChild() {
        return preferences.getString("Child", "");
    }

    public void setPassengerChild(String Child) {
        editor.putString("Child", Child);
        editor.commit();
    }
    public String getPassengerInfant() {
        return preferences.getString("Infant", "");
    }

    public void setPassengerInfant(String Infant) {
        editor.putString("Infant", Infant);
        editor.commit();
    }


}