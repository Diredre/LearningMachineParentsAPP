package com.example.learningmachineparentsapp.Circle.Video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import com.example.learningmachineparentsapp.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningmachineparentsapp.Utils.DpTools;
import com.example.learningmachineparentsapp.Utils.ScrollCalculatorHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GSYVideoActivity extends AppCompatActivity {

    private static String TAG = "GSYVIDEOACTIVITY";
    private RecyclerView recyclerView;
    private List<GSYVideoBean> list;
    private String[] urls = {"http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4",
            "https://vd2.bdstatic.com/mda-jfcccmya6ncp30mi/sc/mda-jfcccmya6ncp30mi.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633445749-0-0-5fd084fd1f354e82109ac6d501b9f650&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
            "https://vd2.bdstatic.com/mda-mhsdyqpsjmzuiy3f/sc/cae_h264/1630058168278505997/mda-mhsdyqpsjmzuiy3f.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446161-0-0-b8a8acdce5c7c031365f5e2c46b5f4cf&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
            "https://vd3.bdstatic.com/mda-jdgcjrv69tvpu5hw/sc/mda-jdgcjrv69tvpu5hw.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446358-0-0-26919d110fd861453690a9f54a79d2a5&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
            "https://vd4.bdstatic.com/mda-ic8ev9xwe9t14068/sc/mda-ic8ev9xwe9t14068.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446547-0-0-7e06ecbbebefc302560ca6f743105d70&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
            "https://vd2.bdstatic.com/mda-jjhcjrfgv8zkzdvr/sc/mda-jjhcjrfgv8zkzdvr.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633504485-0-0-9582e29139b9cccd8db2be7f9e4dc60a&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
            "http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4"
    };

    //控制滚动播放
    ScrollCalculatorHelper scrollCalculatorHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);               //设置无titleBar
        setContentView(R.layout.activity_gsyvideo);

        initData();
        initView();
    }

    private void initData() {
        //视频数据
        list = new ArrayList<>();
        list.add(new GSYVideoBean(urls[0], "@阿拉丁",
                "在充满异域风情的古代阿拉伯王国阿格拉巴，善良的穷小子阿拉丁和勇敢的茉莉公主浪漫邂逅。"));
        list.add(new GSYVideoBean(urls[1],"@七彩小画笔",
                "#幼儿 #捕捉身边的美好 简笔画一学就会系列，孩子一看就会！\uD83D\uDC7C"));
        list.add(new GSYVideoBean(urls[2], "@厚大法考",
                "张三名场面来了，累了累了不想审了 #法外狂徒张三 #法考 #罗翔说刑法"));
        list.add(new GSYVideoBean(urls[3],"@兔小贝Beckybunny",
                "《滁州西涧》兔小贝少儿学前教育课堂 益智早教古诗 唐诗三百首"));
        list.add(new GSYVideoBean(urls[4], "@贝乐虎儿歌",
                "儿童经典故事《龟兔赛跑》宝宝睡前故事"));
        list.add(new GSYVideoBean(urls[5],  "@嘟拉手工绘画",
                "幼儿故事宝宝睡前故事 会说话，说好话"));
        list.add(new GSYVideoBean(urls[6], "@玩具总动员",
                "牛仔警长胡迪和太空骑警巴斯光年的故事"));

        Log.e(TAG, list.get(0).getAuther().toString());
    }

    private void initView() {
        recyclerView = findViewById(R.id.gsy_rv);

        GSYAdapter gsyAdapter = new GSYAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //自定播放帮助类 限定范围为屏幕一半的上下偏移180   括号里不用在意 因为是一个item一个屏幕
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.itemgsy_videoplayer
                , dm.heightPixels / 2 - DpTools.dip2px(this, 180)
                , dm.heightPixels / 2 + DpTools.dip2px(this, 180));

        //让RecyclerView有ViewPager的翻页效果
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        //设置LayoutManager和Adapter
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(gsyAdapter);
        //设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //第一个可见视图,最后一个可见视图
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果newState的状态==RecyclerView.SCROLL_STATE_IDLE;
                //播放对应的视频
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.e("有几个item", firstVisibleItem + "    " + lastVisibleItem);
                //一屏幕显示一个item 所以固定1
                //实时获取设置 当前显示的GSYBaseVideoPlayer的下标
                scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Configuration mConfiguration = this.getResources().getConfiguration();
        int ori = mConfiguration.orientation;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //当前为竖屏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }

        super.onConfigurationChanged(newConfig);
    }


    /**
     * 获取视频封面
     */
    public Drawable getBitmapFormUrl(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return new BitmapDrawable(bitmap);
    }
}