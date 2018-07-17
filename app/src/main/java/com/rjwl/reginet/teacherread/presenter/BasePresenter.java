package com.rjwl.reginet.teacherread.presenter;

import android.content.Context;

/**
 * Created by Administrator on 2018/5/8.
 */

public class BasePresenter {
    Context mContext;
    public void attach(Context context) {
        mContext = context;
    }
    public void onPause() {}
    public void onResume() {}
    public void onDestroy() {
        mContext = null;
    }
}
