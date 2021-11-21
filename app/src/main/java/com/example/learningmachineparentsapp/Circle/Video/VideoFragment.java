package com.example.learningmachineparentsapp.Circle.Video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Circle.Upload.UploadActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Widget.MyJzvdStd;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

public class VideoFragment extends Fragment {

    private Context context;
    private View view;
    private RecyclerView circle_video_rv;
    private VideoAdapter playerAdapter;
    private List<VideoBean> urlList = new ArrayList<>();
    private VideoBean[] urls = {
            new VideoBean("https://vd2.bdstatic.com/mda-jfcccmya6ncp30mi/sc/mda-jfcccmya6ncp30mi.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633445749-0-0-5fd084fd1f354e82109ac6d501b9f650&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
                    "@七彩小画笔", "#幼儿 #捕捉身边的美好 简笔画一学就会系列，孩子一看就会！\uD83D\uDC7C"),
            new VideoBean("https://vd2.bdstatic.com/mda-mhsdyqpsjmzuiy3f/sc/cae_h264/1630058168278505997/mda-mhsdyqpsjmzuiy3f.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446161-0-0-b8a8acdce5c7c031365f5e2c46b5f4cf&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
                    "@厚大法考", "张三名场面来了，累了累了不想审了 #法外狂徒张三 #法考 #罗翔说刑法"),
            new VideoBean("https://vd4.bdstatic.com/mda-jguwy5r78jvxf9xe/sc/mda-jguwy5r78jvxf9xe.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446250-0-0-f391701ec6310ffce33b3fb6a708c785&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
                    "@宝宝巴士", "#宝宝巴士 #儿童安全 出行除了坐安全座椅，还有这些安全知识要知道！"),
            new VideoBean("https://vd3.bdstatic.com/mda-jdgcjrv69tvpu5hw/sc/mda-jdgcjrv69tvpu5hw.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446358-0-0-26919d110fd861453690a9f54a79d2a5&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
                    "@兔小贝Beckybunny", "《滁州西涧》兔小贝少儿学前教育课堂 益智早教古诗 唐诗三百首"),
            new VideoBean("https://vd4.bdstatic.com/mda-ic8ev9xwe9t14068/sc/mda-ic8ev9xwe9t14068.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633446547-0-0-7e06ecbbebefc302560ca6f743105d70&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
                    "@贝乐虎儿歌", "儿童经典故事《龟兔赛跑》宝宝睡前故事"),
            new VideoBean("https://vd2.bdstatic.com/mda-jjhcjrfgv8zkzdvr/sc/mda-jjhcjrfgv8zkzdvr.mp4?v_from_s=hkapp-haokan-nanjing&amp;auth_key=1633504485-0-0-9582e29139b9cccd8db2be7f9e4dc60a&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=3000187_1",
                    "@嘟拉手工绘画", "幼儿故事宝宝睡前故事 会说话，说好话")};


    public VideoFragment(){}

    public VideoFragment(Context context){
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_circle_video, container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initVideoPlayer();
    }

    private void initVideoPlayer(){
        // 设置滑动速度
        setMaxFlingVelocity(circle_video_rv, 500);
        // 初始化数据
        circle_video_rv = view.findViewById(R.id.circle_video_rv);
        for(int i  = 0; i < urls.length; i++){
            urlList.add(urls[i]);
        }
        // 上下滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        circle_video_rv.setLayoutManager(linearLayoutManager);
        // SnapHelper滑动对准
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(circle_video_rv);
        // adapter应用
        playerAdapter = new VideoAdapter(getContext(), urlList);


        //todo: 这一行有问题，标明显一点记得改
        circle_video_rv.setAdapter(playerAdapter);


        circle_video_rv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {}
            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.itemplay_videoplayer);
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
        circle_video_rv.addOnScrollListener(new AutoPlayScrollListener(getContext()));
    }


    /**
     * 监听recycleView滑动状态，
     * 自动播放可见区域内的第一个视频
     */
    private static class AutoPlayScrollListener extends RecyclerView.OnScrollListener {
        private int firstVisibleItem = 0;
        private int lastVisibleItem = 0;
        private int visibleCount = 0;
        private Context context;

        public AutoPlayScrollListener(Context context) {
            this.context = context;
        }

        //被处理的视频状态标签
        private enum VideoTagEnum {
            TAG_AUTO_PLAY_VIDEO,    //自动播放视频
            TAG_PAUSE_VIDEO         //暂停视频
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:    //停止滚动
                    autoPlayVideo(recyclerView, AutoPlayScrollListener.VideoTagEnum.TAG_AUTO_PLAY_VIDEO);
                default:
                    //autoPlayVideo(recyclerView, VideoTagEnum.TAG_PAUSE_VIDEO);//滑动时暂停视频 需要可以加上
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                firstVisibleItem = linearManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;
            }
        }

        /**
         * 循环遍历 可见区域的播放器
         * 然后通过 getLocalVisibleRect(rect)方法计算出哪个播放器完全显示出来
         * getLocalVisibleRect 相关链接：http://www.cnblogs.com/ai-developers/p/4413585.html
         *
         * @param recyclerView
         * @param handleVideoTag 视频需要进行状态
         */
        private void autoPlayVideo(RecyclerView recyclerView, AutoPlayScrollListener.VideoTagEnum handleVideoTag) {
            for (int i = 0; i < visibleCount; i++) {
                if (recyclerView != null && recyclerView.getChildAt(i) != null
                        && recyclerView.getChildAt(i).findViewById(R.id.itemplay_videoplayer) != null) {
                    MyJzvdStd homeGSYVideoPlayer = recyclerView.getChildAt(i)
                            .findViewById(R.id.itemplay_videoplayer);
                    Rect rect = new Rect();
                    homeGSYVideoPlayer.getLocalVisibleRect(rect);
                    int videoheight = homeGSYVideoPlayer.getHeight();

                    if (rect.top == 0 && rect.bottom == videoheight) {
                        handleVideo(handleVideoTag, homeGSYVideoPlayer);
                        // 跳出循环，只处理可见区域内的第一个播放器
                        break;
                    }
                }
            }
        }

        /**
         * 视频状态处理
         *
         * @param handleVideoTag     视频需要进行状态
         * @param homeGSYVideoPlayer JZVideoPlayer播放器
         */
        private void handleVideo(AutoPlayScrollListener.VideoTagEnum handleVideoTag, MyJzvdStd homeGSYVideoPlayer) {
            switch (handleVideoTag) {
                case TAG_AUTO_PLAY_VIDEO:
                    //if ((homeGSYVideoPlayer.state != STATE_PLAYING)) {
                    // 进行播放
                    homeGSYVideoPlayer.startButton.performClick();
                    //}
                    break;
                case TAG_PAUSE_VIDEO:
                    //if ((homeGSYVideoPlayer.state != STATE_PAUSE)) {
                    // 模拟点击,暂停视频
                    // homeGSYVideoPlayer.startButton.performClick();
                    //}
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 改变Recycler的滑动速度
     * @param recyclerView
     * @param velocity    滑动速度默认是8000dp
     */
    public static void setMaxFlingVelocity(RecyclerView recyclerView, int velocity){
        try{
            Field field = recyclerView.getClass().getDeclaredField("mMaxFlingVelocity");
            field.setAccessible(true);
            field.set(recyclerView, velocity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}