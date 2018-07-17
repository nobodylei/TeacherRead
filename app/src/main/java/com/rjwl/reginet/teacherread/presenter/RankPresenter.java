package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Comment;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Student;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */

public class RankPresenter extends BasePresenter {
    private MyRankView myRankView;
    private List<Student> studentList;
    private List<Comment> commentList;

    public RankPresenter(MyRankView myRankView) {
        this.myRankView = myRankView;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
    public List<Comment> getCommentList() {
        return commentList;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myRankView.hidenLoading();
            String message = msg.obj + "";
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    myRankView.showToast(message);
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
                        //bookList = parseGameList(data);
                        studentList = parseStudent(data);
                        myRankView.showStudent();
                        Log.d("TAG1", "展示闯关：" + data);
                    } else {
                        myRankView.showToast(myMsg);
                    }
                    break;
                case 2:
                    int code2 = 0;
                    String data2 = "";
                    String myMsg2 = "";
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        code2 = jsonObject.getInteger("code");
                        data2 = jsonObject.getString("data");
                        myMsg2 = jsonObject.getString("desc");
                        Log.d("TAG1", "返回代码:" + code2);
                        Log.d("TAG1", "得到集合：" + data2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code2 == 200) {
                        commentList = parseJsonList(data2);
                        myRankView.showComment();
                    } else {
                        myRankView.showToast(myMsg2);
                        Log.d("TAG1", "BookPresenter层:" + myMsg2);
                    }
                    break;
            }
        }
    };

    public void getRankByBook(String classId, String bookId) {
        myRankView.showLoading();//得到任务中的排行榜
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.RANK_FOR_BOOK + "?classId=" + classId + "&bookId=" + bookId);
    }

    public void getRank() {
        myRankView.showLoading();//得到排行榜
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.RANK);
    }

    public void getComment(String bookId) {
        myRankView.showLoading();
        MyHttpUtils.okHttpGet(handler, 2, 0, Constant.URL + Constant.COMMENT
                + "?bookId=" + bookId);
    }

    private List<Student> parseStudent(String json) {
        return JSONObject.parseArray(json, Student.class);
    }

    public List<Comment> parseJsonList(String json) {
        return JSONObject.parseArray(json, Comment.class);
    }
}
