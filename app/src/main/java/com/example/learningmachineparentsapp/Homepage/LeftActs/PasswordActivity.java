package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout password_tit;
    private Button password_btn;
    private EditText password_et_psw, password_et_newpsw, password_et_renewpsw;
    private TextView password_tv_forget, password_tv_alter;
    private String psw, newpsw, renewpsw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_password);

        initView();
    }


    private void initView(){
        psw = "";
        newpsw = "";
        renewpsw = "";

        password_tit = findViewById(R.id.password_tit);
        password_tit.setTitle("设置密码");

        password_btn = findViewById(R.id.password_btn);
        password_btn.setOnClickListener(this);

        password_et_psw = findViewById(R.id.password_et_psw);
        password_et_psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                psw = password_et_psw.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                psw = password_et_psw.getText().toString();
                /*if(psw != user.getPsw()){
                    password_tv_alter.setText("密码输入错误");
                }*/
            }
        });
        password_et_newpsw = findViewById(R.id.password_et_newpsw);
        password_et_newpsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newpsw = password_et_newpsw.getText().toString();
                if(checkPsw(newpsw)){
                    password_tv_alter.setVisibility(View.VISIBLE);
                    password_tv_alter.setText("新密码输入不符合规则。");
                }else{
                    password_tv_alter.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                newpsw = password_et_newpsw.getText().toString();
                if(!checkPsw(newpsw)){
                    password_tv_alter.setVisibility(View.VISIBLE);
                    password_tv_alter.setText("新密码输入不符合规则。");
                }else{
                    password_tv_alter.setVisibility(View.INVISIBLE);
                }
            }
        });
        password_et_renewpsw = findViewById(R.id.password_et_renewpsw);
        password_et_renewpsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                renewpsw = password_et_renewpsw.getText().toString();
                if(!renewpsw.equals(newpsw)){
                    password_tv_alter.setVisibility(View.VISIBLE);
                    password_tv_alter.setText("与新密码输入不一致。");
                }else{
                    password_tv_alter.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                renewpsw = password_et_renewpsw.getText().toString();
                if(!renewpsw.equals(newpsw)){
                    password_tv_alter.setVisibility(View.VISIBLE);
                    password_tv_alter.setText("与新密码输入不一致。");
                }else{
                    password_tv_alter.setVisibility(View.INVISIBLE);
                }
            }
        });

        password_tv_forget = findViewById(R.id.password_tv_forget);
        password_tv_forget.setOnClickListener(this);

        password_tv_alter = findViewById(R.id.password_tv_alter);
        password_tv_alter.setVisibility(View.INVISIBLE);
    }


    /**
     * 密码必须是8-16位的数字、字符组合（不能是纯数字或纯字母）
     * @param str 输入的密码
     * @return
     */
    public boolean checkPsw(String str) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        boolean isRight = str.matches(regex);
        return isRight;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.password_btn:
                if(psw.equals("") || newpsw.equals("") || renewpsw.equals("")){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

                if(!newpsw.equals(psw)) {
                    //finish();
                    Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "不能与旧密码保持一致", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.password_tv_forget:
                break;
        }
    }
}