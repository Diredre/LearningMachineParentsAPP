package com.example.learningmachineparentsapp;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocketService;
import com.xuexiang.xui.XUI;
import org.xutils.x;

public class MyApp extends Application {

    private String childId, parentid;
    private SharedPreferences sp;

    //开启webrtc service
    private MyWebSocketService.MyServiceBinder binder;
    public MyWebSocketService myWebSocketService;
    public MyWebSocketService getMyWebSocketService() {
        return myWebSocketService;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("TAG", "onServiceConnected: " );
            //服务与活动成功绑定
            binder = (MyWebSocketService.MyServiceBinder) iBinder;
            Log.e("TAG", "onServiceConnected: before binderservice" );
            myWebSocketService = binder.getService();
            Log.e("TAG", "onServiceConnected: after binderservice" );
            //todo 获取用户信息
//            SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(MainActivity.this);
//            User user = sharedPreferenceUtils.getUser();



        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);

        x.Ext.init(this);
        x.Ext.setDebug(true);

        startWebSocketService();
        Log.e("TAG", "onCreate: "+"dasdas" );
//        startWebSocketService();
//        ServiceConnection se = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                Log.e("TAG", "onServiceConnected: " );
//                binder = (MyWebSocketService.MyServiceBinder) service;
//                myWebSocketService = binder.getService();
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        };
    }

    public void startWebSocketService() {
        Intent i=  new Intent(getApplicationContext(),MyWebSocketService.class);
        getApplicationContext().startService(i);
        Intent bindIntent = new Intent(this, MyWebSocketService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void connect(){
        User user = new User();
        sp = getSharedPreferences("userInfo", 0);
        parentid = sp.getString("PARENTID", "15");
        childId = sp.getString("CHILDID", "1");

        Log.e("Parentid", sp.getString("PARENTID", "15"));
        Log.e("Childid", sp.getString("CHILDID", "1"));

        user.id=Integer.valueOf(parentid);
        user.childId=Integer.valueOf(childId);

        myWebSocketService.setMUser(user);
        Log.e("TAG", "onServiceConnected: before connect" );
        myWebSocketService.connectSocket();
        Log.e("TAG", "onServiceConnected: "+myWebSocketService);
//        startWebSocketService();
    }
}