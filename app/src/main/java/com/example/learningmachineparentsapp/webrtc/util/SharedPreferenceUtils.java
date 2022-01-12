package com.example.learningmachineparentsapp.webrtc.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author cute xyy biu~biu~
 * created on 2021-03-15
 * fun: 用于存储和获取本地数据
 */
public class SharedPreferenceUtils {
    Activity myActivity;
    public SharedPreferenceUtils(Activity activity){
        myActivity = activity;
    }

    /**
     * 保存用户信息到本地
     * @param jsonUser 用户信息的json字符串
     */
    public void saveUser(String jsonUser){
        SharedPreferences preferences = myActivity.getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("jsonUser",jsonUser);
        editor.commit();
    }

    /**
     * 取出用户信息
     * @return
     */
    public User getUser(){
        User user = null;
        SharedPreferences preferences = myActivity.getSharedPreferences("user",MODE_PRIVATE);
        String jsonUser = preferences.getString("jsonUser", "");
        if(jsonUser != "" || jsonUser != null){
            Gson gson = new Gson();
            user = gson.fromJson(jsonUser, User.class);
        }
        return user;
    }

    /**
     * 清除用户信息
     */
    public void logout(){
        SharedPreferences preferences = myActivity.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
