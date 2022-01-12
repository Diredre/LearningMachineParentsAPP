package com.example.learningmachineparentsapp.Homepage.LeftActs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.InputHWDialog;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.RoundImageView;
import com.example.learningmachineparentsapp.View.TitleLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {

    private TitleLayout publish_tit;
    private RelativeLayout publish_nane, publish_icon, publish_children;
    private TextView publish_tv_nownane;
    private RoundImageView publish_riv_icon;
    private static final int CONSTANTS_SELECT_PHOTO_CODE = 1;

    private SharedPreferences sp;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish);

        sp = getSharedPreferences("userInfo", 0);
        name = sp.getString("USER_NAME", "云淡风轻");

        initView();
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字
        }

        publish_tit = findViewById(R.id.publish_tit);
        publish_tit.setTitle("个人资料");

        publish_tv_nownane = findViewById(R.id.publish_tv_nownane);

        publish_nane = findViewById(R.id.publish_nane);
        publish_nane.setOnClickListener(this);

        publish_icon = findViewById(R.id.publish_icon);
        publish_icon.setOnClickListener(this);

        publish_children = findViewById(R.id.publish_children);
        publish_children.setOnClickListener(this);

        publish_riv_icon = findViewById(R.id.publish_riv_icon);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.publish_icon:
                final Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);    // 选择数据
                photoPickerIntent.setType("image/*");                               // 获取所有本地图片
                startActivityForResult(photoPickerIntent, 1);
                break;
            case R.id.publish_nane:
                inputDialogShow();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bitmap = null;
        switch (requestCode) {
            case CONSTANTS_SELECT_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = this.getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(imageStream);
                        int h = publish_riv_icon.getWidth();
                        int w = publish_riv_icon.getHeight();
                        publish_riv_icon.setImageBitmap(bitmap);
                        publish_riv_icon.setMinimumHeight(h);
                        publish_riv_icon.setMinimumWidth(w);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void inputDialogShow(){
        InputHWDialog dialog = new InputHWDialog(this);
        dialog.setTile("修改用户名");
        EditText input = dialog.getEditText();
        dialog.setOnSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().toString().trim().equals("")){
                    String str = input.getText().toString().trim();
                    publish_tv_nownane.setText(str);
                    name = str;
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME", name);
                    editor.commit();
                    Toast.makeText(PublishActivity.this, "修改完成，用户名将在下次登录生效", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(PublishActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setOnCanlceListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
