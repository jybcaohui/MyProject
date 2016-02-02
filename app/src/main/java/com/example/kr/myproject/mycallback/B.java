package com.example.kr.myproject.mycallback;

/**
 * Created by KR on 2016/1/18.
 */
public class B implements CallBack {
    @Override
    public void method() {
        System.out.println("当程序a调用了method方法时我就会答应出来");
    }

}
