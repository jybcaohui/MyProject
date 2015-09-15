package com.example.kr.myproject.animationtest;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class XmlForAnimationActivity extends BaseActivity {

    @InjectView(R.id.onlyone)
    Button butone;
    @InjectView((R.id.plus))
    Button buttwo;
    @InjectView((R.id.textbut))
    Button textbut;
    @InjectView(R.id.image)
    ImageView img;
    @InjectView(R.id.txt)
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_for_animation);
        ButterKnife.inject(this);
        butone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(XmlForAnimationActivity.this, R.anim.alpha);
                img.startAnimation(animation);
            }
        });
        buttwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(XmlForAnimationActivity.this, R.anim.star_ani);
                img.startAnimation(animation);
            }
        });
        textbut.setOnClickListener(new TranslateListener());
    }
    //移动
    class TranslateListener implements View.OnClickListener {
        AnimationSet animationSet;
        public void onClick(View view) {
            // 创建一个AnimationSet对象，参数为Boolean型。
            //true表示试用Animation的interpolator，false则是试用自己的
            animationSet = new AnimationSet(true);
            //参数1~2   x轴开始位置
            // 3~4      x轴结束位置
            //5~6       y轴开始位置
            //7~8       y轴结束位置
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1f,//RELATIVE_TO_SELF以自身中心为原点
                    Animation.RELATIVE_TO_PARENT, 1f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            //设置动画执行的时间
            translateAnimation.setDuration(5000);
            translateAnimation.setRepeatCount(3);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    txt.setText("TextView设置动画");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    txt.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    txt.setText("循环播放动画");
                }
            });
            animationSet.addAnimation(translateAnimation);
            //使用ImageView的startAnimation方法执行动画
            txt.startAnimation(animationSet);

        }
    }


}
