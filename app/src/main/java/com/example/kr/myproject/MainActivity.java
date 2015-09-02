package com.example.kr.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kr.myproject.slidingmenu.SlidingMenuActivity;


import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.but1)
    Button but1;
    @InjectView(R.id.but2)
    Button but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.but1:
                showProgressDialog();       //测试等待框的实现
                Handler handler=new Handler();
                handler.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1=new Intent(MainActivity.this, SlidingMenuActivity.class);
                        startActivity(intent1);
                        toast("跳转到Slidingmenu页面！");     //BaseActivity 的使用
                        closeProgressDialog();
                    }
                },android.os.SystemClock.uptimeMillis()+2*1000);//两秒后执行操作，关闭等待框
                break;
            case R.id.but2:
                toast("跳转到功能2页面！");
        }

    }
}
