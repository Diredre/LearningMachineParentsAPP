package com.example.learningmachineparentsapp.Homepage.Homework;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HwcheckAdapter extends RecyclerView.Adapter<HwcheckAdapter.ViewHolder> {

    private List<HomeworkBean> hwList;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout hwcheck_ll;
        ImageView item_hwcheck_iv;
        TextView item_hwcheck_tv_name, item_hwcheck_tv_time,
                item_hwcheck_tv_state, item_hwcheck_tv_commit;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            hwcheck_ll = itemView.findViewById(R.id.hwcheck_ll);
            item_hwcheck_iv = itemView.findViewById(R.id.item_hwcheck_iv);
            item_hwcheck_tv_name = itemView.findViewById(R.id.item_hwcheck_tv_name);
            item_hwcheck_tv_time = itemView.findViewById(R.id.item_hwcheck_tv_time);
            item_hwcheck_tv_state = itemView.findViewById(R.id.item_hwcheck_tv_state);
            item_hwcheck_tv_commit = itemView.findViewById(R.id.item_hwcheck_tv_commit);
        }
    }

    public HwcheckAdapter(Context context, List<HomeworkBean> hwList) {
        this.context = context;
        this.hwList = hwList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hwcheck, parent, false);
        final HwcheckAdapter.ViewHolder viewHolder = new HwcheckAdapter.ViewHolder(view);
        int parentHeight= parent.getHeight();
        parent.getWidth();
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.height =  (parentHeight / 3);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HwcheckAdapter.ViewHolder holder, int position) {
        HomeworkBean homework = hwList.get(position);

        holder.hwcheck_ll.setOnClickListener(v->{
            Log.e("size", String.valueOf(homework.getPiclist().size())+" " +homework.getCon());
            if(homework.getPiclist().size() == 0){
                Toast.makeText(context, "照片未提交", Toast.LENGTH_SHORT).show();
            }else {
                HwDialog hwDialog = new HwDialog(context, homework.getPiclist(), homework.getUse_time());
                hwDialog.show();
            }
        });

        if(homework.getIsComplete() == 1){
            Glide.with(context)
                    .load("https://z3.ax1x.com/2021/12/03/oUamy4.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("已完成");
        }else if(homework.getIsComplete() == 0){
            Glide.with(context)
                    .load("https://z3.ax1x.com/2021/12/03/oUa5t0.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("未完成");
        }else if(homework.getIsComplete() == 2){
            Glide.with(context)
                    .load("https://s4.ax1x.com/2021/12/11/o7E09e.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("孩子提交");
        }

        holder.item_hwcheck_tv_name.setText(homework.getCon());
        holder.item_hwcheck_tv_time.setText(homework.getUse_time());
        holder.item_hwcheck_tv_commit.setText(homework.getCom_time());
    }

    @Override
    public int getItemCount() {
        return hwList.size();
    }
}
