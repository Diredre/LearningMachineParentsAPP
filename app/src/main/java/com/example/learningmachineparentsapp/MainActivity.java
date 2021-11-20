package com.example.learningmachineparentsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.LoginRegist.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView main_iv_off, main_iv_setting, main_iv_publish, main_iv_purse,
            main_iv_help, main_iv_account, main_iv_cache, main_iv_private, main_iv_qrcode;
    private LinearLayout main_ll_publish, main_ll_purse, main_ll_help, main_ll_account,
            main_ll_cache, main_ll_private;
    private BottomNavigationView nav_view;
    private DrawerLayout main_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        initView();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(nav_view, navController);
    }


    public DrawerLayout getDrawer(){
        return main_drawer;
    }


    private void initView() {
        nav_view = findViewById(R.id.nav_view);
        main_drawer = findViewById(R.id.main_drawer);

        main_iv_setting = findViewById(R.id.main_iv_setting);
        main_iv_setting.setOnClickListener(this);
        Glide.with(MainActivity.this)
                .load("https://z3.ax1x.com/2021/11/01/ICMZlQ.png")
                .into(main_iv_setting);

        main_iv_off = findViewById(R.id.main_iv_off);
        main_iv_off.setOnClickListener(this);
        Glide.with(MainActivity.this)
                .load("https://z3.ax1x.com/2021/11/01/ICMSOA.png")
                .into(main_iv_off);

        main_iv_qrcode = findViewById(R.id.main_iv_qrcode);
        main_iv_qrcode.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqqLef.png")
                .into(main_iv_qrcode);

        main_iv_publish = findViewById(R.id.main_iv_publish);
        main_ll_publish = findViewById(R.id.main_ll_publish);
        main_ll_publish.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqXxYD.png")
                .into(main_iv_publish);

        main_iv_purse = findViewById(R.id.main_iv_purse);
        main_ll_purse = findViewById(R.id.main_ll_purse);
        main_ll_purse.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqjPOI.png")
                .into(main_iv_purse);

        main_iv_help = findViewById(R.id.main_iv_help);
        main_ll_help = findViewById(R.id.main_ll_help);
        main_ll_help.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqjnpQ.png")
                .into(main_iv_help);

        main_iv_account = findViewById(R.id.main_iv_account);
        main_ll_account = findViewById(R.id.main_ll_account);
        main_ll_account.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/Iqjlmq.png")
                .into(main_iv_account);

        main_iv_cache = findViewById(R.id.main_iv_cache);
        main_ll_cache = findViewById(R.id.main_ll_cache);
        main_ll_cache.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqjGkT.png")
                .into(main_iv_cache);

        main_iv_private = findViewById(R.id.main_iv_private);
        main_ll_private = findViewById(R.id.main_ll_private);
        main_ll_private.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqjJtU.png")
                .into(main_iv_private);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_setting:
                Toast.makeText(MainActivity.this, "设置界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_iv_off:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
                break;
            case R.id.main_ll_publish:
                Toast.makeText(MainActivity.this, "我的发布界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_ll_purse:
                Toast.makeText(MainActivity.this, "钱包界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_ll_help:
                Toast.makeText(MainActivity.this, "帮助与反馈界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_ll_account:
                Toast.makeText(MainActivity.this, "账号与安全界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_ll_cache:
                Toast.makeText(MainActivity.this, "清除缓存界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_ll_private:
                Toast.makeText(MainActivity.this, "隐私设置界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_iv_qrcode:
                Toast.makeText(MainActivity.this, "二维码界面还没写", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * 状态栏透明的方法
     *
     * @param activity
     */
    public static void makeStatusBarTransparent(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}