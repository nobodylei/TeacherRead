package com.rjwl.reginet.teacherread.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Student;
import com.rjwl.reginet.teacherread.presenter.StudentPresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 * 学生管理适配器
 */

public class StudentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Student> mStudentList;
    private Boolean isNone;
    private String classId;
    private StudentPresenter studentPresenter;

    public StudentAdapter(Context context, List<Student> studentList,
                          String classId, boolean isNone, StudentPresenter studentPresenter, StuListener stuListener) {
        this.mContext = context;
        this.mStudentList = studentList;
        this.isNone = isNone;
        this.classId = classId;
        this.studentPresenter = studentPresenter;
        this.stuListener = stuListener;
    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStudentList.get(position);
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
            row = LayoutInflater.from(mContext).inflate(R.layout.item_student, parent, false);
            //将布局文件解析成View的过程，需要资源
            wrapper = new Wrapper(row);
            row.setTag(wrapper);
        } else {
            wrapper = (Wrapper) row.getTag();
        }
        SimpleDraweeView imgStudentHead = wrapper.getImgStudentHead();
        TextView tvStudentName = wrapper.getTvStudentName();
        Button btnOk = wrapper.getBtnOk();

        if (isNone) {
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    studentPresenter.passStudent(classId, mStudentList.get(position).getId() + "");
                    stuListener.getStu(mStudentList.get(position));
                    Log.d("TAG1", "通过验证" + position);
                }
            });
        }

        //通过position决定内容
        Student student = mStudentList.get(position);
        imgStudentHead.setImageURI(Uri.parse(student.getHeadPic() + ""));
        tvStudentName.setText(student.getName());

        return row;
    }

    private StuListener stuListener;

    public interface StuListener{
        void getStu(Student student);
    }

    class Wrapper {
        SimpleDraweeView imgStudentHead;
        //ImageView imgStudentHead;
        TextView tvStudentName;
        Button btnOk;


        View row;

        public Wrapper(View row) {
            this.row = row;
        }

        public SimpleDraweeView getImgStudentHead() {
            if (imgStudentHead == null) {
                imgStudentHead = row.findViewById(R.id.img_student_head);
            }
            return imgStudentHead;
        }

        public TextView getTvStudentName() {
            if (tvStudentName == null) {
                //查找
                tvStudentName = row.findViewById(R.id.tv_mystudent_name);
            }
            return tvStudentName;
        }

        public Button getBtnOk() {
            if (btnOk == null) {
                btnOk = row.findViewById(R.id.btn_ok_student);
            }
            return btnOk;
        }
    }
}
