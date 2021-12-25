package com.example.learningmachineparentsapp.Homepage.Homework;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningmachineparentsapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HwDialog extends Dialog {

    private Context context;
    private TextView dialog_hw_time;
    private ImageView dialog_hw_cansel;
    private RecyclerView dialog_hw_rv;
    private List<ImageBean> list = new ArrayList<>();
    private ImageAdapter adapter;

    public HwDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String day = String.valueOf(cal.get(Calendar.DATE));
        dialog_hw_time.setText(year + "年" + month + "月" + day + "日");
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hw, null);
        dialog_hw_cansel = view.findViewById(R.id.dialog_hw_cansel);
        dialog_hw_cansel.setOnClickListener(v->{
            this.dismiss();
        });

        dialog_hw_rv = view.findViewById(R.id.dialog_hw_rv);
        dialog_hw_time = view.findViewById(R.id.dialog_hw_time);

        // 垂直滑动
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        dialog_hw_rv.setLayoutManager(gridLayoutManager);
        //关键就是这个地方
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? gridLayoutManager.getSpanCount() : 1;
                }
            }
        );

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialog_hw_rv.setLayoutManager(layoutManager);*/

        // SnapHelper滑动对准
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(dialog_hw_rv);

        list = initData();
        adapter = new ImageAdapter(context, list);
        dialog_hw_rv.setAdapter(adapter);
        super.setContentView(view);
    }

    private List<ImageBean> initData(){
        List<ImageBean> datalist = new ArrayList<>();
        datalist.add(new ImageBean("https://img2020.cnblogs.com/blog/1497956/202006/1497956-20200603141455315-694953528.png"));
        datalist.add(new ImageBean("https://tse3-mm.cn.bing.net/th/id/OIP-C.0DQkAYscBFja9vVfKt5rtQHaKe?pid=ImgDet&rs=1"));
        datalist.add(new ImageBean("https://tse4-mm.cn.bing.net/th/id/OIP-C.Ee1NO1bHnwyFTmeYxo_BDAAAAA?pid=ImgDet&rs=1"));
        datalist.add(new ImageBean("https://tse3-mm.cn.bing.net/th/id/OIP-C.UNJGOreJ-pQ3_E0YmpJRNwAAAA?pid=ImgDet&rs=1"));
        datalist.add(new ImageBean("https://pic.qqtn.com/up/2019-5/2019050609103715556.jpg"));
        datalist.add(new ImageBean("https://tse3-mm.cn.bing.net/th/id/OIP-C.2s-AXKKedprPf81qDXwXXQAAAA?pid=ImgDet&rs=1"));
        datalist.add(new ImageBean("https://tse1-mm.cn.bing.net/th/id/R-C.47651a6dbd10ce8bb11497f36557d9a5?rik=CRijrj9%2f8V8bSQ&riu=http%3a%2f%2fscimg.jianbihuadq.com%2ftouxiang%2f202003%2f202003211405144.jpg&ehk=mLINB4OuUYmV1Q5jQ%2fB1l2P0udFXy2lRZMkwH2uHGdE%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1"));
        datalist.add(new ImageBean("https://tse1-mm.cn.bing.net/th/id/R-C.4dd6d7ed3cc3a28849dcb3b1a059bdc8?rik=iPigytkD5FBvjg&riu=http%3a%2f%2ffile.qqtouxiang.com%2fgaoguai%2f2020-02-17%2fbcbe80a41c1e0ce1fc149aa84848834e.jpeg&ehk=%2f1i96gleDd2eO4AEP%2bhLw%2frO%2bijZhMEDYDtM%2bu5l5Xw%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1"));
        datalist.add(new ImageBean("https://tse1-mm.cn.bing.net/th/id/R-C.df278478fea664d8fb1816a9abd5513b?rik=WdZamwF%2bCSlIGQ&riu=http%3a%2f%2ftouxiang.vipyl.com%2fattached%2fimage%2f20130227%2f20130227173651965196.jpg&ehk=5WQ%2b1TBcdUz3Zdv0Cfb3dWH1fSlNiv7hEdqyBVZ9jtA%3d&risl=&pid=ImgRaw&r=0"));
        datalist.add(new ImageBean("https://img2020.cnblogs.com/blog/1497956/202006/1497956-20200603141455315-694953528.png"));
        return datalist;
    }

    public void setData(List<ImageBean> imglist){
        list = imglist;
    }
}
