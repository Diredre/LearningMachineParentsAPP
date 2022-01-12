package com.example.learningmachineparentsapp.Homepage.Homework;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.learningmachineparentsapp.View.GroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.learningmachineparentsapp.R;

/**
 * 适配器
 */
public class ArticleAdapter extends GroupRecyclerAdapter<String, Article> {

    private RequestManager mLoader;
    private Context context;

    public ArticleAdapter(Context context) {
        super(context);
        this.context = context;
        mLoader = Glide.with(context.getApplicationContext());
        LinkedHashMap<String, List<Article>> map = new LinkedHashMap<>();
        List<String> titles = new ArrayList<>();
        map.put("历史作业", create(0));
        /*map.put("每周热点", create(1));
        map.put("最高评论", create(2));*/
        titles.add("历史作业");
        /*titles.add("每周热点");
        titles.add("最高评论");*/
        resetGroups(map,titles);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ArticleViewHolder(mInflater.inflate(R.layout.item_list_article, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
        ArticleViewHolder h = (ArticleViewHolder) holder;
        h.mTextTitle.setText(item.getTitle());
        h.mTextContent.setText(item.getContent());
        Glide.with(context)
                .load(item.getImgUrl())
                .into(h.mImageView);
        h.mImageView.setOnClickListener(v->{
            bigImageLoader(item.getImgUrl());
        });
    }

    private static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle, mTextContent;
        private ImageView mImageView;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.tv_title);
            mTextContent = itemView.findViewById(R.id.art_con);
            mImageView = itemView.findViewById(R.id.art_imageView);
        }
    }


    private static Article create(String title, String content, String imgUrl) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setImgUrl(imgUrl);
        return article;
    }

    private static List<Article> create(int p) {
        List<Article> list = new ArrayList<>();
        if (p == 0) {
            list.add(create("《英语天天练》p41-54完成",
                    "已完成",
                    "https://tse3-mm.cn.bing.net/th/id/OIP-C.046bbiK0gPjbS-EVCK_PsQHaJ4?pid=ImgDet&rs=1"));
            list.add(create("默写《望庐山瀑布》和《登飞来峰》",
                    "已完成",
                    "https://tse1-mm.cn.bing.net/th/id/R-C.44e5297b68283125d2a1d080c585102d?rik=1JIc0K4S%2b7LISg&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20190829%2f1ab2012016c54c4da48f4e9128a02647.jpeg&ehk=B7YCPzZ%2fl5tvlQvRFgUtNfT26pJiqGNsixMrD85VF8s%3d&risl=&pid=ImgRaw&r=0"));
            list.add(create("完成数学小练p55",
                    "未完成",
                    "https://tse1-mm.cn.bing.net/th/id/R-C.0680f38c156848531d4cc6e2269cad2b?rik=6fDqARUEmwS%2b8A&riu=http%3a%2f%2fwww.mianfeiwendang.com%2fpic%2fda452edc33dda900bfbb3316de071ccaeb419464%2f2-1192-jpg_6_0_______-822-0-0-822.jpg&ehk=prVq%2f5SIYv%2fTvBrnuyXrd0JVhmRB0Tfj9NJxEECbH5Q%3d&risl=&pid=ImgRaw&r=0"));
            list.add(create("英语第三单元单词预习",
                    "已完成",
                    "https://tse4-mm.cn.bing.net/th/id/OIP-C.W49yvC3ZKH_EsdW6fj5H2gHaIj?pid=ImgDet&rs=1"));
            list.add(create("数学试卷订正并家长签字",
                    "已完成",
                    "https://tse1-mm.cn.bing.net/th/id/R-C.358aea61cc77301c31bdb85aa0d9510b?rik=pcKt6CzKQN%2bMXQ&riu=http%3a%2f%2ffiles.eduuu.com%2fimg%2f2016%2f11%2f17%2f104416_582d1980c9b43.png&ehk=2Wr0CBatrcFuDYKeQ%2f4eypbKk1cfMD9Jmjav2rIj97E%3d&risl=&pid=ImgRaw&r=0"));
        }

        return list;
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
