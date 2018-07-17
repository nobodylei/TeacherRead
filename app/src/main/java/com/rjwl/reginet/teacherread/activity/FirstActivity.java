package com.rjwl.reginet.teacherread.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;

public class FirstActivity extends Activity {
    private boolean isFrist = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    /*SharedPreferences sharedPreferences = getApplication().getSharedPreferences("wel",
                            Activity.MODE_PRIVATE);*/
                    //请求失败时，如果没有保存过账号和密码，则跳转到登录也，否则跳转到选择小区
                    if (isFrist) {
                        Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(FirstActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        SaveOrDeletePrefrence.saveBoolean(getApplicationContext(), "isFrist", true);
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFrist = SaveOrDeletePrefrence.lookBoolean(getApplicationContext(), "isFrist");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        /*这里可以给服务器发送验证，判断是否需要验证*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
