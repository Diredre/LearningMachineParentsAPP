package com.example.learningmachineparentsapp.Homepage.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
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
import com.example.learningmachineparentsapp.Homepage.Homework.HomeworkActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout control_tit;
    private ImageView control_iv_clock, control_iv_tel;
    private SwitchButton control_sb_clock, control_sb_tel;
    private LinearLayout control_ll_set_clock, control_ll_set_tel;
    private TextView control_tv_set_clock, control_tv_set_tel;
    private PieChart control_pc;
    private Button control_btn_adapt;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);

        initView();
        initChart();
    }

    private void initView(){
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
                }else{
                    control_ll_set_clock.setVisibility(View.INVISIBLE);
                }
            }
        });

        control_sb_tel = findViewById(R.id.control_sb_tel);
        control_sb_tel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    control_ll_set_tel.setVisibility(View.VISIBLE);
                }else{
                    control_ll_set_tel.setVisibility(View.INVISIBLE);
                }
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
     * 设置数据
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
                Toast.makeText(this, "应用成功！", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.control_tv_set_clock:
                showTimePickerDialog(this, 2, control_tv_set_clock, calendar);
                break;
            case R.id.control_tv_set_tel:
                showTimePickerDialog(this, 2, control_tv_set_tel, calendar);
                break;
        }
    }
}