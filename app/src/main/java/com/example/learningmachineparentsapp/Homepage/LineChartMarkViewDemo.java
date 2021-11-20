package com.example.learningmachineparentsapp.Homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

import com.example.learningmachineparentsapp.R;

public class LineChartMarkViewDemo extends MarkerView {

    DecimalFormat df = new DecimalFormat(".00");
    private TextView mXValueTv;
    private TextView mYValueTv;

    public LineChartMarkViewDemo(Context context) {
        super(context, R.layout.layout_markview);

        mXValueTv = findViewById(R.id.xValues_tv);
        mYValueTv = findViewById(R.id.yValue_tv);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        //展示自定义X轴值 后的X轴内容
        mXValueTv.setText("X = " + df.format(e.getX()));
        mYValueTv.setText("Y = " + df.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}