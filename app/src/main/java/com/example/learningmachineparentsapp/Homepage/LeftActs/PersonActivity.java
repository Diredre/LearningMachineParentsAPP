package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView person_iv_back, person_iv_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_person);

        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        person_iv_back = findViewById(R.id.person_iv_back);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IOiBee.png")
                .into(person_iv_back);

        person_iv_bg = findViewById(R.id.person_iv_bg);
        person_iv_bg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.person_iv_bg:
                this.finish();
                break;
        }
    }
}