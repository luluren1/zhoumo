package com.example.zhoumo.presenter;

import com.example.zhoumo.callback.DcallBack;

public interface Dpresenter {
    void updataLoginData( String name, String pass);
    void updataRegisterData(String name,String pass,String pass2,String phone,String verify);
}
