package com.example.learningmachineparentsapp.Homepage.Homework;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.learningmachineparentsapp.R;

/**
 * 输入内容dialog
 */
public class InputHWDialog extends Dialog {
    private Context mContext;
    private TextView btnSure;
    private TextView btnCancle;
    private TextView tit;
    private EditText editText;

    public InputHWDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        initView();
    }

    //初始化
    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_input_homework, null);
        tit = view.findViewById(R.id.dialog_inputhw_tit);
        editText = (EditText) view.findViewById(R.id.dialog_inputhw_et);
        btnSure = (TextView) view.findViewById(R.id.dialog_inputhw_sure);
        btnCancle = (TextView) view.findViewById(R.id.dialog_inputhw_cansel);
        super.setContentView(view);
    }

    // 设置标题
    public InputHWDialog setTile(String s) {
        tit.setText(s);
        return this;
    }

    // 获取当前输入框对象
    public EditText getEditText() {
        return editText;
    }

    // 确定键监听器
    public void setOnSureListener(View.OnClickListener listener) {
        btnSure.setOnClickListener(listener);
    }

    // 取消键监听器
    public void setOnCanlceListener(View.OnClickListener listener) {
        btnCancle.setOnClickListener(listener);
    }
}
