package com.example.kr.myproject.edittextwhitimageandclick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditTextActivity extends BaseActivity implements View.OnClickListener{

    //http://2711082222.blog.163.com/blog/static/106302249201232633333181/
    @InjectView(R.id.edt)
    EditText edt;
    @InjectView(R.id.edt2)
    EditText edt2;
    @InjectView(R.id.edt3)
    EditText edt3;
    @InjectView(R.id.edt4)
    EditText text4;
    @InjectView(R.id.but)
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        ButterKnife.inject(this);
        but.setOnClickListener(this);
        setPrice("#lable1#", "#lable2#");
//        addImg();
        clickSpan();
        clickSpanImg();
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
        edt2.append(spannable);
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
        edt3.append("\n");
        edt3.append(spStr);
        edt3.append("\n");
        edt3.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
    }

    public void clickSpanImg(){
        SpannableString spStr = new SpannableString("图片居中显示");
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                toast("图片居中显示");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.RED);//设置连接的文本颜色
                ds.setUnderlineText(false); //去掉下划线
            }
        };
        spStr.setSpan(clickSpan, 0, spStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        //第一张图片
        Drawable drawable = getResources().getDrawable(R.drawable.img);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        SpannableString spannable = new SpannableString(text4.getText()
                .toString() + "[pic]");
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(span, text4.getText()
                        .length(),
                text4.getText()
                        .length() + "[pic]".length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        text4.append("\n");
        text4.append(spannable);
        text4.append(spStr);

        //第二张图片
        Drawable drawable2 = getResources().getDrawable(R.drawable.close1);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(),
                drawable2.getIntrinsicHeight());
        SpannableString spannable2 = new SpannableString("[cancel]");
        ClickableImageSpan span2 = new ClickableImageSpan(drawable2, ImageSpan.ALIGN_BASELINE) {
            @Override
            public void onClick(View view) {
                toast("测试可点击的图片");

            }
        };
        spannable2.setSpan(span2, 0,
                "[cancel]".length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        text4.append(spannable2);
//        text4.append("\n");
        Log.d("text4---", text4.getText().toString());
        text4.setMovementMethod(ClickableMovementMethod.getInstance());//设置超链接为可点击状态
    }
    public void insertImage(String urltext){
        Drawable drawable = getResources().getDrawable(R.drawable.img);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        SpannableString spannable = new SpannableString(urltext);
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(span, 0,urltext.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        int index = Math.max(edt2.getSelectionStart(), 0);
//        spannable.setSpan(new ReplacementSpan() {
//
//            @Override
//            public int getSize(Paint paint, CharSequence text, int start, int end,
//                               Paint.FontMetricsInt fm) {
//                //最后一个参数为end-1，防止这个span最后与下一个字符之间有空格
//                if (fm != null) {
//                    paint.getFontMetricsInt(fm);
//                }
//                return (int) paint.measureText(text, start, end);
//            }
//
//            @Override
//            public void draw(Canvas canvas, CharSequence text, int start, int end,
//                             float x, int top, int y, int bottom, Paint paint) {
////                  String newText = "****" + text.toString().substring(start + 4, end);
//                paint.setUnderlineText(true);
//                canvas.drawText("****", 0, end - start, x, y, paint);
//            }
//        }, index, index + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        Log.d("edt2---", index + "---" + spannable.length() + "--" + index + spannable.length());
        Editable edit = edt2.getEditableText();//获取EditText的文字

        if (index < 0 || index >= edit.length() ){

            edit.append(spannable);

        }else{

            edit.insert(index,spannable);//光标所在位置插入文字

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but:
                if(edt2.isFocused()){
                    insertImage("urltext");
                }else{
                    Toast.makeText(this,"请把光标移动到edt2",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
