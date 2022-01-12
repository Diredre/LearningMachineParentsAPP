package com.example.learningmachineparentsapp;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class okhttpClass {

    private static String PATH1 = "http://221.12.170.98:91/lamp/parent";    //作业，上网控制
    private static String PATH2 = "http://221.12.170.98:91/lamp/parent";    //模块时长分析
    private static String PATH3 = "http://221.12.170.98:91/lamp/";          //登录注册
    private static String PATH4 = "http://221.12.170.98:91/lamp/";          //支付
    private static String PATH5 = "http://221.12.170.98:91/lamp/student";   //消息通知

    private static OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(160000, TimeUnit.MILLISECONDS).build();


    // 作业模块———————————————————————————————————————————————————————————————————————————————————————
    /**
     * @param studentId 学生id
     * @param parentId 家长id
     * @param homework 作业内容
     * @param closingDate 截止日期
     * @return 家长上传作业
     */
    public String UploadHomework(String studentId, String parentId, String homework, String closingDate){
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder.add("studentId",studentId)
                .add("parentId",parentId)
                .add("homework",homework)
                .add("closingDate",closingDate)
                .build();
        Request request=new Request.Builder().url(PATH1 + "/addHomework")
                .post(requestBody).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("homework", e.toString());
        }
        return "FW";
    }


    /**
     * @param studentId
     * @param pn
     * @param pageSize
     * @return 查看作业清单
     */
    public String getHomeworkList(String studentId, String pn, String pageSize){
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder.add("studentId",studentId)
                .add("pn",pn)
                .add("pageSize",pageSize)
                .build();
        Request request=new Request.Builder().url(PATH1 + "/getHomeworkListBySId")
                .post(requestBody).build();
        try (Response response = okHttpClient.newCall(request).execute()){
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
     * @return 根据学生id获取学生上传的图片
     */
    public String getHomeworkPic(String SId){
        FormBody.Builder builder = new FormBody.Builder();
        Request request = new Request.Builder()
                .url(PATH1 + "/getHomeworkPic?SId="+SId)
                .get()
                .build();
        try(Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }



    // 上网控制模块———————————————————————————————————————————————————————————————————————————————————
    /**
     * @param SId
     * @param MId
     * @return 修改时间控制状态 开启->关闭 关闭->开启
     */
    public String updateOpenState(String SId, String MId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.
                add("SId",SId)
                .add("MId",MId)
                .build();
        Request request=new Request.Builder().url(PATH1 + "/updateOpenState").post(requestBody).build();
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
     * @return 根据模块id和时间修改控制时间
     */
    public String updateControlTime(String SId,String MId,String time){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).add("MId",MId).add("time",time).build();
        Request request=new Request.Builder().url(PATH1 + "/updateControlTime").post(requestBody).build();
        try (Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }



    // 时间统计模块———————————————————————————————————————————————————————————————————————————————————
    /**
     * @param SId 学生id
     * @return 传学生id，获取该学生近七天每天的使用总时长，返回时间list，单位：秒
     */
    public String getWithinWeekData(String SId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).build();
        Request request=new Request.Builder()
                .url(PATH2 + "/getWithinWeekData?SId="+SId)
                .post(requestBody)
                .build();
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
     * @return 根据学生id、模块id和日期获取想要的模块使用时长，返回时间（单位：分钟）
     */
    public String getDailyData(String SId,String date,String MId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).add("date",date).add("MId",MId).build();
        Request request=new Request.Builder().url(PATH2 + "/getDailyData").post(requestBody).build();
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
     * @param SId 学生id
     * @return 根据学生id获取近一周各模块的使用比例和时长
     * 返回：按时长降序排列，时长单位：秒
     */
    public String getWithinWeekModuleTime(String SId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("SId",SId).build();
        Request request=new Request.Builder().url(PATH2 + "/getWithinWeekModuleTime").get().build();
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


    // 注册模块——————————————————————————————————————————————————————————————————————————————————————
    /**
     * @param phone
     * @param password
     * @param code
     * @return 家长注册
     */
    public String uploadParentRegister(String phone, String password, String code){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("phone",phone)
                .add("password",password)
                .add("code", code)
                .build();
        Request request=new Request.Builder().url(PATH3 + "/sso/register/parentregister").post(requestBody).build();
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

    /**
     * @param parentId
     * @param machineId 可以是空的
     * @param password
     * @param name
     * @param address
     * @param grade
     * @param cityId 先获取城市ID再发
     * @return 学生注册
     */
    public String uploadChildRegister(Integer parentId, String machineId, String password, String name,
                                      String address, Integer grade, Integer cityId){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("parentId", String.valueOf(parentId))
                .add("machineId",machineId)
                .add("password",password)
                .add("name",name)
                .add("address",address)
                .add("grade", String.valueOf(grade))
                .add("cityId", String.valueOf(cityId))
                .build();
        Request request=new Request.Builder()
                .url(PATH3 + "/sso/register/childregister")
                .post(requestBody)
                .build();
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

    /**
     * @param phone
     * @return 短信验证码
     */
    public void getSms2(String phone){
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone",phone)
                .build();
        final Request request = new Request.Builder()
                .url("http://221.12.170.98:91/lamp//sso/register/getsms?phone="+phone)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.e("getSms2", s);
            }
        });
    }


    /**
     * @param phone
     * @param code
     * @param password
     * @return 通过验证码修改家长密码
     */
    public String modifyParentPassword(String phone, String code, String password){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("phone", phone)
                .add("code", code)
                .add("password", password)
                .build();
        Request request=new Request.Builder()
                .url(PATH3 + "/sso/register/modifyParentPassword")
                .post(requestBody)
                .build();
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

    /**
     * @return 获取城市信息
     */
    public String getCity(){
        Request.Builder builder = new Request.Builder().url(PATH3 + "/sso/register/getcity");
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

    /**
     *
     * @param parentId
     * @return 获取家长绑定的孩子信息
     */
    public String getChilds(String parentId){
        FormBody.Builder builder = new FormBody.Builder();
        Request request = new Request.Builder()
                .url(PATH3 + "/sso/register/getChilds?parentId="+parentId)
                .get()
                .build();

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



    // 登录模块——————————————————————————————————————————————————————————————————————————————————————
    /**
     * @param phone
     * @param password
     * @return 家长账号登录，返回家长id
     */
    public String UploadLogin(String phone, String password){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("phone",phone)
                .add("password",password)
                .build();
        Request request=new Request.Builder().url(PATH3 + "/sso/login/parentlogin").post(requestBody).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("log", e.toString());
        }
        return "FW";
    }


    /**
     * @param uuid 从上面infoUUID获取
     * @return 扫描登录，还需要二次确认登录
     */
    public String scanQrLogin(String uuid){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("uuid",uuid)
                .build();
        Request request=new Request.Builder().url(PATH3 + "/sso/login/scanQrLogin").post(requestBody).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("log", e.toString());
        }
        return "FW";
    }


    /**
     * @param uuid
     * @param account
     * @return 二次登录确认
     */
    public String confirmQrLogin(String uuid, String account){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("uuid",uuid)
                .add("account",account)
                .build();
        Request request=new Request.Builder()
                .url("http://221.12.170.98:91/lamp/" + "/sso/login/confirmQrLogin")
                .post(requestBody).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("log", e.toString());
        }
        return "FW";
    }

    // 短视频模块—————————————————————————————————————————————————————————————————————————————————————
    public String getSurfingInfo(){
        Request.Builder builder=new Request.Builder().url("http://192.168.0.120:8082/getSurfingInfo?SId=1");
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

    public String alipay(String outTradeNo, String subject, String totalAmount, String body){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=builder.add("outTradeNo",outTradeNo)
                .add("subject",subject)
                .add("totalAmount",totalAmount)
                .add("body",body)
                .build();
        Request request=new Request.Builder().url(PATH4 + "/order/alipay").post(requestBody).build();
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


    // 消息通知———————————————————————————————————————————————————————————————————————————————————————
    public String getVideoTypePayInfo(String pID){
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody=builder.add("pID", pID).build();
        Request request = new Request.Builder()
                .url(PATH5 + "/video/getVideoTypePayInfo?pID="+pID)
                .get()
                .build();
        try(Response response=okHttpClient.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FW";
    }

    public String videoTypePay(String infoId){
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder
                .add("infoId", infoId)
                .build();
        Request request=new Request.Builder()
                .url(PATH5 + "/video/videoTypePay")
                .post(requestBody)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()){
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