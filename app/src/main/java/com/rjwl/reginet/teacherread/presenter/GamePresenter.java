package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Book;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.MyClass;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.util.List;

/**
 * Created by yanle on 2018/5/31.
 */

public class GamePresenter extends BasePresenter {
    private MyGameView myGameView;
    private List<Book> bookList;

    public GamePresenter(MyGameView myGameView) {
        this.myGameView = myGameView;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myGameView.hidenLoading();
            String message = msg.obj + "";
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    myGameView.showToast(message);
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
                        bookList = parseGameList(data);
                        myGameView.showGame();
                        Log.d("TAG1", "展示闯关：" + data);
                    } else {
                        myGameView.showToast(myMsg);
                    }
                    break;
            }
        }
    };


    public void getTaskList(String classId) {
        myGameView.showLoading();
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL
                + Constant.GAME_LIST + "?grade=" + classId);
    }

    private List<Book> parseGameList(String json) {
        return JSONObject.parseArray(json, Book.class);
    }
}
