package com.example.kr.myproject.mycallback;

/**
 * Created by KR on 2016/1/18.
 */
public class A {
    public CallBack mCallBack;

    public void setCallfuc(CallBack mc){
        this.mCallBack= mc;
    }
    public void call(){
        this.mCallBack.method();
    }
}
