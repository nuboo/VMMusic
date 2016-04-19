package com.example.vmmusic.app.listener;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.vmmusic.app.utils.AccessTokenKeeper;
import com.example.vmmusic.app.utils.T;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 *
 *
 *
 * Created by awx19 on 2016/4/18.
 */

/**
 * 微博认证授权回调类。
 */
public class AuthListener implements WeiboAuthListener {
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    Context mContext;

    public AuthListener(Context context) {
        mContext = context;
    }

    @Override
    public void onComplete(Bundle values) {
        // 从 Bundle 中解析 Token
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {
            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
            T.showShort(mContext, "授权成功");
        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = values.getString("code");
            String message = "授权失败";
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            T.showShort(mContext, message);
        }
    }

    @Override
    public void onCancel() {
        T.showShort(mContext, "取消授权");
    }

    @Override
    public void onWeiboException(WeiboException e) {
        T.showShort(mContext, "Auth exception : " + e.getMessage());
    }
}