package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Book;
import com.rjwl.reginet.teacherread.entity.BookStack;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;
import com.rjwl.reginet.teacherread.utils.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/18.
 */

public class BooksStackPresenter extends BasePresenter {
    private MyBooksView myBooksView;
    private List<Book> bookList;
    private List<BookStack> bookStackList;

    public BooksStackPresenter(MyBooksView myBooksView) {
        this.myBooksView = myBooksView;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public List<BookStack> getBookStackList() {
        return bookStackList;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = msg.obj + "";
            myBooksView.hidenLoading();
            if ("".equals(message)) {
                return;
            }
            Log.d("TAG1", "BookPresenter层：" + message);
            switch (msg.what) {
                case 0:
                    myBooksView.showToast(message);
                    Log.d("TAG1", "BookPresenter层case0：" + message);
                    break;
                case 1:
                    int code = 0;
                    String data = "";
                    String myMsg = "";
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        code = jsonObject.getInteger("code");
                        data = jsonObject.getString("data");
                        myMsg = jsonObject.getString("desc");
                        Log.d("TAG1", "返回代码:" + code);
                        Log.d("TAG1", "得到书库集合：" + data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code == 200) {
                        bookStackList = parseJsonStackList(data);
                        myBooksView.showBooks();
                        Log.d("TAG1", "BooksStackPresenter得到书库集合code==200：" + bookStackList);
                    } else if (code == 201){
                        myBooksView.skipView();
                        myBooksView.showToast(myMsg + "");
                        Log.d("TAG1", "BooksStackPresenter:" + myMsg);
                    } else {
                        myBooksView.showToast(myMsg + "");
                        Log.d("TAG1", "BooksStackPresenter:" + myMsg);
                    }
                    break;
            }
        }
    };

    public void getBooksStack() {//得到书库列表
        MyHttpUtils.okHttpGet(handler, 1, 0, Constant.URL + Constant.BOOKS_STACK);
        myBooksView.showLoading();
    }

    public void releaseGame(String classId, String bookId, String taskStatement, String task, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("classId", classId);
        map.put("bookId", bookId);
        map.put("taskStatement", taskStatement);
        map.put("task", task);
        Log.d("TAG1", "发布任务的集合 ;" + map);
        MyHttpUtils.okHttpUtils(map, handler, 1, 0, Constant.URL + Constant.RELEASE_GAME);
    }

    public List<BookStack> parseJsonStackList(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<BookStack> bookStacks = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            String book = jsonObject.getString(i + "");
            if (!"[]".equals(book) && book != null) {
                Log.d("TAG1", "得到的书集合：" + i + book);
                List<Book> list = parseJsonList(book);
                BookStack bookStack = new BookStack(StrUtils.intTOStr(i) + "年级", list);
                bookStacks.add(bookStack);
            }
        }
        return bookStacks;
    }

    public List<Book> parseJsonList(String json) {
        return JSONObject.parseArray(json, Book.class);
    }
}


