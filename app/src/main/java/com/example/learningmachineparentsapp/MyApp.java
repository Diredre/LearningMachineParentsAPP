package com.example.learningmachineparentsapp;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocketService;
import com.xuexiang.xui.XUI;
import org.xutils.x;

public class MyApp extends Application {
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
            User user = new User();
            user.id=13;
            user.childId=1051;
            myWebSocketService.setMUser(user);
            Log.e("TAG", "onServiceConnected: before connect" );
            myWebSocketService.connectSocket();
            Log.e("TAG", "onServiceConnected: "+myWebSocketService);

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
    }

    private void startWebSocketService() {
        Intent i=  new Intent(getApplicationContext(),MyWebSocketService.class);
        getApplicationContext().startService(i);
        Intent bindIntent = new Intent(this, MyWebSocketService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }
}
