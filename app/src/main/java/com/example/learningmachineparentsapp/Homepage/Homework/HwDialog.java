package com.example.learningmachineparentsapp.Homepage.Homework;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.okhttpClass;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HwDialog extends Dialog {

    private Context context;
    private TextView dialog_hw_time;
    private ImageView dialog_hw_cansel;
    private RecyclerView dialog_hw_rv;
    private List<ImageBean> list = new ArrayList<>();
    private ImageAdapter adapter;

    public HwDialog(@NonNull Context context, List<ImageBean> list, String tit) {
        super(context);
        this.context = context;
        this.list = list;
        initView(tit);
    }

    private void initView(String tit){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hw, null);
        dialog_hw_cansel = view.findViewById(R.id.dialog_hw_cansel);
        dialog_hw_cansel.setOnClickListener(v->{
            this.dismiss();
        });

        dialog_hw_time = view.findViewById(R.id.dialog_hw_time);
        dialog_hw_time.setText(tit);
        dialog_hw_rv = view.findViewById(R.id.dialog_hw_rv);

        // 垂直滑动
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        dialog_hw_rv.setLayoutManager(gridLayoutManager);
        // 关键就是这个地方
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                return position == 0 ? gridLayoutManager.getSpanCount() : 1;
                }
            }
        );

        // SnapHelper滑动对准
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(dialog_hw_rv);

        adapter = new ImageAdapter(context, list);
        dialog_hw_rv.setAdapter(adapter);
        super.setContentView(view);
    }
}