package com.example.learningmachineparentsapp.Circle.Upload;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
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
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Circle.Video.LocalVideo;
import com.example.learningmachineparentsapp.R;
import com.hw.videoprocessor.VideoProcessor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

/**
 * 上传视频界面
 */
public class UploadActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = UploadActivity.class.getName();
    public  final static int VEDIO_KU = 101;
    private String VIDEOPATH = "";      //视频文件路径
    private UploadProgressDialog uploadProgressDialog;

    private Button upload_btn_upload;
    private ImageView upload_iv_add, upload_iv_back;
    private TextView upload_tv_path;


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

        uploadProgressDialog = new UploadProgressDialog(UploadActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_iv_back:
                UploadActivity.this.finish();
                break;
            case R.id.upload_btn_upload:
                //Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                Toast.makeText(UploadActivity.this, "路径："+VIDEOPATH, Toast.LENGTH_LONG).show();
                if(VIDEOPATH.equals(""))
                    Toast.makeText(UploadActivity.this, "请选择视频后，再点击上传！", Toast.LENGTH_LONG).show();
                else {
                    submit();
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
        /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, UploadActivity.VEDIO_KU);*/

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, UploadActivity.VEDIO_KU);
    }

    /**
     * 回调，视频上传处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            upload_tv_path.setText(VIDEOPATH);
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    /**
     * 获取视频第一帧作为封面
     * @param url
     * @return
     */
    public Bitmap getBitmapFormUrl(String url) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url);
        //getFrameAtTime()--->在setDataSource()之后调用此方法。 如果可能，该方法在任何时间位置找到代表性的帧，
        // 并将其作为位图返回。这对于生成输入数据源的缩略图很有用。
        Bitmap bitmap = retriever.getFrameAtTime();
        retriever.release();
        return bitmap;
    }

    /**
     * 获取视频文件的大小
     * @param file 文件
     * @return 返回文件大小
     * @throws Exception
     */
    private long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        } else {
            Toast.makeText(UploadActivity.this, "文件不存在！",Toast.LENGTH_SHORT).show();
        }
        return size;
    }

    /**
     * 获取视频时长,这里获取的是毫秒
     * @param context
     * @param uri
     * @return
     */
    private int getVideoTime(Context context, Uri uri){
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context,uri);
            mediaPlayer.prepare();
            int duration = mediaPlayer.getDuration();
            return duration;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 视频压缩
     */
    private void videoProcess(){
        /*VideoProcessor.processor(this)
                .input(inputVideoPath) // .input(inputVideoUri)
                .output(outputVideoPath)
                .outWidth(width)
                .outHeight(height)
                .process();*/
    }

    /**
     * 上传视频
     * @return
     */
    private boolean submit(){
        return false;
    }
}