package com.example.zhoumo.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApi {
    String loginUrl = "http://yun918.cn/";

    @FormUrlEncoded
    @POST("study/public/index.php/login")
    Observable<ResponseBody> getLogin(@Field("@username") String name, @Field("password") String pass);

    String registerUrl = "http://yun918.cn/";
    @FormUrlEncoded
    @POST("study/public/index.php/register")
    Observable<ResponseBody> getRegister(@Field("username")String name,@Field("password") String pass,@Field("phone") String phone,@Field("verify") String verify);

    String verifyUrl ="http://yun918.cn/";
    @GET("study/public/index.php/verify")
    Observable<ResponseBody> getVerify();
}
