package com.rjwl.reginet.teacherread.presenter;

import com.rjwl.reginet.teacherread.entity.Teacher;

/**
 * Created by Administrator on 2018/5/11.
 */

public interface MyLoginView extends MyBaseView {

    void saveUser(Teacher user);

    void skipView();
}
