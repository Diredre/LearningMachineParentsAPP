package com.example.learningmachineparentsapp.Homepage.Homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.okhttpClass;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class HwcheckActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int UPDATEHW = 1;
    public static final int UPDATECH = 0;
    public HomeworkGson homeworkGson;
    public ChildrenHwGson childrenHwGson;

    private ImageView hwcheck_iv_add, hwcheck_iv_back, hwcheck_iv_history;
    private RecyclerView hwcheck_rv;
    private HwcheckAdapter hwcheckAdapterr;
    private List<HomeworkBean> list = new ArrayList<>();

    private String SId;
    private SharedPreferences sp;

    int lastVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    /**
     * 当前页面的下标
     */
    private int pageIndex = 1;

    //是否有下一页
    private boolean hasNextPage;

    //主线程创建消息处理器
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Gson gson = new Gson();

            if(msg.what == UPDATEHW){
                try{
                    String str = (String)msg.obj;
                    homeworkGson = gson.fromJson(str, HomeworkGson.class);

                    for(int i = 0; i < homeworkGson.getExtend().getPageInfo().getList().size(); i++) {
                        List<ImageBean> imgList = new ArrayList<>();
                        for(int j = 0; j < homeworkGson.getExtend().getPageInfo().getList().get(i).getPicList().size(); j++) {
                            ImageBean img = new ImageBean(homeworkGson.getExtend().getPageInfo().getList().get(i).getPicList().get(j));
                            Log.e("hwpiclist", img.getImgurl() + " i=" + i + " j=" + j);
                            imgList.add(img);
                        }
                        HomeworkBean hb = new HomeworkBean(
                                homeworkGson.getExtend().getPageInfo().getList().get(i).getHomework(),
                                homeworkGson.getExtend().getPageInfo().getList().get(i).getClosingDate(),
                                homeworkGson.getExtend().getPageInfo().getList().get(i).getIsFinish(),
                                homeworkGson.getExtend().getPageInfo().getList().get(i).getUpdateTime(),
                                imgList);
                        list.add(hb);
                        Log.e("hwpiclistsize", String.valueOf(imgList.size()));
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
            if(msg.what == UPDATECH){
                String str = (String)msg.obj;
                childrenHwGson = gson.fromJson(str, ChildrenHwGson.class);
                for(int i = 0; i < childrenHwGson.getExtend().getResult().size(); i++){
                    List<ImageBean> imgList = new ArrayList<>();
                    for(int j = 0; j < childrenHwGson.getExtend().getResult().get(i).getPicList().size(); j++) {
                        ImageBean img = new ImageBean(childrenHwGson.getExtend().getResult().get(i).getPicList().get(j));
                        Log.e("chpiclist", img.getImgurl() + " i=" + i + " j=" + j);
                        imgList.add(img);
                    }
                    HomeworkBean hb = new HomeworkBean(
                            "孩子提交",
                            childrenHwGson.getExtend().getResult().get(i).getPicDate(),
                            2,
                            childrenHwGson.getExtend().getResult().get(i).getUpdateTime(),
                            imgList);
                    list.add(hb);
                    Log.e("chpiclistsize", String.valueOf(imgList.size()));
                }
            }

            hwcheckAdapterr = new HwcheckAdapter(HwcheckActivity.this, list);
            hwcheck_rv.setAdapter(hwcheckAdapterr);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hwcheck);

        sp = getSharedPreferences("userInfo", 0);
        SId = sp.getString("CHILDID", "1050");
        Log.e("CHILDID", SId);

        initView();
        startData("1", "1", "3");
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.solar_background));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        hwcheck_iv_add = findViewById(R.id.hwcheck_iv_add);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/19/TZMPUK.png")
                .into(hwcheck_iv_add);
        hwcheck_iv_add.setOnClickListener(this);

        hwcheck_iv_history = findViewById(R.id.hwcheck_iv_history);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/19/TZMBPU.png")
                .into(hwcheck_iv_history);
        hwcheck_iv_history.setOnClickListener(this);

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

    }

    private void startData(String studentId, String pn, String pageSize){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tools1 = new okhttpClass();
                String str = tools1.getHomeworkList(studentId, pn, pageSize);
                //通知主线程更新UI
                Message message = new Message();
                message.what = UPDATEHW;
                message.obj = str;
                handler.sendMessage(message);

                okhttpClass tool = new okhttpClass();
                String res = tool.getHomeworkPic(studentId);
                //通知主线程更新UI
                Message message1 = new Message();
                message1.what = UPDATECH;
                message1.obj = res;
                handler.sendMessage(message1);
            }
        }).start();
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
            case R.id.hwcheck_iv_history:
                startActivity(new Intent(this, RecordActivity.class));
                break;
        }
    }

}