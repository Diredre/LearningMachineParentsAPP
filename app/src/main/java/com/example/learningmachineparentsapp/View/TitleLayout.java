package com.example.learningmachineparentsapp.View;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;

/**
 * 标题栏
 */
public class TitleLayout extends LinearLayout {

    private ImageView title_iv;
    private TextView title_tv;


    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);

        title_iv = findViewById(R.id.title_iv);
        Glide.with(context)
                .load("https://z3.ax1x.com/2021/10/24/5WWs54.png")
                .into(title_iv);
        title_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        title_tv = findViewById(R.id.title_tv);

    }

    /**
     * 设置标题文字
     */
    public void setTitle(String s){
        title_tv.setText(s);
    }
}