package com.rjwl.reginet.teacherread.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.activity.LoginActivity;
import com.rjwl.reginet.teacherread.activity.RankActivity;
import com.rjwl.reginet.teacherread.activity.SetMyInfoActivity;
import com.rjwl.reginet.teacherread.activity.ShareActivity;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.entity.Teacher;
import com.rjwl.reginet.teacherread.utils.GreenDaoUtil;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.rjwl.reginet.teacherread.utils.StrUtils;
import com.vondear.rxtools.RxActivityTool;

/**
 * Created by Administrator on 2018/5/25.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private View view;

    private SimpleDraweeView imgMyHead;
    private TextView tvMyName;
    private Teacher teacher;
    private TextView tvApprove;

    private LinearLayout llBookShop;
    private LinearLayout llRank;
    private LinearLayout llSet;
    private LinearLayout llOut;
    private LinearLayout llShare;
    private LinearLayout llHelp;

    private boolean isApprove = false;

    private GreenDaoUtil greenDaoUtil;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        this.mContext = getActivity();
        initView();
        return view;
    }

    private void initView() {
        id = SaveOrDeletePrefrence.lookInt(mContext.getApplicationContext(), "id");
        greenDaoUtil = GreenDaoUtil.getDBUtil(mContext.getApplicationContext());
        teacher = greenDaoUtil.getTeacherById(id);



        imgMyHead = view.findViewById(R.id.img_my_head);
        tvMyName = view.findViewById(R.id.tv_my_name);
        tvApprove = view.findViewById(R.id.tv_approve);
        llBookShop = view.findViewById(R.id.ll_menu_book_shop);
        llRank = view.findViewById(R.id.ll_menu_rank);
        llSet = view.findViewById(R.id.ll_menu_set);
        llOut = view.findViewById(R.id.ll_menu_out);
        llShare = view.findViewById(R.id.ll_share);
        llHelp = view.findViewById(R.id.ll_help);

        if (teacher != null) {
            Log.d("TAG1", "myFragment:" + teacher);
            if ("".equals(teacher.getName()) || teacher.getName() == null || "null".equals(teacher.getName())) {
                String phone = SaveOrDeletePrefrence.look(getActivity().getApplicationContext(), "username") + "";
                Log.d("TAG1", "手机号" + phone);
                tvMyName.setText(StrUtils.setPhone(phone));
            } else {
                tvMyName.setText(teacher.getName());
            }
            imgMyHead.setImageURI(Uri.parse(Constant.IMG + teacher.getCardID() + ""));
        } else {
            //tvMyName.setText(SaveOrDeletePrefrence.look(getActivity().getApplicationContext(), "username"));
            tvMyName.setText("未设置");
        }

        isApprove = SaveOrDeletePrefrence.lookBoolean(mContext.getApplicationContext(), "isProof");
        if (isApprove) {
            tvApprove.setTextColor(getResources().getColor(R.color.background));
            tvApprove.setBackgroundResource(R.drawable.other_bg);
            tvApprove.setText("已认证");
        }

        llBookShop.setOnClickListener(this);
        llRank.setOnClickListener(this);
        llSet.setOnClickListener(this);
        llOut.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        imgMyHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_menu_book_shop:
                String tvc = SaveOrDeletePrefrence.look(mContext.getApplicationContext(), "shop");
                if ("".equals(tvc)) {
                    tvc = "https://s.taobao.com/search?q=%E4%B9%A6%E5%BA%97";
                }
                Uri uri = Uri.parse(tvc);
                Log.d("TAG1", "书籍链接:" + uri.toString());
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent2);
                Log.d("TAG1", "书店");
                break;
            case R.id.ll_menu_rank:
                Intent intent3 = new Intent(mContext, RankActivity.class);
                startActivity(intent3);
                Log.d("TAG1", "排行榜");
                break;
            case R.id.ll_menu_set:

                Log.d("TAG1", "设置");
                break;
            case R.id.ll_menu_out:
                greenDaoUtil.delAll();//清除数据
                SaveOrDeletePrefrence.deleteAll(mContext.getApplicationContext());
               /* SaveOrDeletePrefrence.delete(mContext.getApplicationContext(), "username");
                SaveOrDeletePrefrence.delete(mContext.getApplicationContext(), "password");*/
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                RxActivityTool.finishAllActivity();
                Log.d("TAG1", "退出登录");
                break;
            case R.id.ll_share:
                Intent intent1 = new Intent(mContext, ShareActivity.class);

                startActivity(intent1);
                break;
            case R.id.ll_help:
                Toast.makeText(mContext, "暂未开通", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_my_head:
                Intent intent4 = new Intent(mContext, SetMyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher", teacher);
                intent4.putExtra("teaBundle", bundle);
                startActivity(intent4);
                break;
        }
    }
}
