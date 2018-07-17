package com.rjwl.reginet.teacherread.entity;

/**
 * Created by Administrator on 2018/5/11.
 * 常量类
 */

public class Constant {
    public static final String URL = "http://192.168.1.104:8080//ReadingBooks/";//地址
    public static final String LOGIN_URL = "TeacherLoginServlet";//登录
    public static final String REGISTER_URL = "RegisterTeacherServlet";//注册
    public static final String FORGET_URL = "";//忘记密码

    public static final String APPROVE = "Te";//教师认证 发送id和图片
    public static final String APPROVE_STATE = "TeacherStateServlet";//教师认证状态
    public static final String CLASS_DET = "ClassList";//班级详情

    public static final String CREATE_CLASS = "ClassServlet";//创建班级

    public static final String BOOK_SHOP = "ReturnServlet";//书店链接

    public static final String STUDENT_LIST = "StudentList1";//学生列表
    public static final String PASS_STUDENT = "NoDYesServlet";//学生通过申请

    public static final String GAME_LIST = "N";//任务列表
    public static final String BOOKS_STACK = "G";//书库

    public static final String PHONE_MESSAGE = "Phone";//验证码

    public static final String RANK = "RankingListServlet"; //排行榜
    public static final String RANK_FOR_BOOK = "RankByBookServlet";//任务排行榜
    public static final String RELEASE_GAME = "TGRServlet";//发布任务

    public static final String UPDATA_TEACHER = "Tea";//完善教师个人信息
    public static final String UPDATA_HEAD = "TeaServlet1";//修改头像
    public static final String COMMENT = "NotesServlet";//读书笔记

    public static final String FORGET = "Forget";//忘记密码

    //public static final String IMG = "http://192.168.1.104:8080//ReadingBooks/images/";
   

    //public static final String URL = "http://192.168.2.103:8080//ReadingBooks/";
}