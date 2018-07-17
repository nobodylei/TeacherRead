package com.rjwl.reginet.teacherread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.adapter.CommentAdapter;
import com.rjwl.reginet.teacherread.adapter.RankAdapter;
import com.rjwl.reginet.teacherread.entity.Book;
import com.rjwl.reginet.teacherread.entity.Comment;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Student;
import com.rjwl.reginet.teacherread.presenter.MyRankView;
import com.rjwl.reginet.teacherread.presenter.RankPresenter;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.vondear.rxtools.RxActivityTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class GameIntroActivity extends BaseActivity implements MyRankView {
    //private ImageView imgBook;
    private SimpleDraweeView imgBook;

    private TextView bookName;
    private TextView bookAuthor;
    private TextView bookNumber;
    private TextView bookPrice;

    private TextView tvBookIntro;
    private View view;
    private ListView gaemRank;
    private TextView tvRankTitle;

    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private boolean isBookStack = true;

    private List<Student> userList;
    private RankAdapter rankAdapter;
    private Book book;//得到的书对象
    private String classId;

    private RankPresenter rankPresenter;


    @Override
    void initView() {
        RxActivityTool.addActivity(this);

        title.setText("闯关详情");
        view = findViewById(R.id.in_book_item);
        rankPresenter = new RankPresenter(this);

        imgBook = view.findViewById(R.id.img_item_book);
        bookName = view.findViewById(R.id.tv_book_name);
        bookAuthor = view.findViewById(R.id.tv_book_author);
        bookNumber = view.findViewById(R.id.tv_book_number);
        bookPrice = view.findViewById(R.id.tv_book_price);
        tvRankTitle = findViewById(R.id.tv_rank_title);

        gaemRank = findViewById(R.id.lv_game_rank);
        tvBookIntro = findViewById(R.id.tv_book_intro);//详情

        Bundle bundle = getIntent().getBundleExtra("bookBundle");
        book = (Book) bundle.get("book");
        isBookStack = bundle.getBoolean("isBookStack");
        classId = bundle.getString("classId");

        Log.d("TAG1", "书籍路径：" + book.getHeadPic());
        Log.d("TAG1", "书籍id：" + book.getId());

        imgBook.setImageURI(Uri.parse(Constant.IMG + book.getHeadPic()));
        bookName.setText(book.getName());
        bookAuthor.setText("作者:" + book.getAuthor());
        bookNumber.setText(book.getNum() + "");
        bookPrice.setText(book.getPrice() + "元/本");
        tvBookIntro.setText("\t\t" + book.getDetail());


        //initUser();
        if (isBookStack) {

            tvRankTitle.setText("评论");
            commentList = new ArrayList<>();
            //initList();
            commentAdapter = new CommentAdapter(GameIntroActivity.this, commentList);
            gaemRank.setAdapter(commentAdapter);
            rankPresenter.getComment(book.getId() + "");
        } else {
            userList = new ArrayList<>();
            rankAdapter = new RankAdapter(GameIntroActivity.this, userList);
            gaemRank.setAdapter(rankAdapter);
            rankPresenter.getRankByBook(classId, book.getId() + "");
            tvRankTitle.setText("排行榜");
        }
    }

    private void initList() {
        Comment comment = new Comment();
        comment.setCreateTime("2018-06-09");
        comment.setName("哈哈");
        comment.setSpeech("不怎么样啊。。。。");
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);


    }
    //测试数据
    private void initUser() {
        Student user1 = new Student();
        user1.setName("张三");
        Student user2 = new Student();
        user2.setName("李四");
        Student user3 = new Student();
        user3.setName("王五");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }

    @Override
    int getResId() {
        return R.layout.activity_game_intro;
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
        Toast.makeText(GameIntroActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStudent() {
        List<Student> list = rankPresenter.getStudentList();
        if (list != null) {
            userList.addAll(list);
        }
        rankAdapter.notifyDataSetChanged();
    }

    @Override
    public void showComment() {
        List<Comment> list = rankPresenter.getCommentList();

        if (list != null) {
            commentList.clear();
            commentList.addAll(list);
        }
        commentAdapter.notifyDataSetChanged();
    }
}
