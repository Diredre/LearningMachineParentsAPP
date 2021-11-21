package com.example.learningmachineparentsapp.Circle;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.learningmachineparentsapp.Circle.Trend.FriendFragment;
import com.example.learningmachineparentsapp.Circle.Video.VideoActivity;
import com.example.learningmachineparentsapp.Circle.Video.VideoFragment;
import com.example.learningmachineparentsapp.Discover.fragment.FamilyFragment;
import com.example.learningmachineparentsapp.Discover.fragment.ShopFragment;
import com.example.learningmachineparentsapp.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener{

    private Context context;
    private View view, circle_tv_friend_bg, circle_tv_video_bg;
    private TextView circle_tv_friend, circle_tv_video;
    private ImageView circle_iv_upload;
    private ViewPager circle_vp;
    private List<Fragment> mFragmentLists;
    private ViewPagerAdapter mAdater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container,false);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView(){
        circle_tv_friend_bg = view.findViewById(R.id.circle_tv_friend_bg);
        circle_tv_video_bg = view.findViewById(R.id.circle_tv_video_bg);
        circle_tv_friend = view.findViewById(R.id.circle_tv_friend);
        circle_tv_video = view.findViewById(R.id.circle_tv_video);
        resetState();

        circle_tv_friend.setOnClickListener(this);
        circle_tv_video.setOnClickListener(this);

        mFragmentLists = new ArrayList<>();
        mFragmentLists.add(new FriendFragment(getActivity()));
        mFragmentLists.add(new VideoFragment(getActivity()));

        circle_iv_upload = view.findViewById(R.id.circle_iv_upload);

        circle_vp = view.findViewById(R.id.circle_vp);
        mAdater = new ViewPagerAdapter(getChildFragmentManager(), mFragmentLists);
        circle_vp.addOnPageChangeListener(this);
        circle_vp.setAdapter(mAdater);
    }

    private void resetState(){
        circle_tv_friend_bg.setVisibility(View.VISIBLE);
        circle_tv_video_bg.setVisibility(View.INVISIBLE);
        circle_tv_friend.getPaint().setFakeBoldText(true);
        circle_tv_friend.setTextSize(22);
        circle_tv_video.getPaint().setFakeBoldText(false);
        circle_tv_video.setTextSize(17);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_tv_friend:
                circle_vp.setCurrentItem(0);
                break;
            case R.id.circle_tv_video:
                circle_vp.setCurrentItem(1);
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
                circle_tv_friend_bg.setVisibility(View.VISIBLE);
                circle_tv_video_bg.setVisibility(View.INVISIBLE);
                circle_tv_friend.setTextSize(20);
                circle_tv_friend.getPaint().setFakeBoldText(true);
                circle_tv_video.setTextSize(17);
                circle_tv_video.getPaint().setFakeBoldText(false);
                break;
            case 1:
                circle_tv_friend_bg.setVisibility(View.INVISIBLE);
                circle_tv_video_bg.setVisibility(View.VISIBLE);
                circle_tv_friend.setTextSize(17);
                circle_tv_friend.getPaint().setFakeBoldText(false);
                circle_tv_video.setTextSize(20);
                circle_tv_video.getPaint().setFakeBoldText(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
}