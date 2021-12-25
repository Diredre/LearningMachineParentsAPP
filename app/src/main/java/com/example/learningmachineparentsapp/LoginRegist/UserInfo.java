package com.example.learningmachineparentsapp.LoginRegist;

public class UserInfo {

    private String phone;       //手机号码
    private String password;    //密码
    private String iconid;      //头像图片地址url
    private String name;        //用户名
    private int id;             //家长码

    public UserInfo(String phone, String password, String iconid, String name) {
        this.phone = phone;
        this.password = password;
        this.iconid = iconid;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
