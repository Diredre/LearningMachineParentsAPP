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

    private ImageView main_iv_off, main_iv_setting;
    private BottomNavigationView nav_view;
    private DrawerLayout main_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        makeStatusBarTransparent(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);    //设置手机应用内部状态栏字体图标为黑色
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