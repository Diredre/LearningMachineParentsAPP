package com.example.learningmachineparentsapp.Homepage.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.learningmachineparentsapp.Homepage.Homework.HomeworkActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.TitleLayout;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout control_tit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);

        initView();
    }

    private void initView(){
        control_tit = findViewById(R.id.control_tit);
        control_tit.setTitle("上网控制");
    }

    @Override
    public void onClick(View v) {
    }
}