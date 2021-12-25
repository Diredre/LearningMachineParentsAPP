package com.example.learningmachineparentsapp.Homepage.Homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        /*int parentHeight= parent.getHeight();
        parent.getWidth();
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.height =  (parentHeight / 2);*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageAdapter.ViewHolder holder, int position) {
        ImageBean img = imgList.get(position);

        Glide.with(context)
                .load(img.getImgurl())
                .into(holder.item_iv);
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
