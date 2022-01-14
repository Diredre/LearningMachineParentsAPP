package com.example.learningmachineparentsapp.Homepage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.Control.ControlActivity;
import com.example.learningmachineparentsapp.Homepage.Homework.HwcheckActivity;
import com.example.learningmachineparentsapp.Homepage.Message.MessageActivity;
import com.example.learningmachineparentsapp.Homepage.Module.ModuleActivity;
import com.example.learningmachineparentsapp.LoginRegist.scan;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.MyApp;
import com.example.learningmachineparentsapp.R;

import com.example.learningmachineparentsapp.View.RoundImageView;
import com.example.learningmachineparentsapp.webrtc.pojo.User;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocket;
import com.example.learningmachineparentsapp.webrtc.rtc.MyWebSocketService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private View view, hp_iv_havemes;
    private ImageView hp_iv_tovideochat, hp_iv_towatch, hp_iv_tohomework, hp_iv_tocontrol;
    private LineChart hp_lc;
    private ImageView hp_iv_tomessage, hp_iv_toscan;
    private TextView hp_tv_username;
    private DrawerLayout main_drawer;
    private RoundImageView hp_riv_icon;

    private SharedPreferences sp;
    private String name, childId, parentid;

    LineData mLineData;     // 线集合，所有折现以数组的形式存到此集合中
    XAxis mXAxis;           //X轴
    YAxis mLeftYAxis;       //左侧Y轴
    YAxis mRightYAxis;      //右侧Y轴
    Legend mLegend;         //图例
    LimitLine mLimitline;   //限制线

    private Random mRandom = new Random(); // 随机产生点
    private DecimalFormat mDecimalFormat = new DecimalFormat("#.00");   // 格式化浮点数位两位小数

    // Y值数据链表
    List<Float> mList = new ArrayList<>();
    // Chart需要的点数据链表
    List<Entry> mEntries = new ArrayList<>();
    // LineDataSet:点集合,即一条线
    LineDataSet mLineDataSet = new LineDataSet(mEntries, "每日使用时间总览");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sp = getActivity().getSharedPreferences("userInfo", 0);
        name = sp.getString("USER_NAME", "云淡风轻");

        parentid = sp.getString("PARENTID", "15");
        childId = sp.getString("CHILDID", "1");

        Log.e("newParentid", sp.getString("PARENTID", "15"));
        Log.e("newChildid", sp.getString("CHILDID", "1"));

        User user = new User();
        user.id=Integer.valueOf(parentid);
        user.childId=Integer.valueOf(childId);

        view = inflater.inflate(R.layout.fragment_home, container,false);
        MyApp ma = (MyApp)getActivity().getApplicationContext();
        ms = ma.myWebSocketService;


        MyWebSocket.getInstance().setOnNotifyListener(new MyWebSocket.OnNotifyListener() {
            @Override
            public void onNotify() {
                hp_iv_havemes.setVisibility(View.VISIBLE);
            }
        });
