package com.rjwl.reginet.teacherread.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rjwl.reginet.teacherread.R;

/**
 * Created by Administrator on 2018/5/23.
 */

public abstract class BaseActivity extends AppCompatActivity {
    TextView title;
    ImageView back;

    RelativeLayout rlBase;

    FragmentManager manager;       //声明管理和事务
    FragmentTransaction transaction;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        rlBase = findViewById(R.id.rl_main);
        LinearLayout layout = findViewById(R.id.liner);
        View view = LayoutInflater.from(layout.getContext()).inflate(getResId(), null);
        layout.addView(view);
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            return;
        }
        super.onBackPressed();
        finish();

    }

    abstract void initView();
    abstract int getResId();
}
