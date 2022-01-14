package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.learningmachineparentsapp.okhttpClass;
import com.google.gson.Gson;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public static int UPDATEPR = 1;
    private ImageView reg_iv_back, reg_iv_phone, reg_iv_password,
            reg_iv_code, reg_iv_logo, reg_iv_repassword;
    private TextView reg_tv_phcode;
    private Button reg_btn_reg;
    private EditText reg_et_phone, reg_et_code, reg_et_password, reg_et_repassword;

    private Handler handler;
    private String parentid;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        makeStatusBarTransparent(this);

        sp = getSharedPreferences("userInfo", 0);
        parentid = sp.getString("PARENTID", "15");

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("ISREG", true);
        editor.commit();

        Log.e("PARENTID", parentid);

        initView();
    }

    private void initView(){
        reg_et_phone = findViewById(R.id.reg_et_phone);
        reg_et_code = findViewById(R.id.reg_et_code);
        reg_et_password = findViewById(R.id.reg_et_password);
        reg_et_repassword = findViewById(R.id.reg_et_repassword);

        reg_tv_phcode = findViewById(R.id.reg_tv_phcode);
        reg_tv_phcode.setOnClickListener(this);

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
                } else if(phone.length() != 11){
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else if(psw.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if(repsw.equals("")){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if (!psw.equals(repsw)){
                    Toast.makeText(RegisterActivity.this, "两次密码请保持一致", Toast.LENGTH_SHORT).show();
                }else if(phoneCode.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }else{
                    uploadParentRegister(phone, psw, phoneCode);
                    handler = new Handler(){
                        public void handleMessage(Message msg) {
                            Log.e("RegisterAct", "验证码线程");
                            Gson gson = new Gson();
                            if(msg.what == UPDATEPR){
                                String str = (String)msg.obj;
                                ParentRegGson parent = gson.fromJson(str, ParentRegGson.class);
                                if(parent.getMsg().equals("注册成功")){
                                    Toast.makeText(RegisterActivity.this, "注册成功，请填写信息", Toast.LENGTH_SHORT).show();
                                    parentid = String.valueOf(parent.getData());

                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("PARENTID", String.valueOf(parent.getData()));
                                    editor.commit();

                                    Intent intent = new Intent(RegisterActivity.this, InfoActivity.class);
                                    intent.putExtra("PHONE", phone);
                                    intent.putExtra("PSW", psw);
                                    startActivity(intent);
                                    RegisterActivity.this.finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                                    reg_et_code.setText("");
                                }
                            }
                        }
                    };
                }
                break;
            /**
             * 发送验证码
             */
            case R.id.reg_tv_phcode:
                String phone1 = reg_et_phone.getText().toString().trim();
                Toast.makeText(this, "发送成功"+phone1, Toast.LENGTH_SHORT).show();
                getSMS(phone1);
                break;
            case R.id.reg_iv_back:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
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


    private void uploadParentRegister(String phone, String password, String code){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tool = new okhttpClass();
                String res = tool.uploadParentRegister(phone, password, code);
                Log.e("uploadParentRegister:", res);

                Message message = new Message();
                message.what = UPDATEPR;
                message.obj = res;
                handler.sendMessage(message);
            }
        }).start();
    }

    /**
     * 判断手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }
}