package com.example.learningmachineparentsapp.LoginRegist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.MainActivity;
import com.example.learningmachineparentsapp.R;
import com.example.learningmachineparentsapp.View.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private ImageView info_iv_logo, info_iv_back, info_iv_childename, info_iv_grade, info_iv_city, info_iv_address;
    private EditText info_et_childename, info_et_address;
    private TextView info_tv_grade, info_tv_city;
    private Button info_btn_com;
    private String childename, address;
    private int cityId, grade;
    private Context context;
    private String selectText = "";
    private ArrayList<String> gradeList = new ArrayList<>();
    //申明对象
    CityPickerView mPicker=new CityPickerView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);

        context = InfoActivity.this;
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);

        initData();
        initView();
    }


    private void initData() {
        // 填充列表
        gradeList.clear();

        for (int i = 1; i <= 6; i++) {
            gradeList.add(String.format("%d年级", i));
        }
    }


    private void initView(){
        info_iv_logo = findViewById(R.id.info_iv_logo);
        Glide.with(this)
                .load("https://z3.ax1x.com/2021/10/24/5WK2jS.png")
                .into(info_iv_logo);

        info_iv_back = findViewById(R.id.info_iv_back);
        info_iv_back.setOnClickListener(v->{
            this.finish();
        });

        info_iv_childename = findViewById(R.id.info_iv_childename);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOPCY8.png")
                .into(info_iv_childename);

        info_et_childename = findViewById(R.id.info_et_childename);

        info_iv_grade = findViewById(R.id.info_iv_grade);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOPc7t.png")
                .into(info_iv_grade);

        info_tv_grade = findViewById(R.id.info_tv_grade);
        info_tv_grade.setOnClickListener(v->{
            /*选择年级*/
            showDialog(info_tv_grade, gradeList, 1);
        });

        info_iv_city = findViewById(R.id.info_iv_city);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOP5cQ.png")
                .into(info_iv_city);

        info_tv_city = findViewById(R.id.info_tv_city);
        info_tv_city.setOnClickListener(v->{
            /*选择城市*/
            showCitySelection();
        });

        info_iv_address = findViewById(R.id.info_iv_address);
        Glide.with(this)
                .load("https://s4.ax1x.com/2021/12/13/oOi8C8.png")
                .into(info_iv_address);

        info_et_address = findViewById(R.id.info_et_address);

        info_btn_com = findViewById(R.id.info_btn_com);
        info_btn_com.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "填写完成", Toast.LENGTH_SHORT).show();
        });
    }

    private void showCitySelection(){
        // 添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉
        // 详细属性请看：https://github.com/crazyandcoder/citypicker/wiki/%E6%A0%B7%E5%BC%8F%E4%B8%80%EF%BC%88%E4%BB%BFiOS%E6%BB%9A%E8%BD%AE%E5%AE%9E%E7%8E%B0%EF%BC%89
        CityConfig cityConfig = new CityConfig.Builder()
                .province("浙江省")    //默认显示的省份
                .city("杭州市")        //默认显示省份下面的城市
                .district("西湖区")       //默认显示省市下面的区县数据
                .build();

        mPicker.setConfig(cityConfig);

        // 监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //省份province，城市city，地区district
                info_tv_city.setText(""+province+" "+city+" "+district);
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(context, "已取消");
            }
        });

        //显示
        mPicker.showCityPicker( );
    }

    private void showDialog(TextView textView, ArrayList<String> list, int selected){
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });
    }

    private void showChoiceDialog(ArrayList<String> dataList,final TextView textView,int selected,
                                  WheelView.OnWheelViewListener listener){
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_wheelview,null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        final TextView wheel_tv_cansel = outerView.findViewById(R.id.wheel_tv_cansel);
        final TextView wheel_tv_confirm = outerView.findViewById(R.id.wheel_tv_confirm);
        BottomSheetDialog bottomSheetDialog;

        wheelView.setOffset(2);             // 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);       // 设置数据源
        wheelView.setSeletion(selected);    // 默认选中第三项
        wheelView.setOnWheelViewListener(listener);

        // 显示对话框，点击确认后将所选项的值显示到Button上
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(outerView);
        bottomSheetDialog.show();

        wheel_tv_cansel.setOnClickListener(v->{
            bottomSheetDialog.dismiss();
        });
        wheel_tv_confirm.setOnClickListener(v->{
            info_tv_grade.setText(selectText);
            bottomSheetDialog.dismiss();
        });
    }
}