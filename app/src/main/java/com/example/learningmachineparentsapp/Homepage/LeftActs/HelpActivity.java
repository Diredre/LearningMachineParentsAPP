package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

public class HelpActivity extends AppCompatActivity {

    private TitleLayout help_tit;
    private ImageView help_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);

        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        help_tit = findViewById(R.id.help_tit);
        help_tit.setTitle("帮助与反馈");

        help_iv = findViewById(R.id.help_iv);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/11/oTIbut.png")
                .into(help_iv);
    }
}