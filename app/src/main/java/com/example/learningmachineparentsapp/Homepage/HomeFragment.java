package com.example.learningmachineparentsapp.Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.Control.ControlActivity;
import com.example.learningmachineparentsapp.Homepage.Homework.HomeworkActivity;
import com.example.learningmachineparentsapp.Homepage.Message.MessageActivity;
import com.example.learningmachineparentsapp.Homepage.Videochat.VideochatActivity;
import com.example.learningmachineparentsapp.Homepage.Watch.WatchActivity;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;

import com.example.learningmachineparentsapp.View.RoundImageView;
import com.example.learningmachineparentsapp.Widget.RecyclerViewBannerAdapter;
import com.example.learningmachineparentsapp.databinding.FragmentHomeBinding;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

public class HomeFragment extends Fragment implements View.OnClickListener, BannerLayout.OnBannerItemClickListener{

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button hp_btn_tovideochat, hp_btn_towatch, hp_btn_tohomework, hp_btn_tocontrol;
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
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {

        hp_btn_tovideochat = getView().findViewById(R.id.hp_btn_tovideochat);
        hp_btn_tovideochat.setOnClickListener(this);

        hp_btn_towatch = getView().findViewById(R.id.hp_btn_towatch);
        hp_btn_towatch.setOnClickListener(this);

        hp_btn_tohomework = getView().findViewById(R.id.hp_btn_tohomework);
        hp_btn_tohomework.setOnClickListener(this);

        hp_btn_tocontrol = getView().findViewById(R.id.hp_btn_tocontrol);
        hp_btn_tocontrol.setOnClickListener(this);

        hp_bl_banner = getView().findViewById(R.id.hp_bl_banner);
        hp_bl_banner.setAdapter(banner_horizontal = new RecyclerViewBannerAdapter(urls));
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
            case R.id.hp_btn_tocontrol:
                startActivity(new Intent(getActivity(), ControlActivity.class));
                break;
            case R.id.hp_btn_towatch:
                startActivity(new Intent(getActivity(), WatchActivity.class));
                break;
            case R.id.hp_btn_tohomework:
                startActivity(new Intent(getActivity(), HomeworkActivity.class));
                break;
            case R.id.hp_btn_tovideochat:
                startActivity(new Intent(getActivity(), VideochatActivity.class));
                break;
            case R.id.hp_iv_tomessage:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.hp_riv_icon:
                main_drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
    }
}