package com.example.kr.myproject.edittextwhitimageandclick;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditTextActivity extends BaseActivity {

    @InjectView(R.id.edt)
    EditText edt;
    @InjectView(R.id.edt2)
    EditText edt2;
    @InjectView(R.id.edt3)
    EditText edt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        ButterKnife.inject(this);
        setPrice("#lable1#", "#lable2#");
        addImg();
        clickSpan();
    }
    public void setPrice(String m, String n) {
        String one = "共需";
        String two = m + "橙币";
        String three = ",开通后主播将获得丰厚收入，同时您的财富等级经验增长";
        String four = n + "点";
        String five = "。。。";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(one);
        spannableStringBuilder.append(two);
        spannableStringBuilder.append(three);
        spannableStringBuilder.append(four);
        spannableStringBuilder.append(five);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.red)),
                one.length(), one.length() + two.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.red)),
                one.length() + two.length() + three.length(), one.length() + two.length() + three.length() + four.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edt.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
    }

    public void addImg(){
        Drawable drawable = getResources().getDrawable(R.drawable.ani);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        SpannableString spannable = new SpannableString(edt2.getText()
                .toString() + "[smile]");
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(span, edt2.getText().length(),
                edt2.getText().length() + "[smile]".length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        edt2.setText(spannable);
    }

//    public void clickSpan(){
//        SpannableString spStr = new SpannableString("测试可点击的EditText");
//        RevoClickSpan clickSpan = new RevoClickSpan(this,spStr) ;
//        spStr.setSpan(clickSpan, 0, spStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        edt3.append(spStr);
//        edt3.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
//    }

    public void clickSpan(){
        SpannableString spStr = new SpannableString("#测试可点击的EditText#");
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                toast("测试可点击的EditText");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.RED);//设置连接的文本颜色
                ds.setUnderlineText(false); //去掉下划线
            }
        };
        spStr.setSpan(clickSpan, 0, spStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        edt3.append(spStr);
        edt3.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
    }

}
