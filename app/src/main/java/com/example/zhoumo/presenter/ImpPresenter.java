package com.example.zhoumo.presenter;

import com.example.zhoumo.callback.DcallBack;
import com.example.zhoumo.model.ImpModel;
import com.example.zhoumo.view.Dview;

public class ImpPresenter implements Dpresenter {
    private ImpModel model;
    private Dview dview;

    public ImpPresenter(ImpModel model, Dview dview) {
        this.model = model;
        this.dview = dview;
    }

    @Override
    public void updataLoginData(String name, String pass) {
        model.updataLoginData(new DcallBack() {
            @Override
            public void updataSuccess(String result) {
                dview.updataSuccess(result);
            }

            @Override
            public void updataFail(String error) {
                dview.updataFail(error);
            }
        }, name, pass);
    }

    @Override
    public void updataRegisterData(String name, String pass, String pass2, String phone, String verify) {
        model.updataRegisterData(new DcallBack() {
            @Override
            public void updataSuccess(String result) {

                dview.updataSuccess(result);
            }

            @Override
            public void updataFail(String error) {

                dview.updataFail(error);
            }
        }, name, pass, phone, verify);
    }
}
