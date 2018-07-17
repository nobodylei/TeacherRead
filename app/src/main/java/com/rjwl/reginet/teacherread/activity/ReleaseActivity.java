package com.rjwl.reginet.teacherread.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rjwl.reginet.teacherread.R;
import com.vondear.rxtools.RxActivityTool;

/**
 * Created by Administrator on 2018/5/28.
 * 发布任务
 */

public class ReleaseActivity extends BaseActivity {
    private EditText etTaskName;
    private EditText etExplain;
    private Button btnAddBook;

    private String taskName;
    private String explain;
    private String classId;

    @Override
    void initView() {
        RxActivityTool.addActivity(this);//添加activity
        title.setText("发布任务");

        classId = getIntent().getStringExtra("classId");

        etTaskName = findViewById(R.id.et_task_name);
        etExplain = findViewById(R.id.et_explain);
        btnAddBook = findViewById(R.id.btn_add_book);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG1", "添加书籍");
                taskName = etTaskName.getText() + "";
                explain = etExplain.getText() + "";
                if ("".equals(taskName) || "".equals(explain)) {
                    Toast.makeText(ReleaseActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ReleaseActivity.this, BookStackActivity.class);
                intent.putExtra("taskName", taskName);
                intent.putExtra("explain", explain);
                intent.putExtra("classId", classId);
                startActivity(intent);
            }
        });
    }

    @Override
    int getResId() {
        return R.layout.activity_release;
    }
}
