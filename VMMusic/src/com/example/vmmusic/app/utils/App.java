package com.example.vmmusic.app.utils;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by awx19 on 2016/4/14.
 */
public class App extends Application {
    public  static final String url = " http://192.168.15.247:90/home/api/";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("1513151699", "55a770643ebe03771207604800704843");
        //QQ空间
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        //Twitter
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
    }
}
