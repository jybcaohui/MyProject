package com.example.kr.myproject.gesturedetector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

public class GestureDetectorActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    ViewFlipper flipper; //一次显示一个子view
    GestureDetector detector; //手势监测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detector = new GestureDetector(this);
        setContentView(R.layout.activity_gesture_detector);
        flipper = (ViewFlipper) findViewById(R.id.vf_flipper);
        flipper.setOnTouchListener(this);
    }
    //整体屏幕的滑动事件
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return this.detector.onTouchEvent(event);//由检测器 执行 activity.<span style="font-family: Arial, Helvetica, sans-serif;">onTouchEvent</span>
//
//    }

    /*以下为 OnGestureListener 的方法*/
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        Log.d("onTouch","onFling事件触发");
        if (e1.getX() - e2.getX() > 120) {//向左滑，右边显示
            //this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            //this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper.showNext();
        }
        if (e1.getX() - e2.getX() < -120) {//向右滑，左边显示
            this.flipper.showPrevious();
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("onTouch","onDown事件触发");
        return true;
    }
    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("onTouch","onShowPress事件触发");

    }
    @Override //单击
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("onTouch","onSingleTapUp事件触发");
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("onTouch","onSingleTapUp事件触发");
        return false;
    }
    @Override // 长按
    public void onLongPress(MotionEvent e) {
        Log.d("onTouch","onLongPress事件触发");

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("onTouch","touch事件触发");
        return this.detector.onTouchEvent(event);
    }
}