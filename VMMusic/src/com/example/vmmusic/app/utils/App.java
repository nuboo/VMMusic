package com.example.vmmusic.app.utils;

import android.app.Application;

/**
 *
 *
 * Created by awx19 on 2016/4/14.
 */
public class App extends Application {
	
	
	/**
     * 网络请求地址
     */
    public  static final String url = " http://192.168.15.247:90/api/";

    
   
    /**
     * QQ互联app id
     */
    public String APP_ID = "1105262489";
    /**
     * 微博 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY
     */
    public String APP_KEY = "1513151699";

    /**
     * 回调页
     */
    public String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p/>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p/>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p/>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    @Override
    public void onCreate() {
        super.onCreate();
    }

}
