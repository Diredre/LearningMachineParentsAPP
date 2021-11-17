package com.example.learningmachineparentsapp.Homepage.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.learningmachineparentsapp.Homepage.Homework.HomeworkActivity;
import com.example.learningmachineparentsapp.R;

import static com.example.learningmachineparentsapp.MainActivity.makeStatusBarTransparent;

public class ControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);

        initView();
    }

    private void initView(){

    }
}