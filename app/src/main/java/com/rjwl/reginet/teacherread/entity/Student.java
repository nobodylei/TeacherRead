package com.rjwl.reginet.teacherread.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/9.
 * 学生对象
 */


public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;//学生id
    private String username;//账号
    private String password;//密码
    private String name;//昵称
    private String gender;//性别
    private String schoolname;//学校名
    private String headPic;//头像
    private int classID;//班级id
    private int gradeID;//几年级
    private int yesorno;//几班


    public Student() {
    }

    public Student(int id, String username, String password,
                   String name, String gender, String schoolname, String headPic,
                   int classID, int gradeID, int yesorno) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.schoolname = schoolname;
        this.headPic = headPic;
        this.classID = classID;
        this.gradeID = gradeID;
        this.yesorno = yesorno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public int getYesorno() {
        return yesorno;
    }

    public void setYesorno(int yesorno) {
        this.yesorno = yesorno;
    }

    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", schoolname='" + schoolname + '\'' +
                ", headPic='" + headPic + '\'' +
                ", id=" + id +
                ", classID=" + classID +
                ", gradeID=" + gradeID +
                ", yesorno=" + yesorno +
                '}';
    }
}
