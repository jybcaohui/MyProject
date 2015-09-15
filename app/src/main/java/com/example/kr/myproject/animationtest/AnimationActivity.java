package com.example.kr.myproject.animationtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnimationActivity extends BaseActivity {

    @InjectView(R.id.rotatebut)
    Button rotate;
    @InjectView(R.id.scalebut)
    Button scale;
    @InjectView(R.id.alphabut)
    Button alpha;
    @InjectView(R.id.translatebut)
    Button translate;
    @InjectView(R.id.but)
    Button but;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.but2)
    Button but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.inject(this);
        rotate.setOnClickListener(new RotateListener());
        scale.setOnClickListener(new ScaleListener());
        alpha.setOnClickListener(new AlphaListener());
        translate.setOnClickListener(new TranslateListener());
        but.setOnClickListener(new totalListener());
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AnimationActivity.this,XmlForAnimationActivity.class);
                startActivity(intent);
            }
        });
    }

    //淡入淡出
    class AlphaListener implements View.OnClickListener {
        public void onClick(View view) {
            // 创建一个AnimationSet对象，参数为Boolean型。
            //true表示试用Animation的interpolator，false则是试用自己的
            AnimationSet animationSet = new AnimationSet(true);
            //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            //设置动画执行的时间
            alphaAnimation.setDuration(1000);
            //将alphaAnimation对象添加到AnimationSet当中
            animationSet.addAnimation(alphaAnimation);
            //使用ImageView的startAnimation方法执行动画
            image.startAnimation(animationSet);
        }
    }

    //旋转
    class RotateListener implements View.OnClickListener {
        public void onClick(View view) {
            // 创建一个AnimationSet对象，参数为Boolean型。
            //true表示试用Animation的interpolator，false则是试用自己的
            AnimationSet animationSet = new AnimationSet(true);
            //参数1：从哪个角度开始旋转
            //参数2：转到什么角度
            //后4个参数用于设置围绕者旋转的圆心在哪
            //3:确定x轴坐标的类型，有Absolute绝对坐标、Relative_To_Self相对于自身坐标
            // Relative_TO_Parent相对于父组件的坐标
            //4:x轴的值，0.5f表明是以自身这个控件的一般长度为x轴
            //5：y坐标类型
            //6：y轴的值，0.5f表明是以自身这个控件的一般长度为x轴
            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f
                    , Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(1000);
            animationSet.addAnimation(rotateAnimation);
            //使用ImageView的startAnimation方法执行动画
            image.startAnimation(animationSet);
        }
    }

    //缩放
    class ScaleListener implements View.OnClickListener {
        public void onClick(View view) {
            // 创建一个AnimationSet对象，参数为Boolean型。
            //true表示试用Animation的interpolator，false则是试用自己的
            AnimationSet animationSet = new AnimationSet(true);

            //参数1：x轴初始值
            //参数2：x轴收缩后的值
            //参数3：y轴初始值
            //参数4：y轴收缩后的值
            //参数5：确定x轴坐标的类型
            //参数6：x轴的值，0.5表示以自身一半长做x轴
            //参数7：确定y轴的值
            //参数8：y轴的值，0.5f表明是以自身一半的长度为y轴
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 4f, 1, 4f, Animation.RELATIVE_TO_SELF, 0.5f
                    , Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(2000);
            //将alphaAnimation对象添加到AnimationSet当中
            animationSet.addAnimation(scaleAnimation);
            //使用ImageView的startAnimation方法执行动画
            image.startAnimation(animationSet);
        }
    }

    //移动
    class TranslateListener implements View.OnClickListener {
        public void onClick(View view) {
            // 创建一个AnimationSet对象，参数为Boolean型。
            //true表示试用Animation的interpolator，false则是试用自己的
            AnimationSet animationSet = new AnimationSet(true);
            //参数1~2   x轴开始位置
            // 3~4      x轴结束位置
            //5~6       y轴开始位置
            //7~8       y轴结束位置
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,//RELATIVE_TO_SELF以自身中心为原点
                    Animation.RELATIVE_TO_PARENT, -0.5f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            //设置动画执行的时间
            translateAnimation.setDuration(3000);
            //设置动画的变化速率
            translateAnimation.setInterpolator(new DecelerateInterpolator());

            //添加了这行代码的作用时，view移动的时候 会有弹性效果（颤动）
            translateAnimation.setInterpolator(new OvershootInterpolator());
            //将alphaAnimation对象添加到AnimationSet当中
            animationSet.addAnimation(translateAnimation);
            //使用ImageView的startAnimation方法执行动画
            image.startAnimation(animationSet);

        }
    }

    //综合
    class totalListener implements View.OnClickListener {
        public void onClick(View view) {
            // 创建一个AnimationSet对象，参数为Boolean型。
            //true表示试用Animation的interpolator，false则是试用自己的
            AnimationSet animationSet = new AnimationSet(true);

            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            //设置动画执行的时间
            alphaAnimation.setDuration(4000);
            //将alphaAnimation对象添加到AnimationSet当中
            animationSet.addAnimation(alphaAnimation);

            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f
                    , Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(4000);
            animationSet.addAnimation(rotateAnimation);

//            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 0.1f, 0, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f
//                    , Animation.RELATIVE_TO_SELF, 0.5f);
//            scaleAnimation.setDuration(20);
//            将alphaAnimation对象添加到AnimationSet当中
//            animationSet.addAnimation(scaleAnimation);

            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, -0.5f,//RELATIVE_TO_PARENT以父组件中心为原点
                    Animation.RELATIVE_TO_PARENT, 0.5f,//0.5f表示距父组件中心（即原点）的距离为父组件长的一半
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f);
            //设置动画执行的时间
            translateAnimation.setDuration(4000);
            //将alphaAnimation对象添加到AnimationSet当中
            animationSet.addAnimation(translateAnimation);
//              //解决：只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置，导致点击时会先闪一下的问题
//            animationSet.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    int left = image.getLeft() + (int) (p2 - p1);
//                    int top = image.getTop();
//                    int width = image.getWidth();
//                    int height = image.getHeight();
//                    image.clearAnimation();  //必须在layout前清空，否则会出错
//                    image.layout(left, top, left + width, top + height);
//                }
//            });

            //使用ImageView的startAnimation方法执行动画
            image.startAnimation(animationSet);
//            animationSet.reset();//移动后图片回到原来位置
            animationSet.setFillAfter(true);//true移动后图片固定在结束位置，false移动后返回原位置
//            image.setVisibility(View.GONE);
        }
    }


}
