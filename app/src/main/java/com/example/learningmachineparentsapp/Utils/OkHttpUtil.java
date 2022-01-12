package com.example.learningmachineparentsapp.Utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class OkHttpUtil {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static final MediaType FORM_FILE_TYPE
            = MediaType.parse("video/mp4");

    public static void post_file(String URL,String s,String file_path,final IOkhttp_file iOkhttp_file){
        OkHttpClient okHttpClient = new OkHttpClient();

        File file = new File(file_path);//文件路径

        RequestBody requestBody_video = RequestBody.create(FORM_FILE_TYPE,file);
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file",file.getName(),requestBody_video)
                .addFormDataPart("details",s)
                .build();
        //创建请求
        final Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        System.out.println("request>>>>>>>>>>>>>>>>>"+request);
        //创建call对象 提交异步请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            //请求失败
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ERR", e.toString());
                        iOkhttp_file.failure();
                    }
                });
            }

            @Override
            //请求成功
            public void onResponse(Call call, final Response response) throws IOException {
                //response.body().string() 响应结果：字符串、字节、字符流、字节流
                int code = response.code();
                String get = "";
                Log.e("","code------->"+code);
                if(code == HttpURLConnection.HTTP_OK){
                    ResponseBody requestBody = response.body();
                    if(requestBody != null){
                        get = requestBody.string();
                        System.out.println("#########################get>>>>>>"+get);
                        iOkhttp_file.success(get);
                    }
                }
            }
        });
    }


    //定义一个接口
    public interface IOkhttp_file {
        void success(String str);
        void failure();
    }

}