package com.rjwl.reginet.teacherread.activity;

import android.app.ProgressDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.adapter.StudentAdapter;
import com.rjwl.reginet.teacherread.entity.Student;
import com.rjwl.reginet.teacherread.presenter.MyStudentView;
import com.rjwl.reginet.teacherread.presenter.StudentPresenter;
import com.vondear.rxtools.RxActivityTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanle on 2018/5/30.
 */

public class StudentActivity extends BaseActivity implements MyStudentView, StudentAdapter.StuListener {
    private String classId;
    private StudentPresenter studentPresenter;

    private ListView lvNotPass;
    private ListView lvPass;

    private List<Student> notPassStuList;
    private List<Student> passStuList;

    private StudentAdapter notPassAdapter;
    private StudentAdapter passAdapter;

    private Student student;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        title.setText("学生名单");

        classId = getIntent().getStringExtra("classId");
        studentPresenter = new StudentPresenter(this);

        lvNotPass = findViewById(R.id.lv_not_pass);
        lvPass = findViewById(R.id.lv_pass);
        //未通过学生集合
        notPassStuList = new ArrayList<>();
        passStuList = new ArrayList<>();//已通过学生集合

        //initStudent();//测试数据

        notPassAdapter = new StudentAdapter(StudentActivity.this, notPassStuList, classId,
                true, studentPresenter, this);
        passAdapter = new StudentAdapter(StudentActivity.this, passStuList, classId,
                false, studentPresenter, this);

        lvNotPass.setAdapter(notPassAdapter);
        lvPass.setAdapter(passAdapter);

        //setListViewHeightBasedOnChildren(lvNotPass);//设置listView高度

        //获取学生列表
        studentPresenter.getStudentList(classId);
    }

    private void initStudent() {
        Student student = new Student();
        student.setName("鸣人");
        student.setHeadPic("https://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/730e0cf3d7ca7bcb10eada07be096b63f624a800.jpg");
        notPassStuList.add(student);
        passStuList.add(student);
        Student student2 = new Student();
        student2.setName("佐助");
        student2.setHeadPic("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/0e2442a7d933c89527b0430cd71373f0830200ff.jpg");
        notPassStuList.add(student2);
        passStuList.add(student2);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        passStuList.add(student2);
        passStuList.add(student);
        /*notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);
        notPassStuList.add(student2);
        notPassStuList.add(student);*/
    }

    @Override
    int getResId() {
        return R.layout.activity_student;
    }

    /***
     * 动态设置listview的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() + 1));
        listView.setLayoutParams(params);
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
        Toast.makeText(StudentActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStudent() {
        List<Student> notList = studentPresenter.getNotPassStus();
        List<Student> passList = studentPresenter.getPassStuList();
        if (notList != null) {
            notPassStuList.addAll(notList);
        }
        if (passList != null) {
            passStuList.addAll(passList);
        }
        notPassAdapter.notifyDataSetChanged();
        passAdapter.notifyDataSetChanged();
    }

    @Override
    public void passStudent() {
        if (student != null) {
            notPassStuList.remove(student);
            passStuList.add(student);
        }
        notPassAdapter.notifyDataSetChanged();
        passAdapter.notifyDataSetChanged();
    }

    @Override
    public void getStu(Student student) {
        this.student = student;
    }
}
