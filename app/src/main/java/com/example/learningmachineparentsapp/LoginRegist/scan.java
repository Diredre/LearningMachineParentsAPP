package com.example.learningmachineparentsapp.LoginRegist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scan extends AppCompatActivity {

    private ImageView scan_iv, scan_iv_bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan);

        scan_iv = findViewById(R.id.scan_iv);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/12/obLY1f.png")
                .into(scan_iv);
        scan_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(scan.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.setPrompt("请扫描二维码");           //底部的提示文字
                intentIntegrator.setCameraId(0);                    //前置或者后置摄像头
                intentIntegrator.setBeepEnabled(true);              //扫描成功的提示音，默认开启
                intentIntegrator.setBarcodeImageEnabled(true);      //是否保留扫码成功时候的截图
                intentIntegrator.initiateScan();
            }
        });

        scan_iv_bg = findViewById(R.id.scan_iv_bg);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/12/obj8hR.png")
                .into(scan_iv_bg);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(scanResult!=null){
            String result = scanResult.getContents();
            Toast.makeText(this, result+" 绑定成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterActivity.class));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
