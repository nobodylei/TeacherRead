package com.rjwl.reginet.teacherread.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.activity.GameIntroActivity;
import com.rjwl.reginet.teacherread.entity.Book;
import com.rjwl.reginet.teacherread.entity.Constant;

import java.util.List;

/**
 * Created by yanle on 2018/5/31.
 */

public class GameAdapter extends BaseAdapter {
    private List<Book> bookList;
    private Context mContext;
    private String classId;

    public GameAdapter(List<Book> bookList, Context context, String classId) {
        this.bookList = bookList;
        this.mContext = context;
        this.classId = classId;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //返回项的View
        //持有者
        Wrapper wrapper = null;
        //解析成view
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.item_game, parent, false);
            //将布局文件解析成View的过程，需要资源
            wrapper = new Wrapper(row);
            row.setTag(wrapper);
        } else {
            wrapper = (Wrapper) row.getTag();
        }
        SimpleDraweeView imgBook = wrapper.getImgBook();
        TextView bookName = wrapper.getBookName();
        TextView task = wrapper.getTask();

        //通过position决定内容
        final Book book = bookList.get(position);

        imgBook.setImageURI(Uri.parse(Constant.IMG + book.getHeadPic()));//Constant.URL +
        bookName.setText(book.getName() + "");
        task.setText(book.getTask() + "");


        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GameIntroActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                bundle.putString("classId", classId);
                //bundle.putString("btnTitle", "+加入书架");
                intent.putExtra("bookBundle", bundle);
                mContext.startActivity(intent);
                Log.d("TAG1", "点击数据" + book);
            }
        });

        return row;
    }

    class Wrapper {
        //ImageView imgBook;
        SimpleDraweeView imgBook;
        TextView bookName;
        TextView task;

        View row;

        public Wrapper(View row) {
            this.row = row;
        }

        public SimpleDraweeView getImgBook() {
            if (imgBook == null) {
                imgBook = row.findViewById(R.id.img_game_item);
            }
            return imgBook;
        }

        public TextView getBookName() {
            if (bookName == null) {
                bookName = row.findViewById(R.id.tv_item_book_name);
            }
            return bookName;
        }

        public TextView getTask() {
            if (task == null) {
                task = row.findViewById(R.id.tv_item_game_name);
            }
            return task;
        }
    }
}
