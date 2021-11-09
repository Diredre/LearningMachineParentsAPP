package com.example.learningmachineparentsapp.Homepage.Watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
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
    private Button watch_btn_preview, watch_btn_colse;
    private SurfaceHolder surfaceHolder;
    private TitleLayout watch_tit;
    private ImageView watch_iv_tomodule;
    private Camera camera;
    private boolean isPreview = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();    //得到窗口
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    //设置高亮
        setContentView(R.layout.activity_watch);
        makeStatusBarTransparent(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);    //设置手机应用内部状态栏字体图标为黑色

        initView();
    }

    private void initView() {
        // 设置标题
        watch_tit = findViewById(R.id.watch_tit);
        watch_tit.setTitle("学习监测");

        // 跳转进入学习机使用功能模块时间分析界面
        watch_iv_tomodule = findViewById(R.id.watch_iv_tomodule);
        Glide.with(WatchActivity.this)
                .load("https://z3.ax1x.com/2021/11/05/IKVl8A.png")
                .into(watch_iv_tomodule);
        watch_iv_tomodule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchActivity.this, ModuleActivity.class));
            }
        });

        watch_sv_camera = findViewById(R.id.watch_sv_camera);
        surfaceHolder = watch_sv_camera.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        watch_btn_preview = findViewById(R.id.watch_btn_preview);
        watch_btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPreview) {
                    // 如果没打开->打开摄像头
                    camera = Camera.open();
                    isPreview = true;
                }
                try {
                    watch_btn_preview.setText("对焦");
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
                }
            }
        });

        watch_btn_colse = findViewById(R.id.watch_btn_colse);
        watch_btn_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.stopPreview();
                camera.release();
            }
        });
    }


    protected void onPause() {
        super.onPause();
        if(camera != null){
            camera.stopPreview();
            camera.release();
        }
    }
}