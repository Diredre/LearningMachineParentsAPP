package com.example.learningmachineparentsapp.Discover.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Discover.GoodsDetailActivity;
import com.example.learningmachineparentsapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<GoodsBean> beanList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemgoods_tv_tit, itemgoods_tv_money, itemgoods_tv_peo, itemgoods_tv_num;
        private ImageView itemgoods_iv_icon, itemgoods_iv_add, itemgoods_iv_sub;

        public ViewHolder(View itemView) {
            super(itemView);
            itemgoods_tv_tit = itemView.findViewById(R.id.itemgoods_tv_tit);
            itemgoods_tv_money = itemView.findViewById(R.id.itemgoods_tv_money);
            itemgoods_tv_peo = itemView.findViewById(R.id.itemgoods_tv_peo);
            itemgoods_iv_icon = itemView.findViewById(R.id.itemgoods_iv_icon);
            itemgoods_tv_num = itemView.findViewById(R.id.itemgoods_tv_num);
            itemgoods_iv_add = itemView.findViewById(R.id.itemgoods_iv_add);
            itemgoods_iv_sub = itemView.findViewById(R.id.itemgoods_iv_sub);
        }
    }

    public ShopAdapter(List<GoodsBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods,
                parent, false);*/
        View view = View.inflate(context, R.layout.item_goods, null);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemgoods_iv_sub.setOnClickListener(v->{
            if(Integer.parseInt(viewHolder.itemgoods_tv_num.getText().toString()) > 0) {
                viewHolder.itemgoods_tv_num.setText(Integer.parseInt(viewHolder.itemgoods_tv_num.getText().toString()) - 1 + "");
            }else{
                Toast.makeText(context, "商品数不能小于0", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.itemgoods_iv_add.setOnClickListener(v->{
            viewHolder.itemgoods_tv_num.setText(Integer.parseInt(viewHolder.itemgoods_tv_num.getText().toString())+1+"");
        });

        viewHolder.itemgoods_iv_icon.setOnClickListener(v->{
            int pos = viewHolder.getAdapterPosition();
            GoodsBean goods = beanList.get(pos);
            Intent intent = new Intent(context, GoodsDetailActivity.class);
            intent.putExtra(GoodsDetailActivity.GOODSNAME, goods.getName());
            intent.putExtra(GoodsDetailActivity.GOODSMONNEY, ""+goods.getMoney());
            intent.putExtra(GoodsDetailActivity.GOODSICON, goods.getIconid());
            context.startActivity(intent);
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        GoodsBean goods = beanList.get(position);
        holder.itemgoods_tv_tit.setText(goods.getName());
        Glide.with(context)
                .load(goods.getIconid())
                .into(holder.itemgoods_iv_icon);
        holder.itemgoods_tv_money.setText("" + goods.getMoney());
        holder.itemgoods_tv_peo.setText("" + goods.getPeople());
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }
}
