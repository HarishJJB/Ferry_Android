package com.developeronrent.ferry.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dell on 4/18/2018.
 */

public class CommonConstant {

    public static String API_ROOT = "http://35.154.164.222/ferryapp/public/";
    public static final String REGISTER_API = "api/auth/register";
    public static final String LOGIN_API = "api/auth/login";
    public static final String OTP_API = "api/auth/verify_otp";
    public static final String RESEND_OTP_API = "api/auth/resend_otp";


    public static final int GOOGLE_REQUEST_CODE = 9001;
    public static String social_ID = "social_ID";
    public static String GREY_REQUEST = "0";

    public static String OTP_FOR = "";
    public static String SMS_ORIGIN = "DEVRNT";
    public static String OTP_DELIMITER = ":";
    public static String REG_OTP_NO = "";


    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
