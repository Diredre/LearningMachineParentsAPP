package com.example.learningmachineparentsapp.Circle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Circle.Video.GSYVideoActivity;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.RoundImageView;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private DrawerLayout main_drawer;
    private Context context;
    private View view;
    private RelativeLayout circle_friend, circle_video;
    private ImageView circle_iv_friend, circle_iv_video;
    private RoundImageView hp_riv_icon;

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
        /*circle_friend = view.findViewById(R.id.circle_friend);
        circle_iv_friend = view.findViewById(R.id.circle_iv_friend);*/
        circle_video = view.findViewById(R.id.circle_video);
        circle_iv_video = view.findViewById(R.id.circle_iv_video);

        hp_riv_icon = view.findViewById(R.id.hp_riv_icon);
        hp_riv_icon.setOnClickListener(this);

        Glide.with(getContext())
                .load("https://s4.ax1x.com/2021/12/11/oTII9H.png")
                .into(circle_iv_video);
        /*Glide.with(getContext())
                .load("https://s4.ax1x.com/2021/12/11/oTI2B6.png")
                .into(circle_iv_friend);*/

        //circle_friend.setOnClickListener(this);
        circle_video.setOnClickListener(this);

        main_drawer = ((MainActivity)getActivity()).getDrawer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.circle_friend:
                Toast.makeText(getContext(), "111", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.circle_video:
                startActivity(new Intent(getActivity(), GSYVideoActivity.class));
                break;
            case R.id.hp_riv_icon:
                main_drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }
}