//        try {
//            getWebsocket(sp.getString("PARENTID", "1"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Log.e("", "onCreateView: "+ms );
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initChart();
    }


    private void initView() {
        hp_tv_username = getView().findViewById(R.id.hp_tv_username);
        hp_tv_username.setText(name);

        hp_iv_havemes = getView().findViewById(R.id.hp_iv_havemes);

        hp_iv_tovideochat = getView().findViewById(R.id.hp_iv_tovideochat);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRPa79.png")
                .into(hp_iv_tovideochat);
        hp_iv_tovideochat.setOnClickListener(this);

        hp_iv_towatch = getView().findViewById(R.id.hp_iv_towatch);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRifrF.png")
                .into(hp_iv_towatch);
        hp_iv_towatch.setOnClickListener(this);

        hp_iv_tohomework = getView().findViewById(R.id.hp_iv_tohomework);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRis5n.png")
                .into(hp_iv_tohomework);
        hp_iv_tohomework.setOnClickListener(this);

        hp_iv_tocontrol = getView().findViewById(R.id.hp_iv_tocontrol);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/15/IRijqe.png")
                .into(hp_iv_tocontrol);
        hp_iv_tocontrol.setOnClickListener(this);

        hp_iv_tomessage = getView().findViewById(R.id.hp_iv_tomessage);
        Glide.with(getActivity())
                .load("https://z3.ax1x.com/2021/11/10/IUE6Ve.png")
                .into(hp_iv_tomessage);
        hp_iv_tomessage.setOnClickListener(this);

        hp_iv_toscan = getView().findViewById(R.id.hp_iv_toscan);
        Glide.with(getActivity())
                .load("https://s4.ax1x.com/2022/01/10/7V7zI1.png")
                .into(hp_iv_toscan);
        hp_iv_toscan.setOnClickListener(this);

        main_drawer = ((MainActivity)getActivity()).getDrawer();

        hp_riv_icon = getView().findViewById(R.id.hp_riv_icon);
        hp_riv_icon.setOnClickListener(this);
    }


    private void initChart(){
        hp_lc = getView().findViewById(R.id.hp_lc);
        hp_lc.setOnClickListener(this);

        // 去除右下角
        Description description = new Description();
        description.setText("");
        hp_lc.setDescription(description);

        mXAxis = hp_lc.getXAxis(); // 得到x轴
        mLeftYAxis = hp_lc.getAxisLeft(); // 得到侧Y轴
        mRightYAxis = hp_lc.getAxisRight(); // 得到右侧Y轴
        mLegend = hp_lc.getLegend(); // 得到图例
        mLineData = new LineData();
        hp_lc.setData(mLineData);

        mList.add((float) 13.9);
        mList.add((float) 26.4);
        mList.add((float) 14.6);
        mList.add((float) 15.2);
        mList.add((float) 20.1);
        mList.add((float) 5.8);
        mList.add((float) 12.3);

        // 设置图标基本属性
        setChartBasicAttr(hp_lc);

        // 设置XY轴
        setXYAxis(hp_lc, mXAxis, mLeftYAxis, mRightYAxis);

        // 添加线条
        initLine();

        // 设置图例
        createLegend(mLegend);
    }

    /**
     * 功能：产生随机数（小数点两位）
     */
    public Float getRandom(Float seed) {
        return Float.valueOf(mDecimalFormat.format(mRandom.nextFloat() * seed));
    }

    /**
     * 功能：设置图标的基本属性
     */
    void setChartBasicAttr(LineChart lineChart) {
        lineChart.setDrawGridBackground(false); //是否展示网格线
        lineChart.setDrawBorders(false); //是否显示边界
        lineChart.setDragEnabled(false); //是否可以拖动
        lineChart.setScaleEnabled(false); // 是否可以缩放
        lineChart.setTouchEnabled(true); //是否有触摸事件
        //设置XY轴动画效果
        lineChart.animateY(1500);
        //lineChart.animateX(1500);
    }

    /**
     * 功能：设置XY轴
     */
    void setXYAxis(LineChart lineChart, XAxis xAxis, YAxis leftYAxis, YAxis rightYAxis) {
        /***XY轴的设置***/
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //X轴设置显示位置在底部
        xAxis.setAxisMinimum(0f); // 设置X轴的最小值
        xAxis.setAxisMaximum(20); // 设置X轴的最大值
        xAxis.setLabelCount(20, false); // 设置X轴的刻度数量，第二个参数表示是否平均分配
        xAxis.setGranularity(1f); // 设置X轴坐标之间的最小间隔
        lineChart.setVisibleXRangeMaximum(5);// 当前统计图表中最多在x轴坐标线上显示的总量
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);
        rightYAxis.setAxisMaximum(100f);
        leftYAxis.setGranularity(1f);
        rightYAxis.setGranularity(1f);
        leftYAxis.setLabelCount(20);
        lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.LEFT);// 当前统计图表中最多在Y轴坐标线上显示的总量
        lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.RIGHT);// 当前统计图表中最多在Y轴坐标线上显示的总量
        leftYAxis.setEnabled(false);

