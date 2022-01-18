package com.example.learningmachineparentsapp.Circle.Video.Upload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private SharedPreferences sp;
    private String parentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload);

        sp = getSharedPreferences("userInfo", 0);
        parentId = sp.getString("PARENTID", "15");

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
                upload_tv_path.setText("视频文件路径：" + VIDEOPATH);
                upload_iv_add.setImageBitmap(getVideoThumb(VIDEOPATH));
                Log.e("con", con);

                if(VIDEOPATH.equals(""))
                    Toast.makeText(UploadActivity.this, "请选择视频后，再点击上传！", Toast.LENGTH_LONG).show();
                else {
                    post_file(parentId, "1", con, new File(VIDEOPATH));
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


    /**
     * 上传视频
     * @param parentId
     * @param cityId
     * @param content
     * @param file
     */
    protected void post_file(String parentId, String cityId, String content, File file) {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            Toast.makeText(this, "视频文件不存在，请重新选择", Toast.LENGTH_SHORT).show();
            RequestBody body = RequestBody.create(MediaType.parse("mp4/*"),file);
            String filename = file.getName();
            requestBody.addFormDataPart("file",file.getName(),body).addFormDataPart("type","event");
        }

        Request request = new Request.Builder()
                .url("http://192.168.31.73:8081/social/moments/postvideodynamic")
                .post(requestBody.build())
                .tag(UploadActivity.this)
                .build();
        // readTimeout("请求超时时间",时间单位);
            client.newBuilder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .build()
                    .newCall(request).
                    enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lfq","onFailure");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i("post_file：：",response.message() + ",body " + str);
                } else {
                    Log.i("post_file：：",response.message() + " error : body " + response.body().string());
                }
            }
        });
    }

    /**
     * 获取视频文件第一帧图
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

}