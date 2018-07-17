package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/19.
 */

public class TeacherInfoPresent extends BasePresenter {
    private MyInfoView myInfoView;
    private String headPicture;

    public TeacherInfoPresent(MyInfoView myInfoView) {
        this.myInfoView = myInfoView;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myInfoView.hidenLoading();
            String message = msg.obj + "";
            Log.d("TAG1", "修改资料返回值：" + message);
            if ("".equals(message)) {
                return;
            }
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", "修改资料code==0：" + message);
                    myInfoView.showToast(message);
                    //mMyLoginView.skipView();
                    break;
                case 1:
                    Log.d("TAG1", message + "code==1;当前线程" + Thread.currentThread());
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
                        myInfoView.skipView();
                        myInfoView.showToast(myMsg);
                    } else if (code == 201) {
                        JSONObject head = JSONObject.parseObject(data);
                        headPicture = head.getString("headPicture");
                        myInfoView.showToast(myMsg);
                        myInfoView.getHead();
                    } else {
                        myInfoView.showToast(myMsg);
                    }

                    break;
            }
        }
    };

    public void updataStudent(Map<String, String> map) {//修改个人信息

        Log.d("TAG1", "发送的集合:" + map);
        myInfoView.showLoading();
        MyHttpUtils.okHttpUtils(map, handler, 1, 0, Constant.URL + Constant.UPDATA_TEACHER);
    }

    public void updataHead(Map<String, String> map, Map<String, File> fileMap) {
        myInfoView.showLoading();
        MyHttpUtils.uploadFile(handler, map, fileMap, 1, 0, Constant.URL + Constant.UPDATA_HEAD);//修改头像
    }


    /*public void login(String user, String pass) {
        Log.d("TAG1", "用户名密码:" + user + pass);
        Map<String, String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", pass);

        //mMyLoginView.showLoading();
        MyHttpUtils.okHttpUtils(map, handler, 1, 0, Constant.URL + Constant.LOGIN_URL);
    }*/
}
