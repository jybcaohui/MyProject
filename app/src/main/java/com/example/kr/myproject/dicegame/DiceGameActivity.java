package com.example.kr.myproject.dicegame;

import android.app.Service;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

public class DiceGameActivity extends BaseActivity implements View.OnClickListener,
        SensorEventListener {

    Button play, reset;
    MySurfaceView view;
    Dice dice;
    Canvas canvas;

    SensorManager sensorManager = null;		//传感器管理器
    Vibrator vibrator = null;		//振动传感器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dice_game);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);		//取得传感器管理器的实例
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);

        view = (MySurfaceView)findViewById(R.id.view);
        dice = new Dice(getResources());
        play = (Button)findViewById(R.id.play);
        reset = (Button)findViewById(R.id.reset);
        canvas = new Canvas();

        play.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v == play){
            view.i = 1;
            view.isRun = true;
        }else if(v == reset){
            canvas = view.holder.lockCanvas();
            dice.DrawDice(canvas, view.Dice_x, view.Dice_y);
            view.holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        int sensorType = event.sensor.getType();		//得到传感器的类型
        //values的值，values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if(sensorType == Sensor.TYPE_ACCELEROMETER){		//如果传感器类型是加速度计
            if(Math.abs(values[0])> 12
                    || Math.abs(values[1]) > 12
                    || Math.abs(values[2] )> 12){
                vibrator.vibrate(300);
                view.isRun = true;
                System.out.println("-----摇一摇-----");
            }
        }
    }
}