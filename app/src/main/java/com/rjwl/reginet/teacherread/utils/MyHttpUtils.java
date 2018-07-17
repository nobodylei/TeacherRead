package com.rjwl.reginet.teacherread.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/3.
 * 网络访问
 */

public class MyHttpUtils {
    public static void okHttpUtils(Map map, final Handler handler, final int rightCode, final int erroCode, String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS).build();//设置读取超时时间;
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            builder.add(entry.getKey() + "", entry.getValue() + "");
            //Log.e(entry.getKey() + "", entry.getValue() + "");
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                //Log.d("TAG1", "MyHttpUtils " + call.toString());
                if (e instanceof SocketTimeoutException) {//判断超时异常
                    message.obj = "连接超时";
                } else if (e instanceof ConnectException) {//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    message.obj = "连接异常";
                } else {
                    message.obj = "连接异常";
                }
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });

    }

    public static void okHttpUtilsHead(Context context, HashMap map, final Handler handler, final int rightCode, final int erroCode, String url) {

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            builder.add(entry.getKey() + "", entry.getValue() + "");
            Log.e(entry.getKey() + "", entry.getValue() + "");
        }
        Log.e("token", SaveOrDeletePrefrence.look(context, "token") + "");
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", SaveOrDeletePrefrence.look(context, "token"))
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });

    }

    public static void okHttpGet(final Handler handler, final int rightCode, final int erroCode, String url) {
        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS).build();//设置读取超时时间;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e("get", "url:" + url);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                if (e instanceof SocketTimeoutException) {//判断超时异常
                    message.obj = "连接超时";
                }
                if (e instanceof ConnectException) {//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    message.obj = "连接异常";
                } else {
                    message.obj = "连接异常";
                }
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });
    }


    public static void header(Context context, final Handler handler, final int rightCode, final int erroCode, String url) {
        OkHttpClient mHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", SaveOrDeletePrefrence.look(context, "token"))
                .build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });

    }

   /* public static void okHttpPost(final Handler handler, Map<String, String> map, Map<String, File> fileMap, final int rightCode, final int erroCode, String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS).build();//设置读取超时时间;

        File file = fileMap.get("f1");

        *//*if(!file.exists()) {
            //L.e(file.getAbsolutePath() + " not exist !");
            return;
            ("name", student.getName() + "");
                studentMap.put("y", student.getYesorno() + "");
                studentMap.put("gender", student.getGender() + "");
                studentMap.put("gradeId", student.getGradeID() + "");
                studentMap.put("schoolname
        }*//*

        MultipartBody.Builder multpartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody = multpartBody.addFormDataPart("id",map.get("id"))
                .addFormDataPart("y",map.get("y"))
                .addFormDataPart("name", map.get("name"))
                .addFormDataPart("gender",map.get("gender"))
                .addFormDataPart("gradeId",map.get("gradeId"))
                .addFormDataPart("schoolname",map.get("schoolname"))
                .addFormDataPart("f1", "head.jpeg", RequestBody.create(MediaType.parse("application/octet-stream"),file))
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                //Log.d("TAG1", "MyHttpUtils " + call.toString());
                if (e instanceof SocketTimeoutException) {//判断超时异常
                    message.obj = "连接超时";
                } else if (e instanceof ConnectException) {//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    message.obj = "连接异常";
                } else {
                    message.obj = "连接异常";
                }
                message.what = erroCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });
    }*/

    public static void uploadFile(final Handler handler, Map<String, String> map, Map<String, File> fileMap, final int rightCode, final int errorCode, String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS).build();//设置读取超时时间;

        File file = fileMap.get("f1");
        //参数
        /*FormBody paramsBody=new FormBody.Builder()
                .add("id",map.get("id"))
                .build();


        MediaType type=MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
        RequestBody fileBody=RequestBody.create(type,file);

        RequestBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.ALTERNATIVE)
                //一样的效果
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"params\"")
                        ,paramsBody)
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"file\"; filename=\""+ map.get("fileName") +"\"")
                        , fileBody).build();
        Request request=new Request.Builder().url(url)
                .addHeader("User-Agent","android")
                .header("Content-Type","text/html; charset=utf-8;")
                .post(multipartBody)//传参数、文件或者混合，改一下就行请求体就行
                .build();
*/
        MultipartBody.Builder multpartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg; charset=utf-8"), file);

        RequestBody requestBody = multpartBody.addFormDataPart("id", map.get("id"))
                .addFormDataPart("f1", map.get("fileName"), fileBody)
                .build();

        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);

        Request.Builder builder = new Request.Builder();

        Request request = builder.url(url).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                //Log.d("TAG1", "MyHttpUtils " + call.toString());
                if (e instanceof SocketTimeoutException) {//判断超时异常
                    message.obj = "连接超时";
                } else if (e instanceof ConnectException) {//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    message.obj = "连接异常";
                } else {
                    message.obj = "连接异常";
                }
                message.what = errorCode;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                message.what = rightCode;
                handler.sendMessage(message);
            }
        });
    }
}
