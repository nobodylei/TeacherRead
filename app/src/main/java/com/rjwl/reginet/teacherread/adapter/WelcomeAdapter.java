package com.rjwl.reginet.teacherread.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yanle on 2018/3/1.
 */

public class WelcomeAdapter extends PagerAdapter {

    private List<View> views;

    public WelcomeAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * 获得当前界面数
     * @return
     */
    @Override
    public int getCount() {
        return views.size();
    }

    /**创建
     * 初始化position界面
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(views.get(position));
        return views.get(position);
    }

    /**
     * 判断是否由对象生成界面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**销毁
     * 删除界面
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }
}
