package com.example.learningmachineparentsapp.webrtc.monitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.webrtc.common.Constant;
import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocket;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocketService;
import com.example.learningmachineparentsapp.webrtc.rtc.PeerConnectionObserver;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.AudioTrack;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RendererCommon;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;


import java.util.ArrayList;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {
    String TAG = "VideoCallActivity";
    User mUser;
    ImageButton btnHangUp;

    public static final String VIDEO_TRACK_ID = "1";
    public static final String AUDIO_TRACK_ID = "2";

    //用于数据传输
    private PeerConnection mPeerConnection;
    private PeerConnectionFactory mPeerConnectionFactory;

    //OpenGL ES
    private EglBase eglBase;

    private SurfaceViewRenderer mRemoteSurfaceView;


    private List<PeerConnection.IceServer> iceServers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_monitor);
        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.getSerializable("mUser");
        MyWebSocket.getInstance().setOnMonitorRTCEventListener(mOnRTCEventListener);
        initView();
        initPeerConnection();
    }

    private void initView() {
        btnHangUp = findViewById(R.id.btn_hang_up);
        btnHangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doHangUp();
            }
        });
    }


    private void initPeerConnection() {
        eglBase = EglBase.create();
        mRemoteSurfaceView = findViewById(R.id.RemoteSurfaceView);

        mRemoteSurfaceView.init(eglBase.getEglBaseContext(), null);
        mRemoteSurfaceView.setMirror(true);
        mRemoteSurfaceView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        mRemoteSurfaceView.setEnableHardwareScaler(false);

        mPeerConnectionFactory = createPeerConnectionFactory();

        Log.e(TAG, "initPeerConnection: 初始化成功" );
        createOffer();
    }

    /**
     * 创建PeerConnectionFactory
     *
     * @return
     */
    private PeerConnectionFactory createPeerConnectionFactory() {
        //Initialising PeerConnectionFactory
        PeerConnectionFactory.InitializationOptions initializationOptions = PeerConnectionFactory.InitializationOptions.builder(this)
                .setEnableInternalTracer(true)
                .setFieldTrials("WebRTC-H264HighProfile/Enabled/")
                .createInitializationOptions();
        PeerConnectionFactory.initialize(initializationOptions);

        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
//        options.disableEncryption = true;
        options.disableNetworkMonitor = true;
        PeerConnectionFactory.Builder builder = PeerConnectionFactory.builder()
                .setVideoDecoderFactory(new DefaultVideoDecoderFactory(eglBase.getEglBaseContext()))
                .setVideoEncoderFactory(new DefaultVideoEncoderFactory(eglBase.getEglBaseContext(), true, true))
                .setOptions(options);
        return builder.createPeerConnectionFactory();
    }


    /**
     * 创建offer
     */
    private void createOffer() {
        Log.e(TAG, "createOffer: 创建offer" );
        if (mPeerConnection == null)
            createPeerConnection();
        MediaConstraints mediaConstraints = new MediaConstraints();
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("RtpDataChannels", "true"));
        mPeerConnection.createOffer(new SimpleSdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                mPeerConnection.setLocalDescription(this, sessionDescription);
                JSONObject message = new JSONObject();
                try {
                    JSONObject data = new JSONObject();
                    data.put("type", Constant.MONITOR_OFFER);
                    data.put("sdp", sessionDescription.description);
                    data.put("from", mUser.id);
                    data.put("to", mUser.childId);
                    message.put("type","CallParent");
                    message.put("data",data.toString());
                    MyWebSocket.getInstance().sendMessage(message.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, mediaConstraints);
    }

    /**
     * 创建PeerConnection
     */
    private void createPeerConnection() {
        // 配置STUN穿透服务器  转发服务器
        iceServers = new ArrayList<>();
        iceServers.add(PeerConnection.IceServer.builder(Constant.TURN)
                .setUsername("wf09").setPassword("wf09").createIceServer());
        iceServers.add(PeerConnection.IceServer.builder(Constant.TURN2)
                .setUsername("wf09").setPassword("wf09").createIceServer());
        iceServers.add(PeerConnection.IceServer.builder(Constant.TURN4)
                .setUsername("deepsoft").setPassword("deepsoft").createIceServer());
        iceServers.add(PeerConnection.IceServer.builder(Constant.STUN).createIceServer());
        PeerConnection.RTCConfiguration configuration = new PeerConnection.RTCConfiguration(iceServers);
        configuration.iceTransportsType= PeerConnection.IceTransportsType.RELAY;
        PeerConnectionObserver connectionObserver = getObserver();
        mPeerConnection = mPeerConnectionFactory.createPeerConnection(configuration, connectionObserver);
    }

    @NonNull
    private PeerConnectionObserver getObserver() {
        return new PeerConnectionObserver() {
            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {
                super.onIceCandidate(iceCandidate);
                Log.e(TAG, "onIceCandidate: " + iceCandidate);
                JSONObject message = new JSONObject();
                try {
                    JSONObject data = new JSONObject();
                    data.put("to", mUser.childId);
                    data.put("from", mUser.id);
                    data.put("type", Constant.MONITOR_CANDIDATE);
                    data.put("label", iceCandidate.sdpMLineIndex);
                    data.put("id", iceCandidate.sdpMid);
                    data.put("candidate", iceCandidate.sdp);
                    message.put("type","CallParent");
                    message.put("data",data.toString());
                    MyWebSocket.getInstance().sendMessage(message.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                super.onIceConnectionChange(iceConnectionState);

            }

            @Override
            public void onAddStream(MediaStream mediaStream) {
                super.onAddStream(mediaStream);
                List<VideoTrack> videoTracks = mediaStream.videoTracks;
                if (videoTracks != null && videoTracks.size() > 0) {
                    VideoTrack videoTrack = videoTracks.get(0);
                    if (videoTrack != null) {
                        videoTrack.addSink(mRemoteSurfaceView);
                    }
                }
                List<AudioTrack> audioTracks = mediaStream.audioTracks;
                if (audioTracks != null && audioTracks.size() > 0) {
                    AudioTrack audioTrack = audioTracks.get(0);
                    if (audioTrack != null) {
//                       todo audioTrack.setVolume(AudioManager.STREAM_VOICE_CALL);
                        audioTrack.setVolume(5);
                    }
                }
            }


        };
    }

    private void onRemoteCandidateReceived(JSONObject msg) {
        IceCandidate remoteIceCandidate = new IceCandidate(msg.optString("id"),
                msg.optInt("label"), msg.optString("candidate"));
        mPeerConnection.addIceCandidate(remoteIceCandidate);
    }

    /**
     * 收到answer
     *
     * @param msg
     */
    private void onRemoteAnswerReceived(JSONObject msg) {
        String description = msg.optString("sdp");
        mPeerConnection.setRemoteDescription(new SimpleSdpObserver(),
                new SessionDescription(SessionDescription.Type.ANSWER, description));
    }


    private MyWebSocket.OnMonitorRTCEventListener mOnRTCEventListener = new MyWebSocket.OnMonitorRTCEventListener() {


        @Override
        public void onMonitorAnswer(JSONObject msg) {
            Log.e(TAG, "onMonitorAnswer: ");
            onRemoteAnswerReceived(msg);
        }

        @Override
        public void onMonitorCandidate(JSONObject msg) {
            Log.e(TAG, "onMonitorCandidate: ");
            onRemoteCandidateReceived(msg);
        }

    };


    private class SimpleSdpObserver implements SdpObserver {
        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {

        }

        @Override
        public void onSetSuccess() {

        }

        @Override
        public void onCreateFailure(String s) {

        }

        @Override
        public void onSetFailure(String s) {

        }
    }

    /**
     * 挂断电话
     */
    private void doHangUp() {
        new AlertDialog.Builder(this).setTitle("提醒").setMessage("确认结束监控")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyWebSocketService.state="null";
                        MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId, Constant.MONITOR_FINISH);
                        MonitorActivity.this.finish();
                        release();
                    }
                })
                .setNegativeButton("否", null).show().setCanceledOnTouchOutside(false);
    }

    /**
     * 释放资源
     */
    private void release() {
        if (mPeerConnection == null) {
            return;
        }
        mPeerConnection.close();
        mPeerConnection = null;
        mRemoteSurfaceView.release();
        PeerConnectionFactory.stopInternalTracingCapture();
        PeerConnectionFactory.shutdownInternalTracer();
        mPeerConnectionFactory.dispose();
    }
}