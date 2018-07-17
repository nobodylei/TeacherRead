package com.rjwl.reginet.teacherread.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2018/4/23.
 * 轮播图
 */

public class ViewPagerAdapter extends PagerAdapter {
    private String[] mImageUris;
    private SimpleDraweeView[] mSimpleDraweeViews;
    private ViewPagerOnClick viewPagerOnClick;

    public ViewPagerAdapter(String[] imageUris, SimpleDraweeView[] simpleDraweeViews, ViewPagerOnClick viewPagerOnClick) {
        this.mImageUris = imageUris;
        this.mSimpleDraweeViews = simpleDraweeViews;
        this.viewPagerOnClick = viewPagerOnClick;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        SimpleDraweeView t = mSimpleDraweeViews[position % mSimpleDraweeViews.length];

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerOnClick.onClick(position, mImageUris[position % mImageUris.length]);
            }
        });

        container.addView(t);
        Uri uri = Uri.parse(mImageUris[position % mImageUris.length]);
        t.setImageURI(uri);
        return t;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface ViewPagerOnClick {
        void onClick(int posotion, String url);
    }

}
