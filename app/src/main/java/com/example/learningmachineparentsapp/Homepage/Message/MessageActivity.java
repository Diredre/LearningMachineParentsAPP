package com.example.learningmachineparentsapp.Homepage.Message;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class MessageActivity extends AppCompatActivity {

    private TitleLayout message_tit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message);

        initView();
    }

    private void initView(){
        message_tit = findViewById(R.id.message_tit);
        message_tit.setTitle("消息通知");
    }

}