package com.example.learningmachineparentsapp.Homepage.Message;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.okhttpClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageBean> messageBeanList = new ArrayList<>();
    private Context context;


    public MessageAdapter(Context context, List<MessageBean> list) {
        super();
        messageBeanList = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        final MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MessageAdapter.ViewHolder holder, int position) {
        MessageBean messageBean = messageBeanList.get(position);
        holder.mTextTitle.setText(messageBean.getFromUser());
        holder.mTextContent.setText(messageBean.getContent());
        Glide.with(context)
                .load(messageBean.getImgurl())
                .into(holder.mImageView);

        holder.msg_view.setVisibility(MessageBean.getRead() ? View.INVISIBLE : View.VISIBLE);

        holder.msg_all.setOnClickListener(v->{
            messageBean.setRead(true);
            holder.msg_view.setVisibility(View.INVISIBLE);
            videoTypePay("" + messageBean.getId());

            messageBean.setContent(messageBean.getType() + "已支付");
            holder.mTextContent.setText(messageBean.getContent());

            Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return messageBeanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextTitle, mTextContent;
        private ImageView mImageView;
        private LinearLayout msg_all;
        private View msg_view;

        private ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.msg_tv_title);
            mTextContent = itemView.findViewById(R.id.msg_tv_con);
            mImageView = itemView.findViewById(R.id.msg_imageView);
            msg_all = itemView.findViewById(R.id.msg_all);
            msg_view = itemView.findViewById(R.id.msg_view);
        }
    }


    /**
     * 增删改查数据
     */
    public void removeItemData(int position){
        messageBeanList.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();     //防止错位
        //刷新下标，不然下标就重复
        notifyItemRangeChanged(position, messageBeanList.size());
    }

    public void addItemData(MessageBean data) {
        messageBeanList.add(messageBeanList.size(), data);
        notifyItemInserted(messageBeanList.size());
        //刷新下标，不然下标就不连续
        notifyItemRangeChanged(messageBeanList.size(), messageBeanList.size());
    }

    public void changeItemData(int position, MessageBean data) {
        messageBeanList.set(position, data);
        notifyItemChanged(position);
    }


    private void videoTypePay(String infoId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tool = new okhttpClass();
                String res = tool.videoTypePay(infoId);
                Log.e("videoTypePay", res);
            }
        }).start();
    }
}
