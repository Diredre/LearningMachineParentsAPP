package com.example.learningmachineparentsapp.Circle.Video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.jzvd.JzvdStd;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Widget.MyJzvdStd;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<VideoBean> urlList;
    private Context context;

    //评论区dialog
    private BottomSheetDialog dialog;
    private boolean isLike = false;
    private VideoCommentAdapter comment_adapter;
    private List<VideoCommentBean> comList = new ArrayList<>();
    private VideoCommentBean[] coms = {
            new VideoCommentBean(R.mipmap.icon, "你也在学习吗", "18分钟前",
                    "之前去上海的时候特地跑去参观了一下~", "24"),
            new VideoCommentBean(R.mipmap.icon, "悠悠", "5小时前",
                    "我们的目标是星辰大海。", "13"),
            new VideoCommentBean(R.mipmap.icon, "名字不能引人注意", "3天前",
                    "好好学习！", "5"),
            new VideoCommentBean(R.mipmap.icon, "宁波小学卢孟", "8天前",
                    "历史不会把你们忘记，那飘扬着的五星红旗，就是用你们热血浸染的。那高矗的胜利丰碑，正是用你们的头颅堆砌的。立足今天，放眼未来，让我们一同来学习革命先烈的爱国精神和英雄气概，在人生道路上，克服重重艰难险阻，迈向光明，迈向未来。", "7"),
            new VideoCommentBean(R.mipmap.icon, "红星小学德华", "17天前",
                    "够意思，你似干介个的！我就知道你是个爱国up，一定和爱国网站在一起！当年前几期站里就掏过钱，新一季您老安排下？", "5"),
            new VideoCommentBean(R.mipmap.icon, "针不戳", "1个月前",
                    "回首党史，缅怀先烈，我们看到在革命先烈用鲜血打下的江山里，一代代新生的中国共产党人牢记初心，开拓奋进，带领中国人民进行社会主义建设、实施改革开放，一点一滴地把先烈的理想变为现实。", "13"),
            new VideoCommentBean(R.mipmap.icon, "pol1", "2个月前",
                    "想想当年，想想当年！先烈那么困难都打败了卖国代理人。兄弟们都支楞起来！一定要打败日本资本代理人！", "8"),
            new VideoCommentBean(R.mipmap.icon, "为乐当及时の朱裕民", "3个月前",
                    "强烈推荐《觉醒年代》电视剧，好看好哭", "0")};

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MyJzvdStd itemplay_videoplayer;
        private TextView itemplay_tv_auther, itemplay_tv_con;
        private ImageView itemplay_iv_like, itemplay_iv_comment,
                itemplay_iv_award, itemplay_iv_more;
        private TextView itemplay_tv_like, itemplay_tv_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            itemplay_videoplayer = itemView.findViewById(R.id.itemplay_videoplayer);
            itemplay_tv_auther = itemView.findViewById(R.id.itemplay_tv_auther);
            itemplay_tv_con = itemView.findViewById(R.id.itemplay_tv_con);

            itemplay_iv_like = itemView.findViewById(R.id.itemplay_iv_like);
            itemplay_iv_comment = itemView.findViewById(R.id.itemplay_iv_comment);
            itemplay_iv_award = itemView.findViewById(R.id.itemplay_iv_award);
            itemplay_iv_more = itemView.findViewById(R.id.itemplay_iv_more);
            itemplay_tv_like = itemView.findViewById(R.id.itemplay_tv_like);
            itemplay_tv_comment = itemView.findViewById(R.id.itemplay_tv_comment);

            itemplay_iv_like.setColorFilter(Color.parseColor("#FFFFFF"));
            itemplay_iv_comment.setColorFilter(Color.parseColor("#FFFFFF"));
            itemplay_iv_award.setColorFilter(Color.parseColor("#FFFFFF"));
            itemplay_iv_more.setColorFilter(Color.parseColor("#FFFFFF"));
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

        viewHolder.itemplay_iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int likenum1 = Integer.parseInt(viewHolder.itemplay_tv_like.getText().toString()) - 1, likenum2 = likenum1 + 2;
                if(!isLike) {
                    viewHolder.itemplay_iv_like.setColorFilter(Color.parseColor("#2196F3"));
                    viewHolder.itemplay_tv_like.setTextColor(Color.parseColor("#2196F3"));
                    viewHolder.itemplay_tv_like.setText("" + likenum2);
                    isLike = true;
                }else{
                    viewHolder.itemplay_iv_like.setColorFilter(Color.parseColor("#FFFFFF"));
                    viewHolder.itemplay_tv_like.setTextColor(Color.parseColor("#FFFFFF"));
                    viewHolder.itemplay_tv_like.setText("" + likenum1);
                    isLike = false;
                }
            }
        });

        viewHolder.itemplay_iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog(viewHolder.itemplay_tv_comment);
            }
        });

        viewHolder.itemplay_iv_award.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AwardDialog(context).show();
            }
        });
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


    /**
     * 弹出评论区（底部dialog
     */
    private void showCommentDialog(TextView tv){
        dialog = new BottomSheetDialog(context);
        View commentView = LayoutInflater.from(context).inflate(R.layout.dialog_comment_list,null);
        dialog.setContentView(commentView);

        final ImageView dialog_comment_list_cansel = commentView.findViewById(R.id.dialog_comment_list_cansel);
        dialog_comment_list_cansel.setOnClickListener(v->{
            dialog.dismiss();
        });

        final RecyclerView dialog_comment_list_rv = commentView.findViewById(R.id.dialog_comment_list_rv);
        for(int i = 0; i < 8; i++){
            comList.add(coms[i]);
        }

        //解决显示不全的情况
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialog_comment_list_rv.setLayoutManager(linearLayoutManager);
        comment_adapter = new VideoCommentAdapter(context, comList);
        dialog_comment_list_rv.setAdapter(comment_adapter);

        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et_com);
        final Button bt_comment = commentView.findViewById(R.id.dialog_comment_btn_send);
        final TextView dialog_comment_list_account = commentView.findViewById(R.id.dialog_comment_list_account);
        int account = Integer.parseInt(tv.getText().toString());
        dialog_comment_list_account.setText("" + account);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    commentText.setText("");
                    Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                    VideoCommentBean comment = new VideoCommentBean(R.mipmap.icon,
                            "蜡笔小新", "刚刚", commentContent, "0");
                    //comment_adapter.addTheCommentData(comment);
                    comList.add(0, comment);
                    comment_adapter.notifyItemInserted(0);
                    dialog_comment_list_rv.getLayoutManager().scrollToPosition(0);

                    int comnum = Integer.parseInt(tv.getText().toString()) + 1;
                    tv.setText("" + comnum);
                    dialog_comment_list_account.setText("" + comnum);
                }else {
                    Toast.makeText(context,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length() > 2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        dialog.show();
    }

}