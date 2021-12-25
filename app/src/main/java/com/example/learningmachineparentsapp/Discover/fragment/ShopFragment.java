package com.example.learningmachineparentsapp.Discover.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.learningmachineparentsapp.R;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private Context context;
    private View view;
    private RecyclerView fragmentshop_rv;
    private ShopAdapter shopAdapter;
    private List<GoodsBean> beanList = new ArrayList<>();


    public ShopFragment(){}

    public ShopFragment(Context context){
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView(){
        beanList.add(new GoodsBean("1", "https://s4.ax1x.com/2021/12/25/TUEV2j.jpg",
                "LED儿童智能魔方", 32.51, 128));
        beanList.add(new GoodsBean("2", "https://s4.ax1x.com/2021/12/25/TUEho8.jpg",
                "音乐机器人 小鸡爱萝卜", 127.72, 23));
        beanList.add(new GoodsBean("3", "https://s4.ax1x.com/2021/12/25/TUVvnI.jpg",
                "防贪睡闹钟", 88.88, 320));
        beanList.add(new GoodsBean("4", "https://s4.ax1x.com/2021/12/25/TUZVun.jpg",
                "水滴贪吃豆", 54.50, 58));
        beanList.add(new GoodsBean("5", "https://s4.ax1x.com/2021/12/25/TUZwCD.jpg",
                "益智游戏棋", 78.00, 61));
        beanList.add(new GoodsBean("6", "https://img13.360buyimg.com/n1/jfs/t12655/194/385600663/434041/a7d721d/5a0ab413N4f06e9f8.jpg",
                "幼儿教育：《我不想发脾气，我想好好说》", 25.31, 108));


        fragmentshop_rv = view.findViewById(R.id.fragmentshop_rv);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        fragmentshop_rv.setLayoutManager(layoutManager);
        shopAdapter = new ShopAdapter(beanList, context);
        fragmentshop_rv.setAdapter(shopAdapter);
    }
}
