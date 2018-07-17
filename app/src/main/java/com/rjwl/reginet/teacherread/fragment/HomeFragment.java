package com.rjwl.reginet.teacherread.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.activity.BookStackActivity;
import com.rjwl.reginet.teacherread.activity.CreateClass;
import com.rjwl.reginet.teacherread.activity.ProofActivity;
import com.rjwl.reginet.teacherread.activity.ReadPlanActivity;
import com.rjwl.reginet.teacherread.adapter.ClassAdapter;
import com.rjwl.reginet.teacherread.adapter.ViewPagerAdapter;
import com.rjwl.reginet.teacherread.entity.MyClass;
import com.rjwl.reginet.teacherread.presenter.HomePersenter;
import com.rjwl.reginet.teacherread.presenter.MyHomeView;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/5/25.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, MyHomeView {
    public static final String TAG = "home";

    private Context mContext;

    private View view;
    private SearchView svBooks;//搜索框

    private String tvc;//书店链接

    private RelativeLayout rlClassList;
    private LinearLayout llCreateClass;//创建班级
    private LinearLayout llBookShop;//书店
    private LinearLayout llTeacherProof;//教师认证
    private LinearLayout llBookStack;//书库
    private LinearLayout llReadPlan;//阅读规划

    private TextView tvTeacher;
    private ListView lvClassList;

    private List<MyClass> mMyClassList;
    private ClassAdapter classAdapter;

    private HomePersenter homePersenter;

    //轮播图片相关
    private LinearLayout linearLayout;
    private ViewPager homeViewPager;
    //临时图片
    String[] imageUris = {
            "http://img15.3lian.com/2016/h1/68/d/134.jpg",
            "http://img3.redocn.com/tupian/20160330/weimeiertongdushuguanggaosheji_6084043.jpg",
            "https://b-ssl.duitang.com/uploads/item/201207/29/20120729222617_tLMfx.thumb.700_0.jpeg"
    };
    SimpleDraweeView[] simpleDraweeViews;
    private ImageView[] points;// 圆点集合
    private int currentItem;
    private ScheduledExecutorService executor;
    private ViewPagerAdapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        this.mContext = getActivity();
        initView();
        return view;
    }

    private void initView() {
        homePersenter = new HomePersenter(this);
        //搜索框
        svBooks = view.findViewById(R.id.sv_book_serch);

        svBooks.clearFocus();
        svBooks.setFocusable(false);

        llCreateClass = view.findViewById(R.id.ll_create_class);
        llBookShop = view.findViewById(R.id.ll_book_shop);
        llTeacherProof = view.findViewById(R.id.ll_teacher_proof);
        llBookStack = view.findViewById(R.id.ll_book_stack);
        llReadPlan = view.findViewById(R.id.ll_book_plan);

        rlClassList = view.findViewById(R.id.rl_class_list);
        tvTeacher = view.findViewById(R.id.tv_home);
        lvClassList = view.findViewById(R.id.lv_class_list);

        mMyClassList = new ArrayList<>();

       /* Bundle bundle = getArguments();
        if (bundle != null) {
            MyClass myClass = (MyClass) bundle.getSerializable("myClass");
            Log.d("TAG1", "创建的班级");
            mMyClassList.add(myClass);
        }*/
        //临时数据
        //initClass();
        classAdapter = new ClassAdapter(mContext, mMyClassList);
        lvClassList.setAdapter(classAdapter);

       /* if (true) {//临时数据
            SaveOrDeletePrefrence.saveBoolean(mContext.getApplicationContext(), "isProof", true);
            llCreateClass.setVisibility(View.VISIBLE);
            rlClassList.setVisibility(View.VISIBLE);

            llTeacherProof.setVisibility(View.GONE);
            tvTeacher.setVisibility(View.GONE);
        } else {
            llCreateClass.setVisibility(View.GONE);
            rlClassList.setVisibility(View.GONE);

            llTeacherProof.setVisibility(View.VISIBLE);
            tvTeacher.setVisibility(View.VISIBLE);
        }*/

        //判断老师是否验证
        homePersenter.approveState(SaveOrDeletePrefrence.lookInt(mContext.getApplicationContext(), "id") + "");
        //书店链接
        homePersenter.bookShop();
        //轮播图相关
        int size = initSize();
        initTextViews(size);
        initViewPager();

        llCreateClass.setOnClickListener(this);
        llBookShop.setOnClickListener(this);
        llTeacherProof.setOnClickListener(this);
        llReadPlan.setOnClickListener(this);
        llBookStack.setOnClickListener(this);

        setSearchView();
        svBooks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TAG1", "搜索：" + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG1", "输入搜索：" + newText);
                return true;
            }
        });
    }


    //测试数据
    private void initClass() {

        MyClass myClass = new MyClass();
        myClass.setClassID(9527);
        myClass.setClassname("复仇者联盟");
        myClass.setCount(21);
        mMyClassList.add(myClass);
        mMyClassList.add(myClass);
    }


    //去掉搜索框下划线
    private void setSearchView() {
        if (svBooks != null) {

            int search_mag_icon_id = svBooks.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
            ImageView mSearchViewIcon = svBooks.findViewById(search_mag_icon_id);
            android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) mSearchViewIcon.getLayoutParams();

            int id = svBooks.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchTextView = svBooks.findViewById(id);
            //获取出SearchView中间文本的布局参数，跟上面的图标布局文件进行对比
            android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) searchTextView.getLayoutParams();

            //发现textLayoutParams中的高度是固定108的，而图标的布局文件的高度是-2也就是WrapContent，将文本的高度也改成WrapContent就可以了
            textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
            searchTextView.setLayoutParams(textLayoutParams);
            searchTextView.setTextSize(15);

            try {        //--拿到字节码
                Class<?> argClass = svBooks.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(svBooks);
                //--设置背景
                mView.setBackgroundResource(R.drawable.search_view_line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //初始化viewPager
    private void initViewPager() {
        linearLayout = view.findViewById(R.id.viewPager_lineatLayout);
        homeViewPager = view.findViewById(R.id.vp_home);
        viewPagerAdapter = new ViewPagerAdapter(imageUris, simpleDraweeViews, new ViewPagerAdapter.ViewPagerOnClick() {
            @Override
            public void onClick(int posotion, String url) {
                Log.d("viewPager", "ViewPager点击事件： " + posotion + " ; " + url);
                //Intent intent = new Intent(getActivity(), null);//NewsActivity.class);
                //intent.putExtra("imageUrl", url);
                //startActivity(intent);
            }
        });

        homeViewPager.setAdapter(viewPagerAdapter);
        initPoint();
        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                setCurrentPoint(position % imageUris.length);
                startAutoScroll();//手动切换完成后恢复自动播放
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        currentItem = imageUris.length * 1000; //取一个中间的大数字，防止接近边界
        homeViewPager.setCurrentItem(currentItem);
    }

    private int initSize() {
        int size;
        if (imageUris.length > 3) {
            size = imageUris.length;
        } else {
            size = imageUris.length * 2; // 小于3个时候, 需要扩大一倍, 防止出错
        }
        return size;
    }

    private void initTextViews(int size) {
        SimpleDraweeView[] tvs = new SimpleDraweeView[size];

        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = new SimpleDraweeView(getActivity());
            tvs[i].getHierarchy().setPlaceholderImage(R.mipmap.ic_launcher);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tvs[i].setLayoutParams(layoutParams);
            simpleDraweeViews = tvs;
        }
    }

    //开始轮播图片
    private void startAutoScroll() {
        stopAutoScroll();
        executor = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new Runnable() {
            @Override
            public void run() {
                selectNextItem();
            }

            private void selectNextItem() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        homeViewPager.setCurrentItem(++currentItem);
                    }
                });
            }
        };
        int delay = 4;
        int period = 5;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        executor.scheduleAtFixedRate(command, delay, period, timeUnit);
    }

    //停止轮播图片
    private void stopAutoScroll() {
        if (executor != null) {
            executor.shutdownNow();
        }

    }

    //初始化轮播圆点
    private void initPoint() {
        points = new ImageView[imageUris.length];
        //全部圆点设置为未选中状态
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setBackgroundResource(R.drawable.nomal);
        }
        //currentItem = 0;
        points[currentItem % imageUris.length].setBackgroundResource(R.drawable.select);
    }

    //设置当前的点
    private void setCurrentPoint(int position) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setBackgroundResource(R.drawable.nomal);
        }
        points[position].setBackgroundResource(R.drawable.select);
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();//激活时自动播放
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();//停止播放
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_create_class:
                Intent intent1 = new Intent(mContext, CreateClass.class);
                startActivity(intent1);
                Log.d(TAG, "创建班级");
                break;
            case R.id.ll_book_shop:
                Log.d(TAG, "书店");
                if ("".equals(tvc) || tvc == null) {
                    tvc = "https://s.taobao.com/search?q=%E4%B9%A6%E5%BA%97";
                }
                Uri uri = Uri.parse(tvc);
                Log.d("TAG1", "书籍链接:" + uri.toString());
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent2);
                break;
            case R.id.ll_teacher_proof:
                Intent intent = new Intent(mContext, ProofActivity.class);
                startActivity(intent);
                Log.d(TAG, "教师认证");
                break;
            case R.id.ll_book_stack:
                Log.d(TAG, "书库");
                Intent intent4 = new Intent(mContext, BookStackActivity.class);
                intent4.putExtra("isBookStack", true);
                startActivity(intent4);
                break;
            case R.id.ll_book_plan:
                Log.d(TAG, "阅读规划");
                Intent intent3 = new Intent(mContext, ReadPlanActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hidenLoading() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showClassList() {
        List<MyClass> list = homePersenter.getClassList();
        if (list != null) {
            mMyClassList.addAll(list);
        }
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void modfiyState(boolean isProof) {
        SaveOrDeletePrefrence.saveBoolean(mContext.getApplicationContext(), "isProof", isProof);
        if (isProof) {
            //展示班级
            homePersenter.getClassList(SaveOrDeletePrefrence.lookInt(mContext.getApplicationContext(), "id") + "");
            llCreateClass.setVisibility(View.VISIBLE);
            rlClassList.setVisibility(View.VISIBLE);

            llTeacherProof.setVisibility(View.GONE);
            tvTeacher.setVisibility(View.GONE);
        } else {
            llCreateClass.setVisibility(View.GONE);
            rlClassList.setVisibility(View.GONE);

            llTeacherProof.setVisibility(View.VISIBLE);
            tvTeacher.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bookShop() {
        tvc = homePersenter.getTvc();
        SaveOrDeletePrefrence.save(mContext.getApplicationContext(), "shop", tvc);
    }
}
