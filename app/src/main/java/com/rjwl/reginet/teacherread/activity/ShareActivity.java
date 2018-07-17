package com.rjwl.reginet.teacherread.activity;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.vondear.rxtools.RxActivityTool;

import java.util.HashMap;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.PlatformConfig;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;

/**
 * Created by Administrator on 2018/6/6.
 */

public class ShareActivity extends BaseActivity implements View.OnClickListener {
    private Button btnShare;
    private ShareParams shareParams;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private ImageView imgWechat;
    private ImageView imgWxfriends;
    private ImageView imgQQ;
    private ImageView imgQzone;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(ShareActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
            Log.d("TAG1", "分享结果:" + toastMsg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };

    @Override
    void initView() {
        RxActivityTool.addActivity(this);

        PlatformConfig platformConfig = new PlatformConfig();
        platformConfig.setWechat("wxfbcf963528caed34", "3d2bc690f6769530b84272d5aa1b9acd");
        platformConfig.setQQ("1106973504", "pi2CaHRDfpQaVeJj");
        JShareInterface.init(getApplicationContext(), platformConfig);

        alertDialogBuilder = new AlertDialog.Builder(ShareActivity.this, R.style.dialog);

        btnShare = findViewById(R.id.btn_share);

        title.setText("分享");

        /*shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setText("馨阅读教师端");*/

       /* shareParams = new ShareParams();
        shareParams.setTitle("馨阅读");
        shareParams.setText("app下载");
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl("www.baidu.com");//必须
        shareParams.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.share_teacher));*/
        List<String> list = JShareInterface.getPlatformList();
        Log.d("TAG1", "配置的平台：" + list);
        JShareInterface.authorize(WechatMoments.Name, null);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //JShareInterface.share(WechatMoments.Name, shareParams, mPlatActionListener);
                Log.d("TAG1", "点击分享");
                alertDialog = alertDialogBuilder.create();
                View view1 = LayoutInflater.from(ShareActivity.this).inflate(R.layout.share_dialog, null);
                initDialogView(view1);
                alertDialog.setView(view1);
                alertDialog.show();
                settingWifiDialog(alertDialog);
            }
        });
    }

    private void settingWifiDialog(AlertDialog alertDialog) {

        /*Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);*/

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        Window window = alertDialog.getWindow();
        android.view.WindowManager.LayoutParams p = window.getAttributes();  //获取对话框当前的参数值
        window.setGravity(Gravity.BOTTOM);
        p.height = (int) (d.getHeight() * 0.2);  //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 1);    //宽度设置为屏幕的0.5
        window.setAttributes(p);    //设置生效
    }

    private void initDialogView(View view) {
        imgWechat = view.findViewById(R.id.img_share_wechat);
        imgWxfriends = view.findViewById(R.id.img_share_wxcircle);
        imgQQ = view.findViewById(R.id.img_share_qq);
        imgQzone = view.findViewById(R.id.img_share_qzone);

        imgWechat.setOnClickListener(this);
        imgWxfriends.setOnClickListener(this);
        imgQQ.setOnClickListener(this);
        imgQzone.setOnClickListener(this);

    }

    private PlatActionListener mPlatActionListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享成功";
                Log.d("TAG1", "分享成功");
                handler.sendMessage(message);
            }
            dissDialog();
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享失败:" + (error != null ? error.getMessage() : "") + "---" + errorCode;
                Log.d("TAG1", "分享失败");
                handler.sendMessage(message);
            }
            dissDialog();
        }

        @Override
        public void onCancel(Platform platform, int action) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享取消";
                Log.d("TAG1", "分享取消");
                handler.sendMessage(message);
            }
            dissDialog();
        }
    };

    @Override
    int getResId() {
        return R.layout.activity_share;
    }

    /*name 平台名称，值可选 Wechat.Name、WechatMoments.Name、
    WechatFavorite.Name、SinaWeibo.Name、SinaWeiboMessage.Name、
    QQ.Name、QZone.Name、Facebook.Name、FbMessenger.Name、Twitter.Name。*/
    @Override
    public void onClick(View v) {
        String str = "";
        /*PlatformConfig platformConfig = new PlatformConfig();
        platformConfig.setWechat("wx24ddb563862ef903", "3d2bc690f6769530b84272d5aa1b9acd");
        platformConfig.setQQ("RW4YCT4DBVIu5IaP", "RW4YCT4DBVIu5IaP");*/
        switch (v.getId()) {
            case R.id.img_share_wechat:
                shareWeChat();
                str = Wechat.Name;
                break;
            case R.id.img_share_wxcircle:
                shareWeChat();
                str = WechatMoments.Name;
                break;
            case R.id.img_share_qq:
                shareQQ();
                str = QQ.Name;
                break;
            case R.id.img_share_qzone:
                shareQQ();
                str = QZone.Name;
                break;
        }
        //JShareInterface.init(getApplicationContext(), platformConfig);
        JShareInterface.share(str, shareParams, mPlatActionListener);
    }

    private void shareWeChat() {
        shareParams = new ShareParams();
        shareParams.setTitle("馨阅读教师端");
        shareParams.setText("馨阅读下载");
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl("www.baidu.com");//必须
        shareParams.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.share_teacher));
    }

    private void shareQQ() {
        shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle("馨阅读教师端");
        shareParams.setText("馨阅读下载");
        shareParams.setUrl("http://www.baidu.com/");
        shareParams.setImageUrl("https://mmbiz.qlogo.cn/mmbiz_png/4Ziarc5RpNK4eP9zch7ozdRD9lCibLJ2A2iasicPhIMMU1JV1QMdIZSC2RYysdWKxpJHedmcNK5GRvwsQEZxCZLXow/0?wx_fmt=png");
        //shareParams.setImagePath(file.getAbsolutePath());
    }




    public void dissDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
