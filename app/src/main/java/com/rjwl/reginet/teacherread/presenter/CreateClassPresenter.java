package com.rjwl.reginet.teacherread.presenter;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.MyClass;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

/**
 * Created by Administrator on 2018/5/30.
 */

public class CreateClassPresenter extends BasePresenter {
    private MyCreateClassView myCreateClassView;
    private MyClass myClass;


    public CreateClassPresenter(MyCreateClassView myCreateClassView) {
        this.myCreateClassView = myCreateClassView;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myCreateClassView.hidenLoading();
            String message = msg.obj + "";
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    myCreateClassView.showToast(message);
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
                        myClass = JSONObject.parseObject(data, MyClass.class);
                        myCreateClassView.getMyClass(myClass);
                        Log.d("TAG1", "展示班级：");
                    } else {
                        myCreateClassView.showToast(myMsg);
                    }
                    break;
            }
        }
    };

    public void createClass(MyClass myClass, String teacherId) {
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.CREATE_CLASS + "?id=" + teacherId + "&t2=" + myClass.getGrade()
                + "&t3=" + myClass.getClassname());
        myCreateClassView.showLoading();
    }

}
