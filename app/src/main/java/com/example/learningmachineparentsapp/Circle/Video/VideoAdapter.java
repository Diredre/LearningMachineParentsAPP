package com.example.learningmachineparentsapp.Circle.Video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import cn.jzvd.JzvdStd;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Widget.MyJzvdStd;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<VideoBean> urlList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MyJzvdStd itemplay_videoplayer;
        private TextView itemplay_tv_auther, itemplay_tv_con;

        public ViewHolder(View itemView) {
            super(itemView);
            itemplay_videoplayer = itemView.findViewById(R.id.itemplay_videoplayer);
            itemplay_tv_auther = itemView.findViewById(R.id.itemplay_tv_auther);
            itemplay_tv_con = itemView.findViewById(R.id.itemplay_tv_con);
        }
    }

    public VideoAdapter(Context context, List<VideoBean> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_player,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideoAdapter.ViewHolder holder, int position) {
        VideoBean url = urlList.get(position % urlList.size());
        //视频内容+作者
        holder.itemplay_tv_con.setText(url.getCon());
        holder.itemplay_tv_auther.setText(url.getAuther());
        //视频封面
        /*Glide.with(holder.itemplay_videoplayer.getContext())
                .load(url.getImageUrl())
                .into(holder.itemplay_videoplayer.thumbImageView);*/
        holder.itemplay_videoplayer.thumbImageView.setImageBitmap(getBitmapFormUrl(url.getVideoUrl()));
        //视频
        holder.itemplay_videoplayer.WIFI_TIP_DIALOG_SHOWED = true;          //取消播放时在非WIFIDialog提示
        holder.itemplay_videoplayer.setUp(url.getVideoUrl(), "", JzvdStd.SCREEN_NORMAL);
        holder.itemplay_videoplayer.setVideoImageDisplayType(1);
        holder.itemplay_videoplayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.itemplay_videoplayer.startButton.performClick();             //自动播放
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    /**
     * 获取视频封面
     */
    public Bitmap getBitmapFormUrl(String url) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url);
        //getFrameAtTime()--->在setDataSource()之后调用此方法。 如果可能，该方法在任何时间位置找到代表性的帧，
        // 并将其作为位图返回。这对于生成输入数据源的缩略图很有用。
        Bitmap bitmap = retriever.getFrameAtTime();
        retriever.release();
        return bitmap;
    }
}