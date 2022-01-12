package com.example.learningmachineparentsapp.webrtc.video_call;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.webrtc.common.Constant;
import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocket;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocketService;
import com.example.learningmachineparentsapp.webrtc.rtc.PeerConnectionObserver;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.DataChannel;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.Logging;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RendererCommon;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.List;

public class VideoCallActivity extends AppCompatActivity {
    String TAG = "VideoCallActivity";
    User mUser;
    Boolean isSender;
    ImageButton btnHangUp;

    public static final String VIDEO_TRACK_ID = "1";
    public static final String AUDIO_TRACK_ID = "2";

    //用于数据传输
    private PeerConnection mPeerConnection;
    private PeerConnectionFactory mPeerConnectionFactory;

    //OpenGL ES
    private EglBase eglBase;
    //纹理渲染
    private SurfaceTextureHelper mSurfaceTextureHelper;

    private SurfaceViewRenderer mLocalSurfaceView;
    private SurfaceViewRenderer mRemoteSurfaceView;

    private VideoTrack mVideoTrack;
    private AudioTrack mAudioTrack;
    private VideoCapturer mVideoCapturer;
    private VideoSource mVideoSource;
    private AudioSource mAudioSource;

    private List<PeerConnection.IceServer> iceServers;
    private List<String> streamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_call);
        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.getSerializable("mUser");
        isSender = bundle.getBoolean("isSender");
        MyWebSocket.getInstance().setOnRTCListener(mOnRTCEventListener);
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
        mLocalSurfaceView = findViewById(R.id.LocalSurfaceView);
        mRemoteSurfaceView = findViewById(R.id.RemoteSurfaceView);

        mLocalSurfaceView.init(eglBase.getEglBaseContext(), null);
        mLocalSurfaceView.setMirror(true);
        mLocalSurfaceView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        mLocalSurfaceView.setEnableHardwareScaler(false);
        mLocalSurfaceView.setZOrderMediaOverlay(true);

        mRemoteSurfaceView.init(eglBase.getEglBaseContext(), null);
        mRemoteSurfaceView.setMirror(true);
        mRemoteSurfaceView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        mRemoteSurfaceView.setEnableHardwareScaler(false);

        mPeerConnectionFactory = createPeerConnectionFactory();

        mVideoCapturer = createVideoCapturer();
        startLocalVideoCapture(mLocalSurfaceView);
        startLocalAudioCapture();
        Log.e(TAG, "initPeerConnection: 初始化成功" );
        if (isSender) {
            createOffer();
        }else {
            MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId, Constant.PICK_UP);
        }
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
     * 准备摄像头
     *
     * @return
     */
    private VideoCapturer createVideoCapturer() {
        if (Camera2Enumerator.isSupported(this)) {
            return createCameraCapturer(new Camera2Enumerator(this));
        } else {
            return createCameraCapturer(new Camera1Enumerator(true));
        }
    }

    private VideoCapturer createCameraCapturer(CameraEnumerator enumerator) {
        final String[] deviceNames = enumerator.getDeviceNames();

        // First, try to find front facing camera
        Log.d(TAG, "Looking for front facing cameras.");
        for (String deviceName : deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                Logging.d(TAG, "Creating front facing camera capturer.");
                VideoCapturer VideoCapturer = enumerator.createCapturer(deviceName, null);
                if (VideoCapturer != null) {
                    return VideoCapturer;
                }
            }
        }

        // Front facing camera not found, try something else
        Log.d(TAG, "Looking for other cameras.");
        for (String deviceName : deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                Logging.d(TAG, "Creating other camera capturer.");
                VideoCapturer VideoCapturer = enumerator.createCapturer(deviceName, null);
                if (VideoCapturer != null) {
                    return VideoCapturer;
                }
            }
        }
        return null;
    }

    /**
     * 创建本地视频
     *
     * @param localSurfaceView
     */
    private void startLocalVideoCapture(SurfaceViewRenderer localSurfaceView) {
        mVideoSource = mPeerConnectionFactory.createVideoSource(true);
        mSurfaceTextureHelper = SurfaceTextureHelper.create(Thread.currentThread().getName(), eglBase.getEglBaseContext());
        mVideoCapturer.initialize(mSurfaceTextureHelper, this, mVideoSource.getCapturerObserver());
        mVideoCapturer.startCapture(Constant.VIDEO_RESOLUTION_WIDTH, Constant.VIDEO_RESOLUTION_HEIGHT, Constant.VIDEO_FPS); // width, height, frame per second

        mVideoTrack = mPeerConnectionFactory.createVideoTrack(VIDEO_TRACK_ID, mVideoSource);
        mVideoTrack.setEnabled(true);
        mVideoTrack.addSink(localSurfaceView);
    }

    /**
     * 创建本地音频
     */
    private void startLocalAudioCapture() {
        //语音
        MediaConstraints audioConstraints = new MediaConstraints();
        //回声消除
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation", "true"));
        //自动增益
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl", "true"));
        //高音过滤
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googHighpassFilter", "true"));
        //噪音处理
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));
        mAudioSource = mPeerConnectionFactory.createAudioSource(audioConstraints);
        mAudioTrack = mPeerConnectionFactory.createAudioTrack(AUDIO_TRACK_ID, mAudioSource);
        mAudioTrack.setEnabled(true);
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
                    data.put("type", Constant.OFFER);
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
     * 创建answer
     */
    private void createAnswer() {
        if (mPeerConnection == null) {
            createPeerConnection();
        }
        MediaConstraints mediaConstraints = new MediaConstraints();
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("RtpDataChannels", "true"));
        mPeerConnection.createAnswer(new SimpleSdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                mPeerConnection.setLocalDescription(this, sessionDescription);
                JSONObject message = new JSONObject();
                try {
                    JSONObject data = new JSONObject();
                    data.put("type", Constant.ANSWER);
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
        streamList = new ArrayList<>();
        MediaStream localMediaStream = mPeerConnectionFactory.createLocalMediaStream(Constant.LOCAL_VIDEO_STREAM);
        localMediaStream.addTrack(mVideoTrack);
        mPeerConnection.addTrack(mVideoTrack, streamList);
        mPeerConnection.addStream(localMediaStream);
        MediaStream localMediaStream2 = mPeerConnectionFactory.createLocalMediaStream(Constant.LOCAL_AUDIO_STREAM);
        localMediaStream2.addTrack(mAudioTrack);
        mPeerConnection.addTrack(mAudioTrack, streamList);
        mPeerConnection.addStream(localMediaStream2);
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
                    data.put("type", Constant.CANDIDATE);
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
     * 收到offer
     *
     * @param msg
     */
    private void onRemoteOfferReceived(JSONObject msg) {
        if (mPeerConnection == null) {
            createPeerConnection();
        }
        String description = msg.optString("sdp");
        mPeerConnection.setRemoteDescription(new SimpleSdpObserver(),
                new SessionDescription(SessionDescription.Type.OFFER, description));
        createAnswer();
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


    private MyWebSocket.OnRTCEventListener mOnRTCEventListener = new MyWebSocket.OnRTCEventListener() {

        @Override
        public void onOffer(JSONObject msg) {
            Log.e(TAG, "onOffer: ");
            onRemoteOfferReceived(msg);
        }

        @Override
        public void onAnswer(JSONObject msg) {
            Log.e(TAG, "onAnswer: ");
            onRemoteAnswerReceived(msg);
        }

        @Override
        public void onCandidate(JSONObject msg) {
            Log.e(TAG, "onCandidate: ");
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
     * 返回之前处理资源释放
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doHangUp();
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

        }
        return true;
    }

    /**
     * 挂断电话
     */
    private void doHangUp() {
        new AlertDialog.Builder(this).setTitle("提醒").setMessage("确认结束通话")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyWebSocketService.state="null";
                        MyWebSocket.getInstance().sendMessage(mUser.id,mUser.childId, Constant.HANG_UP);
                        VideoCallActivity.this.finish();
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
        mLocalSurfaceView.release();
        mRemoteSurfaceView.release();
        mVideoCapturer.dispose();
        mSurfaceTextureHelper.dispose();
        PeerConnectionFactory.stopInternalTracingCapture();
        PeerConnectionFactory.shutdownInternalTracer();
        mPeerConnectionFactory.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}