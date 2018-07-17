package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Student;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.util.List;

/**
 * Created by yanle on 2018/5/31.
 */

public class StudentPresenter extends BasePresenter {
    private MyStudentView myStudentView;

    private List<Student> notPassStus;
    private List<Student> passStuList;

    public StudentPresenter(MyStudentView myStudentView){
        this.myStudentView = myStudentView;
    }

    public List<Student> getNotPassStus() {
        return notPassStus;
    }

    public List<Student> getPassStuList() {
        return passStuList;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = msg.obj + "";
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    myStudentView.showToast(message);
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
                        if ("".equals(data) || "null".equals(data) || data == null) {
                            return;
                        }
                        JSONObject studentList = JSONObject.parseObject(data);
                        String notPass = studentList.getString("1");
                        notPassStus = parseArrayStu(notPass);
                        String pass = studentList.getString("0");
                        passStuList = parseArrayStu(pass);
                        myStudentView.showStudent();
                    } else if(code == 201){
                        myStudentView.passStudent();
                        myStudentView.showToast(myMsg);
                    } else {
                        myStudentView.showToast(myMsg);
                    }
                    break;
            }
        }
    };

    public void getStudentList(String classId) {
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.STUDENT_LIST + "?classID=" + classId);
    }

    public void passStudent(String classId, String studentId) {
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.PASS_STUDENT + "?classId=" + classId + "&studentId=" + studentId);
    }

    private List<Student> parseArrayStu(String json) {
        Log.d("TAG1", "得到的学生" + json);
        if ("[]".equals(json)) {
            return null;
        }
        return JSONObject.parseArray(json, Student.class);
    }
}
