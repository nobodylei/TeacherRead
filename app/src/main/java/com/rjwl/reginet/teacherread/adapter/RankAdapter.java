package com.rjwl.reginet.teacherread.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Student;
import com.rjwl.reginet.teacherread.utils.StrUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 * 排行榜
 */

public class RankAdapter extends BaseAdapter {
    private Context mContext;
    private List<Student> mUserList;

    public RankAdapter(Context context, List<Student> userList) {
        this.mContext = context;
        this.mUserList = userList;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
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
            row = LayoutInflater.from(mContext).inflate(R.layout.item_rank, parent, false);
            //将布局文件解析成View的过程，需要资源
            wrapper = new Wrapper(row);
            row.setTag(wrapper);
        } else {
            wrapper = (Wrapper) row.getTag();
        }
        SimpleDraweeView imgHead = wrapper.getImgHead();
        TextView tvRankName = wrapper.getTvRankName();
        TextView tvRankId = wrapper.getTvRankId();

        //通过position决定内容
        Student user = mUserList.get(position);
        imgHead.setImageURI(Uri.parse(Constant.IMG + user.getHeadPic()));
        if (user.getName() == null || "".equals(user.getName()) || "null".equals(user.getName())){
            tvRankName.setText("未知");
        } else {
            tvRankName.setText(user.getName());
        }
        tvRankId.setText((position + 1) + ".");

        return row;
    }

    class Wrapper {
        SimpleDraweeView imgHead;
        //MyImageView imgHead;
        TextView tvRankName;
        TextView tvRankId;

        View row;

        public Wrapper(View row) {
            this.row = row;
        }

        public SimpleDraweeView getImgHead() {
            if (imgHead == null) {
                imgHead = row.findViewById(R.id.img_rank_head);
            }
            return imgHead;
        }

        public TextView getTvRankName() {
            if (tvRankName == null) {
                //查找
                tvRankName = row.findViewById(R.id.tv_rank_name);
            }
            return tvRankName;
        }

        public TextView getTvRankId() {
            if (tvRankId == null) {
                tvRankId = row.findViewById(R.id.tv_rank_id);
            }
            return tvRankId;
        }
    }
}
