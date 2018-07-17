package com.rjwl.reginet.teacherread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.adapter.BookStackAdapter;
import com.rjwl.reginet.teacherread.entity.Book;
import com.rjwl.reginet.teacherread.entity.BookStack;
import com.rjwl.reginet.teacherread.presenter.BooksStackPresenter;
import com.rjwl.reginet.teacherread.presenter.MyBooksView;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.vondear.rxtools.RxActivityTool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanle on 2018/5/28.
 */

public class BookStackActivity extends BaseActivity implements MyBooksView, BookStackAdapter.SendBookId {
    //测试
    private ListView lvBookStack;
    private List<Book> bookList;
    private List<BookStack> bookStackList;
    private BookStackAdapter bookStackAdapter;

    private BooksStackPresenter booksStackPresenter;

    private SearchView svBook;
    private Button btnOk;
    private TextView tvHaveBook;

    private String taskName;
    private String explain;
    private String classId;
    private String bookId;
    private Book books;
    private String id;

    private boolean isBookStack = false;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        title.setText("书库");
        bookList = new ArrayList<>();
        bookStackList = new ArrayList<>();
        //用假数据初始化集合
        //initList();
        booksStackPresenter = new BooksStackPresenter(BookStackActivity.this);

        Intent intent = getIntent();
        taskName = intent.getStringExtra("taskName");
        explain = intent.getStringExtra("explain");
        classId = intent.getStringExtra("classId");

        isBookStack = intent.getBooleanExtra("isBookStack", false);

        id = SaveOrDeletePrefrence.lookInt(getApplicationContext(), "id") + "";
        Log.d("TAG1", "任务名：" + taskName);
        Log.d("TAG1", "任务说明：" + explain);
        tvHaveBook = findViewById(R.id.tv_have_book);
        Bundle bundle = intent.getBundleExtra("bookBundle");
        if (bundle != null) {
            books = (Book) bundle.getSerializable("book");
            if (books != null) {
                bookId = books.getId() + "";
                tvHaveBook.setVisibility(View.VISIBLE);
                tvHaveBook.setText("已选择:" + books.getName());

            }
        }

        lvBookStack = findViewById(R.id.lv_book_stack);
        bookStackAdapter = new BookStackAdapter(bookStackList, BookStackActivity.this, this, explain, taskName, classId, isBookStack);
        lvBookStack.setAdapter(bookStackAdapter);
        svBook = findViewById(R.id.sv_book_stack);
        btnOk = findViewById(R.id.btn_release_ok);


        if (isBookStack) {
            btnOk.setVisibility(View.GONE);
            svBook.setVisibility(View.GONE);
        }

        booksStackPresenter.getBooksStack();

        svBook.clearFocus();
        svBook.setFocusable(false);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG1", "发布任务");
                if (bookId == null) {
                    Toast.makeText(BookStackActivity.this, "请选择书籍", Toast.LENGTH_SHORT).show();
                    return;
                }
                booksStackPresenter.releaseGame(classId, bookId, explain, taskName, id);
            }
        });

        setSearchView();
        svBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @Override
    int getResId() {
        return R.layout.activity_book_stack;
    }

    //去掉搜索框下划线
    private void setSearchView() {
        if (svBook != null) {

            int search_mag_icon_id = svBook.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
            ImageView mSearchViewIcon = svBook.findViewById(search_mag_icon_id);
            android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) mSearchViewIcon.getLayoutParams();

            int id = svBook.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchTextView = svBook.findViewById(id);
            //获取出SearchView中间文本的布局参数，跟上面的图标布局文件进行对比
            android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) searchTextView.getLayoutParams();

            //发现textLayoutParams中的高度是固定108的，而图标的布局文件的高度是-2也就是WrapContent，将文本的高度也改成WrapContent就可以了
            textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
            searchTextView.setLayoutParams(textLayoutParams);
            searchTextView.setTextSize(15);

            try {        //--拿到字节码
                Class<?> argClass = svBook.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(svBook);
                //--设置背景
                mView.setBackgroundResource(R.drawable.search_view_line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        svBook.clearFocus();
        svBook.setFocusable(false);
    }

    private void initList() {
        //测试数据

        // booksStackPresenter.getBooksStack();
        Book book1 = new Book();
        book1.setName("天涯明月刀");
        book1.setAuthor("古龙");
        book1.setPrice("10.04");
        book1.setNum("22.8");
        book1.setHeadPic("http://pic2.52pk.com/files/141115/1283496_110744_1.jpg");
        book1.setDetail("《天涯明月刀》的故事是傅红雪拯救自我、拯救他人最终达到自我升华的过程，" +
                "天涯、明月、刀三个词是傅红雪给人的三个意向，" +
                "天涯般辽阔的胸襟，明月般剔透的心灵加上一把“无敌于天下”的快刀。" +
                "代表着古龙在低谷期时挣扎、痛苦过但依然热爱着生活的一种温暖乐观的心态。");
        Book book2 = new Book();
        book2.setName("三体");
        book2.setAuthor("刘慈欣");
        book2.setPrice("25.8");
        book2.setNum("10.2");
        book2.setHeadPic("http://www.artsbj.com/UploadFiles/2014-06/wq/2014060414234167686.jpg");
        book2.setDetail("《三体》是刘慈欣创作的系列长篇科幻小说，由《三体》、《三体Ⅱ·黑暗森林》、" +
                "《三体Ⅲ·死神永生》组成，作品讲述了地球人类文明和三体文明的信息交流、" +
                "生死搏杀及两个文明在宇宙中的兴衰历程。");
        Book book3 = new Book();
        book3.setName("流星蝴蝶剑");
        book3.setAuthor("古龙");
        book3.setPrice("15.04");
        book3.setNum("20.3");
        book3.setHeadPic("http://y2.ifengimg.com/haina/2015_39/2dbbfd12c465c50_w559_h800.jpg");
        book3.setDetail(" 主要讲述了“老伯”孙玉伯的孙府与万鹏王的十二飞鹏帮相互对峙，" +
                "以及孟星魂、小蝶、孙玉伯、律香川、高老大等人各怀心思，" +
                "相互算计，恩恩怨怨，爱恨情仇的故事。");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        BookStack bookStack1 = new BookStack("一年级", bookList);
        BookStack bookStack2 = new BookStack("二年级", bookList);
        BookStack bookStack3 = new BookStack("三年级", bookList);
        BookStack bookStack4 = new BookStack("四年级", bookList);
        bookStackList.add(bookStack1);
        bookStackList.add(bookStack2);
        bookStackList.add(bookStack3);
        bookStackList.add(bookStack4);
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage("请稍后...");
            progressDialog.show();
            //progressDialog = ProgressDialog.show(this, "", "请稍后...");
        } else if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.setTitle("");
            progressDialog.setMessage("请稍后...");
        }
        progressDialog.show();
    }

    @Override
    public void hidenLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(BookStackActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBooks() {
        List<BookStack> list = booksStackPresenter.getBookStackList();
        if (list != null) {
            bookStackList.addAll(list);
        }
        Log.d("TAG1", "我的书架集合：" + bookList);
        bookStackAdapter.notifyDataSetChanged();
    }

    @Override
    public void skipView() {
        Intent intent = new Intent(BookStackActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void getBookId(Book book) {
        bookId = book.getId() + "";
        Log.d("TAG1", "书籍Id" + bookId);
        if (bookId != null) {
            tvHaveBook.setVisibility(View.VISIBLE);
            tvHaveBook.setText("已选择:" + book.getName());
        }
    }
}
