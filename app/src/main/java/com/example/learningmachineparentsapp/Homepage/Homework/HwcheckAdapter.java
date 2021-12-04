package com.example.learningmachineparentsapp.Homepage.Homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

public class HwcheckAdapter extends RecyclerView.Adapter<HwcheckAdapter.ViewHolder> {

    private List<HomeworkBean> hwList;
    private Context context;
    private SimpleDateFormat commit_format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat format = new SimpleDateFormat("MM-dd");


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_hwcheck_iv;
        TextView item_hwcheck_tv_name, item_hwcheck_tv_time,
                item_hwcheck_tv_state, item_hwcheck_tv_commit;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hwcheck, parent, false);
        final HwcheckAdapter.ViewHolder viewHolder = new HwcheckAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HwcheckAdapter.ViewHolder holder, int position) {
        HomeworkBean homework = hwList.get(position);

        if(homework.isComplete()){
            Glide.with(context)
                    .load("https://z3.ax1x.com/2021/12/03/oUamy4.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("已完成");
        }else{
            Glide.with(context)
                    .load("https://z3.ax1x.com/2021/12/03/oUa5t0.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("未完成");
        }

        holder.item_hwcheck_tv_name.setText(homework.getCon());
        holder.item_hwcheck_tv_time.setText(format.format(homework.getUse_time()));
        holder.item_hwcheck_tv_commit.setText(commit_format.format(homework.getCom_time()));
    }

    @Override
    public int getItemCount() {
        return hwList.size();
    }
}
