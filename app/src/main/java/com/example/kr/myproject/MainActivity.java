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
import android.widget.EditText;

import com.example.kr.myproject.analyhtml.AnalyHtmlActivity;
import com.example.kr.myproject.animationtest.AnimationActivity;
import com.example.kr.myproject.dicegame.DiceGameActivity;
import com.example.kr.myproject.edittextwhitimageandclick.EditTextActivity;
import com.example.kr.myproject.filemanager.FileManagerActivity;
import com.example.kr.myproject.flowlayout.FlowLayoutActivity;
import com.example.kr.myproject.fragmentswithtab.ActivityWithFragments;
import com.example.kr.myproject.gesturedetector.GestureDetectorActivity;
import com.example.kr.myproject.imageoprate.ChangeImageActivity;
import com.example.kr.myproject.keybordlayout.KeybordActivity;
import com.example.kr.myproject.myshare.ShareActivity;
import com.example.kr.myproject.myspinner.MySpinnerActivity;
import com.example.kr.myproject.myvideoplayer.VideoViewActivity;
import com.example.kr.myproject.placechoose.PlaceChooseActivity;
import com.example.kr.myproject.pulltorefereshandrecycleview.PullAndRecycleActivity;
import com.example.kr.myproject.qqtwowayslidingmenu.QQMenuActivity;
import com.example.kr.myproject.slidingmenu.SlidingMenuActivity;
import com.example.kr.myproject.weixinheadimagecut.CutImageActivity;
import com.example.kr.myproject.weixinmainpage.WeiXinPageActivity;
import com.example.kr.myproject.yanzhengma.IdentifyingCodeActivity;


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
    @InjectView(R.id.but9)
    Button but9;
    @InjectView(R.id.but10)
    Button but10;
    @InjectView(R.id.but11)
    Button but11;
    @InjectView(R.id.but12)
    Button but12;
    @InjectView(R.id.but13)
    Button but13;
    @InjectView(R.id.but14)
    Button but14;
    @InjectView(R.id.but15)
    Button but15;
    @InjectView(R.id.but16)
    Button but16;
    @InjectView(R.id.but17)
    Button but17;
    @InjectView(R.id.but18)
    Button but18;
    @InjectView(R.id.but19)
    Button but19;
    @InjectView(R.id.but20)
    Button but20;
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
        but9.setOnClickListener(this);
        but10.setOnClickListener(this);
        but11.setOnClickListener(this);
        but12.setOnClickListener(this);
        but13.setOnClickListener(this);
        but14.setOnClickListener(this);
        but15.setOnClickListener(this);
        but16.setOnClickListener(this);
        but17.setOnClickListener(this);
        but18.setOnClickListener(this);
        but19.setOnClickListener(this);
        but20.setOnClickListener(this);
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
            case R.id.but9:
                Intent intent9=new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent9);
                break;
            case R.id.but10:
                Intent intent10=new Intent(MainActivity.this, EditTextActivity.class);
                startActivity(intent10);
                break;
            case R.id.but11:
                Intent intent11=new Intent(MainActivity.this, GestureDetectorActivity.class);
                startActivity(intent11);
                break;
            case R.id.but12:
                Intent intent12=new Intent(MainActivity.this, DiceGameActivity.class);
                startActivity(intent12);
                break;
            case R.id.but13:
                Intent intent13=new Intent(MainActivity.this, ShareActivity.class);
                startActivity(intent13);
                break;
            case R.id.but14:
                Intent intent14=new Intent(MainActivity.this, FlowLayoutActivity.class);
                startActivity(intent14);
                break;
            case R.id.but15:
                Intent intent15=new Intent(MainActivity.this, AnalyHtmlActivity.class);
                startActivity(intent15);
                break;
            case R.id.but16:
                Intent intent16=new Intent(MainActivity.this, ChangeImageActivity.class);
                startActivity(intent16);
                break;
            case R.id.but17:
                Intent intent17=new Intent(MainActivity.this, MySpinnerActivity.class);
                startActivity(intent17);
                break;
            case R.id.but18:
                Intent intent18=new Intent(MainActivity.this, FileManagerActivity.class);
                startActivity(intent18);
                break;
            case R.id.but19:
                Intent intent19=new Intent(MainActivity.this, KeybordActivity.class);
                startActivity(intent19);
                break;
            case R.id.but20:
                Intent intent20=new Intent(MainActivity.this, IdentifyingCodeActivity.class);
                startActivity(intent20);
                break;
            default:
                break;
        }

    }
}
