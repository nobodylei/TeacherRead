package com.rjwl.reginet.teacherread.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/8.
 * 班级对象
 */

public class MyClass implements Serializable {

    private String classname;
    private String result;
    private int grade;
    private int count;
    private int classID;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "classname='" + classname + '\'' +
                ", result='" + result + '\'' +
                ", grade='" + grade + '\'' +
                ", count=" + count +
                ", classID=" + classID +
                '}';
    }
}
