package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

public class PrivateActivity extends AppCompatActivity {

    private TitleLayout private_tit;
    private SwitchButton private_sb_friend, private_sb_allow;

    private SharedPreferences sp;
    private boolean friend = false, allow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_private);

        sp = getSharedPreferences("userInfo", 0);
        friend = sp.getBoolean("FRIEND", false);
        allow = sp.getBoolean("ALLOW", false);
        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        private_tit = findViewById(R.id.private_tit);
        private_tit.setTitle("隐私设置");

        private_sb_friend = findViewById(R.id.private_sb_friend);
        private_sb_allow.setChecked(friend);
        private_sb_friend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                friend = isChecked;
            }
        });

        private_sb_allow = findViewById(R.id.private_sb_allow);
        private_sb_allow.setChecked(allow);
        private_sb_allow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                allow = isChecked;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //记住用户名、密码、id
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("ALLOW", allow);
        editor.putBoolean("FRIEND", friend);
        editor.commit();
    }
}