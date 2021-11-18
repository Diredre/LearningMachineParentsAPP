package com.example.learningmachineparentsapp.Homepage.Module;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Utils.AnimationUtils;
import com.example.learningmachineparentsapp.Utils.ShowUtils;
import com.example.learningmachineparentsapp.View.TitleLayout;
import com.example.learningmachineparentsapp.Utils.ChartUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 学习机使用功能模块时间分析界面
 */
public class ModuleActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout module_tit;

    private TextView tvDate;
    private ImageView ivDate;
    private LineChart moudule_lc_time;
    private static final String[] dates = new String[]{"今日", "本周", "本月"};
    private List<String> dateList = Arrays.asList(dates);

    private BarChart module_bc_chart;
    private TextView module_tv_time, module_tv_usetime;
    private List<BarEntry> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_module);

        initView();
    }


    private void initView(){
        module_tit = findViewById(R.id.module_tit);
        module_tit.setTitle("使用情况");

        tvDate = (TextView) findViewById(R.id.module_tv_date);
        ivDate = (ImageView) findViewById(R.id.module_iv_date);
        ivDate.setColorFilter(Color.WHITE);
        tvDate.setOnClickListener(this);
        ivDate.setOnClickListener(this);
        moudule_lc_time = findViewById(R.id.moudule_lc_time);
        ChartUtils.initChart(moudule_lc_time);
        ChartUtils.notifyDataSetChanged(moudule_lc_time, getData(), ChartUtils.dayValue);

        module_bc_chart = findViewById(R.id.module_bc_chart);
        chart();

        module_tv_time = findViewById(R.id.module_tv_time);
        Date time = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        module_tv_time.setText(df.format(time));

        module_tv_usetime = findViewById(R.id.module_tv_usetime);
    }


    private void chart(){
        //添加数据
        list.add(new BarEntry(1,7));     //其中两个数字对应的分别是   X轴   Y轴
        list.add(new BarEntry(2,13));
        list.add(new BarEntry(3,12));
        list.add(new BarEntry(4,6));
        list.add(new BarEntry(5,3));

        BarDataSet barDataSet = new BarDataSet(list,"时间");   //list是你这条线的数据  "语文" 是你对这条线的描述
        BarData barData = new BarData(barDataSet);
        module_bc_chart.setData(barData);

        //背景
        module_bc_chart.setBackgroundColor(0x00FFFFFF);          //背景颜色
        module_bc_chart.getXAxis().setDrawGridLines(false);      //是否绘制X轴上的网格线（背景里面的竖线）
        module_bc_chart.getAxisLeft().setDrawGridLines(false);   //是否绘制Y轴上的网格线（背景里面的横线）

        //对于右下角一串字母的操作
        module_bc_chart.getDescription().setEnabled(false);                //是否显示右下角描述
        module_bc_chart.getDescription().setText("这是修改那串英文的方法");    //修改右下角字母的显示
        module_bc_chart.getDescription().setTextSize(20);                  //字体大小
        module_bc_chart.getDescription().setTextColor(Color.RED);          //字体颜色

        //图例
        Legend legend = module_bc_chart.getLegend();
        legend.setEnabled(false);    //是否显示图例
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);    //图例的位置

        //X轴
        XAxis xAxis = module_bc_chart.getXAxis();
        xAxis.setDrawGridLines(false);          //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.setAxisLineColor(0xFFD7D7D7);     //X轴颜色
        xAxis.setAxisLineWidth(1);              //X轴粗细
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
        xAxis.setValueFormatter(new IAxisValueFormatter() {   //X轴自定义坐标
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v == 1){
                    return "故事";
                }
                if (v == 2){
                    return "视频";
                }
                if (v == 3){
                    return "学习";
                }
                if (v == 4){
                    return "作业";
                }
                if (v == 5){
                    return "社交";
                }
                return "";
            }
        });
        xAxis.setAxisMaximum(6);   //X轴最大数值
        xAxis.setAxisMinimum(0);   //X轴最小数值
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        xAxis.setLabelCount(6,false);

        //Y轴
        YAxis AxisLeft = module_bc_chart.getAxisLeft();
        AxisLeft.setDrawGridLines(true);  //是否绘制Y轴上的网格线（背景里面的横线）
        AxisLeft.setAxisLineColor(0xFFD7D7D7);  //Y轴颜色
        AxisLeft.setAxisLineWidth(2);           //Y轴粗细
        /*AxisLeft.setValueFormatter(new IAxisValueFormatter() {  //Y轴自定义坐标
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                for (int a = 0 ;a < 16 ;a++){
                    if (a == v){
                        return "第"+a+"个";
                    }
                }
                return "";
            }
        });*/
        AxisLeft.setAxisMaximum(15);    //Y轴最大数值
        AxisLeft.setAxisMinimum(0);     //Y轴最小数值
        //Y轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        AxisLeft.setLabelCount(15,false);

        //是否隐藏右边的Y轴（不设置的话有两条Y轴 同理可以隐藏左边的Y轴）
        module_bc_chart.getAxisLeft().setEnabled(false);

        //柱子
        barDataSet.setColor(0xFF3DDC84);                //柱子的颜色
        //barDataSet.setColors(Color.BLACK,Color.BLUE);   //设置柱子多种颜色  循环使用
        barDataSet.setBarBorderColor(Color.WHITE);      //柱子边框颜色
        barDataSet.setBarBorderWidth(0);                //柱子边框厚度
        barDataSet.setBarShadowColor(Color.RED);
        barDataSet.setHighlightEnabled(true);          //选中柱子是否高亮显示  默认为true
        barDataSet.setStackLabels(new String[]{"aaa","bbb","ccc"});
        //定义柱子上的数据显示    可以实现加单位    以及显示整数（默认是显示小数）
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                if (entry.getY() == v){
                    return v + "min";
                }
                return "";
            }
        });

        //数据更新
        module_bc_chart.notifyDataSetChanged();
        module_bc_chart.invalidate();

        //动画（如果使用了动画可以则省去更新数据的那一步）
        module_bc_chart.animateY(2000); //在Y轴的动画  参数是动画执行时间 毫秒为单位
        //watch_bc_chart.animateX(2000); //X轴动画
        //watch_bc_chart.animateXY(2000,2000);//XY两轴混合动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.module_tv_date:
            case R.id.module_iv_date:
                String data = tvDate.getText().toString();
                if (!ShowUtils.isPopupWindowShowing()) {
                    AnimationUtils.startModeSelectAnimation(ivDate, true);
                    ShowUtils.showPopupWindow(this, tvDate, 90, 166, dateList,
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    ShowUtils.updatePopupWindow(position);
                                    AnimationUtils.startModeSelectAnimation(ivDate, false);
                                    ShowUtils.popupWindowDismiss();
                                    tvDate.setText(dateList.get(position));
                                    // 更新图表
                                    ChartUtils.notifyDataSetChanged(moudule_lc_time, getData(), position);
                                }
                            });
                } else {
                    AnimationUtils.startModeSelectAnimation(ivDate, false);
                    ShowUtils.popupWindowDismiss();
                }

                if (dateList.get(0).equals(data)) {
                    ShowUtils.updatePopupWindow(0);
                } else if (dateList.get(1).equals(data)) {
                    ShowUtils.updatePopupWindow(1);
                } else if (dateList.get(2).equals(data)) {
                    ShowUtils.updatePopupWindow(2);
                }
                break;

            default:
                break;
        }
    }

    private List<Entry> getData() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 15));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 20));
        return values;
    }
}