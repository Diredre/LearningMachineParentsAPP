package com.example.learningmachineparentsapp.Discover;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Discover.Cart.CartActivity;
import com.example.learningmachineparentsapp.Discover.fragment.FamilyFragment;
import com.example.learningmachineparentsapp.Discover.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;
import com.example.learningmachineparentsapp.R;

public class NotificationsFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Context context;
    private View view;
    private ViewPager discover_vp;
    private List<Fragment> frags = new ArrayList<>();
    private DiscoverPagerAdapter discoverPagerAdapter;
    private View discover_tv_shop_bg, discover_tv_family_bg;
    private TextView discover_tv_shop, discover_tv_family;
    private ImageView discover_iv_recomicon, discover_iv_cart, discover_iv1, discover_iv2,
            discover_iv3, discover_iv4, discover_iv5, discover_iv6, discover_iv7, discover_iv8;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        discover_iv1 = view.findViewById(R.id.discover_iv1);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TaHG7t.png")
                .into(discover_iv1);
        discover_iv2 = view.findViewById(R.id.discover_iv2);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TaHNh8.png")
                .into(discover_iv2);
        discover_iv3 = view.findViewById(R.id.discover_iv3);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/Ta7kIf.png")
                .into(discover_iv3);
        discover_iv4 = view.findViewById(R.id.discover_iv4);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TaThPU.png")
                .into(discover_iv4);
        discover_iv5 = view.findViewById(R.id.discover_iv5);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/Ta7NQJ.png")
                .into(discover_iv5);
        discover_iv6 = view.findViewById(R.id.discover_iv6);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TaHPl4.png")
                .into(discover_iv6);
        discover_iv7 = view.findViewById(R.id.discover_iv7);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TaT6rn.png")
                .into(discover_iv7);
        discover_iv8 = view.findViewById(R.id.discover_iv8);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/25/TaIFaT.png")
                .into(discover_iv8);

        discover_tv_shop_bg = view.findViewById(R.id.discover_tv_shop_bg);
        discover_tv_family_bg = view.findViewById(R.id.discover_tv_family_bg);
        discover_tv_shop = view.findViewById(R.id.discover_tv_shop);
        discover_tv_family = view.findViewById(R.id.discover_tv_family);
        resetState();

        discover_tv_shop.setOnClickListener(this);
        discover_tv_family.setOnClickListener(this);

        frags.add(new ShopFragment(getActivity()));
        frags.add(new FamilyFragment(getActivity()));

        discoverPagerAdapter = new DiscoverPagerAdapter(getChildFragmentManager(), frags);

        discover_vp = view.findViewById(R.id.discover_vp);
        discover_vp.addOnPageChangeListener(this);
        discover_vp.setAdapter(discoverPagerAdapter);

        discover_iv_recomicon = view.findViewById(R.id.discover_iv_recomicon);
        Glide.with(getActivity())
                .load("https://p3.ifengimg.com/2018_48/988CD5B9ACEE9EFB029973C1A9994747EC739736_w2953_h2953.jpg")
                .into(discover_iv_recomicon);

        discover_iv_cart = view.findViewById(R.id.discover_iv_cart);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/28/on6mM6.png")
                .into(discover_iv_cart);
        discover_iv_cart.setOnClickListener(this);
    }

    private void resetState(){
        discover_tv_shop_bg.setVisibility(View.VISIBLE);
        discover_tv_family_bg.setVisibility(View.INVISIBLE);
        discover_tv_shop.getPaint().setFakeBoldText(true);
        discover_tv_shop.setTextSize(20);
        discover_tv_family.getPaint().setFakeBoldText(false);
        discover_tv_family.setTextSize(16);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.discover_tv_shop:
                discover_vp.setCurrentItem(0);
                break;
            case R.id.discover_tv_family:
                discover_vp.setCurrentItem(1);
                break;
            case R.id.discover_iv_cart:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        resetState();
        switch (position) {
            case 0:
                discover_tv_shop_bg.setVisibility(View.VISIBLE);
                discover_tv_family_bg.setVisibility(View.INVISIBLE);
                discover_tv_shop.setTextSize(22);
                discover_tv_shop.getPaint().setFakeBoldText(true);
                discover_tv_family.setTextSize(16);
                discover_tv_family.getPaint().setFakeBoldText(false);
                break;
            case 1:
                discover_tv_shop_bg.setVisibility(View.INVISIBLE);
                discover_tv_family_bg.setVisibility(View.VISIBLE);
                discover_tv_shop.setTextSize(16);
                discover_tv_shop.getPaint().setFakeBoldText(false);
                discover_tv_family.setTextSize(20);
                discover_tv_family.getPaint().setFakeBoldText(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
}