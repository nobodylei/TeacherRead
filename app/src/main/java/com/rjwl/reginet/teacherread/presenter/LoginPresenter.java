package com.rjwl.reginet.teacherread.presenter;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Teacher;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/8.
 * 登录注册相关
 */

public class LoginPresenter extends BasePresenter {
    private MyLoginView mMyLoginView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mMyLoginView.hidenLoading();
            String message = msg.obj + "";
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    mMyLoginView.showToast(message);
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
                        mMyLoginView.saveUser(parseJson(data));
                        mMyLoginView.skipView();
                        Log.d("TAG1", "登录、注册：" + parseJson(data));
                    } else if (code == 201) {
                        mMyLoginView.showToast(myMsg);
                        mMyLoginView.skipView();
                    } else {
                        mMyLoginView.showToast(myMsg);
                    }
                    break;
            }
        }
    };


    public LoginPresenter(MyLoginView myLoginView) {
        this.mMyLoginView = myLoginView;
    }

    //登录
    public void lookPhoneNumber(String num, String pass) {
        if ("".equals(num)) {
            mMyLoginView.showToast("请输入手机号");
            return;
        } else if ("".equals(pass)) {
            mMyLoginView.showToast("请输入密码");
            return;
        }
        login(num, pass);
    }

    //获取验证码
    public void lookPhoneNumber(String number) {
        if ("".equals(number) || number.length() != 11) {
            mMyLoginView.showToast("请输入正确的手机号");
            return;
        }
        MyHttpUtils.okHttpGet(handler, 2, 0, Constant.URL
                + Constant.PHONE_MESSAGE + "?username=" + number);
    }

    //注册
    public void lookPhoneNumber(String number, String pass, String register, boolean isReg) {
        if ("".equals(number)) {
            mMyLoginView.showToast("请输入手机号");
            return;
        } else if ("".equals(pass)) {
            mMyLoginView.showToast("请输入密码");
            return;
        } else if ("".equals(register)) {
            mMyLoginView.showToast("请输入验证码");
        }
        if (isReg) {
            register(number, pass, register);
        } else {
            forget(number, pass, register);
        }
    }

    public void forget(String number, String password, String register) {
        Map<String, String> map = new HashMap<>();
        map.put("username", number);
        map.put("pwd", password);
        map.put("code", register);
        mMyLoginView.showLoading();
        MyHttpUtils.okHttpUtils(map, handler, 1, 0, Constant.URL + Constant.FORGET);
    }

    //注册
    public void register(String number, String password, String register) {
        Log.d("TAG1", "账号密码 " + number + password);
        Map<String, String> map = new HashMap<>();
        map.put("username", number);
        map.put("pwd", password);
        //map.put("code", register);

        mMyLoginView.showLoading();
        MyHttpUtils.okHttpUtils(map, handler, 1, 0, Constant.URL + Constant.REGISTER_URL);
    }

    public void login(String user, String pass) {
        Log.d("TAG1", "用户名密码:" + user + pass);
        Map<String, String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", pass);

        mMyLoginView.showLoading();
        MyHttpUtils.okHttpUtils(map, handler, 1, 0, Constant.URL + Constant.LOGIN_URL);
    }

    private Teacher parseJson(String json) {
        return JSONObject.parseObject(json, Teacher.class);
    }

}

