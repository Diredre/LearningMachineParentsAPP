package com.example.learningmachineparentsapp.Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.learningmachineparentsapp.Circle.Upload.UploadActivity;
import com.example.learningmachineparentsapp.Homepage.Homework.HomeworkActivity;
import com.example.learningmachineparentsapp.Homepage.Message.MessageActivity;
import com.example.learningmachineparentsapp.Homepage.Watch.WatchActivity;
import com.example.learningmachineparentsapp.R;

import com.example.learningmachineparentsapp.Widget.RecyclerViewBannerAdapter;
import com.example.learningmachineparentsapp.databinding.FragmentHomeBinding;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

public class HomeFragment extends Fragment implements View.OnClickListener, BannerLayout.OnBannerItemClickListener{

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button hp_btn_toupload, hp_btn_towatch, hp_btn_tohomework, hp_btn_tomessage;
    private BannerLayout hp_bl_banner;
    private RecyclerViewBannerAdapter banner_horizontal;

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

        hp_btn_toupload = binding.hpBtnToupload;
        hp_btn_toupload.setOnClickListener(this);

        hp_btn_towatch = getView().findViewById(R.id.hp_btn_towatch);
        hp_btn_towatch.setOnClickListener(this);

        hp_btn_tohomework = getView().findViewById(R.id.hp_btn_tohomework);
        hp_btn_tohomework.setOnClickListener(this);

        hp_btn_tomessage = getView().findViewById(R.id.hp_btn_tomessage);
        hp_btn_tomessage.setOnClickListener(this);

        hp_bl_banner = getView().findViewById(R.id.hp_bl_banner);
        hp_bl_banner.setAdapter(banner_horizontal = new RecyclerViewBannerAdapter(urls));
        banner_horizontal.setOnBannerItemClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.hp_btn_toupload:
                startActivity(new Intent(getActivity(), UploadActivity.class));
                break;
            case R.id.hp_btn_towatch:
                startActivity(new Intent(getActivity(), WatchActivity.class));
                break;
            case R.id.hp_btn_tohomework:
                startActivity(new Intent(getActivity(), HomeworkActivity.class));
                break;
            case R.id.hp_btn_tomessage:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
        }
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
    }
}