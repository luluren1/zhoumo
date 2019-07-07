package com.example.zhoumo.model;

import android.util.Log;

import com.example.zhoumo.api.MyApi;
import com.example.zhoumo.callback.DcallBack;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpModel implements Dmodel {
    private static final String TAG = "ImpModel";

    @Override
    public void updataLoginData(final DcallBack dcallBack, String name, String pass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.loginUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyApi myApi = retrofit.create(MyApi.class);

        Observable<ResponseBody> login = myApi.getLogin(name, pass);

        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: " + d);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext: " + string);
                            dcallBack.updataSuccess(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dcallBack.updataFail("请求错误" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updataRegisterData(final DcallBack dcallBack, String name, String pass, String phone, String verify) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.registerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApi myApi = retrofit.create(MyApi.class);

        Observable<ResponseBody> register = myApi.getRegister(name, pass, phone, verify);

        register.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            dcallBack.updataSuccess(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dcallBack.updataFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