//        leftYAxis.setCenterAxisLabels(true);// 将轴标记居中
//        leftYAxis.setDrawZeroLine(true); // 原点处绘制 一条线
//        leftYAxis.setZeroLineColor(Color.RED);
//        leftYAxis.setZeroLineWidth(1f);
    }

    /**
     * 功能：对图表中的曲线初始化，并且默认显示第一条
     */
    void initLine() {

        createLine(mList, mEntries, mLineDataSet, Color.BLUE, mLineData, hp_lc);

        // mLineData.getDataSetCount() 总线条数
        // mLineData.getEntryCount() 总点数
        // mLineData.getDataSetByIndex(index).getEntryCount() 索引index处折线的总点数
        // 每条曲线添加到mLineData后，从索引0处开始排列
        for (int i = 0; i < mLineData.getDataSetCount(); i++) {
            hp_lc.getLineData().getDataSets().get(i).setVisible(false); //
        }
        showLine(0);
    }

    /**
     * 功能：根据索引显示或隐藏指定线条
     */
    public void showLine(int index) {
        hp_lc.getLineData()
                .getDataSets()
                .get(index)
                .setVisible(true);
        hp_lc.invalidate();
    }

    /**
     * 功能：动态创建一条曲线
     */
    private void createLine(List<Float> dataList, List<Entry> entries, LineDataSet lineDataSet, int color, LineData lineData, LineChart lineChart) {
        for (int i = 0; i < dataList.size(); i++) {
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, dataList.get(i));// Entry(x,y)
            entries.add(entry);
        }

        // 初始化线条
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);

        if (lineData == null) {
            lineData = new LineData();
            lineData.addDataSet(lineDataSet);
            lineChart.setData(lineData);
        } else {
            lineChart.getLineData().addDataSet(lineDataSet);
        }

        lineChart.invalidate();
    }


    /**
     * 曲线初始化设置,一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color); // 设置曲线颜色
        lineDataSet.setCircleColor(color);  // 设置数据点圆形的颜色
        lineDataSet.setDrawCircleHole(true);// 设置曲线值的圆点是否是空心
        lineDataSet.setLineWidth(1f); // 设置折线宽度
        lineDataSet.setCircleRadius(3f); // 设置折现点圆点半径
        lineDataSet.setValueTextSize(10f);

        lineDataSet.setDrawFilled(true); //设置折线图填充
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }

    }


    /**
     * 功能：创建图例
     */
    private void createLegend(Legend legend) {
        /***折线图例 标签 设置***/
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }


    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView(LineChart lineChart) {
        LineChartMarkViewDemo mv = new LineChartMarkViewDemo(getActivity());
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }


    /**
     * 动态添加数据
     * 在一个LineChart中存放的折线，其实是以索引从0开始编号的
     *
     * @param yValues y值
     */
    MyWebSocketService ms;
    public void addEntry(LineData lineData, LineChart lineChart, float yValues, int index) {

        // 通过索引得到一条折线，之后得到折线上当前点的数量
        int xCount = lineData.getDataSetByIndex(index).getEntryCount();

        Entry entry = new Entry(xCount, yValues); // 创建一个点
        lineData.addEntry(entry, index); // 将entry添加到指定索引处的折线中
        //通知数据已经改变
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();

        //把yValues移到指定索引的位置
        lineChart.moveViewToAnimated(xCount - 4, yValues, YAxis.AxisDependency.LEFT, 1000);// TODO: 2019/5/4 内存泄漏，异步 待修复
        lineChart.invalidate();
    }


    @Override
    public void onClick(View v) {
        if(ms==null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),"请稍后再试",Toast.LENGTH_SHORT).show();
                }
            });
            MyApp m = (MyApp)getActivity().getApplicationContext();
            ms = m.myWebSocketService;

        }else {
            switch (v.getId()) {
                case R.id.hp_iv_tocontrol:
                    startActivity(new Intent(getActivity(), ControlActivity.class));
                    break;
                case R.id.hp_iv_towatch:
                    //todo 家长监控
//                startActivity(new Intent(getActivity(), WatchActivity.class));
                    ms.monitor();
                    break;
                case R.id.hp_iv_tohomework:
                    startActivity(new Intent(getActivity(), HwcheckActivity.class));
                    break;
                case R.id.hp_iv_tovideochat:
                    //todo 视频通话
//                startActivity(new Intent(getActivity(), VideochatActivity.class));
                    ms.call();
                    break;
                case R.id.hp_iv_tomessage:
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                    break;
                case R.id.hp_riv_icon:
                    main_drawer.openDrawer(Gravity.LEFT);
                    break;
                case R.id.hp_lc:
                    //startActivity(new Intent(getActivity(), ModuleActivity.class));
                    break;
                case R.id.hp_iv_toscan:
                    startActivity(new Intent(getActivity(), scan.class));
                    break;
            }
        }
    }


    /**
     * 接收消息
     * @param pid
     * @throws JSONException
     */
    private void getWebsocket(String pid) throws JSONException {
        final WebSocket[] mwebsocket = new WebSocket[1];
        Request request = new Request.Builder().url("ws://192.168.31.73:8085/parentServer/" + pid).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.e("websocket_onClosed", "run() returned:");
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.e("websocket_onClosing", "run() returned:" + reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.e("websocket_onFailure", "run() returned:" + request.toString());
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                Log.e("websocket_onMessage", "run() returned:" + text);
                hp_iv_havemes.setVisibility(View.VISIBLE);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                mwebsocket[0] = webSocket;
                Log.e("websocket_onopen", "run() returned:" + "连接到服务器");
                hp_iv_havemes.setVisibility(View.VISIBLE);
            }
        });
    }
}