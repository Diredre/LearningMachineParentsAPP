package com.example.learningmachineparentsapp.Homepage.Message;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.SlideRecyclerView;
import com.example.learningmachineparentsapp.View.TitleLayout;
import com.example.learningmachineparentsapp.okhttpClass;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class MessageActivity extends AppCompatActivity {

    public static int MESSAGE = 1;
    private ImageView message_iv_reply, message_iv_like, message_iv_pay, message_iv_inform;
    private TitleLayout message_tit;
    private SlideRecyclerView message_rv;
    private List<MessageBean> messageBeanList = new ArrayList<>();
    private MessageAdapter messageAdapter;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            if (msg.what == MESSAGE) {
                String s = (String) msg.obj;
                MessageGson messageGson = gson.fromJson(s, MessageGson.class);
                for(int i = 0; i < messageGson.getData().size(); i++) {
                    int id = messageGson.getData().get(i).getId();
                    String fromUser =  messageGson.getData().get(i).getName();
                    String imgurl = "https://s4.ax1x.com/2022/01/08/7CxQW8.png";
                    boolean paystate = messageGson.getData().get(i).getPayState() == 1;

                    String content;
                    if(paystate){
                        content = messageGson.getData().get(i).getTypeName() + "已经支付";
                    }else{
                        content = messageGson.getData().get(i).getTypeName() + "待支付解锁";
                    }

                    MessageBean mb = new MessageBean(id, content, fromUser, imgurl, paystate);
                    mb.setType(messageGson.getData().get(i).getTypeName());
                    messageBeanList.add(mb);
                }

                messageAdapter = new MessageAdapter(MessageActivity.this, messageBeanList);
                message_rv.setAdapter(messageAdapter);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message);

        getVideoTypePayInfo("1");
        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        message_tit = findViewById(R.id.message_tit);
        message_tit.setTitle("消息通知");

        message_iv_reply = findViewById(R.id.message_iv_reply);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/19/Te8GZj.png")
                .into(message_iv_reply);

        message_iv_like = findViewById(R.id.message_iv_like);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/19/Te8Mz8.png")
                .into(message_iv_like);

        message_iv_pay = findViewById(R.id.message_iv_pay);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/19/TeGQt1.png")
                .into(message_iv_pay);

        message_iv_inform = findViewById(R.id.message_iv_inform);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/19/Te8aWV.png")
                .into(message_iv_inform);

        message_rv = findViewById(R.id.message_rv);
        message_rv.setItemAnimator(new DefaultItemAnimator());
        message_rv.setItemAnimator(new SlideInUpAnimator());        //设置上浮动画
        message_rv.getItemAnimator().setAddDuration(500);
        message_rv.getItemAnimator().setRemoveDuration(500);

        // 垂直滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        message_rv.setLayoutManager(linearLayoutManager);

        // SnapHelper滑动对准
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(message_rv);

        //messageBeanList = initData();
    }


    private List<MessageBean> initData() {
        List<MessageBean> list = new ArrayList<>();
        list.add(new MessageBean(1, "系统测试通知1", "学习机官方", "https://s4.ax1x.com/2022/01/08/7CxQW8.png", false));
        list.add(new MessageBean(2, "系统测试通知2", "学习机官方", "https://s4.ax1x.com/2022/01/08/7CxQW8.png", true));
        list.add(new MessageBean(3, "系统测试通知3", "学习机官方", "https://s4.ax1x.com/2022/01/08/7CxQW8.png", false));
        return list;
    }


    private void getVideoTypePayInfo(String pID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tool = new okhttpClass();
                String res = tool.getVideoTypePayInfo(pID);
                Message mes = new Message();
                mes.what = MESSAGE;
                mes.obj = res;
                handler.sendMessage(mes);
            }
        }).start();
    }
}