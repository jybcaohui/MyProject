package com.example.kr.myproject;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by hui.cao on 2016/3/2.
 */
public class MyProjectApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
