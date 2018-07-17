package com.rjwl.reginet.teacherread.activity;

import android.widget.TextView;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.entity.Teacher;
import com.rjwl.reginet.teacherread.utils.GreenDaoUtil;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.vondear.rxtools.RxActivityTool;


/**
 * Created by Administrator on 2018/5/9.
 * 老师和学生信息
 */

public class TeacherActivity extends BaseActivity {
    //老师信息
    private TextView tvTeacherName;
    private TextView tvTeacherSex;
    private TextView tvTeacherSchool;

    private GreenDaoUtil greenDaoUtil;
    private int id;

    private Teacher teacher;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity

        id = SaveOrDeletePrefrence.lookInt(getApplicationContext(), "id");
        greenDaoUtil = GreenDaoUtil.getDBUtil(getApplicationContext());
        teacher = greenDaoUtil.getTeacherById(id);

        tvTeacherName = findViewById(R.id.tv_teacher_name);
        tvTeacherSex = findViewById(R.id.tv_teacher_sex);
        tvTeacherSchool = findViewById(R.id.tv_teacher_school);

        //boolean isTeacher = getIntent().getBooleanExtra("isTeacher", false);
        title.setText("教师详情");
        /*Bundle bundle = getIntent().getBundleExtra("teacherBundle");
        Teacher teacher = (Teacher) bundle.getSerializable("teacher");*/
        if (!"".equals(teacher.getName()) || teacher.getName() != null || !"null".equals(teacher.getName()))
            tvTeacherName.setText(teacher.getName() + "");
        if (!"".equals(teacher.getGender()) || teacher.getGender() != null || !"null".equals(teacher.getGender()))
            //tvTeacherSex.setText(teacher.getGender() + "");
        if (!"".equals(teacher.getSchoolname()) || teacher.getSchoolname() != null || !"null".equals(teacher.getSchoolname()))
            tvTeacherSchool.setText(teacher.getSchoolname() + "");
    }

    private void initStudent() {
        /*studentList = new ArrayList<>();
        Student s1 = new Student();
        s1.setName("张国民");
        s1.setGender("男");
        s1.setSchoolname("满族中学");
        s1.setGradeID(7);
        s1.setYesorno(2);
        Student s2 = new Student();
        s2.setName("李建军");
        s2.setGender("男");
        s2.setSchoolname("职教中心");
        s2.setGradeID(3);
        s2.setYesorno(2);
        Student s3 = new Student();
        s3.setName("宋明");
        s3.setGender("男");
        s3.setSchoolname("第三中学");
        s3.setGradeID(6);
        s3.setYesorno(3);
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);*/
    }


    @Override
    int getResId() {
        return R.layout.activity_teacher;
    }

}
