package com.rjwl.reginet.teacherread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.adapter.GameAdapter;
import com.rjwl.reginet.teacherread.entity.Book;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.MyClass;
import com.rjwl.reginet.teacherread.presenter.GamePresenter;
import com.rjwl.reginet.teacherread.presenter.MyGameView;
import com.rjwl.reginet.teacherread.utils.StrUtils;
import com.vondear.rxtools.RxActivityTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/28.
 * 班级详情
 */

public class ClassActivity extends BaseActivity implements View.OnClickListener, MyGameView{
    private TextView tvClassName;
    private TextView tvClassId;
    private TextView tvClassNumber;
    private LinearLayout llTeacher;//老师
    private LinearLayout llStudent;//学生
    //private RelativeLayout rlTask;//闯关任务
    private TextView tvNoneTask;

    private GridView gvGame;

    private List<Book> bookList;
    private GameAdapter gameAdapter;
    private GamePresenter gamePresenter;

    private boolean isNone = false;

    private MyClass myClass;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        title.setText("班级详情");

        gamePresenter = new GamePresenter(this);

        tvClassName = findViewById(R.id.tv_class_msg_name);
        tvClassId = findViewById(R.id.tv_class_msg_id);
        tvClassNumber = findViewById(R.id.tv_class_msg_number);
        llStudent = findViewById(R.id.ll_student);
        llTeacher = findViewById(R.id.ll_teacher);
        //rlTask = findViewById(R.id.rl_task);
        gvGame = findViewById(R.id.gv_game_list);
        tvNoneTask = findViewById(R.id.tv_none_task);

        /*if (isNone) {//临时数据
            tvNoneTask.setVisibility(View.VISIBLE);
            gvGame.setVisibility(View.GONE);
        } else {
            tvNoneTask.setVisibility(View.GONE);
            gvGame.setVisibility(View.VISIBLE);
        }*/
        //得到传递的班级对象
        Bundle bundle = getIntent().getBundleExtra("classBundle");
        myClass = (MyClass) bundle.get("myClass");
        bookList = new ArrayList<>();//展示任务
        //initBook();测试数据
        gameAdapter = new GameAdapter(bookList, this, myClass.getClassID() + "");
        gvGame.setAdapter(gameAdapter);

        llTeacher.setOnClickListener(this);
        llStudent.setOnClickListener(this);//设置点击事件

        tvClassName.setText(myClass.getClassname());
        tvClassId.setText("班级ID:" + myClass.getClassID());
        tvClassNumber.setText("班级人数:" + myClass.getCount());

        gamePresenter.getTaskList(StrUtils.intTOStr(myClass.getGrade()) + "年级");
    }

    @Override
    int getResId() {
        return R.layout.activity_class;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_teacher:
                intent = new Intent(ClassActivity.this, TeacherActivity.class);
                intent.putExtra("classId", myClass.getClassID() + "");
                startActivity(intent);
                Log.d("TAG1", "教师详情");
                break;
            case R.id.ll_student:
                intent = new Intent(ClassActivity.this, StudentActivity.class);
                intent.putExtra("classId", myClass.getClassID() + "");
                startActivity(intent);
                Log.d("TAG1", "学生管理");
                break;
            default:
                break;
        }
    }

    //测试数据
    public void initBook() {
        Book book1 = new Book();
        book1.setName("天涯明月刀");
        book1.setTask("天涯");
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
        book2.setTask("黑暗森林");
        book2.setAuthor("刘慈欣");
        book2.setPrice("25.8");
        book2.setNum("10.2");
        book2.setHeadPic("http://www.artsbj.com/UploadFiles/2014-06/wq/2014060414234167686.jpg");
        book2.setDetail("《三体》是刘慈欣创作的系列长篇科幻小说，由《三体》、《三体Ⅱ·黑暗森林》、" +
                "《三体Ⅲ·死神永生》组成，作品讲述了地球人类文明和三体文明的信息交流、" +
                "生死搏杀及两个文明在宇宙中的兴衰历程。");
        Book book3 = new Book();
        book3.setName("流星蝴蝶剑");
        book3.setTask("流星");
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
        bookList.add(book3);
        bookList.add(book2);
        bookList.add(book1);
        bookList.add(book3);
        bookList.add(book2);
        bookList.add(book1);
        bookList.add(book3);
        bookList.add(book2);
        bookList.add(book1);
        bookList.add(book3);
        bookList.add(book2);
        bookList.add(book1);
    }

    @Override
    public void showGame() {
        List<Book> list = gamePresenter.getBookList();
        if (list != null && list.size() != 0) {
            bookList.addAll(list);
            tvNoneTask.setVisibility(View.GONE);
            gvGame.setVisibility(View.VISIBLE);
        }
        gameAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage("请稍后...");
            progressDialog.show();
            //progressDialog = ProgressDialog.show(this, "", "请稍后...");
        } else if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.setTitle("");
            progressDialog.setMessage("请稍后...");
        }
        progressDialog.show();
    }

    @Override
    public void hidenLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(ClassActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
