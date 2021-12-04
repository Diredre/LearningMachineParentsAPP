package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        help_tit = findViewById(R.id.help_tit);
        help_tit.setTitle("帮助与反馈");

        help_iv = findViewById(R.id.help_iv);
        Glide.with(this)
                .load("https://s6.jpg.cm/2021/12/04/L556j2.png")
                .into(help_iv);
    }
}