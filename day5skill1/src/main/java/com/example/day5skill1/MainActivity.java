package com.example.day5skill1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.day5skill1.adapter.RecyAdapter;
import com.example.day5skill1.api.MyApi;
import com.example.day5skill1.bean.DemoBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecy;
    /**
     * 操作
     */
    private Button mOperate;
    /**
     * 删除
     */
    private Button mDelete;
    /**
     * 完成
     */
    private Button mComplete;
    private ArrayList<DemoBean.DataBean> list;
    private RecyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //陆艳红
    }

    private static final String TAG = "MainActivity";

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyApi myApi = retrofit.create(MyApi.class);

        Observable<DemoBean> data = myApi.getData();

        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DemoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DemoBean demoBean) {
                        list.addAll(demoBean.getData());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        mRecy = (RecyclerView) findViewById(R.id.recy);
        mOperate = (Button) findViewById(R.id.operate);
        mOperate.setOnClickListener(this);
        mDelete = (Button) findViewById(R.id.delete);
        mDelete.setOnClickListener(this);
        mComplete = (Button) findViewById(R.id.complete);
        mComplete.setOnClickListener(this);

        mRecy.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        adapter = new RecyAdapter(this, list);

        mRecy.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.operate:
                adapter.che = true;
                adapter.notifyDataSetChanged();
                break;
            case R.id.delete:
                for (int i = 0; i < list.size(); i++) {
                    boolean visiable = list.get(i).isVisiable();
                    if (visiable) {
                        list.remove(i);
                        i--;

                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.complete:
                adapter.che = false;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
