package com.example.learningmachineparentsapp.Circle.Video.Upload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Circle.Video.GSYVideoBean;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Utils.OkHttpUtil;
import com.example.learningmachineparentsapp.Utils.PathUtils;
import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 上传视频界面
 */
public class UploadActivity extends AppCompatActivity implements View.OnClickListener{

    private Handler handler = new Handler(Looper.getMainLooper());
    public static final String TAG = UploadActivity.class.getName();
    public final static int VIDEO = 101;
    private static String VIDEOPATH = "";      //视频文件路径
    private String con, getStr;
    private UploadProgressDialog uploadProgressDialog;

    private Button upload_btn_upload;
    private ImageView upload_iv_add, upload_iv_back;
    private TextView upload_tv_path;
    private EditText upload_et_con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload);

        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        upload_btn_upload = findViewById(R.id.upload_btn_upload);
        upload_btn_upload.setOnClickListener(this);

        upload_iv_add = findViewById(R.id.upload_iv_add);
        upload_iv_add.setOnClickListener(this);
        Glide.with(UploadActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WWN2n.png")
                .into(upload_iv_add);

        upload_iv_back = findViewById(R.id.upload_iv_back);
        upload_iv_back.setOnClickListener(this);
        Glide.with(UploadActivity.this)
                .load("https://z3.ax1x.com/2021/10/24/5WWs54.png")
                .into(upload_iv_back);

        upload_tv_path = findViewById(R.id.upload_tv_path);

        upload_et_con = findViewById(R.id.upload_et_con);

        uploadProgressDialog = new UploadProgressDialog(UploadActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_iv_back:
                UploadActivity.this.finish();
                break;
            case R.id.upload_btn_upload:
                con = upload_et_con.getText().toString().trim();
                Log.e("con", con);

                if(VIDEOPATH.equals(""))
                    Toast.makeText(UploadActivity.this, "请选择视频后，再点击上传！", Toast.LENGTH_LONG).show();
                else {
                    GSYVideoBean video = new GSYVideoBean("@云淡风轻", con);
                    Gson gson = new Gson();
                    String video_json = gson.toJson(video);
                    System.out.println("video_json:::"+video_json);
                    uploadVideo(video_json);
                    uploadProgressDialog = new UploadProgressDialog(this);
                    uploadProgressDialog.show();
                }
                break;
            case R.id.upload_iv_add:
                choiceVideo();  //打开相册
                upload_tv_path.setText("视频文件路径：" + VIDEOPATH);
                break;
        }
    }

    /**
     * 从相册中选择视频
     */
    private void choiceVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, UploadActivity.VIDEO);
    }

    /**
     * 回调，视频上传处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case VIDEO:
                    if (data != null) {
                        String realPathFromUri = PathUtils.getRealPathFromUri(this, data.getData());
                        VIDEOPATH = realPathFromUri;
                        Log.e("data.getData():", ""+data.getData());
                        Log.e("视频路径：",""+realPathFromUri);
                    } else {
                        Toast.makeText(this, "视频损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


    private void uploadVideo(String video_json) {
        String TOUPLOADVIDEO = "http://192.168.31.95:8081/parent/file/fileuploadwithvideo";
        System.out.println("TOUPLOADVIDEO>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+TOUPLOADVIDEO);

        OkHttpUtil.post_file(TOUPLOADVIDEO, video_json, VIDEOPATH, new OkHttpUtil.IOkhttp_file() {
            @Override
            public void success(final String str) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getStr = str;
                                Log.i("Search","getStr>>>>>>>>>>>"+getStr);
                                if(getStr.equals("true")){
                                    uploadProgressDialog.hide();
                                    Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    uploadProgressDialog.hide();
                                    Toast.makeText(UploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void failure() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadActivity.this, "不要紧张，网络好像出了点问题", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

}