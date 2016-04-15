package com.example.vmmusic.app.utils;

import android.app.Application;

/**
 * Created by awx19 on 2016/4/14.
 */
public class App extends Application {
    /**
     * 网络请求地址
     */
    public String url = " http://192.168.15.247:90/home/api/";
    /**
     * QQ互联app id
     */
    public String APP_ID = "1105262489";

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
