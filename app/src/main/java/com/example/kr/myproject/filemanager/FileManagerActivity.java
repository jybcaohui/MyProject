package com.example.kr.myproject.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kr.myproject.R;

public class FileManagerActivity extends Activity{


    public static final int FILE_RESULT_CODE = 1;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        Button button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.fileText);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(FileManagerActivity.this,MyFileManager.class);
                startActivityForResult(intent, FILE_RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(FILE_RESULT_CODE == requestCode){
            Bundle bundle = null;
            if(data!=null&&(bundle=data.getExtras())!=null){
                textView.setText("选择文件夹为："+bundle.getString("file"));
            }
        }
    }
}

