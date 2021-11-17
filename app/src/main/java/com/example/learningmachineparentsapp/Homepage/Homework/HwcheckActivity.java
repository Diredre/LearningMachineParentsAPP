package com.example.learningmachineparentsapp.Homepage.Homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.learningmachineparentsapp.R;
import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class HwcheckActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView hwcheck_iv_add, hwcheck_iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hwcheck);

        initView();
    }

    private void initView(){
        hwcheck_iv_add = findViewById(R.id.hwcheck_iv_add);
        hwcheck_iv_add.setOnClickListener(this);

        hwcheck_iv_back = findViewById(R.id.hwcheck_iv_back);
        hwcheck_iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.hwcheck_iv_add:
                startActivity(new Intent(this, HomeworkActivity.class));
                break;
            case R.id.hwcheck_iv_back:
                this.finish();
                break;
        }
    }
}