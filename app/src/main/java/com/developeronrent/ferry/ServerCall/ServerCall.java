package com.developeronrent.ferry.ServerCall;

import android.content.Context;

import com.developeronrent.ferry.Utils.CommonConstant;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018-01-04.
 */

public class ServerCall {

    private static ServerCall sInstance;

    private static APIServices REST_CLIENT;
    private Context context;
    //==============================================================================================
    private ServerCall(Context context) {
        this.context = context;
        init();
    }

    //==============================================================================================
    public static ServerCall getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ServerCall(context);
        }
        return sInstance;
    }

    //==============================================================================================
    public static APIServices get() {
        return REST_CLIENT;
    }

    //==============================================================================================
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CommonConstant.API_ROOT)
                .client(getClient())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(getClient())
                .build();
        REST_CLIENT = retrofit.create(APIServices.class);
    }

    //==============================================================================================
    private static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return client;
    }
    //==============================================================================================
}
