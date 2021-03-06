package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Discover.MailActivity;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Utils.ToastUtils;
import com.example.learningmachineparentsapp.Widget.CirclePgBar;
import com.example.learningmachineparentsapp.Widget.JellyInterpolator;
import com.example.learningmachineparentsapp.okhttpClass;
import com.example.learningmachineparentsapp.webrtc.common.Constant;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

/**
 * ????????????
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static int LOGIN = 1;
    private ImageView login_iv_phone, login_iv_password, login_iv_logo, login_iv_bg;
    private TextView login_tv_resgister, login_tv_forget;
    private EditText login_et_phone, login_et_password;
    private Button login_btn_login;
    private CheckBox login_cb_aotologin, login_cb_rempsw;
    private LinearLayout input_layout_phone, input_layout_psw;
    private View login_ll_input, login_ll_progress;
    private CirclePgBar login_pb;
    private float mWidth, mHeight;

    private String phone, psw;
    private boolean auto_login_flag = false,
            rem_psw_flag = false;
    private SharedPreferences sp;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            if (msg.what == LOGIN) {
                String s = (String) msg.obj;
                LoginGson loginGson = gson.fromJson(s, LoginGson.class);
                int code = loginGson.getCode();
                Log.e("login", s);
                if (code == 200) {
                    phone = login_et_phone.getText().toString().trim();
                    psw = login_et_password.getText().toString().trim();
                    String id = "" + loginGson.getData().getId();
                    if (login_cb_rempsw.isChecked()) {
                        //???????????????????????????id
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_PHONE", phone);
                        editor.putString("PASSWORD", psw);
                        editor.putString("PARENTID", id);
                        editor.putBoolean("AUTOLOGIN", auto_login_flag);
                        editor.putBoolean("REMPSW", rem_psw_flag);
                        editor.commit();
                    }
                    getChilds(id);

                    // ???????????????????????????
                    mWidth = login_btn_login.getMeasuredWidth();
                    mHeight = login_btn_login.getMeasuredHeight();
                    login_btn_login.setWidth((int) (mWidth + 1));
                    login_btn_login.setHeight((int) (mHeight + 1));
                    // ???????????????
                    input_layout_phone.setVisibility(View.INVISIBLE);
                    input_layout_psw.setVisibility(View.INVISIBLE);
                    inputAnimator(login_ll_input, mWidth, mHeight);
                    // ???????????????3s??????100%????????????????????????
                    login_pb.setProgress(100, 3000);
                    login_pb.setOnCircleProgressListener(new CirclePgBar.OnCircleProgressListener() {
                        @Override
                        public boolean OnCircleProgress(int progress) {
                            if (progress == 100) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                login_pb.setProgress(0);
                                LoginActivity.this.finish();
                            }
                            return false;
                        }
                    });
                }else{
                    Log.d("TAG", "onResponse: ");
                    ToastUtils.show(LoginActivity.this, "????????????????????????");
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        makeStatusBarTransparent(LoginActivity.this);

        //softwareUpdate();

        initView();

        sp = getSharedPreferences("userInfo", 0);
        phone = sp.getString("USER_PHONE", "");
        psw = sp.getString("PASSWORD", "");
        auto_login_flag = sp.getBoolean("AUTOLOGIN", false);
        rem_psw_flag = sp.getBoolean("REMPSW", false);

        if(rem_psw_flag){
            login_et_phone.setText(phone);
            login_et_password.setText(psw);
            login_cb_rempsw.setChecked(true);
        }
        if(auto_login_flag) {
            //?????????????????????????????????
            login_cb_aotologin.setChecked(true);
            login_cb_rempsw.setChecked(true);
            login_et_phone.setText(phone);
            login_et_password.setText(psw);
            new LoginThread().start();
            //?????????????????????????????????
            login_cb_aotologin.setChecked(true);
            //????????????
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    }

    private void initView(){

        // login??????
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_login.setOnClickListener(this);

        // ??????????????????
        login_tv_resgister = findViewById(R.id.login_tv_resgister);
        login_tv_resgister.setOnClickListener(this);

        // ??????????????????
        login_tv_forget = findViewById(R.id.login_tv_forget);
        login_tv_forget.setOnClickListener(this);

        // ??????????????????????????????????????????
        input_layout_psw = findViewById(R.id.input_layout_psw);
        input_layout_phone = findViewById(R.id.input_layout_phone);

        //?????????
        login_et_phone = findViewById(R.id.login_et_phone);
        login_et_password = findViewById(R.id.login_et_password);

        // ????????????
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
                .load("https://z3.ax1x.com/2021/10/24/5WK2jS.png")
                .into(login_iv_logo);
        //**____????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        login_iv_logo.setOnClickListener(this);

        login_iv_bg = findViewById(R.id.login_iv_bg);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/12/obj8hR.png")
                .into(login_iv_bg);

        // CirclePgBar?????????
        login_pb = findViewById(R.id.login_pb);

        // ???????????????????????????
        login_ll_input = findViewById(R.id.login_ll_input);
        login_ll_progress = findViewById(R.id.login_ll_progress);

        login_cb_aotologin = findViewById(R.id.login_cb_aotologin);
        login_cb_aotologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    auto_login_flag = true;
                    rem_psw_flag = true;
                    login_cb_rempsw.setChecked(true);
                }else{
                    auto_login_flag = false;
                    rem_psw_flag = false;
                    login_cb_rempsw.setChecked(false);
                }
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("AUTOLOGIN", auto_login_flag);
                editor.commit();
            }
        });
        login_cb_rempsw = findViewById(R.id.login_cb_rempsw);
        login_cb_rempsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rem_psw_flag = true;
                }else{
                    rem_psw_flag = false;
                }
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("REMPSW", rem_psw_flag);
                editor.commit();
            }
        });
    }


    /**
     * ????????????
     */
    private void UploadLogin(String phone, String psw){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("phone",phone)
                        .addFormDataPart("password",psw)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/" + "/sso/login/parentlogin")
                        .post(body)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("Login.onResponse", s);
                        Message mes = new Message();
                        mes.what = LOGIN;
                        mes.obj = s;
                        handler.sendMessage(mes);
                    }
                });
            }
        }).start();
    }


    public void getChilds(String parentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttpClass tool = new okhttpClass();
                String res = tool.getChilds(sp.getString("PARENTID", "15"));
                Log.e("getChild", res);
                Gson gson = new Gson();
                GetChildGson getChildGson = gson.fromJson(res, GetChildGson.class);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("CHILDID", getChildGson.getData().get(0).getSid());
                editor.putString("CHILDACCOUNT", getChildGson.getData().get(0).getAccount());
                editor.commit();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // login??????
            case R.id.login_btn_login:
                phone = login_et_phone.getText().toString().trim();
                psw = login_et_password.getText().toString().trim();
                UploadLogin(phone, psw);
                break;

            // ????????????
            case R.id.login_tv_resgister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.finish();
                break;

            // ????????????
            case R.id.login_tv_forget:
                startActivity(new Intent(this, RemeberActivity.class));
                break;

            case R.id.login_iv_logo:
                break;
        }
    }

    /**
     * ????????????????????????
     * @param view ??????
     * @param w ???
     * @param h ???
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
                 * ?????????????????????????????????????????????????????????????????????
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
     * ??????????????????
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
     * ??????????????????
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

    /**????????? ??????????????????2?????????????????????*/
    class LoginThread extends Thread{
        @Override
        public void run() {
            try {
                sleep(2000);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ????????????
     *
     * @param context
     * @param versionCode   ?????????
     * @param url           apk????????????
     * @param updateMessage ????????????
     * @param isForced      ??????????????????
     */
    public static void checkUpdate(Context context, int versionCode, String url, String updateMessage, boolean isForced) {
        if (versionCode > UpdateManager.getInstance().getVersionCode(context)) {
            int type = 0;//???????????????0??????????????????1??????????????????2???????????????
            if (UpdateManager.getInstance().isWifi(context)) {
                type = 1;
            }
            if (isForced) {
                type = 2;
            }

            //?????????????????????
            String downLoadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";
            File dir = new File(downLoadPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            if (fileName == null && TextUtils.isEmpty(fileName) && !fileName.contains(".apk")) {
                fileName = context.getPackageName() + ".apk";
            }
            File file = new File(downLoadPath + fileName);

            //????????????
            UpdateManager.getInstance().setType(type).setUrl(url).setUpdateMessage(updateMessage).setFileName(fileName).setIsDownload(file.exists());
            if (type == 1 && !file.exists()) {
                UpdateManager.getInstance().downloadFile(context);
            } else {
                UpdateManager.getInstance().showDialog(context);
            }
        }
    }

    private void softwareUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/student"+
                                "student/version/getVersion")
                        .get()
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.d("softwareUpdate", "onResponse: "+s);
                        try {
                            JSONObject js = new JSONObject(s);
                            JSONObject data = js.getJSONObject("data");
                            String url = data.getString("address");
                            int version = data.getInt("version");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkUpdate(LoginActivity.this,version,url,"??????",false);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}