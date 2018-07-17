package com.rjwl.reginet.teacherread.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/8.
 */

public class Book implements Serializable {
    private String name;
    private String author;
    private String price;
    private String classif;
    private String num;
    private int id;
    private String detail;//书籍简介
    private String tvc;//购买路径
    private String headPic;//图片
    private String task;//任务名称
    private String taskStatement;//任务说明

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskStatement() {
        return taskStatement;
    }

    public void setTaskStatement(String taskStatement) {
        this.taskStatement = taskStatement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClassif() {
        return classif;
    }

    public void setClassif(String classif) {
        this.classif = classif;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTvc() {
        return tvc;
    }

    public void setTvc(String tvc) {
        this.tvc = tvc;
    }


}
