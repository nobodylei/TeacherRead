package com.rjwl.reginet.teacherread.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.presenter.MyProofView;
import com.rjwl.reginet.teacherread.presenter.ProofPrefrence;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

/**
 * Created by Administrator on 2018/5/28.
 * 教师认证页面
 */

public class ProofActivity extends BaseActivity implements View.OnClickListener, MyProofView{
    private SimpleDraweeView imgUpTeacher;
    private Button btnUpOk;

    private static String path = "/storage/emulated/0/Pictures/";// sd路径
    private File file;
    private Uri resultUri;
    private Map<String, File> fileMap;
    private Map<String, String> idMap;

    private ProofPrefrence proofPrefrence;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        title.setText("认证");

        proofPrefrence = new ProofPrefrence(this);

        imgUpTeacher = findViewById(R.id.img_up_teacher);
        btnUpOk = findViewById(R.id.btn_up_ok);

        fileMap = new HashMap<>();
        idMap = new HashMap<>();
        idMap.put("id", SaveOrDeletePrefrence.lookInt(getApplicationContext(), "id") + "");

        imgUpTeacher.setOnClickListener(this);
        btnUpOk.setOnClickListener(this);

        initPermission();
    }

    @Override
    int getResId() {
        return R.layout.activity_proof;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_up_teacher:
                initDialogChooseImage();
                Log.d("TAG1", "上传资格证");
                break;
            case R.id.btn_up_ok:
                Log.d("TAG1", "完成上传");
                boolean b = SaveOrDeletePrefrence.lookBoolean(getApplicationContext(), "isUp");
                if (b) {
                    Toast.makeText(ProofActivity.this, "请等待通过...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fileMap.get("f1") == null) {
                    Toast.makeText(ProofActivity.this, "请添加上传文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                proofPrefrence.upApprove(idMap, fileMap);
                break;
            default:
                break;
        }
    }

    private void initDialogChooseImage() {
        //新建一个File，传入文件夹目录
        File file = new File(path);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
            file.mkdirs();
        }
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(ProofActivity.this, TITLE);
        dialogChooseImage.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG1", "选择相机之后:" + resultCode + " ;" + requestCode);
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }
                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                Log.d("TAG1", "选择相机之后:" + resultCode);
                if (resultCode == RESULT_OK) {
                    //data.getExtras().get("data");
                    // RxPhotoTool.cropImage(SetMyInfoActivity.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                    Log.d("TAG1", "照片的Url" + RxPhotoTool.imageUriFromCamera);
                    Log.d("TAG1", "照片的绝对路径:" + RxPhotoTool.getImageAbsolutePath(this, RxPhotoTool.imageUriFromCamera));
                }
                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.nothing)
                        //异常占位图(当加载异常的时候出现的图片)
                        .error(R.drawable.nothing)
                        //禁止Glide硬盘缓存缓存
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(ProofActivity.this).
                        load(RxPhotoTool.cropImageUri).
                        apply(options).
                        thumbnail(0.5f).
                        into(imgUpTeacher);
                Log.d("TAAG1", "普通剪裁后处理：" + file.getAbsolutePath());
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    file = roadImageView(resultUri, imgUpTeacher);
                    fileMap.put("f1", file);
                    idMap.put("fileName", file.getName());

                    Log.d("TAAG1", "文件路径：" + file.getAbsolutePath());

                    RxSPTool.putContent(ProofActivity.this, "AVATAR", resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(ProofActivity.this).
                load(uri).
                thumbnail(0.5f).
                into(imageView);

        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //options.setCircleDimmedLayer(true);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    private void initPermission() {
        String permissions[] = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_EXTERNAL_STORAGE

        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

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
        Toast.makeText(ProofActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void slipView() {
        SaveOrDeletePrefrence.saveBoolean(getApplicationContext(), "isUp", true);
        Intent intent = new Intent(ProofActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
