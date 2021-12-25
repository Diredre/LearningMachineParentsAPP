package com.example.learningmachineparentsapp.Homepage.Watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.Module.ModuleActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

import java.io.IOException;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class WatchActivity extends AppCompatActivity {

    private SurfaceView watch_sv_camera;
    private SurfaceHolder surfaceHolder;
    private ImageView watch_iv_close;
    private Camera camera;
    private boolean isPreview = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();    //得到窗口
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    //设置高亮
        setContentView(R.layout.activity_watch);

        initView();
    }


    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        watch_sv_camera = findViewById(R.id.watch_sv_camera);
        surfaceHolder = watch_sv_camera.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        watch_iv_close = findViewById(R.id.watch_iv_close);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/11/17/I4icKx.png")
                .into(watch_iv_close);
        watch_iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.stopPreview();
                camera.release();
                finish();
            }
        });

        /*if (!isPreview) {
            // 如果没打开->打开摄像头
            camera = Camera.open();
            isPreview = true;
        }
        try {
            camera.setPreviewDisplay(surfaceHolder);// 设置预览
            Camera.Parameters parameters = camera.getParameters();// 获取摄像头参数
            parameters.setPictureFormat(PixelFormat.JPEG);// 设置图片为jpg
            parameters.set("jpeg-quality", 80);// 设置图片质量
            camera.setParameters(parameters);   // 重新设置摄像头参数
            camera.startPreview();              // 开始预览
            camera.setDisplayOrientation(90);   // 不加的话，预览的图像就是横的
            camera.autoFocus(null);         // 自动对焦
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    protected void onPause() {
        super.onPause();
        if(camera != null){
            camera.stopPreview();
            camera.release();
        }
    }
}