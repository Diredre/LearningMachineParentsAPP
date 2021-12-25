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

public class GoodsDetailActivity extends AppCompatActivity {

    public static String GOODSNAME = "name";
    public static String GOODSMONNEY = "money";
    public static String GOODSICON = "imgurl";

    private TitleLayout goods_detail_tit;
    private ImageView goods_detail_iv_icon;
    private TextView goods_detail_name, goods_detail_money, goods_detail_tv_cart, goods_detail_tv_pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);

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

        goods_detail_tit = findViewById(R.id.goods_detail_tit);
        goods_detail_tit.setTitle("商品详情");

        goods_detail_iv_icon = findViewById(R.id.goods_detail_iv_icon);
        goods_detail_name = findViewById(R.id.goods_detail_name);
        goods_detail_money = findViewById(R.id.goods_detail_money);
        Glide.with(this)
                .load(imgurl)
                .into(goods_detail_iv_icon);
        goods_detail_name.setText(name);
        goods_detail_money.setText("￥" + money);

        goods_detail_tv_cart = findViewById(R.id.goods_detail_tv_cart);
        goods_detail_tv_cart.setOnClickListener(v->{
            Toast.makeText(this, "加入购物车成功", Toast.LENGTH_SHORT).show();
        });

        goods_detail_tv_pay = findViewById(R.id.goods_detail_tv_pay);
        goods_detail_tv_pay.setOnClickListener(v->{
            startActivity(new Intent(this, MailActivity.class));
            finish();
        });
    }
}