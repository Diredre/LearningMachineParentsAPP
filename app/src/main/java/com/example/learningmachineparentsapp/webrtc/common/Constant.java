package com.example.learningmachineparentsapp.webrtc.common;

/**
 * @author cute xyy biu~biu~
 * created on
 * fun:
 */
public class Constant {
    public static final String URL2 = "ws://221.12.170.98:91/lamp/websocket/parentServer/";//server服务器地址

    //    public static final String URL2 = "ws://192.168.31.73:8081/websocket/parentServer/";//server服务器地址
    public static final String URL = "ws://101.133.173.40:8083/socket/";//server服务器地址
    public static final String STUN = "stun:stun.xten.com";//穿透服务器
    public static final String STUN2 = "stun:stun.voipbuster.com";//穿透服务器
    public static final String STUN3 = "stun:stun.voxgratia.org";//穿透服务器
    public static final String STUN4 = "stun:stun.sipgate.net";//穿透服务器

    public static final String TURN = "turn:139.196.14.120:5349";//穿透服务器
    public static final String TURN2 = "turn:111.67.194.143:5349";//穿透服务器
    public static final String TURN3 = "turn:101.43.98.134:5349";//穿透服务器
    public static final String TURN4 = "turn:221.12.170.98:3478";//穿透服务器

    /**
     * 与孩子打电话
     */
    public static final String CALL = "call"; //1.0 收到 打电话请求
    public static final String CANCEL_CALL = "cancel_call"; //取消打电话
    public static final String PICK_UP = "pick_up"; //3.0收到 同意接电话
    public static final String REFUSE_CALL = "refuse_call"; //3.1 收到 拒绝接电话
    public static final String HANG_UP = "hang_up"; //11.0 收到任一方的挂断电话
    public static final String BUSYING = "busying";


    /**
     * 监控
     */
    public static final String MONITOR = "monitor";
    public static final String BUSYING_MONITOR = "busying_monitor";//当前不能被监控
    public static final String MONITOR_SUCCESS="monitor_success";//当前可以监控

    public static final String LOCAL_VIDEO_STREAM = "localVideoStream";
    public static final String LOCAL_AUDIO_STREAM = "localAudioStream";
    public static final int VIDEO_RESOLUTION_WIDTH = 320;
    public static final int VIDEO_RESOLUTION_HEIGHT = 240;
    public static final int VIDEO_FPS = 60;

    public static final String OFFER = "offer"; //发送offer sdp
    public static final String ANSWER = "answer"; //发送answer sdp
    public static final String CANDIDATE = "candidate"; //发送 candidate

    public static final String MONITOR_OFFER = "monitor_offer"; //发送offer sdp
    public static final String MONITOR_ANSWER = "monitor_answer"; //发送answer sdp
    public static final String MONITOR_CANDIDATE = "monitor_candidate"; //发送 candidate
    public static final String MONITOR_FINISH = "monitor_finish";
}
