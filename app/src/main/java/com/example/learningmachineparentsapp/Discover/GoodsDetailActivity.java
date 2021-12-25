package com.example.learningmachineparentsapp.Discover;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private ImageView goods_detail_iv_icon, goods_detail_iv_like, goods_detail_iv_share, goods_detail_iv_choose;
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
        Glide.with(this)
                .load(imgurl)
                .into(goods_detail_iv_icon);
        goods_detail_iv_icon.setOnClickListener(v->{
            bigImageLoader(imgurl);
        });
        goods_detail_name = findViewById(R.id.goods_detail_name);
        goods_detail_name.setText(name);
        goods_detail_money = findViewById(R.id.goods_detail_money);
        goods_detail_money.setText("￥" + money);

        goods_detail_tv_cart = findViewById(R.id.goods_detail_tv_cart);
        goods_detail_tv_cart.setOnClickListener(v->{
            Toast.makeText(this, "加入购物车成功", Toast.LENGTH_SHORT).show();
        });

        goods_detail_tv_pay = findViewById(R.id.goods_detail_tv_pay);
        goods_detail_tv_pay.setOnClickListener(v->{
            Intent intent2 = new Intent(this, MailActivity.class);
            intent2.putExtra(MailActivity.GOODSNAME, name);
            intent2.putExtra(MailActivity.GOODSMONNEY, money);
            intent2.putExtra(MailActivity.GOODSICON, imgurl);
            startActivity(intent2);
            finish();
        });

        goods_detail_iv_like = findViewById(R.id.goods_detail_iv_like);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TUsxAI.png")
                .into(goods_detail_iv_like);

        goods_detail_iv_share = findViewById(R.id.goods_detail_iv_share);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TadZgH.png")
                .into(goods_detail_iv_share);

        goods_detail_iv_choose = findViewById(R.id.goods_detail_iv_choose);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TadQVP.png")
                .into(goods_detail_iv_choose);
    }

    /**
     * 方法里直接实例化一个imageView不用xml文件，传入bitmap设置图片
     */
    private void bigImageLoader(String url){
        LayoutInflater inflater = LayoutInflater.from(this);
        View imgEntryView = inflater.inflate(R.layout.dialog_photo, null);
        // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        ImageView img = imgEntryView.findViewById(R.id.large_image);
        Glide.with(this)
                .load(url)
                .into(img);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        // 点击大图关闭dialog
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }
}