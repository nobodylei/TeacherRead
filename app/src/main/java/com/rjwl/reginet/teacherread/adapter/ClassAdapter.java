package com.rjwl.reginet.teacherread.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.activity.ClassActivity;
import com.rjwl.reginet.teacherread.entity.MyClass;

import java.util.List;


/**
 * Created by yanle on 2018/5/8.
 * 班级适配器
 */

public class ClassAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyClass> mClassList;

    public ClassAdapter(Context context, List<MyClass> classList) {
        this.mContext = context;
        this.mClassList = classList;

    }

    @Override
    public int getCount() {
        return mClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //返回项的View
        //持有者
        Wrapper wrapper = null;
        //解析成view
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.item_class, parent, false);
            //将布局文件解析成View的过程，需要资源
            wrapper = new Wrapper(row);
            row.setTag(wrapper);
        } else {
            wrapper = (Wrapper) row.getTag();
        }
        SimpleDraweeView imgClass = wrapper.getImgClass();
        TextView className = wrapper.getClassName();
        TextView classNumber = wrapper.getClassNumber();
        TextView classId = wrapper.getClassId();
        final Button btnClass = wrapper.getBtnClass();
        //通过position决定内容
        final MyClass myClass = mClassList.get(position);
        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TAG1", "classAdapter");
                //未完成
                //if ("查看".equals(btnClass.getText())) {
                    skipView(myClass);
                /*} else {
                    presenter.addClass(myClass.getClassID() + "",
                            SaveOrDeletePrefrence.lookInt(mContext.getApplicationContext(), "id") + "");
                    //btnClass.setText("等待通过");
                }*/
            }
        });
        imgClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipView(myClass);
            }
        });
        //btnClass.setText(myClass.getResult() + "");
        className.setText(myClass.getClassname() + "");
        classNumber.setText("班级人数:" + myClass.getCount());
        classId.setText("班级ID:" + myClass.getClassID());
        return row;
    }

    private void skipView(MyClass myClass) {
        Intent intent = new Intent(mContext, ClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("myClass", myClass);
        intent.putExtra("classBundle", bundle);
        mContext.startActivity(intent);
    }

    class Wrapper {
        SimpleDraweeView imgClass;
        TextView className;
        TextView classNumber;
        TextView classId;
        Button btnClass;

        View row;

        public Wrapper(View row) {
            this.row = row;
        }

        public SimpleDraweeView getImgClass() {
            if (imgClass == null) {
                imgClass = row.findViewById(R.id.img_class);
            }
            return imgClass;
        }

        public TextView getClassName() {
            if (className == null) {
                //查找
                className = row.findViewById(R.id.tv_class_name);
            }
            return className;
        }

        public TextView getClassNumber() {
            if (classNumber == null) {
                //查找
                classNumber = row.findViewById(R.id.tv_class_number);
            }
            return classNumber;
        }

        public TextView getClassId() {
            if (classId == null) {
                //查找
                classId = row.findViewById(R.id.tv_class_id);
            }
            return classId;
        }

        public Button getBtnClass() {
            if (btnClass == null) {
                btnClass = row.findViewById(R.id.btn_class_look);
            }
            return btnClass;
        }
    }
}
