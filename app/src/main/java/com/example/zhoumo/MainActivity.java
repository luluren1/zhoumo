package com.example.zhoumo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhoumo.model.ImpModel;
import com.example.zhoumo.presenter.ImpPresenter;
import com.example.zhoumo.view.Dview;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Dview {

    private EditText mLoginName;
    private EditText mLoginPwd;
    /**
     * 登录
     */
    private Button mLogin;
    /**
     * 注册
     */
    private Button mRegister;
    private ImpPresenter impPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        impPresenter = new ImpPresenter(new ImpModel(), this);
        //陆艳红
    }


    private void initView() {
        mLoginName = (EditText) findViewById(R.id.login_name);
        mLoginPwd = (EditText) findViewById(R.id.login_pwd);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login:
                String name = mLoginName.getText().toString();
                String pwd = mLoginPwd.getText().toString();
                impPresenter.updataLoginData(name,pwd);

                break;
            case R.id.register:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
        }
    }

    private static final String TAG = "MainActivity";

    @Override
    public void updataSuccess(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.optInt("code");
            String ret = jsonObject.optString("ret");
            if (code == 200) {
                Toast.makeText(this, ret, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main3Activity.class));
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
