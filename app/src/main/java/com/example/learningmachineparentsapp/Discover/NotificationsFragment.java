package com.example.learningmachineparentsapp.Discover;

import android.content.Context;
import android.content.Intent;
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
    private ImageView discover_iv_recomicon;


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
        discover_tv_shop_bg = view.findViewById(R.id.discover_tv_shop_bg);
        discover_tv_family_bg = view.findViewById(R.id.discover_tv_family_bg);
        discover_tv_shop = view.findViewById(R.id.discover_tv_shop);
        discover_tv_shop.getPaint().setFakeBoldText(true);
        discover_tv_shop.setTextSize(20);
        discover_tv_family = view.findViewById(R.id.discover_tv_family);
        discover_tv_family.getPaint().setFakeBoldText(false);
        discover_tv_family.setTextSize(16);

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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.discover_tv_shop:
                discover_tv_shop_bg.setVisibility(View.VISIBLE);
                discover_tv_family_bg.setVisibility(View.INVISIBLE);
                discover_tv_shop.setTextSize(22);
                discover_tv_shop.getPaint().setFakeBoldText(true);
                discover_tv_family.setTextSize(16);
                discover_tv_family.getPaint().setFakeBoldText(false);
                discover_vp.setCurrentItem(0);
                break;
            case R.id.discover_tv_family:
                discover_tv_shop_bg.setVisibility(View.INVISIBLE);
                discover_tv_family_bg.setVisibility(View.VISIBLE);
                discover_tv_shop.setTextSize(16);
                discover_tv_shop.getPaint().setFakeBoldText(false);
                discover_tv_family.setTextSize(20);
                discover_tv_family.getPaint().setFakeBoldText(true);
                discover_vp.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
    public void onPageSelected(int position) { }

    @Override
    public void onPageScrollStateChanged(int state) { }
}