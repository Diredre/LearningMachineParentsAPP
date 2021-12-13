package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;

public class InfoActivity extends AppCompatActivity {

    private ImageView info_iv_back, info_iv_childename, info_iv_grade, info_iv_city, info_iv_address;
    private EditText info_et_childename, info_et_address;
    private TextView info_tv_grade, info_tv_city;
    private Button info_btn_com;
    private String childename, address;
    private int cityId, grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);

        initView();
    }

    private void initView(){
        info_iv_back = findViewById(R.id.info_iv_back);
        info_iv_back.setOnClickListener(v->{
            this.finish();
        });

        info_iv_childename = findViewById(R.id.info_iv_childename);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOPCY8.png")
                .into(info_iv_childename);

        info_et_childename = findViewById(R.id.info_et_childename);

        info_iv_grade = findViewById(R.id.info_iv_grade);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOPc7t.png")
                .into(info_iv_grade);

        info_tv_grade = findViewById(R.id.info_tv_grade);

        info_iv_city = findViewById(R.id.info_iv_city);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOP5cQ.png")
                .into(info_iv_city);

        info_tv_city = findViewById(R.id.info_tv_city);
        info_tv_city.setOnClickListener(v->{
            /*选择城市*/
        });

        info_iv_address = findViewById(R.id.info_iv_address);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOi8C8.png")
                .into(info_iv_address);

        info_et_address = findViewById(R.id.info_et_address);

        info_btn_com = findViewById(R.id.info_btn_com);
        info_btn_com.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "填写完成", Toast.LENGTH_SHORT).show();
        });
    }
}