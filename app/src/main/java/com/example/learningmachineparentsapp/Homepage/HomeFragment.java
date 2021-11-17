package com.example.learningmachineparentsapp.Homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.Control.ControlActivity;
import com.example.learningmachineparentsapp.Homepage.Homework.HwcheckActivity;
import com.example.learningmachineparentsapp.Homepage.Message.MessageActivity;
import com.example.learningmachineparentsapp.Homepage.Module.ModuleActivity;
import com.example.learningmachineparentsapp.Homepage.Videochat.VideochatActivity;
import com.example.learningmachineparentsapp.Homepage.Watch.WatchActivity;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;

import com.example.learningmachineparentsapp.View.RoundImageView;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

public class HomeFragment extends Fragment implements View.OnClickListener, BannerLayout.OnBannerItemClickListener{

    private Context context;
    private View view;
    private ImageView hp_iv_tovideochat, hp_iv_towatch, hp_iv_tohomework, hp_iv_tocontrol;
    private BannerLayout hp_bl_banner;
    private RecyclerViewBannerAdapter banner_horizontal;
    private ImageView hp_iv_tomessage;
    private DrawerLayout main_drawer;
    private RoundImageView hp_riv_icon;

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {

        hp_iv_tovideochat = getView().findViewById(R.id.hp_iv_tovideochat);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRPa79.png")
                .into(hp_iv_tovideochat);
        hp_iv_tovideochat.setOnClickListener(this);

        hp_iv_towatch = getView().findViewById(R.id.hp_iv_towatch);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRifrF.png")
                .into(hp_iv_towatch);
        hp_iv_towatch.setOnClickListener(this);

        hp_iv_tohomework = getView().findViewById(R.id.hp_iv_tohomework);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRis5n.png")
                .into(hp_iv_tohomework);
        hp_iv_tohomework.setOnClickListener(this);

        hp_iv_tocontrol = getView().findViewById(R.id.hp_iv_tocontrol);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRijqe.png")
                .into(hp_iv_tocontrol);
        hp_iv_tocontrol.setOnClickListener(this);

        hp_bl_banner = getView().findViewById(R.id.hp_bl_banner);
        hp_bl_banner.setAdapter(banner_horizontal = new RecyclerViewBannerAdapter(urls));
        hp_bl_banner.setOnClickListener(this);
        banner_horizontal.setOnBannerItemClickListener(this);

        hp_iv_tomessage = getView().findViewById(R.id.hp_iv_tomessage);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/10/IUE6Ve.png")
                .into(hp_iv_tomessage);
        hp_iv_tomessage.setOnClickListener(this);

        main_drawer = ((MainActivity)getActivity()).getDrawer();

        hp_riv_icon = getView().findViewById(R.id.hp_riv_icon);
        hp_riv_icon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hp_iv_tocontrol:
                startActivity(new Intent(getActivity(), ControlActivity.class));
                break;
            case R.id.hp_iv_towatch:
                startActivity(new Intent(getActivity(), WatchActivity.class));
                break;
            case R.id.hp_iv_tohomework:
                startActivity(new Intent(getActivity(), HwcheckActivity.class));
                break;
            case R.id.hp_iv_tovideochat:
                startActivity(new Intent(getActivity(), VideochatActivity.class));
                break;
            case R.id.hp_iv_tomessage:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.hp_riv_icon:
                main_drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.hp_bl_banner:
                startActivity(new Intent(getActivity(), ModuleActivity.class));
                break;
        }
    }


    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(getActivity(), ModuleActivity.class));
        Toast.makeText(getActivity(), "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
    }
}