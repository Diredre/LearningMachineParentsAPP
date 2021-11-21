package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout account_tit;
    private ImageView account_iv_phone;
    private RelativeLayout account_rl_phone, account_rl_psw, account_rl_device;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account);

        initView();
    }

    private void initView(){
        account_tit = findViewById(R.id.account_tit);
        account_tit.setTitle("账号与安全");

        account_iv_phone = findViewById(R.id.account_iv_phone);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/21/IXlZ6g.png")
                .into(account_iv_phone);

        account_rl_phone = findViewById(R.id.account_rl_phone);
        account_rl_phone.setOnClickListener(this);

        account_rl_psw = findViewById(R.id.account_rl_psw);
        account_rl_psw.setOnClickListener(this);

        account_rl_device = findViewById(R.id.account_rl_device);
        account_rl_device.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_rl_phone:
                Toast.makeText(this,"开发中",Toast.LENGTH_SHORT).show();
                break;
            case R.id.account_rl_psw:
                startActivity(new Intent(this, PasswordActivity.class));
                break;
            case R.id.account_rl_device:
                startActivity(new Intent(this, DeviceActivity.class));
                break;
        }
    }
}