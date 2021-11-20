package com.example.learningmachineparentsapp.Circle.Video;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.learningmachineparentsapp.R;

public class VideoFragment extends Fragment {

    private Context context;
    private View view;


    public VideoFragment(){}

    public VideoFragment(Context context){
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_circle_video, container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView(){}
}
