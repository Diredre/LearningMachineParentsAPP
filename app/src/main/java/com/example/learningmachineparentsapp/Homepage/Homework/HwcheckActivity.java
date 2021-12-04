package com.example.learningmachineparentsapp.Homepage.Homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class HwcheckActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView hwcheck_iv_add, hwcheck_iv_back;
    private RecyclerView hwcheck_rv;
    private HwcheckAdapter hwcheckAdapterr;
    private List<HomeworkBean> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hwcheck);

        initView();
    }

    private void initView(){
        hwcheck_iv_add = findViewById(R.id.hwcheck_iv_add);
        hwcheck_iv_add.setOnClickListener(this);

        hwcheck_iv_back = findViewById(R.id.hwcheck_iv_back);
        hwcheck_iv_back.setOnClickListener(this);

        hwcheck_rv = findViewById(R.id.hwcheck_rv);
        // 垂直滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hwcheck_rv.setLayoutManager(linearLayoutManager);

        // SnapHelper滑动对准
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(hwcheck_rv);

        list = initData();
        hwcheckAdapterr = new HwcheckAdapter(this, list);
        hwcheck_rv.setAdapter(hwcheckAdapterr);
    }

    private List<HomeworkBean> initData(){
        List<HomeworkBean> dataList = new ArrayList<>();
        dataList.add(new HomeworkBean("背九九乘法表", new Date(2021-1900, 11,1),
                true, new Date(System.currentTimeMillis())));
        dataList.add(new HomeworkBean("写完一元一次方程练习", new Date(2021-1900, 10,28),
                false, new Date(System.currentTimeMillis())));
        dataList.add(new HomeworkBean("默写静夜思", new Date(2021-1900, 11,5),
                false, new Date(System.currentTimeMillis())));
        dataList.add(new HomeworkBean("背九九乘法表", new Date(2021-1900, 11,1),
                true, new Date(System.currentTimeMillis())));
        dataList.add(new HomeworkBean("写完一元一次方程练习", new Date(2021-1900, 10,28),
                false, new Date(System.currentTimeMillis())));
        dataList.add(new HomeworkBean("默写静夜思", new Date(2021-1900, 11,5),
                false, new Date(System.currentTimeMillis())));

        return dataList;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.hwcheck_iv_add:
                startActivity(new Intent(this, HomeworkActivity.class));
                break;
            case R.id.hwcheck_iv_back:
                this.finish();
                break;
        }
    }
}