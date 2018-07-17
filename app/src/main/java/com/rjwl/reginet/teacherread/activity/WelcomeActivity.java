package com.rjwl.reginet.teacherread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.adapter.WelcomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private List<View> views;
    private int[] images = {R.drawable.wel1, R.drawable.wel2, R.drawable.wel3};
    private WelcomeAdapter welcomeAdapter;
    private ImageView[] points;
    private LinearLayout linearLayout;
    private Button btn;
    //当页下标
    private int currentPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置满屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_viewpager);
        initView();
        initData();
    }

    /**
     * 初始化圆点
     */
    private void initPoint() {
        points = new ImageView[images.length];
        //全部圆点设置为未选中状态
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setBackgroundResource(R.drawable.nomal);
            points[i].setOnClickListener(this);
            points[i].setTag(i);
        }
        currentPoint = 0;
        points[currentPoint].setBackgroundResource(R.drawable.select);
    }

    /**
     * 初始化view
     */
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        views = new ArrayList<View>();
        welcomeAdapter = new WelcomeAdapter(views);
        linearLayout = findViewById(R.id.lineatLayout);
        btn = findViewById(R.id.btn);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                //设置宽高
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i : images) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(i);
            //设置图片属性
            imageView.setLayoutParams(layoutParams);
            views.add(imageView);
        }
        viewPager.setAdapter(welcomeAdapter);
        initPoint();
        viewPager.setOnPageChangeListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
       // Toast.makeText(this, "onPageScrolled", Toast.LENGTH_SHORT).show();
    }

    /**
     * 当新页面被选中时调用
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        setCurrentPoint(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        //Toast.makeText(this, "onPageScrollStateChanged", Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置当前选中的点
     */
    private void setCurrentPoint(int position) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setBackgroundResource(R.drawable.nomal);
        }
        points[position].setBackgroundResource(R.drawable.select);
        if(position == points.length - 1) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();
        } else {
            int i = (Integer) v.getTag();
            viewPager.setCurrentItem(i);
        }
    }
}
