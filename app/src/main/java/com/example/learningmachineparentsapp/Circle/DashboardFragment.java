package com.example.learningmachineparentsapp.Circle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Circle.Trend.FriendFragment;
import com.example.learningmachineparentsapp.Circle.Video.VideoActivity;
import com.example.learningmachineparentsapp.Circle.Video.VideoFragment;
import com.example.learningmachineparentsapp.Discover.fragment.FamilyFragment;
import com.example.learningmachineparentsapp.Discover.fragment.ShopFragment;
import com.example.learningmachineparentsapp.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private View view;
    private RelativeLayout circle_friend, circle_video;
    private ImageView circle_iv_friend, circle_iv_video;

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
        circle_friend = view.findViewById(R.id.circle_friend);
        circle_video = view.findViewById(R.id.circle_video);
        circle_iv_video = view.findViewById(R.id.circle_iv_video);
        circle_iv_friend = view.findViewById(R.id.circle_iv_friend);

        Glide.with(getContext())
                .load("")
                .into(circle_iv_video);
        Glide.with(getContext())
                .load("")
                .into(circle_iv_friend);

        circle_friend.setOnClickListener(this);
        circle_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_friend:
                Toast.makeText(getContext(), "111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.circle_video:
                startActivity(new Intent(getActivity(),VideoActivity.class));
                break;
        }
    }
}