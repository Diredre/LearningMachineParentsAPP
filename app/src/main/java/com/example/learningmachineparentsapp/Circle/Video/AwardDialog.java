package com.example.learningmachineparentsapp.Circle.Video;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.Utils.DisplayUtils;

public class AwardDialog extends Dialog {

    private ImageButton dialog_award_cansel;
    private ProgressBar videoplay_progess;
    private TextView videoplay_progess_value;
    private LinearLayout videoplay_full;


    public AwardDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.dialog_award);

        videoplay_progess = findViewById(R.id.videoplay_progess);
        videoplay_progess_value = findViewById(R.id.videoplay_progess_value);
        videoplay_full = findViewById(R.id.videoplay_full);

        initprogess();

        dialog_award_cansel = findViewById(R.id.dialog_award_cansel);
        dialog_award_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AwardDialog.this.dismiss(); //关闭dialog
            }
        });
    }

    private void initprogess(){
        videoplay_progess.setProgress(36);
        videoplay_progess_value.setText(new StringBuffer().append(videoplay_progess.getProgress()).append("%"));

        setPosWay();

        /*ToastUtil.showToast("进度为66");
        Toast.makeText(this,"进度为:--66",Toast.LENGTH_SHORT).show();

        videoplay_full.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int w = getWindowManager().getDefaultDisplay().getWidth();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getRawX();
                        videoplay_progess.setProgress(100 * x1 / w);
                        setPos();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = (int) event.getRawX();
                        dx = x2 - x1;
                        if (Math.abs(dx) > w / 100) { //改变条件 调整进度改变速度
                            x1 = x2; // 去掉已经用掉的距离， 去掉这句 运行看看会出现效果
                            videoplay_progess.setProgress(videoplay_progess.getProgress() + dx * 100 / w);
                            setPos();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setPos();
        }
    }

    private void setPosWay() {
        videoplay_progess_value.post(new Runnable() {
            @Override
            public void run() {
                setPos();
            }
        });
    }

    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        int w = DisplayUtils.dip2px(getContext(), 100);
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) videoplay_progess_value.getLayoutParams();
        int pro = videoplay_progess.getProgress();
        int tW = videoplay_progess_value.getWidth();
        Log.e("总长 = ",""+pro);
        Log.e("进度条 = ",""+tW);
        params.leftMargin = tW/pro >= 1 ? (int)(w) : (int)(tW/pro * w);
        /*if (w * pro / 100 + tW * 0.3 > w) {
            params.leftMargin = (int) (w - tW * 3.5);
        } else if (w * pro / 100 < tW * 3.1) {
            params.leftMargin = 0;
        } else {
            params.leftMargin = (int) (w * pro / 100 - tW * 3.1);
        }*/
        videoplay_progess_value.setLayoutParams(params);
    }
}
