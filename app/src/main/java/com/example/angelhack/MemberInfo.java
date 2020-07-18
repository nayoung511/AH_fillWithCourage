package com.example.angelhack;

import android.widget.RadioButton;

public class MemberInfo {
    private String name;
    private String phone;
    private String usertype;

    public MemberInfo(String name, String phone, String usertype){
        this.name = name;
        this.phone = phone;
        this.usertype = usertype;
    }


    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getUsertype(){return this.usertype;}
    public void setUsertype(String usertype){this.usertype = usertype;}
}
