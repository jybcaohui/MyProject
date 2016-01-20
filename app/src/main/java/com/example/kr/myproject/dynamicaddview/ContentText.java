package com.example.kr.myproject.dynamicaddview;

/**
 * Created by KR on 2015/11/30.
 */
public class ContentText {
    private int type;//0:文字；1:图片
    private String text;

    public ContentText(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
