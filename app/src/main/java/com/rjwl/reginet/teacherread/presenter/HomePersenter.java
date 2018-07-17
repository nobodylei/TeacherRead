package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.MyClass;
import com.rjwl.reginet.teacherread.entity.Teacher;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/29.
 */

public class HomePersenter extends BasePresenter {
    private MyHomeView myHomeView;
    private List<MyClass> classList;

    public HomePersenter(MyHomeView myHomeView) {
        this.myHomeView = myHomeView;
    }

    public List<MyClass> getClassList() {
        return classList;
    }

    private String tvc = "";

    public String getTvc() {
        return tvc;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String message = msg.obj + "";
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    myHomeView.showToast(message);
                    break;
                case 1:
                    Log.d("TAG1", message + "当前线程" + Thread.currentThread());
                    int code = 0;
                    String data = "";
                    String myMsg = "";
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        code = jsonObject.getInteger("code");
                        data = jsonObject.getString("data");
                        myMsg = jsonObject.getString("desc");
                        Log.d("TAG1", "返回代码" + code);
                        Log.d("TAG1", data + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code == 200) {
                        classList = parseJson(data);
                        myHomeView.showClassList();
                        Log.d("TAG1", "展示班级：" + parseJson(data));
                    } else {
                        myHomeView.showToast(myMsg);
                    }
                    break;
                case 2:
                    Log.d("TAG1", message + "当前线程" + Thread.currentThread());
                    int code2 = 0;
                    String data2 = "";
                    String myMsg2 = "";
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        code2 = jsonObject.getInteger("code");
                        data2 = jsonObject.getString("data");
                        myMsg = jsonObject.getString("desc");
                        Log.d("TAG1", "返回代码" + code2);
                        Log.d("TAG1", data2 + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code2 == 200) {
                        myHomeView.modfiyState(true);

                    } else {
                        myHomeView.modfiyState(false);
                    }
                    break;
                case 3:
                    int code3 = 0;
                    String data3 = "";
                    String desc = "";
                    try {
                        if ("".equals(message)) {
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        code3 = jsonObject.getInteger("code");
                        data3 = jsonObject.getString("data");
                        desc = jsonObject.getString("desc");
                        Log.d("TAG1", "返回代码:" + code3);
                        Log.d("TAG1", "得到集合：" + data3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code3 == 200) {
                        JSONObject json = JSONObject.parseObject(data3);
                        tvc = json.getString("tvc");
                        myHomeView.bookShop();
                    } else {
                        myHomeView.showToast(desc);
                        Log.d("TAG1", "BookPresenter层:" + desc);
                    }
                    break;
            }
        }
    };


    //查看班级
    public void getClassList(String teacherId) {
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.CLASS_DET + "?id=" + teacherId);
    }

    //认证状态
    public void approveState(String teacherId) {
        MyHttpUtils.okHttpGet(handler, 2, 0, Constant.URL
                + Constant.APPROVE_STATE + "?id=" + teacherId);
    }

    //书店链接
    public void bookShop() {
        MyHttpUtils.okHttpGet(handler, 3, 0, Constant.URL + Constant.BOOK_SHOP);
    }

    private List<MyClass> parseJson(String json) {
        return JSONObject.parseArray(json, MyClass.class);
    }
}
