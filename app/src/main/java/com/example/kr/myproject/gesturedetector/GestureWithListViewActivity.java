package com.example.kr.myproject.gesturedetector;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GestureWithListViewActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{

    @InjectView(R.id.list1)
    ListView list1;
    @InjectView(R.id.list2)
    ListView list2;
    List<String> list;
    ViewFlipper flipper; //一次显示一个子view
    GestureDetector detector; //手势监测器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gesture_with_list_view);
        ButterKnife.inject(this);
        detector = new GestureDetector(this);
        flipper = (ViewFlipper) findViewById(R.id.vf_flipper);
        flipper.setOnTouchListener(this);
        list=new ArrayList<String>();
        int i=0;
        while (i<10){
            String item="item";
            list.add(item+i);
            i++;
        }
        list1.setAdapter(new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list));
        list2.setAdapter(new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list));
    }
    /*以下为 OnGestureListener 的方法*/
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        Log.d("onTouch", "onFling事件触发");
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

