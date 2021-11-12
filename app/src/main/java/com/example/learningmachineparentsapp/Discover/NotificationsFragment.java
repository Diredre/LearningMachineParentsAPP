package com.example.learningmachineparentsapp.Discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.learningmachineparentsapp.Discover.fragment.FamilyFragment;
import com.example.learningmachineparentsapp.Discover.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;
import com.example.learningmachineparentsapp.R;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private View view;
    private ViewPager discover_vp;
    private List<Fragment> frags = new ArrayList<>();
    private DiscoverPagerAdapter discoverPagerAdapter;
    private View discover_tv_shop_bg, discover_tv_family_bg;
    private TextView discover_tv_shop, discover_tv_family;


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
        discover_tv_family = view.findViewById(R.id.discover_tv_family);

        frags.add(new ShopFragment(getActivity()));
        frags.add(new FamilyFragment(getActivity()));
        //discoverPagerAdapter = new DiscoverPagerAdapter(frags);
        discover_vp = view.findViewById(R.id.discover_vp);
        //discover_vp.setAdapter(discoverPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //case R.id.
        }
    }
}