package com.example.kr.myproject.edittextwhitimageandclick;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * Created by KR on 2015/9/15.
 */
public class RevoClickSpan extends ClickableSpan {
    Context mContext;
    SpannableString mId;
    @Override
    public void onClick(View widget) {
        Toast.makeText(mContext,mId,Toast.LENGTH_SHORT).show();
    }

    public RevoClickSpan(Context con,  SpannableString id) {
        super();
        this.mContext = con;
        this.mId = id;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.RED);//设置连接的文本颜色
        ds.setUnderlineText(false); //去掉下划线
    }
}