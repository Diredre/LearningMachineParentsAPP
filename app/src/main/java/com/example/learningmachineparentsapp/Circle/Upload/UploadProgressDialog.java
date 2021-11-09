package com.example.learningmachineparentsapp.Circle.Upload;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Widget.CirclePgBar;

/**
 * 视频上传的进度条dialog
 */
public class UploadProgressDialog extends Dialog {

    private ImageButton pb_dialog_cansel;
    private CirclePgBar pb_dialog_bar;
    private TextView dialog_uppb_text;
    private Context con;

    public UploadProgressDialog(@NonNull Context context){
        super(context);
        con = context;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.dialog_upload_progress);

        pb_dialog_cansel = findViewById(R.id.dialog_uppb_cansel);
        pb_dialog_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadProgressDialog.this.dismiss(); //关闭dialog
            }
        });

        pb_dialog_bar = findViewById(R.id.dialog_uppb_bar);
        //设置进度条满100%动画共用时5s
        pb_dialog_bar.setProgress(100,3000);
        //在自定义View写了一个接口回调,来监听进度
        pb_dialog_bar.setOnCircleProgressListener(new CirclePgBar.OnCircleProgressListener() {
            @Override
            public boolean OnCircleProgress(int progress) {
                if(progress == 100){
                    UploadProgressDialog.this.dismiss(); //关闭dialog
                    Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        dialog_uppb_text = findViewById(R.id.dialog_uppb_text);
    }

    public void setPb_dialog_bar(int progress){
        pb_dialog_bar.setProgress(progress);
        dialog_uppb_text.setText(progress + "%");
    }
}