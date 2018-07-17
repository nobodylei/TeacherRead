package com.rjwl.reginet.teacherread.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rjwl.reginet.teacherread.entity.Constant;
import com.rjwl.reginet.teacherread.utils.MyHttpUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by yanle on 2018/5/31.
 */

public class ProofPrefrence extends BasePresenter {
    private MyProofView myProofView;

    public ProofPrefrence(MyProofView myProofView) {
        this.myProofView = myProofView;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myProofView.hidenLoading();
            String message = msg.obj + "";
            Log.d("TAG1", message + "");
            switch (msg.what) {
                case 0:
                    Log.d("TAG1", message + "");
                    myProofView.showToast(message);
                    break;
                case 1:
                    Log.d("TAG1", message + "当前线程" + Thread.currentThread());
                    int code = 0;
                    String data = "";
                    String myMsg = "";
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        code = jsonObject.getInteger("code");
                        data = jsonObject.getString("data");
                        myMsg = jsonObject.getString("desc");
                        Log.d("TAG1", "返回代码" + code);
                        Log.d("TAG1", data + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code == 200) {
                        myProofView.showToast(myMsg);
                        myProofView.slipView();
                        Log.d("TAG1", "文件已上传");
                    } else {
                        myProofView.showToast(myMsg);
                    }
                    break;
            }
        }
    };

    public void upApprove(Map<String, String> map, Map<String, File> fileMap) {
        MyHttpUtils.uploadFile(handler, map, fileMap, 1, 0, Constant.URL + Constant.APPROVE);
        myProofView.showLoading();
    }
}
