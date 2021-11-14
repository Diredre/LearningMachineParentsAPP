package com.example.learningmachineparentsapp.Circle;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.learningmachineparentsapp.Circle.Video.VideoActivity;
import com.example.learningmachineparentsapp.R;

public class DashboardFragment extends Fragment {

    private Context context;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container,false);
        return view;
    }
}