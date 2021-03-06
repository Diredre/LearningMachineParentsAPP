package com.example.learningmachineparentsapp.Homepage.Homework;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learningmachineparentsapp.Homepage.InputHWDialog;
import com.example.learningmachineparentsapp.R;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    private List<HomeworkBean> hwList;
    private Context context;

    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView et;
        ImageView iv_cal;
        TextView tv_del;
        TextView timetv;

        public ViewHolder(View v){
            super(v);
            this.et = v.findViewById(R.id.item_puthw_et);
            this.iv_cal = v.findViewById(R.id.item_puthw_iv_cal);
            this.tv_del = v.findViewById(R.id.item_puthw_tv_del);
            this.timetv = v.findViewById(R.id.item_puthw_tv);
        }
    }


    public HomeworkAdapter(Context context, List<HomeworkBean> hwList) {
        this.context = context;
        this.hwList = hwList;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_puthw, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        HomeworkBean homework = hwList.get(position);

        holder.et.setText(homework.getCon());
        holder.et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialogShow(position);
            }
        });

        Glide.with(context)
                .load("https://z3.ax1x.com/2021/11/05/IKZhTS.png")
                .into(holder.iv_cal);
        holder.iv_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(context,  2, holder.timetv, calendar, homework);
                Glide.with(context)
                        .load("https://z3.ax1x.com/2021/11/05/IKF5BF.png")
                        .into(holder.iv_cal);
            }
        });

        /*Glide.with(context)
                .load("https://z3.ax1x.com/2021/10/30/5x8T3Q.png")
                .into(holder.iv_ach);*/
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hwList.size();
    }

    /**
     * ????????????
     */
    public static void showDatePickerDialog(Context context, int themeResId, final TextView tv, Calendar calendar, HomeworkBean hw) {
        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(context, themeResId, new DatePickerDialog.OnDateSetListener() {
            // ???????????????(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // ????????????????????????????????????????????????????????????
                tv.setText("?????????????????????" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                hw.setUse_time(new Date(year, monthOfYear+1, dayOfMonth).toString());
            }
        }
                // ??????????????????
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * ????????????
     */
    public static void showTimePickerDialog(Context context, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // ????????????TimePickerDialog??????????????????????????????
        new TimePickerDialog(context, themeResId,
                // ???????????????
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText("???????????????" + hourOfDay + "???" + minute + "???");
                    }
                }
                // ??????????????????
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true????????????24?????????
                , true).show();
    }

    /**
     * ??????????????????
     */
    public void removeItemData(int position){
        hwList.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();     //????????????
        //????????????????????????????????????
        notifyItemRangeChanged(position, hwList.size());
    }

    public void addItemData(HomeworkBean data) {
        hwList.add(hwList.size(), data);
        notifyItemInserted(hwList.size());
        //???????????????????????????????????????
        notifyItemRangeChanged(hwList.size(), hwList.size());
    }

    public void changeItemData(int position, HomeworkBean data) {
        hwList.set(position, data);
        notifyItemChanged(position);
    }

    /**
     * ??????????????????dialog
     */
    public void inputDialogShow(int position){
        InputHWDialog inputHWDialog = new InputHWDialog(context);
        EditText input = inputHWDialog.getEditText();
        inputHWDialog.setOnSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().toString().trim().equals("")) {
                    hwList.get(position).setCon(input.getText().toString().trim());
                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
                    inputHWDialog.dismiss();
                }else{
                    Toast.makeText(context, "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        inputHWDialog.setOnCanlceListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputHWDialog.dismiss();
            }
        });
        inputHWDialog.setTile("????????????");
        inputHWDialog.show();
    }

    /**
     * ???String????????????????????????Date??????,?????????????????????????????????????????????????????????????????????
     */
    public static Date str2Date(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = null;
        try {
            date = (Date) format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
