package com.example.kr.myproject.dicegame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	static int screen_width;		//设备屏幕的宽
	static int screen_height;		//设备屏幕的高
	int Dice_x;
	int Dice_y;
	SurfaceHolder holder;	//取得surfaceView的控制类的引用
	Thread th;		//取得线程类的引用
	boolean flag;		//线程执行的标志位
	boolean isRun;		//控制骰子的投掷
	Canvas canvas = null;
	Dice dice;	//骰子的引用
	private int sleepSpan = 150;		//线程休眠时间30
	int i;		//用于循环的
	Handler mHandler;

	public MySurfaceView(Context context,  AttributeSet attr) {
		super(context, attr);
		dice  = new Dice(this.getResources());
		holder = this.getHolder();		//取得控制类
		holder.addCallback(this);		//取得surfaceView的监视器
		th = new Thread(this);		//实例化线程
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();		//取得屏幕宽度
		screen_height = this.getHeight();	//取得屏幕高度
		Dice_x =(int)screen_width/2 - (int)dice.avgWidth/2;		//计算骰子的X位置
		Dice_y =(int)screen_height/3 - (int)dice.diceHeight/2;		//计算骰子的Y位置
//		Dice_x = 300;
//		Dice_y =200;
		System.out.println("screen_width=" + screen_width + ", screen_height=" + screen_height + ", Dice_x=" + Dice_x + ", Dice_y=" + Dice_y);
		flag = true;	//给flag设置初始值
		th.start();	//启动线程
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	public void run(){
		while(flag){
			if(isRun){	//用判断语句控制是否进入骰子的投掷过程
				for(i = 1; i <= 20; i++){		//随机取骰子值10次，完成投掷，建立此循环的目的是为了让线程持续执行，让投掷可控，
					canvas = holder.lockCanvas();		//取得surfaceView的canvas
					dice.playDice(canvas, Dice_x, Dice_y);	//随机投掷骰子
					if(canvas != null){
						holder.unlockCanvasAndPost(canvas);	//释放holder的画布
					}
				}
				isRun = false;
			}
		}
		try{
			Thread.sleep(sleepSpan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
