package com.rjwl.reginet.teacherread;


import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.vondear.rxtools.RxTool;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2018/5/23.
 */

public class MyApplication extends MultiDexApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        Fresco.initialize(this);
        //JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
