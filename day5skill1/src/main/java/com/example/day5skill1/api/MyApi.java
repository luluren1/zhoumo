package com.example.day5skill1.api;

import com.example.day5skill1.bean.DemoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyApi {
    String BASE_URL ="http://www.qubaobei.com/";
    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<DemoBean> getData();
}
