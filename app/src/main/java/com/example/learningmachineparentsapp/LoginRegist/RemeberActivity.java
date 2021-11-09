package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;
/**
 * 忘记（找回）密码界面
 */
public class RemeberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remeber);
        makeStatusBarTransparent(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);    //设置手机应用内部状态栏字体图标为黑色
    }
}