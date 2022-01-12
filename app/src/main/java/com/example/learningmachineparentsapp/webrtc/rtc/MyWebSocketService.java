package com.example.learningmachineparentsapp.webrtc.rtc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learningmachineparentsapp.webrtc.common.Constant;
import com.example.learningmachineparentsapp.webrtc.monitor.MonitorActivity;
import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.example.learningmachineparentsapp.webrtc.video_call.VideoCallActivity;


/**
 * @author cute xyy biu~biu~
 * created on 2021-12-06
 * fun:
 */
public class MyWebSocketService extends Service {
    private static final String TAG = "MyWebSocketService";
    private static final int ON_CALL = 1;
    private static final int CALLING = 2;
    private static final int NO_RESPONSE = 3;
    private static final int ON_REFUSE_CALL = 4;
    private static final int ON_PICK_UP = 5;
    private static final int ON_BUSYING = 6;
    private static final int ON_HANG_UP = 7;
    private static final int ON_BUSYING_MONITOR = 8;
    private static final int ON_MONITOR_SUCCESS = 9;
    private MyServiceBinder mBinder = new MyServiceBinder();
    User mUser;
    public static String state = "null"; //当前通话的状态
    int toId;


    public class MyServiceBinder extends Binder {
        public MyWebSocketService getService() {
            return MyWebSocketService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * 心跳检测时间
     */
    private static final long HEART_BEAT_RATE = 15 * 1000;//每隔15秒进行一次对长连接的心跳检测
    private long sendTime = 0L;
    // 发送心跳包
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ON_CALL:
                    onCall();
                    break;
                case CALLING:
                    //正在打电话
                    showCallDialog();
                    break;
                case NO_RESPONSE:
                    //没人接听
                    noResponse();
                    break;
                case ON_BUSYING:
                    //对方正在忙
                    onBusying();
                    break;
                case ON_PICK_UP:
                    //对方接听
                    onPickUp();
                    break;
                case ON_REFUSE_CALL:
                    //对方拒绝接听：
                    onRefuse();
                    break;
                case ON_HANG_UP:
                    //对方在我还没接通就挂断了
                    onHangUp();
                    break;
                /**
                 * 监控
                 */
                case ON_BUSYING_MONITOR:
                    //不能监控
                    Toast.makeText(getApplicationContext(),"对方已被占线，暂时无法监控",Toast.LENGTH_SHORT).show();
                    break;
                case ON_MONITOR_SUCCESS:
                    //能监控
                    Intent intent = new Intent(getApplicationContext(), MonitorActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mUser", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };



    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                if(!MyWebSocket.getInstance().isConnect()){
                    if(MyWebSocket.getInstance().isClosed()){
                        reConnect();
                    }
                }else{
                    new InitSocketThread().start();
                }
                sendTime = System.currentTimeMillis();
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);//每隔一定的时间，对长连接进行一次心跳检测
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setMUser(User user){
        this.mUser = user;
    }

    public void connectSocket(){
        MyWebSocket.getInstance().setOnCallListener(new MyOnCallListener());
        MyWebSocket.getInstance().setOnMonotorListener(new MyOnMonitorListener());
        new InitSocketThread().start();
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                MyWebSocket.getInstance().connect(mUser.id);
                mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启重连
     */
    private void reConnect() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    //重连
                    MyWebSocket.getInstance().reConnect();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    public void onDestroy() {
        MyWebSocket.getInstance().closeConnect();
        super.onDestroy();
    }


    /**
     * -----------------socket监听到的打电话事件-------------------------
     */
    AlertDialog onCallDialog;
    private class MyOnCallListener implements MyWebSocket.OnCallListener{
        @Override
        public void onCall() {
            Log.e(TAG, "onCall: "+state );
            if(state.equals("null"))
                mHandler.sendEmptyMessage(ON_CALL);
            else
                MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId, Constant.BUSYING);
        }

        @Override
        public void onRefuseCall() {
            mHandler.removeMessages(NO_RESPONSE);
            mHandler.sendEmptyMessage(ON_REFUSE_CALL);
        }

        @Override
        public void onPickUp() {
            mHandler.removeMessages(NO_RESPONSE);
            mHandler.sendEmptyMessage(ON_PICK_UP);
        }

        @Override
        public void onBusying() {
            mHandler.removeMessages(NO_RESPONSE);
            mHandler.sendEmptyMessage(ON_BUSYING);
        }

        @Override
        public void onHangUp() {
            mHandler.sendEmptyMessage(ON_HANG_UP);
        }
    }

    /**
     * 弹出接到电话对话框
     */
    private void onCall() {
        state="onCall";
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("来电");
        builder.setMessage("收到来自孩子的视频通话，是否接听");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                state="null";
                MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId,Constant.REFUSE_CALL);
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                state="calling";
                Intent intent = new Intent(getApplication(), VideoCallActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mUser", mUser);
                bundle.putBoolean("isSender",false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        onCallDialog = builder.create();
        //8.0系统加强后台管理;，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onCallDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            onCallDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        onCallDialog.show();
        onCallDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 对方接听电话
     */
    private void onPickUp() {
        callDialog.dismiss();
        if(state.equals("call")){
            state="calling";
            Intent intent = new Intent(getApplication(),VideoCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable("mUser", mUser);
            bundle.putBoolean("isSender",true);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            //如果已经超过了时间，直接挂断电话
            MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId,Constant.HANG_UP);
        }
    }

    /**
     * 对方拒绝接听
     */
    private void onRefuse() {
        if(state.equals("call")){
            callDialog.dismiss();
            Toast.makeText(this,"对方拒绝接听",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 对方正在通话
     */
    private void onBusying() {
        state="null";
        callDialog.dismiss();
        Toast.makeText(this,"对方被占线，请稍后再拨",Toast.LENGTH_LONG).show();
    }


    /**
     * 对方挂断电话
     */
    private void onHangUp() {
        if(state.equals("onCall")){
            //如果我还没接通，对方就挂了
            onCallDialog.dismiss();
            state="null";
            Toast.makeText(this,"对方已挂断",Toast.LENGTH_SHORT).show();
        }else if(state.equals("calling")){
            //如果我接通了，对方挂了
            //todo
            Toast.makeText(this,"对方已经挂断",Toast.LENGTH_SHORT).show();
        }else{
            state="null";
        }
    }

    /**
     * -----------------------发出去的打电话消息-------------------------------
     */
    public void call() {
        MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId,Constant.CALL);
        state = "call";
        mHandler.sendEmptyMessage(CALLING);
        mHandler.sendEmptyMessageDelayed(NO_RESPONSE,10000);
    }


    AlertDialog callDialog;
    /**
     * 打电话提醒对话框
     */
    public void showCallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        callDialog = builder.setTitle("拨打")
                .setMessage("正在等待对方接听……")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        state="null";
                        mHandler.removeMessages(NO_RESPONSE);
                        MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId,Constant.HANG_UP);
                    }
                }).create();
        //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            callDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            callDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        callDialog.show();
        callDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 没有接听到对方的电话
     */
    private void noResponse() {
        MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId, Constant.HANG_UP);
        state = "null";
        callDialog.dismiss();
        Toast.makeText(this,"对方没有接听，请稍后再拨",Toast.LENGTH_LONG).show();
    }


    /**
     * ------------------收到的监控信息----------------------------
     */
    private class MyOnMonitorListener implements MyWebSocket.OnMonitorListener{{
    }

        @Override
        public void onBusyingMonitor() {
            mHandler.sendEmptyMessage(ON_BUSYING_MONITOR);

        }

        @Override
        public void onMonitorSuccess() {
            mHandler.sendEmptyMessage(ON_MONITOR_SUCCESS);
        }
    }

    /**
     * -------------------发出去的监控信息-------------------------
     */

    public void monitor() {
        MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId,Constant.MONITOR);
        state = "monitor";
    }

}
