package com.example.learningmachineparentsapp.Discover.BeanAdapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<ShopBean> shopBeanList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemgoods_tv_tit, itemgoods_tv_money, itemgoods_tv_peo;
        private ImageView itemgoods_iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemgoods_tv_tit = itemView.findViewById(R.id.itemgoods_tv_tit);
            itemgoods_tv_money = itemView.findViewById(R.id.itemgoods_tv_money);
            itemgoods_tv_peo = itemView.findViewById(R.id.itemgoods_tv_peo);
            itemgoods_iv_icon = itemView.findViewById(R.id.itemgoods_iv_icon);
        }
    }

    public ShopAdapter(List<ShopBean> shopBeanList, Context context) {
        this.shopBeanList = shopBeanList;
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

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ShopBean shop = shopBeanList.get(position);
        holder.itemgoods_tv_tit.setText(shop.getTit());
        Glide.with(context)
                .load(shop.getIconid())
                .into(holder.itemgoods_iv_icon);
        holder.itemgoods_tv_money.setText(""+shop.getMoney());
        holder.itemgoods_tv_peo.setText(""+shop.getPeople());
    }

    @Override
    public int getItemCount() {
        return shopBeanList.size();
    }
}
