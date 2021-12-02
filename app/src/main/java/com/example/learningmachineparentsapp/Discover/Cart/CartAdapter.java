package com.example.learningmachineparentsapp.Discover.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Discover.fragment.GoodsBean;
import com.example.learningmachineparentsapp.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CheckViewHolder> {

    private static boolean flag = false;
    private Context mContext;
    private List<GoodsBean> lists;
    private HashMap<Integer, Boolean> Maps = new HashMap<Integer, Boolean>();
    private double total = 0.0;
    private TextView tv_total;

    //成员方法，初始化checkBox的状态，默认全部不选中
    public CartAdapter(Context context, List<GoodsBean> lists) {
        this.mContext = context;
        this.lists = lists;
        tv_total = ((CartActivity)context).getTotal();
        initMap();
    }

    //初始化map内的数据状态，全部重置为false，即为选取状态
    private void initMap() {
        for (int i = 0; i < lists.size(); i++) {
            Maps.put(i, false);
        }
    }

    //获取最终的map存储数据
    public Map<Integer, Boolean> getMap() {
        return Maps;
    }

    //全选
    public Map<Integer, Boolean> allMap() {
        for (int i = 0; i < lists.size(); i++) {
            Maps.put(i, true);
        }
        notifyDataSetChanged();
        return Maps;
    }

    // 删除
    public Map<Integer, Boolean> deleteMap() {
        /*for (Integer key : Maps.keySet()) {
            Maps.remove(key);
        }*/
        for (int i = 0; i < lists.size(); i++) {
            removeItemData(i);
            Maps.remove(i);
        }
        notifyDataSetChanged();
        return Maps;
    }

    //取消全选
    public Map<Integer, Boolean> cancelAllMap() {
        for (int i = 0; i < lists.size(); i++) {
            Maps.put(i, false);
        }
        notifyDataSetChanged();
        return Maps;
    }

    //反选
    public Map<Integer, Boolean> invertMap() {
        if (flag) {
            for (Integer key : Maps.keySet()) {
                Maps.put(key, false);
            }
        }else {
            for (Integer key : Maps.keySet()) {
                Maps.put(key, true);
            }
        }
        flag = !flag;
        notifyDataSetChanged();
        return Maps;
    }

    @Override
    public CartAdapter.CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartAdapter.CheckViewHolder checkViewHolder =
                new CartAdapter.CheckViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false));
        return checkViewHolder;
    }

    @Override
    public void onBindViewHolder(CartAdapter.CheckViewHolder holder, final int position) {
        GoodsBean goods = lists.get(position);
        holder.item_cart_tv_name.setText(goods.getName());
        Glide.with(mContext)
                .load(goods.getIconid())
                .into(holder.item_cart_iv_shopicon);
        holder.item_cart_tv_money.setText("" + goods.getMoney());
        holder.item_cart_tv_num.setText("" +goods.getNum());

        holder.item_cart_tv_sub.setOnClickListener(v->{
            holder.item_cart_tv_num.setText(Integer.parseInt(holder.item_cart_tv_num.getText().toString())-1+"");
            if(goods.isCheck()){
                total -= goods.getMoney();
            }
            tv_total.setText(""+total);
            if(holder.item_cart_tv_num.getText().toString().equals("0")){
                removeItemData(position);
            }
        });

        holder.item_cart_tv_add.setOnClickListener(v->{
            holder.item_cart_tv_num.setText(Integer.parseInt(holder.item_cart_tv_num.getText().toString())+1+"");
            if(goods.isCheck()){
                total += goods.getMoney();
            }
            tv_total.setText(""+total);
        });

        holder.item_cart_rb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Maps.put(position, isChecked);
                if(isChecked) {
                    total += goods.getNum()*goods.getMoney();
                }else{
                    total -= goods.getNum()*goods.getMoney();
                }
                tv_total.setText("" + total);
            }
        });

        if (Maps.get(position) == null) {
            Maps.put(position, false);
        }
        //没有设置tag之前会有item重复选框出现，设置tag之后，此问题解决
        holder.item_cart_rb_check.setChecked(Maps.get(position));
    }


    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }


    public void setTotal(double total) {
        this.total = total;
    }


    public double getTotal(){
        return total;
    }


    /**
     * 增删数据
     */
    public void removeItemData(int position){
        lists.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();     //防止错位
        //刷新下标，不然下标就重复
        notifyItemRangeChanged(position, lists.size());
    }


    public void addItemData(GoodsBean data) {
        lists.add(lists.size(), data);
        notifyItemInserted(lists.size());
        //刷新下标，不然下标就不连续
        notifyItemRangeChanged(lists.size(), lists.size());
    }


    class CheckViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_cart_iv_shopicon;
        private CheckBox item_cart_rb_check;
        private TextView item_cart_tv_name, item_cart_tv_money, item_cart_tv_add,
                item_cart_tv_sub, item_cart_tv_num;

        public CheckViewHolder(View itemView) {
            super(itemView);
            item_cart_rb_check = itemView.findViewById(R.id.item_cart_rb_check);
            item_cart_iv_shopicon = itemView.findViewById(R.id.item_cart_iv_shopicon);
            item_cart_tv_name = itemView.findViewById(R.id.item_cart_tv_name);
            item_cart_tv_money = itemView.findViewById(R.id.item_cart_tv_money);
            item_cart_tv_add = itemView.findViewById(R.id.item_cart_tv_add);
            item_cart_tv_sub = itemView.findViewById(R.id.item_cart_tv_sub);
            item_cart_tv_num = itemView.findViewById(R.id.item_cart_tv_num);
        }
    }
}
