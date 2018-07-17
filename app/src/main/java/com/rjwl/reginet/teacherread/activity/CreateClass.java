package com.rjwl.reginet.teacherread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.rjwl.reginet.teacherread.entity.MyClass;
import com.rjwl.reginet.teacherread.presenter.CreateClassPresenter;
import com.rjwl.reginet.teacherread.presenter.MyCreateClassView;
import com.rjwl.reginet.teacherread.utils.SaveOrDeletePrefrence;
import com.vondear.rxtools.RxActivityTool;

/**
 * Created by Administrator on 2018/5/29.
 */

public class CreateClass extends BaseActivity implements MyCreateClassView{
    private EditText etCalssName;
    private Spinner spinnerGrade;
    private Button btnCreateClass;

    private MyClass myClass;

    private ArrayAdapter<CharSequence> gradeAdapter;

    private CreateClassPresenter createClassPresenter;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        title.setText("创建班级");

        createClassPresenter = new CreateClassPresenter(CreateClass.this);

        myClass = new MyClass();
        myClass.setGrade(1);

        etCalssName = findViewById(R.id.et_class_name);
        spinnerGrade = findViewById(R.id.spinner_grade);
        btnCreateClass = findViewById(R.id.btn_create_class);

        //2、新建ArrayAdapter，数组适配器
        gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item);
        //3、adapter 设置一个下拉列表的样式
        gradeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerGrade.setAdapter(gradeAdapter);
        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myClass.setGrade((i + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(etCalssName.getText() + "")) {
                    Toast.makeText(CreateClass.this, "请输入班级名", Toast.LENGTH_SHORT).show();
                    return;
                }
                myClass.setClassname(etCalssName.getText() + "");
                createClassPresenter.createClass(myClass,
                        SaveOrDeletePrefrence.lookInt(getApplicationContext(), "id") + "");
                Log.d("TAG1", "创建班级：" + myClass);

            }
        });
    }

    @Override
    int getResId() {
        return R.layout.activity_create_class;
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
        Toast.makeText(CreateClass.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMyClass(MyClass myClass) {
        Intent intent = new Intent(CreateClass.this, MainActivity.class);
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("myClass", myClass);
        intent.putExtra("classBundle", bundle);*/
        startActivity(intent);
        finish();
    }
}
