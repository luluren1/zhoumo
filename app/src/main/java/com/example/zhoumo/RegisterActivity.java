package com.example.zhoumo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhoumo.api.MyApi;
import com.example.zhoumo.model.ImpModel;
import com.example.zhoumo.presenter.ImpPresenter;
import com.example.zhoumo.view.Dview;

import org.json.JSONException;
import org.json.JSONObject;

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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Dview {

    /**
     * 用户名
     */
    private EditText mRegisterName;
    /**
     * 密码
     */
    private EditText mRegisterPwd;
    /**
     * 确认密码
     */
    private EditText mRegisterPwd2;
    /**
     * 手机号
     */
    private EditText mRegisterPhone;
    /**
     * 验证码
     */
    private EditText mRegisterVerify;
    /**
     * 注册
     */
    private Button mBtnRegister;
    private ImpPresenter impPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        impPresenter = new ImpPresenter(new ImpModel(), this);
        initView();
        initVerify();
    }

    private void initVerify() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.verifyUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyApi myApi = retrofit.create(MyApi.class);

        Observable<ResponseBody> verify = myApi.getVerify();

        verify.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            JSONObject jsonObject = new JSONObject(string);
                            String data = jsonObject.optString("data");
                            mRegisterVerify.setText(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mRegisterName = (EditText) findViewById(R.id.register_name);
        mRegisterPwd = (EditText) findViewById(R.id.register_pwd);
        mRegisterPwd2 = (EditText) findViewById(R.id.register_pwd2);
        mRegisterPhone = (EditText) findViewById(R.id.register_phone);
        mRegisterVerify = (EditText) findViewById(R.id.register_verify);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_register:
                String name = mRegisterName.getText().toString();
                String pwd = mRegisterPwd.getText().toString();
                String pwd2 = mRegisterPwd2.getText().toString();
                String phont = mRegisterPhone.getText().toString();
                String verify = mRegisterVerify.getText().toString();
                impPresenter.updataRegisterData(name, pwd, pwd2, phont, verify);
                break;
        }
    }

    private static final String TAG = "RegisterActivity";

    @Override
    public void updataSuccess(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.optInt("code");
            String ret = jsonObject.optString("ret");
            if (code == 200) {
                Toast.makeText(this, ret, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, ret, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updataFail(String error) {
        Log.i(TAG, "updataFail: " + error);
    }
}
