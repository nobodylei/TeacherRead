package com.rjwl.reginet.teacherread.activity;

import com.rjwl.reginet.teacherread.R;
import com.vondear.rxtools.RxActivityTool;

/**
 * Created by Administrator on 2018/6/9.
 */

public class ReadPlanActivity extends BaseActivity {
    @Override
    void initView() {
        RxActivityTool.addActivity(this);
        title.setText("阅读规划");
    }

    @Override
    int getResId() {
        return R.layout.activity_plan;
    }
}
