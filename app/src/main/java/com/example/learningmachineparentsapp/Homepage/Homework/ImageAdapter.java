package com.example.learningmachineparentsapp.Homepage.Homework;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ImageBean> imgList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView item_iv;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            item_iv = itemView.findViewById(R.id.item_iv);
        }
    }

    public ImageAdapter(Context context, List<ImageBean> imgList){
        this.context = context;
        this.imgList = imgList;
    }

    @NonNull
    @NotNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img, parent, false);
        final ImageAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageAdapter.ViewHolder holder, int position) {
        ImageBean img = imgList.get(position);
        Glide.with(context)
                .load(img.getImgurl())
                .into(holder.item_iv);
        holder.item_iv.setOnClickListener(v->{
            bigImageLoader(img.getImgurl());
        });
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }


    /**
     * 方法里直接实例化一个imageView不用xml文件，传入bitmap设置图片
     */
    private void bigImageLoader(String url){
        LayoutInflater inflater = LayoutInflater.from(context);
        View imgEntryView = inflater.inflate(R.layout.dialog_photo, null);
        // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        ImageView img = imgEntryView.findViewById(R.id.large_image);
        Glide.with(context)
                .load(url)
                .into(img);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        // 点击大图关闭dialog
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }
}
