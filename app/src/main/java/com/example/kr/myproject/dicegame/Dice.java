package com.example.kr.myproject.dicegame;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.kr.myproject.R;

public class Dice {
	private Bitmap diceBitmap;		//骰子的图片引用
	private Bitmap tempBitmap;		//骰子的临时引用
//	private Bitmap tempBitmap1;
//	private Bitmap tempBitmap2;
//	private Bitmap tempBitmap3;
//	private Bitmap tempBitmap4;
//	private Bitmap tempBitmap5;
//	private Bitmap tempBitmap6;
	int diceWidth;		//骰子的宽（6个面的骰子在一张图上画）
	int diceHeight;		//骰子的高
	int avgWidth;		//骰子图片的平均宽度
	//	int x, y;		//骰子显示的坐标
	private int nowNum;		//骰子的当前值
	private Random random;		//获取随即对象
	Paint paint;		//画笔的引用

	public Dice(Resources res){
		paint = new Paint();
		paint.setAntiAlias(true);	//消除画笔的锯齿
		initBitmap(res);		//初始化骰子图片
//		diceWidth = tempBitmap1.getWidth();		//初始化图片宽度
//		diceHeight = tempBitmap1.getHeight();		//初始化图片高度
		diceWidth = diceBitmap.getWidth();		//初始化图片宽度
		diceHeight = diceBitmap.getHeight();		//初始化图片高度

		avgWidth = diceWidth / 6;		//初始化平均宽度
		nowNum = 0;
		random = new Random();
		System.out.println("diceWidth=" + diceWidth +", diceHeight" + diceHeight + ", avgWidth" + avgWidth);
//		x = MySurfaceView.screen_width/2 - avgWidth/2;
//		y = MySurfaceView.screen_height/3 - diceHeight/2;
	}

	public void initBitmap(Resources res){
		diceBitmap = BitmapFactory.decodeResource(res, R.drawable.dice);	//大图片，供截取

//		tempBitmap1=BitmapFactory.decodeResource(res, R.drawable.dice1);	//小图片可直接用
//		tempBitmap2=BitmapFactory.decodeResource(res, R.drawable.dice2);
//		tempBitmap3=BitmapFactory.decodeResource(res, R.drawable.dice3);
//		tempBitmap4=BitmapFactory.decodeResource(res, R.drawable.dice4);
//		tempBitmap5=BitmapFactory.decodeResource(res, R.drawable.dice5);
//		tempBitmap6=BitmapFactory.decodeResource(res, R.drawable.dice6);

	}

	private Bitmap getbitmap()	{	        //根据序号截取大图上相应骰子数图片
		return Bitmap.createBitmap(diceBitmap, avgWidth*(nowNum), 0, avgWidth, diceHeight);
	}

	public void playDice(Canvas canvas, int x, int y){		//随即绘制骰子
		nowNum = random.nextInt(6);	//取随机值：0，1，2，3，4，5
		tempBitmap = getbitmap();
//		nowNum = random.nextInt(5);
//		if(nowNum==0){
//			tempBitmap=tempBitmap1;
//		}else if(nowNum==1){
//			tempBitmap=tempBitmap2;
//		}else if(nowNum==2){
//			tempBitmap=tempBitmap3;
//		}else if(nowNum==3){
//			tempBitmap=tempBitmap4;
//		}else if(nowNum==4){
//			tempBitmap=tempBitmap5;
//		}else if(nowNum==5){
//			tempBitmap=tempBitmap6;
//		}
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(tempBitmap, x, y, paint);
	}

	public void DrawDice(Canvas canvas, int x, int y){		//绘制一个固定的骰子，即值为1的骰子，在投骰子的游戏开始前绘制
		tempBitmap = getbitmap();
		canvas.drawColor(Color.WHITE);
		System.out.println("----------------1-----------------");
		canvas.drawBitmap(tempBitmap,x, y, paint);
	}
}
