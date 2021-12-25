package com.example.learningmachineparentsapp.Homepage.Message;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class MessageActivity extends AppCompatActivity {

    private ImageView message_iv_reply, message_iv_like, message_iv_pay, message_iv_inform;
    private TitleLayout message_tit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message);

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
    }

}