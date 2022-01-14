package com.example.learningmachineparentsapp.LoginRegist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Utils.ToastUtils;
import com.example.learningmachineparentsapp.okhttpClass;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class scan extends AppCompatActivity {

    private ImageView scan_btn, scan_iv_logo, scan_iv_bg, scan_iv_back;
    private String childId, childAccount;
    private SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan);
        makeStatusBarTransparent(this);

        sp = getSharedPreferences("userInfo", 0);
        childAccount = sp.getString("CHILDACCOUNT", "16390362587663");
        childId = sp.getString("CHILDID", "1");
        Log.e("CHILDACCOUNT", childAccount);

        scan_btn = findViewById(R.id.scan_btn);
        Glide.with(this)
                .load("https://s4.ax1x.com/2022/01/12/7nr8mV.png")
                .into(scan_btn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(scan.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.setPrompt("请扫描二维码");       //底部的提示文字
                intentIntegrator.setCameraId(0);                //前置或者后置摄像头
                intentIntegrator.setBeepEnabled(true);          //扫描成功的提示音，默认开启
                intentIntegrator.setBarcodeImageEnabled(true);  //是否保留扫码成功时候的截图
                intentIntegrator.initiateScan();
            }
        });

        scan_iv_logo = findViewById(R.id.scan_iv_logo);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/10/24/5WK2jS.png")
                .into(scan_iv_logo);


        scan_iv_bg = findViewById(R.id.scan_iv_bg);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/12/obj8hR.png")
                .into(scan_iv_bg);

        scan_iv_back = findViewById(R.id.scan_iv_back);
        scan_iv_back.setOnClickListener(v -> {
            this.finish();
            startActivity(new Intent(this, MainActivity.class));
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(scanResult != null){
            String result = scanResult.getContents();
            searchQrLogin(result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void searchQrLogin(String uuid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("uuid",uuid)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/" + "/sso/login/scanQrLogin")
                        .post(body)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("UUID", uuid);
                        Log.e("childAccount", childAccount);
                        String s = response.body().string();
                        Log.e("tag", "onResponse: "+s);
                        try {
                            JSONObject js = new JSONObject(s);
                            int code = js.getInt("code");
                            if (code == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(scan.this,"绑定成功",Toast.LENGTH_SHORT).show();
                                        confirmBitmap(uuid, childAccount);
                                    }
                                });
                            } else {
                                ToastUtils.show(scan.this, "扫描失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public void confirmBitmap(String uuid, String account){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("uuid",uuid)
                        .addFormDataPart("account",account)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://221.12.170.98:91/lamp/"+
                                "/sso/login/confirmQrLogin")
                        .post(body)
                        .build();

                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) { }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String response_answer = response.body().string();
                        Log.d("TAG", "onResponse: "+response_answer);
                        try {
                            JSONObject js = new JSONObject(response_answer);
                            int code = js.getInt("code");
                            if (code == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(scan.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(scan.this, MainActivity.class));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(scan.this,"扫描失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}
