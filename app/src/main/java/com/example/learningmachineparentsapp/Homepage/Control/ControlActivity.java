package com.example.learningmachineparentsapp.Homepage.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;
import com.example.learningmachineparentsapp.okhttpClass;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ControlActivity extends AppCompatActivity implements View.OnClickListener{

    public static int Control = 1;
    private TitleLayout control_tit;
    private ImageView control_iv_clock, control_iv_tel;
    private SwitchButton control_sb_clock, control_sb_tel;
    private LinearLayout control_ll_set_clock, control_ll_set_tel;
    private TextView control_tv_set_clock, control_tv_set_tel;
    private PieChart control_pc;
    private Button control_btn_adapt;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private static int hour = 0, min = 0;

    /*1:：绿色上网，2：互帮互学*/
    private String MId = "1";
    private String SId;

    private SharedPreferences sp;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Gson gson = new Gson();
            try{
                String s = (String)msg.obj;
                ControlDisplayGson controlDisplayGson = gson.fromJson(s, ControlDisplayGson.class);
                if(controlDisplayGson.getCode() == 200){
                    for(int i = 0; i < controlDisplayGson.getExtend().getSurfingInfo().size(); i++) {
                        int mid = controlDisplayGson.getExtend().getSurfingInfo().get(i).getModuleId();
                        boolean state = controlDisplayGson.getExtend().getSurfingInfo().get(i).getIfOpen() == 1;
                        int time = controlDisplayGson.getExtend().getSurfingInfo().get(i).getControlTime();
                        Log.e("state", ""+controlDisplayGson.getExtend().getSurfingInfo().get(i).getIfOpen());
                        Log.e("state", ""+controlDisplayGson.getExtend().getSurfingInfo().get(i).getControlTime());
                        Log.e("state", ""+time);
                        if(mid == 1){
                            control_sb_clock.setChecked(state);
                            if(state) {
                                control_tv_set_clock.setVisibility(View.VISIBLE);
                                control_tv_set_clock.setText(time/3600+"小时"+time%3600/60+"分钟");
                            }
                        }else{
                            control_sb_tel.setChecked(state);
                            if(state) {
                                control_tv_set_tel.setVisibility(View.VISIBLE);
                                control_tv_set_tel.setText(time/3600+"小时"+time%3600/60+"分钟");
                            }
                        }
                    }
                }
            } catch (JsonSyntaxException e) {
                    e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);

        sp = getSharedPreferences("userInfo", 0);
        SId = sp.getString("CHILDID", "1050");
        Log.e("CHILDID", SId);

        getSearchTime();

        initView();
        initChart();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        control_tit = findViewById(R.id.control_tit);
        control_tit.setTitle("上网控制");

        control_iv_clock = findViewById(R.id.control_iv_clock);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/25/oFvVLn.png")
                .into(control_iv_clock);

        control_iv_tel = findViewById(R.id.control_iv_tel);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/11/oTInnP.png")
                .into(control_iv_tel);

        control_sb_clock = findViewById(R.id.control_sb_clock);
        control_sb_clock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    control_ll_set_clock.setVisibility(View.VISIBLE);
                    MId = "1";
                }else{
                    control_ll_set_clock.setVisibility(View.INVISIBLE);
                }
                updateOpenState(SId, MId);
            }
        });

        control_sb_tel = findViewById(R.id.control_sb_tel);
        control_sb_tel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    control_ll_set_tel.setVisibility(View.VISIBLE);
                    MId = "2";
                }else{
                    control_ll_set_tel.setVisibility(View.INVISIBLE);
                }
                updateOpenState(SId, MId);
            }
        });

        control_ll_set_clock = findViewById(R.id.control_ll_set_clock);
        control_ll_set_clock.setVisibility(View.INVISIBLE);
        control_ll_set_tel = findViewById(R.id.control_ll_set_tel);
        control_ll_set_tel.setVisibility(View.INVISIBLE);

        control_tv_set_clock = findViewById(R.id.control_tv_set_clock);
        control_tv_set_clock.setOnClickListener(this);

        control_tv_set_tel = findViewById(R.id.control_tv_set_tel);
        control_tv_set_tel.setOnClickListener(this);

        control_btn_adapt = findViewById(R.id.control_btn_adapt);
        control_btn_adapt.setOnClickListener(this);
    }

    private void initChart(){
        control_pc = findViewById(R.id.control_pc);
        control_pc.setUsePercentValues(true);
        control_pc.getDescription().setEnabled(false);
        control_pc.setExtraOffsets(5, 10, 5, 5);

        control_pc.setDragDecelerationFrictionCoef(0.95f);
        // 设置中间文件
        control_pc.setCenterText(generateCenterSpannableText());

        control_pc.setDrawHoleEnabled(true);
        control_pc.setHoleColor(Color.WHITE);

        control_pc.setTransparentCircleColor(Color.WHITE);
        control_pc.setTransparentCircleAlpha(110);

        control_pc.setHoleRadius(58f);
        control_pc.setTransparentCircleRadius(61f);

        control_pc.setDrawCenterText(true);

        control_pc.setRotationAngle(0);
        // 触摸旋转
        control_pc.setRotationEnabled(true);
        control_pc.setHighlightPerTapEnabled(true);

        // 去除右下角
        Description description = new Description();
        description.setText("");
        control_pc.setDescription(description);

        // 变化监听
        //control_pc.setOnChartValueSelectedListener(this);

        // 模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(18, "娱乐"));
        entries.add(new PieEntry(59, "学习"));
        entries.add(new PieEntry(23, "视频"));

        // 设置数据
        setData(entries);

        control_pc.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = control_pc.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        control_pc.setEntryLabelColor(Color.WHITE);
        control_pc.setEntryLabelTextSize(12f);
    }

    /**
     * 设置中间文字
     */
    private SpannableString generateCenterSpannableText() {
        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
        SpannableString s = new SpannableString("近七天时间分析");
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    /**
     * 设置图表数据
     */
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        control_pc.setData(data);
        control_pc.highlightValues(null);
        //刷新
        control_pc.invalidate();
    }


    /**
     * 时间选择
     */
    public static void showTimePickerDialog(Context context, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        new TimePickerDialog(context, themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText("" + hourOfDay + "小时" + minute + "分");
                        hour = hourOfDay;
                        min = minute;
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.control_btn_adapt:
                int time = hour * 3600 + min * 60;
                sendControlData(SId, MId, ""+time);
                break;
            case R.id.control_tv_set_clock:
                showTimePickerDialog(this, 2, control_tv_set_clock, calendar);
                break;
            case R.id.control_tv_set_tel:
                showTimePickerDialog(this, 2, control_tv_set_tel, calendar);
                break;
        }
    }


    private void updateOpenState(String SId,String MId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("SId",SId)
                        .addFormDataPart("MId",MId)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/parent"+"/updateOpenState")
                        .post(body)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) { }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String response_answer = response.body().string();
                        Log.e("updateOpenState", response_answer);
                    }
                });
            }
        }).start();
    }

    private void sendControlData(String SId, String MId, String time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("SId",SId)
                        .addFormDataPart("MId",MId)
                        .addFormDataPart("time",time)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/parent"+"/updateControlTime")
                        .post(body)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) { }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String response_answer = response.body().string();
                        Log.e("sendControlData", response_answer);
                        Gson gson = new Gson();
                        ControlGson controlGson = gson.fromJson(response_answer, ControlGson.class);
                        if(controlGson.getCode() == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ControlActivity.this,"应用成功",Toast.LENGTH_SHORT).show();
                                    ControlActivity.this.finish();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ControlActivity.this,"应用失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    public void getSearchTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/parent"+"/getSurfingInfo?SId="+SId)
                        .get()
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("getSearchTime", s);
                        Message msg = new Message();
                        msg.what = Control;
                        msg.obj = s;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }

}