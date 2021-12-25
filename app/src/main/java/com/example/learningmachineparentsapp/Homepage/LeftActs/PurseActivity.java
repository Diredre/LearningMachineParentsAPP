package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

public class PurseActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout purse_tit;
    private TextView purse_tv_bing;
    private ImageView purse_iv_card, purse_iv_transation, purse_iv_pay, purse_iv_help;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_purse);

        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        purse_tit = findViewById(R.id.purse_tit);
        purse_tit.setTitle("钱包");

        purse_tv_bing = findViewById(R.id.purse_tv_bing);
        purse_tv_bing.setOnClickListener(this);

        purse_iv_card = findViewById(R.id.purse_iv_card);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/24/oCxpvR.png")
                .into(purse_iv_card);
        purse_iv_card.setOnClickListener(this);

        purse_iv_transation = findViewById(R.id.purse_iv_transation);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/24/oCxHRH.png")
                .into(purse_iv_transation);

        purse_iv_pay = findViewById(R.id.purse_iv_pay);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/24/oCxQqP.png")
                .into(purse_iv_pay);

        purse_iv_help = findViewById(R.id.purse_iv_help);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/24/oCxOsI.png")
                .into(purse_iv_help);
    }

    /**
     * 弹出底部dialog
     */
    private void showDialog(){
        dialog = new Dialog(this, R.style.DialogTheme);
        View bottomView = View.inflate(this, R.layout.dialog_purse, null);
        dialog.setContentView(bottomView);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final ImageView dialog_purse_cansel = bottomView.findViewById(R.id.dialog_purse_cansel);
        dialog_purse_cansel.setOnClickListener(v->{
            dialog.dismiss();
        });
        final ImageView dialog_purse_iv_phone = bottomView.findViewById(R.id.dialog_purse_iv_phone);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/24/oPJ0I0.png")
                .into(dialog_purse_iv_phone);
        final ImageView dialog_purse_iv = bottomView.findViewById(R.id.dialog_purse_iv);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/24/oCxpvR.png")
                .into(dialog_purse_iv);
        final Button dialog_purse_btn_send = bottomView.findViewById(R.id.dialog_purse_btn_send);
        dialog_purse_btn_send.setOnClickListener(v-> {
            Toast.makeText(this,"登录成功", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.purse_iv_card:
            case R.id.purse_tv_bing:
                showDialog();
                break;
        }
    }
}