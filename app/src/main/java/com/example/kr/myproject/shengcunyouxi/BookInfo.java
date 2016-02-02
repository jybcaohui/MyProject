package com.example.kr.myproject.shengcunyouxi;

/**
 * Created by KR on 2016/1/22.
 */
public class BookInfo {
    private int id;
    private String bookName;
    private String bookIntro;
    private String authName;
    private int subNum;     //订阅
    private int uvNum;    //UV
    private int integral; // 积分
    private String cover;

    public BookInfo(int id, String bookName, String bookIntro, String authName, int subNum, int uvNum, int integral, String cover) {
        this.id = id;
        this.bookName = bookName;
        this.bookIntro = bookIntro;
        this.authName = authName;
        this.subNum = subNum;
        this.uvNum = uvNum;
        this.integral = integral;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public int getSubNum() {
        return subNum;
    }

    public void setSubNum(int subNum) {
        this.subNum = subNum;
    }

    public int getUvNum() {
        return uvNum;
    }

    public void setUvNum(int uvNum) {
        this.uvNum = uvNum;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
