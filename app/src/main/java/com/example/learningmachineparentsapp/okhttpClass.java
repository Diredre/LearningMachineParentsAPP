package com.example.learningmachineparentsapp;

import android.os.Looper;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class okhttpClass {
    private static OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(160000, TimeUnit.MILLISECONDS).build();

    //------------------------------post------------------------------------------------------------

    /**
     * @param studentId 学生id
     * @param parentId 家长id
     * @param homework 作业内容
     * @param closingDate 截止日期
     * @return
     */
    public String UploadHomework(String studentId, String parentId, String homework, String closingDate){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("studentId",studentId)
                .add("parentId",parentId)
                .add("homework",homework)
                .add("closingDate",closingDate)
                .build();
        Request request=new Request.Builder().url("http://192.168.31.95:8082/addHomework").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }

    /**
     * @param SId 学生id
     * @return
     */
    public String getWithinWeekData(String SId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).build();
        Request request=new Request.Builder().url("http://192.168.31.95:8082/getWithinWeekData").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }

    /**
     * @param SId 学生id
     * @param date 日期
     * @param MId 模块id
     * @return
     */
    public String getDailyData(String SId,String date,String MId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).add("date",date).add("MId",MId).build();
        Request request=new Request.Builder().url("http://192.168.31.95:8082/getDailyData").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("getDailyData-e",e.toString());
        }
        return "FW";
    }

    /**
     * @param SId
     * @param MId
     * @return
     */
    public String updateOpenState(String SId,String MId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).add("MId",MId).build();
        Request request=new Request.Builder().url("http://192.168.31.95:8082/updateOpenState").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }

    /**
     * @param SId
     * @param MId
     * @param time 时间
     * @return
     */
    public String updateControlTime(String SId,String MId,String time){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).add("MId",MId).add("time",time).build();
        Request request=new Request.Builder().url("http://192.168.31.95:8082/updateControlTime").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }
    // ------------------------------get------------------------------------------------------------
    public String getHomeworkListBySId(){
        Request.Builder builder=new Request.Builder().url("http://192.168.31.95:8082/getHomeworkListBySId?studentId=1");
        builder.method("GET",null);

        Request request=builder.build();
        try(Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }


    public String getModuleName(){
        Request.Builder builder=new Request.Builder().url("http://192.168.31.95:8082/getModuleName");
        builder.method("GET",null);

        Request request=builder.build();
        try(Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }

    public String getSurfingInfo(){
        Request.Builder builder=new Request.Builder().url("http://192.168.31.95:8082/getSurfingInfo?SId=1");
        builder.method("GET",null);

        Request request=builder.build();
        try(Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }

    public String UploadLogin(String phone, String password){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("phone",phone)
                .add("password",password)
                .build();
        Request request=new Request.Builder().url("http://192.168.31.73:8083/sso/login/parentlogin").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("log", e.toString());
        }
        return "FW";
    }
}