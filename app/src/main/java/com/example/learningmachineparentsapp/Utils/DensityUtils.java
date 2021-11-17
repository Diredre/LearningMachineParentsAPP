package com.example.learningmachineparentsapp.Utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * px与dp互相转换
 */
public class DensityUtils {

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }
}