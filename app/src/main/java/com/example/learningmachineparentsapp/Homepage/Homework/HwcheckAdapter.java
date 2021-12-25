package com.example.learningmachineparentsapp.Homepage.Homework;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private SimpleDateFormat commit_format = new SimpleDateFormat("yyyy-MM-dd\nhh:mm:ss");
    private SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");


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
            HwDialog hwDialog = new HwDialog(context, initData());
            hwDialog.show();
        });

        if(homework.getIsComplete()== 1){
            Glide.with(context)
                    .load("https://z3.ax1x.com/2021/12/03/oUamy4.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("已完成");
        }else if(homework.getIsComplete()== 0){
            Glide.with(context)
                    .load("https://z3.ax1x.com/2021/12/03/oUa5t0.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("未完成");
        }else if(homework.getIsComplete()== 2){
            Glide.with(context)
                    .load("https://s4.ax1x.com/2021/12/11/o7E09e.png")
                    .into(holder.item_hwcheck_iv);
            holder.item_hwcheck_tv_state.setText("孩子提交");
        }

        holder.item_hwcheck_tv_name.setText(homework.getCon());
        holder.item_hwcheck_tv_time.setText(format.format(homework.getUse_time()));
        holder.item_hwcheck_tv_commit.setText(commit_format.format(homework.getCom_time()));
    }

    @Override
    public int getItemCount() {
        return hwList.size();
    }

    private List<ImageBean> initData(){
        List<ImageBean> datalist = new ArrayList<>();
        datalist.add(new ImageBean("http://192.168.31.95:8082/img597188762.png"));
        datalist.add(new ImageBean("http://192.168.31.95:8082/1639038590.jpg"));
        datalist.add(new ImageBean("http://192.168.31.95:8082/1639040071.jpg"));
        datalist.add(new ImageBean("http://192.168.31.95:8082/1639038590.jpg"));
        return datalist;
    }


    private void startData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tools = new okhttpClass();
                String res = tools.getHomeworkPic();
                Log.e("getHomeworkPic()", res);
            }
        }).start();
    }
}
