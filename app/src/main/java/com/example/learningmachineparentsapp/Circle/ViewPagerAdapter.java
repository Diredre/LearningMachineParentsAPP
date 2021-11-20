package com.example.learningmachineparentsapp.Circle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentLists = new ArrayList<>();

    ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentLists) {
        super(fm);
        this.mFragmentLists = mFragmentLists;
    }

    //获取Fragment
    @Override
    public Fragment getItem(int position) {

        return mFragmentLists.get(position);
    }

    //总共有mFragmentLists.size()个需要显示
    @Override
    public int getCount() {

        return mFragmentLists.size();
    }
}