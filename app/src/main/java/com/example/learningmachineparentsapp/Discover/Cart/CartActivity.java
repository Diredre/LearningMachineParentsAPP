package com.example.learningmachineparentsapp.Discover.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.learningmachineparentsapp.Discover.fragment.GoodsBean;
import com.example.learningmachineparentsapp.Discover.GoodsDetailActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRvView;
    private CheckBox mInvert;
    private CartAdapter cartAdapter;
    private List<GoodsBean> lists = new ArrayList<>();
    private TextView cart_tv_total_price, cart_tv_delete, cart_tv_pay;
    private CheckBox cart_all_chekbox;
    private TitleLayout cart_tit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cart);

        initView();
        initAdapter();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        cart_tit = findViewById(R.id.cart_tit);
        cart_tit.setTitle("购物车");

        //模拟数据
        lists.add(new GoodsBean("1", "https://s4.ax1x.com/2021/12/25/TUEV2j.jpg",
                "LED儿童智能魔方", 32.51, 2, true));
        lists.add(new GoodsBean("4", "https://s4.ax1x.com/2021/12/25/TUZVun.jpg",
                "水滴贪吃豆", 54.50, 1, true));
        lists.add(new GoodsBean("5", "https://s4.ax1x.com/2021/12/25/TUZwCD.jpg",
                "益智游戏棋", 78.00, 1, true));
        lists.add(new GoodsBean("6", "https://img13.360buyimg.com/n1/jfs/t12655/194/385600663/434041/a7d721d/5a0ab413N4f06e9f8.jpg",
                "幼儿教育：《我不想发脾气，我想好好说》", 25.31, 1, true));

        mRvView = findViewById(R.id.cart_rv_view);
        cart_tv_total_price = findViewById(R.id.cart_tv_total_price);
        cart_all_chekbox = findViewById(R.id.cart_all_chekbox);
        mInvert = findViewById(R.id.cart_all_chekbox);
        cart_tv_delete = findViewById(R.id.cart_tv_delete);
        cart_tv_pay = findViewById(R.id.cart_tv_pay);
        cart_tit = findViewById(R.id.cart_tit);
        cart_tv_total_price.setOnClickListener(this);
        mInvert.setOnClickListener(this);
        cart_tv_delete.setOnClickListener(this);
        cart_tv_pay.setOnClickListener(this);
        cart_tit.setTitle("购物车");
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(linearLayoutManager);
        cartAdapter = new CartAdapter(this, lists);
        mRvView.setAdapter(cartAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_all_chekbox:
                cartAdapter.invertMap();
                break;
            case R.id.cart_tv_delete:
                showMsg("删除成功");
                cartAdapter.deleteMap();
                break;
            case R.id.cart_tv_pay:
                /*showMsg("支付成功");
                cartAdapter.deleteMap();*/
                startActivity(new Intent(this, GoodsDetailActivity.class));
                this.finish();
                break;
        }
    }

    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public TextView getTotal(){
        return cart_tv_total_price;
    }
}
