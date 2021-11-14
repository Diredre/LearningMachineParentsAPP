package com.example.learningmachineparentsapp.Discover.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learningmachineparentsapp.Discover.BeanAdapter.ShopAdapter;
import com.example.learningmachineparentsapp.Discover.BeanAdapter.ShopBean;
import com.example.learningmachineparentsapp.R;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private Context context;
    private View view;
    private RecyclerView fragmentshop_rv;
    private ShopAdapter shopAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();


    public ShopFragment(){}

    public ShopFragment(Context context){
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView(){
        for(int i = 0; i < 15; i++) {
            shopBeanList.add(new ShopBean("https://img13.360buyimg.com/n1/jfs/t12655/194/385600663/434041/a7d721d/5a0ab413N4f06e9f8.jpg",
                    "幼儿教育：《我不想发脾气，我想好好说》", 12.00, 108));
        }

        fragmentshop_rv = view.findViewById(R.id.fragmentshop_rv);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        fragmentshop_rv.setLayoutManager(layoutManager);
        shopAdapter = new ShopAdapter(shopBeanList, context);
        fragmentshop_rv.setAdapter(shopAdapter);
    }
}
