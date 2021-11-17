package com.example.learningmachineparentsapp.Homepage;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.learningmachineparentsapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.strategy.DiskCacheStrategyEnum;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuexiang
 * @since 2019-05-30 00:50
 */
public class RecyclerViewBannerAdapter extends BaseRecyclerAdapter<String> {

    /**
     * 默认加载图片
     */
    private ColorDrawable mColorDrawable;

    /**
     * 是否允许进行缓存
     */
    private boolean mEnableCache = true;

    private BannerLayout.OnBannerItemClickListener mOnBannerItemClickListener;


    public RecyclerViewBannerAdapter() {
        super();
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    public RecyclerViewBannerAdapter(List<String> list) {
        super(list);
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    public RecyclerViewBannerAdapter(String[] list) {
        super(Arrays.asList(list));
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    /**
     * 适配的布局
     */
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_main_banner;
    }

    /**
     * 绑定数据
     */
    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, final int position, String imgUrl) {
        //ImageView imageView = holder.findViewById(R.id.item_mb_iv);
        LineChart item_mb_lc = holder.findViewById(R.id.item_mb_lc);
        item_mb_lc.setOnClickListener(v->{
            if (mOnBannerItemClickListener != null) {
                mOnBannerItemClickListener.onItemClick(position);
            }
        });
        /*if (!TextUtils.isEmpty(imgUrl)) {
            ImageLoader.get().loadImage(imageView, imgUrl, mColorDrawable,
                    mEnableCache ? DiskCacheStrategyEnum.RESOURCE : DiskCacheStrategyEnum.NONE);
        } else {
            imageView.setImageDrawable(mColorDrawable);
        }
        imageView.setOnClickListener(v -> {
            if (mOnBannerItemClickListener != null) {
                mOnBannerItemClickListener.onItemClick(position);
            }
        });*/
    }

    /**
     * 设置是否允许缓存
     */
    public RecyclerViewBannerAdapter enableCache(boolean enableCache) {
        mEnableCache = enableCache;
        return this;
    }

    /**
     * 获取是否允许缓存
     */
    public boolean getEnableCache() {
        return mEnableCache;
    }

    public ColorDrawable getColorDrawable() {
        return mColorDrawable;
    }

    public RecyclerViewBannerAdapter setColorDrawable(ColorDrawable colorDrawable) {
        mColorDrawable = colorDrawable;
        return this;
    }

    public RecyclerViewBannerAdapter setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        mOnBannerItemClickListener = onBannerItemClickListener;
        return this;
    }
}
