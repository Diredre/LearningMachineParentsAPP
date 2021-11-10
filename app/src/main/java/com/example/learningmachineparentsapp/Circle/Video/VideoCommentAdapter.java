package com.example.learningmachineparentsapp.Circle.Video;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningmachineparentsapp.View.RoundImageView;

import org.jetbrains.annotations.NotNull;

import com.example.learningmachineparentsapp.R;

import java.util.List;

public class VideoCommentAdapter extends RecyclerView.Adapter<VideoCommentAdapter.ViewHolder> {

    private List<VideoCommentBean> commentBeanList;
    private Context context;

    public VideoCommentAdapter(Context context, List<VideoCommentBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RoundImageView userIcon;
        TextView userId, content, time, num;
        ImageView like;
        boolean isLike = false;

        public ViewHolder(View v){
            super(v);
            this.userId = v.findViewById(R.id.item_videocom_userName);
            this.userIcon = v.findViewById(R.id.item_videocom_userLogo);
            this.content = v.findViewById(R.id.item_videocom_story);
            this.time = v.findViewById(R.id.item_videocom_time);
            this.like = v.findViewById(R.id.item_videocom_iv_like);
            this.num = v.findViewById(R.id.item_videocom_tv_num);

            like.setColorFilter(Color.parseColor("#919191"));
            int like1 = Integer.parseInt(num.getText().toString()), like2 = like1 + 1;
            Log.e("like=", ""+like1);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isLike) {
                        like.setColorFilter(Color.parseColor("#F44336"));
                        num.setTextColor(Color.parseColor("#F44336"));
                        num.setText("" + like2);
                        isLike = true;
                    }else{
                        like.setColorFilter(Color.parseColor("#919191"));
                        num.setTextColor(Color.parseColor("#919191"));
                        num.setText("" + like1);
                        isLike = false;
                    }
                }
            });
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_comment, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        VideoCommentBean com = commentBeanList.get(position);
        holder.userId.setText(com.getNickName());
        holder.userIcon.setImageResource(com.getId());
        holder.content.setText(com.getContent());
        holder.time.setText(com.getCreateDate());
        holder.num.setText(com.getNum());
        //Glide.with(context).load(peo.getImageId()).into(holder.peoIcon);
    }

    @Override
    public int getItemCount() {
        return commentBeanList.size();
    }


    /**
     * 新的留言数据
     */
    public void addTheCommentData(VideoCommentBean videoCommentBean){
        if(videoCommentBean !=null){
            commentBeanList.add(videoCommentBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据不能为空");
        }
    }
}