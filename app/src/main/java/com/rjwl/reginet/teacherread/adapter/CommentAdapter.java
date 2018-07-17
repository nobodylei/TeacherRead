package com.rjwl.reginet.teacherread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.entity.Comment;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.mContext = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
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
            row = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
            //将布局文件解析成View的过程，需要资源
            wrapper = new Wrapper(row);
            row.setTag(wrapper);
        } else {
            wrapper = (Wrapper) row.getTag();
        }
        TextView tvName = wrapper.getTvName();
        TextView tvTime = wrapper.getTvTime();
        TextView tvSpeech = wrapper.getTvSpeech();

        //通过position决定内容
        Comment comment = commentList.get(position);
        if (comment.getName() == null || "".equals(comment.getName()) || "null".equals(comment.getName())) {
            tvName.setText("未知");
        } else {
            tvName.setText(comment.getName());
        }
        tvTime.setText(comment.getCreateTime());
        tvSpeech.setText(comment.getSpeech());

        return row;
    }

    class Wrapper {
        TextView tvName;
        TextView tvTime;
        TextView tvSpeech;

        View row;

        public Wrapper(View row) {
            this.row = row;
        }

        public TextView getTvName() {
            if (tvName == null) {
                tvName = row.findViewById(R.id.tv_comment_name);
            }
            return tvName;
        }

        public TextView getTvTime() {
            if (tvTime == null) {
                //查找
                tvTime = row.findViewById(R.id.tv_comment_time);
            }
            return tvTime;
        }

        public TextView getTvSpeech() {
            if (tvSpeech == null) {
                tvSpeech = row.findViewById(R.id.tv_comment_speech);
            }
            return tvSpeech;
        }
    }
}
