package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Widget.CirclePgBar;
import com.example.learningmachineparentsapp.Widget.JellyInterpolator;
import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView login_iv_phone, login_iv_password, login_iv_logo;
    private TextView login_tv_resgister, login_tv_forget;
    private Button login_btn_login;
    private LinearLayout input_layout_phone, input_layout_psw;
    private View login_ll_input, login_ll_progress;
    private CirclePgBar login_pb;
    private float mWidth, mHeight;

    private UserInfo userInfo;  // 用户


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        makeStatusBarTransparent(LoginActivity.this);

        initView();
    }

    private void initView(){
        // login按钮
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_login.setOnClickListener(this);

        // “立即注册”
        login_tv_resgister = findViewById(R.id.login_tv_resgister);
        login_tv_resgister.setOnClickListener(this);

        // “忘记密码”
        login_tv_forget = findViewById(R.id.login_tv_forget);
        login_tv_forget.setOnClickListener(this);

        // 输入密码和手机号码的外层布局
        input_layout_psw = findViewById(R.id.input_layout_psw);
        input_layout_phone = findViewById(R.id.input_layout_phone);

        // 一些图标
        login_iv_phone = findViewById(R.id.login_iv_phone);
        Glide.with(LoginActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WrkMF.png")
                .into(login_iv_phone);

        login_iv_password = findViewById(R.id.login_iv_password);
        Glide.with(LoginActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WrP2T.png")
                .into(login_iv_password);

        login_iv_logo = findViewById(R.id.login_iv_logo);
        Glide.with(LoginActivity.this)
                //.load("https://z3.ax1x.com/2021/10/24/5RqzY6.png")
                //.load("https://z3.ax1x.com/2021/10/24/5RObZ9.png")
                .load("https://z3.ax1x.com/2021/10/24/5WK2jS.png")
                .into(login_iv_logo);

        // CirclePgBar进度条
        login_pb = findViewById(R.id.login_pb);

        // 输入框和进度条布局
        login_ll_input = findViewById(R.id.login_ll_input);
        login_ll_progress = findViewById(R.id.login_ll_progress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // login按钮
            case R.id.login_btn_login:
                // 计算出控件的高与宽
                mWidth = login_btn_login.getMeasuredWidth();
                mHeight = login_btn_login.getMeasuredHeight();
                login_btn_login.setWidth((int)(mWidth+1));
                login_btn_login.setHeight((int)(mHeight+1));
                // 隐藏输入框
                input_layout_phone.setVisibility(View.INVISIBLE);
                input_layout_psw.setVisibility(View.INVISIBLE);
                inputAnimator(login_ll_input, mWidth, mHeight);
                // 设置进度条3s达到100%，跳转进入主界面
                login_pb.setProgress(100, 3000);
                login_pb.setOnCircleProgressListener(new CirclePgBar.OnCircleProgressListener() {
                    @Override
                    public boolean OnCircleProgress(int progress) {
                        if(progress == 100){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            login_pb.setProgress(0);
                            LoginActivity.this.finish();
                        }
                        return false;
                    }
                });
                break;

            // 立即注册
            case R.id.login_tv_resgister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.finish();
                break;

            // 忘记密码
            case R.id.login_tv_forget:
                startActivity(new Intent(LoginActivity.this, RemeberActivity.class));
                break;
        }
    }

    /**
     * 输入框的动画效果
     * @param view 控件
     * @param w 宽
     * @param h 高
     */
    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(login_ll_input, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                login_ll_progress.setVisibility(View.VISIBLE);
                progressAnimator(login_ll_progress);
                login_ll_input.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
        });

    }

    /**
     * 出现进度动画
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        login_ll_progress.setVisibility(View.GONE);
        login_ll_input.setVisibility(View.VISIBLE);
        input_layout_phone.setVisibility(View.VISIBLE);
        input_layout_psw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) login_ll_input.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        login_ll_input.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(login_ll_input, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
}