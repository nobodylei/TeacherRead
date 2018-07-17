package com.rjwl.reginet.teacherread.utils;

/**
 * Created by Administrator on 2018/5/14.
 * 字符串相关
 */

public class StrUtils {


    public static String setPhone(String phone) {
        if (phone.length() != 11) {
            return phone;
        }
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    public static String intTOStr(int i) {
        String myClass = "一";
        switch (i) {
            case 1:
                myClass = "一";
                break;
            case 2:
                myClass = "二";
                break;
            case 3:
                myClass = "三";
                break;
            case 4:
                myClass = "四";
                break;
            case 5:
                myClass = "五";
                break;
            case 6:
                myClass = "六";
                break;
            case 7:
                myClass = "七";
                break;
            case 8:
                myClass = "八";
                break;
            case 9:
                myClass = "九";
                break;
            case 10:
                myClass = "十";
                break;
            case 11:
                myClass = "十一";
                break;
            case 12:
                myClass = "十二";
                break;
            case 13:
                myClass = "十三";
                break;
            case 14:
                myClass = "十四";
                break;
            case 15:
                myClass = "十五";
                break;
            default:
                break;
        }
        return myClass;
    }
}
