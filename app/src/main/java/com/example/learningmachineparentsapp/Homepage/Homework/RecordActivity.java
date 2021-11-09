package com.example.learningmachineparentsapp.Homepage.Homework;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningmachineparentsapp.R;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

/**
 * 家长查看自己布置的作业记录
 */
public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);
        makeStatusBarTransparent(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);    //设置手机应用内部状态栏字体图标为黑色

        initView();
    }

    private void  initView(){

    }
}