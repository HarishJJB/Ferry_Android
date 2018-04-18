package com.developeronrent.ferry.ServerCall;



import com.developeronrent.ferry.Response.LoginResponse;
import com.developeronrent.ferry.Response.OTPResponse;
import com.developeronrent.ferry.Response.RegisterResponse;
import com.developeronrent.ferry.Utils.CommonConstant;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 2018-01-04.
 */

public interface APIServices {


    /*Registration or Login*/
    @FormUrlEncoded
    @POST(CommonConstant.REGISTER_API)
    Call<RegisterResponse> Register(@Field("email") String email, @Field("mobile_no") String mobile_no,
                                    @Field("password") String password, @Field("device_type") String device,
                                    @Field("fcm_id") String fcm);

    @FormUrlEncoded
    @POST(CommonConstant.LOGIN_API)
    Call<LoginResponse> Login(@Field("mobile_no") String number, @Field("password") String password,
                              @Field("fcm_id") String fcm_id);


    /*OTP*/

    @FormUrlEncoded
    @POST(CommonConstant.OTP_API)
    Call<OTPResponse> OTP(@Field("mobile_no") String mobile_no, @Field("password") String password, @Field("otp") String otp);

    /*Resend OTP*/

    @FormUrlEncoded
    @POST(CommonConstant.RESEND_OTP_API)
    Call<LoginResponse> Resend_OTP(@Field("mobile_no") String mobile_no);



}
