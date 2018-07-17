package com.rjwl.reginet.teacherread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.adapter.BookLibraryAdapter;
import com.rjwl.reginet.teacherread.entity.Book;
import com.vondear.rxtools.RxActivityTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class BookLibraryActivity extends BaseActivity {
    private GridView gvLibrary;
    private BookLibraryAdapter bookLibraryAdapter;
    private List<Book> bookList;
    private String currTitle = "";
    private String taskName;
    private String explain;
    private String classId;
    private boolean isBookStack;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        gvLibrary = findViewById(R.id.gv_library);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("books");
        taskName = intent.getStringExtra("taskName");
        explain = intent.getStringExtra("explain");
        classId = intent.getStringExtra("classId");
        isBookStack = intent.getBooleanExtra("isBookStack", false);
        bookList = new ArrayList<>();
        List<Book> list = (List<Book>) bundle.getSerializable("booklist");
        if (list != null) {
            bookList.addAll(list);
        }
        currTitle = bundle.getString("library");
        title.setText(currTitle);
        //initBook();
        bookLibraryAdapter = new BookLibraryAdapter(BookLibraryActivity.this, bookList, explain, taskName, classId, isBookStack);
        gvLibrary.setAdapter(bookLibraryAdapter);
    }

    @Override
    int getResId() {
        return R.layout.activity_library;
    }

    //初始化书籍信息
    private void initBook() {
        Book book1 = new Book();
        book1.setName("天涯明月刀");
        book1.setAuthor("古龙");
        book1.setPrice("10.04");
        book1.setNum("22.8");
        book1.setHeadPic("http://pic2.52pk.com/files/141115/1283496_110744_1.jpg");
        book1.setDetail("《天涯明月刀》的故事是傅红雪拯救自我、拯救他人最终达到自我升华的过程，" +
                "天涯、明月、刀三个词是傅红雪给人的三个意向，" +
                "天涯般辽阔的胸襟，明月般剔透的心灵加上一把“无敌于天下”的快刀。" +
                "代表着古龙在低谷期时挣扎、痛苦过但依然热爱着生活的一种温暖乐观的心态。");
        Book book2 = new Book();
        book2.setName("三体");
        book2.setAuthor("刘慈欣");
        book2.setPrice("25.8");
        book2.setNum("10.2");
        book2.setHeadPic("http://www.artsbj.com/UploadFiles/2014-06/wq/2014060414234167686.jpg");
        book2.setDetail("《三体》是刘慈欣创作的系列长篇科幻小说，由《三体》、《三体Ⅱ·黑暗森林》、" +
                "《三体Ⅲ·死神永生》组成，作品讲述了地球人类文明和三体文明的信息交流、" +
                "生死搏杀及两个文明在宇宙中的兴衰历程。");
        Book book3 = new Book();
        book3.setName("流星蝴蝶剑");
        book3.setAuthor("古龙");
        book3.setPrice("15.04");
        book3.setNum("20.3");
        book3.setHeadPic("http://y2.ifengimg.com/haina/2015_39/2dbbfd12c465c50_w559_h800.jpg");
        book3.setDetail(" 主要讲述了“老伯”孙玉伯的孙府与万鹏王的十二飞鹏帮相互对峙，" +
                "以及孟星魂、小蝶、孙玉伯、律香川、高老大等人各怀心思，" +
                "相互算计，恩恩怨怨，爱恨情仇的故事。");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book3);
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book1);
        bookList.add(book2);
    }
}
