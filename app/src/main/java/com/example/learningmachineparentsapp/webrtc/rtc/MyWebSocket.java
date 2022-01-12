package com.example.learningmachineparentsapp.webrtc.rtc;

import android.util.Log;


import com.example.learningmachineparentsapp.webrtc.common.Constant;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * @author cute xyy biu~biu~
 * created on 2021-12-06
 * fun:
 */
public class MyWebSocket {
    String TAG = "MyWebSocket";
    private static MyWebSocket mInstance;
    WebSocketClient socket;
    private MyWebSocket() {

    }

    //单例
    public static MyWebSocket getInstance() {
        synchronized (MyWebSocket.class) {
            if (mInstance == null) {
                mInstance = new MyWebSocket();
            }
        }
        return mInstance;
    }


    /**
     * 接口
     */
    private OnCallListener monCallListener;
    private OnMonitorListener monMonitorListener;
    private OnRTCEventListener mOnRTCEventListener;
    private OnMonitorRTCEventListener mOnMonitorRTCEventListener;
    public interface OnCallListener{
        void onCall();

        void onRefuseCall();

        void onPickUp();

        void onBusying();

        void onHangUp();
    }

    public interface OnMonitorListener{
        void onBusyingMonitor();

        void onMonitorSuccess();
    }

    public interface OnRTCEventListener{
        void onOffer(JSONObject msg);

        void onAnswer(JSONObject msg);

        void onCandidate(JSONObject msg);
    }

    public interface OnMonitorRTCEventListener{
        void onMonitorAnswer(JSONObject msg);

        void onMonitorCandidate(JSONObject msg);

    }

    public void setOnCallListener(OnCallListener onCallListener){
        this.monCallListener = onCallListener;
    }

    public void setOnMonotorListener(OnMonitorListener onMonotorListener){
        this.monMonitorListener = onMonotorListener;
    }

    public void setOnRTCListener(OnRTCEventListener onRTCEventListener){
        this.mOnRTCEventListener = onRTCEventListener;
    }

    public void setOnMonitorRTCEventListener(OnMonitorRTCEventListener onMonitorRTCEventListener){
        this.mOnMonitorRTCEventListener = onMonitorRTCEventListener;
    }

    /**
     * 连接websocket
     */
    public void connect(int id) throws InterruptedException {
        Log.e(TAG, "connect: ");
        URI uri = URI.create(Constant.URL2 + id);
        socket = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.e(TAG, "onOpen: ");
            }

            @Override
            public void onMessage(String message) {
                Log.e("TAG", "onMessage: " + message);
                if(message.equals("连接成功")) return;
                try {
                    JSONObject jsonMessage = new JSONObject(message);
                    String type = jsonMessage.optString("type");
                    switch (type) {
                        /**
                         * 互帮互助
                         */
                        case Constant.CALL:
                            monCallListener.onCall();
                            break;
                        case Constant.REFUSE_CALL:
                            monCallListener.onRefuseCall();
                            break;
                        case Constant.BUSYING:
                            monCallListener.onBusying();
                            break;
                        case Constant.PICK_UP:
                            monCallListener.onPickUp();
                            break;
                        case Constant.HANG_UP:
                            monCallListener.onHangUp();
                            break;
                        /**
                         * webrtc
                         */
                        case Constant.ANSWER:
                            mOnRTCEventListener.onAnswer(jsonMessage);
                            break;
                        case Constant.OFFER:
                            mOnRTCEventListener.onOffer(jsonMessage);
                            break;
                        case Constant.CANDIDATE:
                            mOnRTCEventListener.onCandidate(jsonMessage);
                            break;

                        /**
                         * 监控
                         */
                        case Constant.BUSYING_MONITOR:
                            monMonitorListener.onBusyingMonitor();
                            break;
                        case Constant.MONITOR_SUCCESS:
                            monMonitorListener.onMonitorSuccess();
                            break;
                        case Constant.MONITOR_ANSWER:
                            mOnMonitorRTCEventListener.onMonitorAnswer(jsonMessage);
                            break;
                        case Constant.MONITOR_CANDIDATE:
                            mOnMonitorRTCEventListener.onMonitorCandidate(jsonMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG, "onClose: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG, "onError: " + ex.getMessage());
            }
        };
        socket.connectBlocking();
    }


    public boolean isConnect() {
        return socket == null;
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public void reConnect() throws InterruptedException {
        socket.reconnectBlocking();
    }


    /**
     * 断开连接
     */
    public void closeConnect() {
        try {
            if (null != socket) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket = null;
        }
    }

    /**
     * --------------------处理各种发送请求---------------------
     */

    public void sendMessage(int from, int to, String type){
        JSONObject jsonMessage = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            data.put("from",from);
            data.put("to",to);
            data.put("type", type);
            jsonMessage.put("type", "CallParent");
            jsonMessage.put("data",data.toString());
            socket.send(jsonMessage.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        socket.send(message);
    }

}
