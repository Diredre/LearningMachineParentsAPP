package com.example.learningmachineparentsapp.Discover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

public class MailActivity extends AppCompatActivity {

    public static String GOODSNAME = "name";
    public static String GOODSMONNEY = "money";
    public static String GOODSICON = "imgurl";

    private TitleLayout mail_tit;
    private ImageView mail_iv_goods, mail_iv_address;
    private TextView mail_tv_pay, mail_tv_name, mail_tv_money, mail_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mail);

        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        Intent intent = getIntent();
        String name = intent.getStringExtra(GOODSNAME);
        String imgurl = intent.getStringExtra(GOODSICON);
        String money = intent.getStringExtra(GOODSMONNEY);

        mail_tit = findViewById(R.id.mail_tit);
        mail_tit.setTitle("结算");

        mail_iv_goods = findViewById(R.id.mail_iv_goods);
        Glide.with(this)
                .load(imgurl)
                .into(mail_iv_goods);

        mail_tv_name = findViewById(R.id.mail_tv_name);
        mail_tv_name.setText(name);

        mail_tv_money = findViewById(R.id.mail_tv_money);
        mail_tv_money.setText(money);

        mail_price = findViewById(R.id.mail_price);
        mail_price.setText(money);

        mail_iv_address = findViewById(R.id.mail_iv_address);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TU8qCn.png")
                .into(mail_iv_address);

        mail_tv_pay = findViewById(R.id.mail_tv_pay);
        mail_tv_pay.setOnClickListener(v->{
            finish();
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        });
    }
}