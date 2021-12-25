package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.Homework.HomeworkActivity;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;
import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;
import com.example.learningmachineparentsapp.Widget.Code;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView reg_iv_back, reg_iv_phone, reg_iv_password,
            reg_iv_code, reg_iv_logo, reg_iv_repassword;
    private TextView reg_tv_phcode;
    private Button reg_btn_reg;
    private EditText reg_et_phone, reg_et_code, reg_et_password, reg_et_repassword;
    private String real_code;       // 生成验证码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        makeStatusBarTransparent(this);

        initView();
    }

    private void initView(){
        reg_et_phone = findViewById(R.id.reg_et_phone);
        reg_et_code = findViewById(R.id.reg_et_code);
        reg_et_password = findViewById(R.id.reg_et_password);
        reg_et_repassword = findViewById(R.id.reg_et_repassword);

        reg_tv_phcode = findViewById(R.id.reg_tv_phcode);
        //reg_tv_phcode.setImageBitmap(Code.getInstance().createBitmap());
        reg_tv_phcode.setOnClickListener(this);
        real_code = Code.getInstance().getCode().toLowerCase();

        // reg按钮
        reg_btn_reg = findViewById(R.id.reg_btn_reg);
        reg_btn_reg.setOnClickListener(this);

        // 一些图标
        reg_iv_phone = findViewById(R.id.reg_iv_phone);
        Glide.with(RegisterActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WrkMF.png")
                .into(reg_iv_phone);

        reg_iv_code = findViewById(R.id.reg_iv_code);
        Glide.with(RegisterActivity.this)
                .load("https://z3.ax1x.com/2021/11/01/IPS5on.png")
                .into(reg_iv_code);

        reg_iv_password = findViewById(R.id.reg_iv_password);
        Glide.with(RegisterActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WrP2T.png")
                .into(reg_iv_password);

        reg_iv_repassword = findViewById(R.id.reg_iv_repassword);
        Glide.with(RegisterActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WrP2T.png")
                .into(reg_iv_repassword);

        reg_iv_logo = findViewById(R.id.reg_iv_logo);
        Glide.with(RegisterActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WK2jS.png")
                .into(reg_iv_logo);

        reg_iv_back = findViewById(R.id.reg_iv_back);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/06/IMMs8U.png")
                .into(reg_iv_back);
        reg_iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_btn_reg:
                String phoneCode = reg_et_code.getText().toString().toLowerCase();
                String phone = reg_et_phone.getText().toString().trim();
                String psw = reg_et_password.getText().toString().trim();
                String repsw = reg_et_repassword.getText().toString().trim();

                if(phone.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    //reg_iv_phcode.setImageBitmap(Code.getInstance().createBitmap());
                    real_code = Code.getInstance().getCode().toLowerCase();
                } else if(psw.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    //reg_iv_phcode.setImageBitmap(Code.getInstance().createBitmap());
                    real_code = Code.getInstance().getCode().toLowerCase();
                } else if(repsw.equals("")){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    //reg_iv_phcode.setImageBitmap(Code.getInstance().createBitmap());
                    real_code = Code.getInstance().getCode().toLowerCase();
                } else if (psw.equals(repsw)){
                    Toast.makeText(RegisterActivity.this, "两次密码请保持一致", Toast.LENGTH_SHORT).show();
                    //reg_iv_phcode.setImageBitmap(Code.getInstance().createBitmap());
                    real_code = Code.getInstance().getCode().toLowerCase();
                }

                if(phoneCode.equals(real_code)) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }else if (phoneCode.equals(real_code)) {
                    Toast.makeText(RegisterActivity.this, "注册成功，请填写信息", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, InfoActivity.class));
                    RegisterActivity.this.finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    //reg_iv_phcode.setImageBitmap(Code.getInstance().createBitmap());
                    real_code = Code.getInstance().getCode().toLowerCase();
                }
                break;
            /**
             * 发送验证码
             */
            case R.id.reg_tv_phcode:
                //reg_iv_phcode.setImageBitmap(Code.getInstance().createBitmap());
                //real_code = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.reg_iv_back:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}