package com.example.kr.myproject.pulltorefereshandrecycleview;

/**
 * Created by KR on 2015/9/10.
 */
public class ItemInfo {

    public ItemInfo(int id, String txt) {
        this.id = id;
        this.txt = txt;
    }

    private int id;
    private String txt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
