package com.example.learningmachineparentsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.LeftActs.AccountActivity;
import com.example.learningmachineparentsapp.Homepage.LeftActs.HelpActivity;
import com.example.learningmachineparentsapp.Homepage.LeftActs.PersonActivity;
import com.example.learningmachineparentsapp.Homepage.LeftActs.PrivateActivity;
import com.example.learningmachineparentsapp.Homepage.LeftActs.PublishActivity;
import com.example.learningmachineparentsapp.Homepage.LeftActs.PurseActivity;
import com.example.learningmachineparentsapp.LoginRegist.LoginActivity;
import com.example.learningmachineparentsapp.Utils.FloatTool;
import com.example.learningmachineparentsapp.View.RoundImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView main_iv_publish, main_iv_purse, main_iv_help, main_iv_account,
            main_iv_cache, main_iv_private, main_iv_qrcode;
    private LinearLayout main_ll_publish, main_ll_purse, main_ll_help, main_ll_account,
            main_ll_cache, main_ll_private;
    private RoundImageView main_riv_usericon;
    private TextView main_tv_off, main_tv_username;
    private BottomNavigationView nav_view;
    private DrawerLayout main_drawer;

    public static UserBean user = new UserBean();

    private SharedPreferences sp;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        FloatTool.RequestOverlayPermission(this);
        getPremission();

        if(!checkFloatPermission())
            setFloatPermission();
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("userInfo", 0);
        name = sp.getString("USER_NAME", "云淡风轻");

        initView();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(nav_view, navController);

    }

    /**
     * 动态获取权限
     */
    /*String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.SYSTEM_ALERT_WINDOW,};*/
    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码
    private void getPremission() {
        /*mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0;i<permissions.length;i++){
            if (ContextCompat.checkSelfPermission(this,permissions[i])!=
                    PackageManager.PERMISSION_GRANTED){
                mPermissionList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
            }
        }
        //申请权限
        if (mPermissionList.size()>0){//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this,permissions,mRequestCode);
        }*/

        AndPermission.with(this)
                .runtime()
                .permission(Permission.RECORD_AUDIO)
                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                .rationale((context, permissions, executor) -> {
                    // 此处可以选择显示提示弹窗
                    executor.execute();
                })
                // 用户给权限了
                .onGranted(permissions -> Toast.makeText(this, "用户已经给权限", Toast.LENGTH_SHORT).show())
                // 用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(permissions -> {
                    Toast.makeText(this, "用户拒绝权限", Toast.LENGTH_SHORT).show();
                })
                .start();

        AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA)
                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                .rationale((context, permissions, executor) -> {
                    // 此处可以选择显示提示弹窗
                    executor.execute();
                })
                // 用户给权限了
                .onGranted(permissions -> Toast.makeText(this, "用户已经给权限", Toast.LENGTH_SHORT).show())
                // 用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(permissions -> {
                    Toast.makeText(this, "用户拒绝权限", Toast.LENGTH_SHORT).show();
                })
                .start();
    }


    /**
     * 获取悬浮弹窗权限
     */
    public boolean checkFloatPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(MainActivity.this)) {
                return true;
            } else {
                return false;
                //若没有权限，提示获取.
            }
        }else {
            //SDK在23以下，不用管.
            return true;
        }
    }

    public void setFloatPermission(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        Toast.makeText(MainActivity.this,"需要取得权限以使用悬浮窗",Toast.LENGTH_SHORT).show();
        startActivityForResult(intent,1);
    }


    /** Activity执行结果 */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FloatTool.onActivityResult(requestCode, resultCode, data, this);

        if (requestCode == 1){
            if (checkFloatPermission()){
                System.out.println("悬浮窗权限申请成功...");
            }else{
                System.out.println("悬浮窗权限申请失败...");
                finish();
            }
        }
    }


    public DrawerLayout getDrawer(){
        return main_drawer;
    }


    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        nav_view = findViewById(R.id.nav_view);
        main_drawer = findViewById(R.id.main_drawer);

        main_tv_off = findViewById(R.id.main_tv_off);
        main_tv_off.setOnClickListener(this);

        main_iv_qrcode = findViewById(R.id.main_iv_qrcode);
        /*main_iv_qrcode.setOnClickListener(this);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/20/IqqLef.png")
                .into(main_iv_qrcode);*/

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

        main_riv_usericon = findViewById(R.id.main_riv_usericon);
        main_riv_usericon.setOnClickListener(this);

        main_tv_username = findViewById(R.id.main_tv_username);
        main_tv_username.setText(name);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tv_off:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
                break;
            case R.id.main_ll_publish:
                startActivity(new Intent(MainActivity.this, PublishActivity.class));
                break;
            case R.id.main_ll_purse:
                startActivity(new Intent(MainActivity.this, PurseActivity.class));
                break;
            case R.id.main_ll_help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;
            case R.id.main_ll_account:
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                break;
            case R.id.main_ll_cache:
                Toast.makeText(MainActivity.this, "清除缓存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_ll_private:
                startActivity(new Intent(MainActivity.this, PrivateActivity.class));
                break;
            case R.id.main_iv_qrcode:
                //Toast.makeText(MainActivity.this, "二维码界面还没写", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_riv_usericon:
                startActivity(new Intent(MainActivity.this, PersonActivity.class));
                break;
        }
    }


    /**
     * 状态栏透明的方法
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


    /**【双击退出应用】*/
    private long exitTime = 0;          //保存点击的时间
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {       //连续按退出 <= 2s才能退出应用
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}