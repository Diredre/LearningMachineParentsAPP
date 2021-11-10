package com.example.learningmachineparentsapp.Circle.Upload;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.learningmachineparentsapp.R;

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
        makeStatusBarTransparent(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);    //设置手机应用内部状态栏字体图标为黑色

        initView();
    }

    private void initView(){
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
                    /*File file = new File(VIDEOPATH);
                    String postUrl = "http://101.35.7.157/api/upload";
                    uploadProgressDialog.show();
                    HttpUtil.postFile(postUrl, new ProgressListener() {
                        @Override
                        public void onProgress(long currentBytes, long contentLength, boolean done) {
                            Log.i(TAG, "currentBytes==" + currentBytes +
                                    "==contentLength==" + contentLength +
                                    "==done==" + done);
                            int progress = (int) (currentBytes * 100 / contentLength);
                            uploadProgressDialog.setPb_dialog_bar(progress);
                            //post_text.setText(progress + "%");
                        }
                    }, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response != null) {
                                String result = response.body().string();
                                Log.i(TAG, "result===" + result);
                            }
                        }
                    }, file);*/
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
        startActivityForResult(i, UploadActivity.VEDIO_KU);
        VIDEOPATH =*/

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        this.startActivityForResult(intent, UploadActivity.VEDIO_KU);
    }

    /**
     * 回调，视频上传处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == UploadActivity.VEDIO_KU && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            VIDEOPATH = cursor.getString(columnIndex);
            Log.e("myx 视频路径：", VIDEOPATH);
            cursor.close();
            //换个图片表示已选中该视频
            upload_iv_add.setImageDrawable(getResources().getDrawable(R.mipmap.added));
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }*/
        switch (requestCode) {
            // TODO 视频
            case UploadActivity.VEDIO_KU:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        Uri uri = data.getData();
                        uri = geturi(this, data);
                        File file = null;
                        if (uri.toString().indexOf("file") == 0) {
                            file = new File(new URI(uri.toString()));
                            VIDEOPATH = file.getPath();
                        } else {
                            VIDEOPATH = getPath(uri);
                            file = new File(VIDEOPATH);
                        }
                        if (!file.exists()) break;
                        if (file.length() > 100 * 1024 * 1024) {// "文件大于100M";
                            break;
                        }
                        //换个图片表示已选中该视频
                        upload_iv_add.setImageDrawable(getResources().getDrawable(R.mipmap.added));

                        //视频播放
                        // mVideoView.setVideoURI(uri);
                        // mVideoView.start();
                        //开始上传视频，
                        // submitVedio();
                    } catch (Exception e) {
                        String a = e + "";
                    } catch (OutOfMemoryError e) {
                        String a = e + "";
                    }
                }
                break;
        }
    }


    public static Uri geturi(Context context, android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index != 0){
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                        Log.i("urishi", uri.toString());
                    }
                }
            }
        }
        return uri;
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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


    public static ByteArrayInputStream getByteArrayInputStream(File file){
        return new ByteArrayInputStream(getByetsFromFile(file));
    }

    /**
     * 视频文件转换为流方法
     *  ByteArrayInputStream ins = new ByteArrayInputStream(picBytes);
     * @param file
     * @return
     */
    public static byte[] getByetsFromFile(File file){
        FileInputStream is = null;
        // 获取文件大小
        long length = file.length();
        // 创建一个数据来保存文件数据
        byte[] fileData = new byte[(int)length];

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int bytesRead=0;
        // 读取数据到byte数组中
        while(bytesRead != fileData.length) {
            try {
                bytesRead += is.read(fileData, bytesRead, fileData.length - bytesRead);
                if(is != null)
                    is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return fileData;
    }

    /**
     * 断点续传
     */
    public void submit(){
        try {
            File file = new File(VIDEOPATH);
            FileInputStream is = null;
            long length = file.length();    // 获取文件大小
            byte[] fileData = null;         // 创建一个数据来保存文件数据

            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // 读取数据到byte数组中
            List<ByteArrayInputStream> temp = new ArrayList<>();
            int len = 0;
            fileData = new byte[1000 * 1000 * 2];

            //断点续传
            while ((len = is.read(fileData)) != -1) {
                temp = new ArrayList<>();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
                temp.add(byteArrayInputStream);
                //这里是提交数组流到后台
                //RegisterControlService.submitVedioSon(
                //SubVedioViewActivity.this, temp, fInfos, subIdx);
                temp.clear();
                byteArrayInputStream.close();
            }
            if (is != null)
                is.close();
        } catch (Exception ex) {
        }
    }
}