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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Widget.MyJzvdStd;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JzvdStd;

public class GSYAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "GSYAdapter";
    private Context context;
    private List<GSYVideoBean> list;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;

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


    public GSYAdapter(Context context, List<GSYVideoBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gsyvideo,
                parent, false);
        final GSYAdapter.ViewHolder viewHolder = new GSYAdapter.ViewHolder(view);

        viewHolder.itemgsy_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int likenum1 = Integer.parseInt(viewHolder.itemgsy_tv_like.getText().toString()) - 1, likenum2 = likenum1 + 2;
                if(!isLike) {
                    viewHolder.itemgsy_iv_like.setColorFilter(Color.parseColor("#2196F3"));
                    viewHolder.itemgsy_tv_like.setTextColor(Color.parseColor("#2196F3"));
                    viewHolder.itemgsy_tv_like.setText("" + likenum2);
                    isLike = true;
                }else{
                    viewHolder.itemgsy_iv_like.setColorFilter(Color.parseColor("#FFFFFF"));
                    viewHolder.itemgsy_tv_like.setTextColor(Color.parseColor("#FFFFFF"));
                    viewHolder.itemgsy_tv_like.setText("" + likenum1);
                    isLike = false;
                }
            }
        });

        viewHolder.itemgsy_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog(viewHolder.itemgsy_tv_comment);
            }
        });

        viewHolder.itemgsy_award.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AwardDialog(context).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");
        //配置视频播放器参数
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(list.get(position).getVideoUrl())
                //.setVideoTitle(list.get(position).getCon())
                .setVideoTitle("")
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setReleaseWhenLossAudio(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!vh.itemgsy_videoplayer.isIfCurrentIsFullscreen()) {
                            //静音
                            //GSYVideoManager.instance().setNeedMute(true);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        //GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        vh.itemgsy_videoplayer.getCurrentPlayer().getTitleTextView().setText((String)objects[0]);
                    }
                }).build(vh.itemgsy_videoplayer);

        //设置返回键
        vh.itemgsy_videoplayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        //vh.itemgsy_videoplayer.getBackButton().setVisibility(View.GONE);
        vh.itemgsy_videoplayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.itemgsy_videoplayer.startWindowFullscreen(context, false, true);
            }
        });
        //实现第一个视频自动播放
        if(position==0){
            vh.itemgsy_videoplayer.startPlayLogic();
        }

        //视频内容+作者
        vh.itemgsy_tv_con.setText(list.get(position).getCon());
        vh.itemgsy_tv_auther.setText(list.get(position).getAuther());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private StandardGSYVideoPlayer itemgsy_videoplayer;
        private TextView itemgsy_tv_auther, itemgsy_tv_con;
        private ImageView itemgsy_iv_like, itemgsy_iv_comment, itemgsy_iv_award;
        private TextView itemgsy_tv_like, itemgsy_tv_comment;
        private LinearLayout itemgsy_like, itemgsy_comment, itemgsy_award;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
            itemgsy_videoplayer = itemView.findViewById(R.id.itemgsy_videoplayer);

            itemgsy_tv_auther = itemView.findViewById(R.id.itemgsy_tv_auther);
            itemgsy_tv_con = itemView.findViewById(R.id.itemgsy_tv_con);

            itemgsy_iv_like = itemView.findViewById(R.id.itemgsy_iv_like);
            itemgsy_iv_comment = itemView.findViewById(R.id.itemgsy_iv_comment);
            itemgsy_iv_award = itemView.findViewById(R.id.itemgsy_iv_award);
            itemgsy_tv_like = itemView.findViewById(R.id.itemgsy_tv_like);
            itemgsy_tv_comment = itemView.findViewById(R.id.itemgsy_tv_comment);

            itemgsy_like = itemView.findViewById(R.id.itemgsy_like);
            itemgsy_comment = itemView.findViewById(R.id.itemgsy_comment);
            itemgsy_award = itemView.findViewById(R.id.itemgsy_award);

            itemgsy_iv_like.setColorFilter(Color.parseColor("#FFFFFF"));
            itemgsy_iv_comment.setColorFilter(Color.parseColor("#FFFFFF"));
            itemgsy_iv_award.setColorFilter(Color.parseColor("#FFFFFF"));
        }
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