package com.rjwl.reginet.teacherread.presenter;

/**
 * Created by Administrator on 2018/5/8.
 */

public interface MyBaseView {

    //等待提示
    void showLoading();

    void hidenLoading();
    //显示信息
    void showToast(String msg);

}
