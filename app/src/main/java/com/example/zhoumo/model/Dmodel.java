package com.example.zhoumo.model;

import com.example.zhoumo.callback.DcallBack;

public interface Dmodel {
    void updataLoginData(DcallBack dcallBack,String name,String pass);
    void updataRegisterData(DcallBack dcallBack,String name,String pass,String phone,String verify);
}
