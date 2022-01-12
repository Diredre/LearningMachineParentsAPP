package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.okhttpClass;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;
/**
 * 忘记（找回）密码界面
 */
public class RemeberActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView rem_iv_back, rem_iv_logo, rem_iv_phone, rem_iv_code, rem_iv_password, rem_iv_repassword;
    private TextView rem_tv_phcode;
    private Button rem_btn_reg;
    private EditText rem_et_phone, rem_et_code, rem_et_password, rem_et_repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remeber);
        makeStatusBarTransparent(this);

        initView();
    }

    private void initView(){
        rem_et_phone = findViewById(R.id.rem_et_phone);
        rem_et_code = findViewById(R.id.rem_et_code);
        rem_et_password = findViewById(R.id.rem_et_password);
        rem_et_repassword = findViewById(R.id.rem_et_repassword);

        // yes按钮
        rem_btn_reg = findViewById(R.id.rem_btn_reg);
        rem_btn_reg.setOnClickListener(this);

        // 一些图标
        rem_iv_phone = findViewById(R.id.rem_iv_phone);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/10/24/5WrkMF.png")
                .into(rem_iv_phone);

        rem_iv_code = findViewById(R.id.rem_iv_code);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/01/IPS5on.png")
                .into(rem_iv_code);

        rem_iv_password = findViewById(R.id.rem_iv_password);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/10/24/5WrP2T.png")
                .into(rem_iv_password);

        rem_iv_repassword = findViewById(R.id.rem_iv_repassword);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/10/24/5WrP2T.png")
                .into(rem_iv_repassword);

        rem_iv_logo = findViewById(R.id.rem_iv_logo);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/10/24/5WK2jS.png")
                .into(rem_iv_logo);

        rem_iv_back = findViewById(R.id.rem_iv_back);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/06/IMMs8U.png")
                .into(rem_iv_back);
        rem_iv_back.setOnClickListener(this);

        rem_tv_phcode = findViewById(R.id.rem_tv_phcode);
        rem_tv_phcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rem_btn_reg:
                String phoneCode = rem_et_code.getText().toString().toLowerCase();
                String phone = rem_et_phone.getText().toString().trim();
                String psw = rem_et_password.getText().toString().trim();
                String repsw = rem_et_repassword.getText().toString().trim();
                if(phone.equals("")){
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                } else if(psw.equals("")){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if(repsw.equals("")){
                    Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                } else if (!psw.equals(repsw)){
                    Toast.makeText(this, "两次密码请保持一致", Toast.LENGTH_SHORT).show();
                } else {
                    modifyParentPassword(phone, phoneCode, psw);
                    Toast.makeText(this, "更改密码成功", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                break;
            case R.id.rem_tv_phcode:
                String phone1 = rem_et_phone.getText().toString().trim();
                getSMS(phone1);
                break;
            case R.id.rem_iv_back:
                finish();
                break;
        }
    }


    private void modifyParentPassword(String phone, String code, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tool = new okhttpClass();
                String res = tool.modifyParentPassword(phone, code, password);
                Log.e("modifyParentPassword:", res);
            }
        }).start();
    }

    private void getSMS(String phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tool = new okhttpClass();
                Log.e("getSMS:", phone);
                tool.getSms2(phone);
            }
        }).start();
    }

}