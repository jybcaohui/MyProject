package com.example.kr.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.kr.myproject.fragmentswithtab.ActivityWithFragments;
import com.example.kr.myproject.myvideoplayer.VideoViewActivity;
import com.example.kr.myproject.placechoose.PlaceChooseActivity;
import com.example.kr.myproject.pulltorefereshandrecycleview.PullAndRecycleActivity;
import com.example.kr.myproject.qqtwowayslidingmenu.QQMenuActivity;
import com.example.kr.myproject.slidingmenu.SlidingMenuActivity;
import com.example.kr.myproject.weixinheadimagecut.CutImageActivity;
import com.example.kr.myproject.weixinmainpage.WeiXinPageActivity;


import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.but1)
    Button but1;
    @InjectView(R.id.but2)
    Button but2;
    @InjectView(R.id.but3)
    Button but3;
    @InjectView(R.id.but4)
    Button but4;
    @InjectView(R.id.but5)
    Button but5;
    @InjectView(R.id.but6)
    Button but6;
    @InjectView(R.id.but7)
    Button but7;
    @InjectView(R.id.but8)
    Button but8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
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

        switch (id){
            case R.id.option1:
                toast("选择了操作1");
                break;
            case R.id.option2:
                Intent intent2=new Intent(MainActivity.this, ActivityWithFragments.class);
                startActivity(intent2);
                toast("跳转到fragments页面！");
                break;
            case R.id.action_settings:
                toast("设置属性");
                break;
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
                Intent intent2=new Intent(MainActivity.this, ActivityWithFragments.class);
                startActivity(intent2);
                toast("跳转到fragments页面！");
                break;
            case R.id.but3:
                Intent intent3=new Intent(MainActivity.this, QQMenuActivity.class);
                startActivity(intent3);
                break;
            case R.id.but4:
                Intent intent4=new Intent(MainActivity.this, WeiXinPageActivity.class);
                startActivity(intent4);
                break;
            case R.id.but5:
                Intent intent5=new Intent(MainActivity.this, PullAndRecycleActivity.class);
                startActivity(intent5);
                break;
            case R.id.but6:
                Intent intent6=new Intent(MainActivity.this, VideoViewActivity.class);
                startActivity(intent6);
                break;
            case R.id.but7:
                Intent intent7=new Intent(MainActivity.this, CutImageActivity.class);
                startActivity(intent7);
                break;
            case R.id.but8:
                Intent intent8=new Intent(MainActivity.this, PlaceChooseActivity.class);
                startActivity(intent8);
                break;
        }

    }
}
