package com.example.learningmachineparentsapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;


/**
 * FloatTool.java:应用悬浮窗权限请求
 *
 * AndroidMainifest.xml中添加： <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 *
 * 用法：
 * 1、请求悬浮窗权限：FloatTool.RequestOverlayPermission(this);
 * 2、处理悬浮窗权限请求结果：FloatTool.onActivityResult(requestCode, resultCode, data, this);
 * -----
 */
public class FloatTool{
    public static boolean CanShowFloat = false;

    private static final int REQUEST_OVERLAY = 5004;

    /** 动态请求悬浮窗权限 */
    public static void RequestOverlayPermission(Activity Instatnce) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(Instatnce)) {
                String ACTION_MANAGE_OVERLAY_PERMISSION = "android.settings.action.MANAGE_OVERLAY_PERMISSION";
                Intent intent = new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + Instatnce.getPackageName()));

                Instatnce.startActivityForResult(intent, REQUEST_OVERLAY);
            }
            else {
                CanShowFloat = true;
            }
        }
    }

    /** 浮窗权限请求，Activity执行结果，回调函数 */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void onActivityResult(int requestCode, int resultCode, Intent data, final Activity Instatnce) {
        // Toast.makeText(activity, "onActivityResult设置权限！", Toast.LENGTH_SHORT).show();
        if (requestCode == REQUEST_OVERLAY)		// 从应用权限设置界面返回
        {
            if(resultCode == Activity.RESULT_OK) {
                CanShowFloat = true;		// 设置标识为可显示悬浮窗
            }
            else {
                CanShowFloat = false;

                if (!Settings.canDrawOverlays(Instatnce))	// 若当前未允许显示悬浮窗，则提示授权
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Instatnce);
                    builder.setCancelable(false);
                    builder.setTitle("悬浮窗权限未授权");
                    builder.setMessage("应用需要悬浮窗权限，以展示浮标");
                    builder.setPositiveButton("去添加 权限", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            RequestOverlayPermission(Instatnce);
                        }
                    });

                    builder.setNegativeButton("拒绝则 退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            // 若拒绝了所需的权限请求，则退出应用
                            Instatnce.finish();
                            System.exit(0);
                        }
                    });
                    builder.show();
                }
            }
        }
    }
}