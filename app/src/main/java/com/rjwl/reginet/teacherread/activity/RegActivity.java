package com.rjwl.reginet.teacherread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.entity.Teacher;
import com.rjwl.reginet.teacherread.presenter.LoginPresenter;
import com.rjwl.reginet.teacherread.presenter.MyLoginView;
import com.rjwl.reginet.teacherread.utils.GreenDaoUtil;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.vondear.rxtools.RxActivityTool;

/**
 * Created by Administrator on 2018/5/3.
 * 注册和找回密码
 */

public class RegActivity extends BaseActivity implements View.OnClickListener, MyLoginView {
    private String regTitle;
    private boolean isReg;
    private EditText userName;
    private EditText passWord;
    private EditText etVerify;
    private Button btnVerify;
    private Button btnRegOrForget;

    private LoginPresenter mLoginPresenter;
    private String number;
    private String pass;
    private String verify;

    private GreenDaoUtil greenDaoUtil;

    private Boolean threadTag = false;
    private Thread thread;
    private int count = 60;

    // private Student student;

    //等待Dialog

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity

        rlBase.setVisibility(View.GONE);
        Intent intent = getIntent();
        //regTitle = intent.getStringExtra("title");
        isReg = intent.getBooleanExtra("isReg", false);
        userName = findViewById(R.id.et_username_reg);
        passWord = findViewById(R.id.et_password_reg);
        etVerify = findViewById(R.id.et_verify_reg);
        btnVerify = findViewById(R.id.btn_verify);
        btnRegOrForget = findViewById(R.id.btn_reg_forget);

        mLoginPresenter = new LoginPresenter(this);
        greenDaoUtil = GreenDaoUtil.getDBUtil(getApplicationContext());

        title.setText(regTitle);
        if (isReg) {
            btnRegOrForget.setText("注册");
        } else {
            btnRegOrForget.setText("修改密码");
        }

        btnVerify.setOnClickListener(this);
        btnRegOrForget.setOnClickListener(this);
    }

    @Override
    int getResId() {
        return R.layout.activity_register_forget;
    }

    @Override
    public void onClick(View v) {
        number = userName.getText() + "";
        pass = passWord.getText() + "";
        verify = etVerify.getText() + "";
        switch (v.getId()) {
            case R.id.btn_reg_forget:
                Log.d("TAG1", "注册、登录");

                mLoginPresenter.lookPhoneNumber(number, pass, verify, isReg);

                break;
            case R.id.btn_verify:
                Log.d("TAG1", "获取验证码");
                if (threadTag == false) {
                    if (number.length() != 0) {
                        upDataTime();
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入手机号！", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                mLoginPresenter.lookPhoneNumber(number);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String errMsg = (String) msg.obj;
                    //Toast.makeText(RegistActivity.this, errMsg, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //更新时间
                    btnVerify.setText(count + "s");
                    break;
                case 2:
                    btnVerify.setText("获取验证码");
                    count = 60;
                    threadTag = false;
                    break;
            }
        }
    };

    private void upDataTime() {
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                threadTag = true;
                while (threadTag) {
                    count--;
                    if (count > 0) {
                        handler.sendEmptyMessage(1);
                    } else if (count == 0) {
                        handler.sendEmptyMessage(2);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        thread.start();
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
        Toast.makeText(RegActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveUser(Teacher user) {
        Log.d("TAG1", "保存：" + user.toString());
        SaveOrDeletePrefrence.saveInt(getApplicationContext(), "id", user.getId());
        SaveOrDeletePrefrence.save(getApplicationContext(), "username", number);
        SaveOrDeletePrefrence.save(getApplicationContext(), "password", pass);

        if (greenDaoUtil.getTeacherById(user.getId()) == null) {
            greenDaoUtil.delAll();
            greenDaoUtil.insertTea(user);
        }
    }

    @Override
    public void skipView() {
        if (isReg) {
            Intent intent = new Intent(RegActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent2 = new Intent(RegActivity.this, LoginActivity.class);
            startActivity(intent2);
            finish();
        }
    }
}
