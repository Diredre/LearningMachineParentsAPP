package com.example.learningmachineparentsapp.Discover;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class DiscoverPagerAdapter extends FragmentPagerAdapter  {

    // 外部传入的ViewPager对应的Item对象
    private List<Fragment> mList;

    public DiscoverPagerAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }
}