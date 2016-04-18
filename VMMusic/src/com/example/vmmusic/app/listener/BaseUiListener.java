package com.example.vmmusic.app.listener;

import android.content.Context;

import com.example.vmmusic.app.utils.T;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by awx19 on 2016/4/15.
 */
public class BaseUiListener implements IUiListener {
    Context mContext;

    public BaseUiListener(Context context) {
        mContext = context;
    }

    @Override
    public void onComplete(Object response) {
        if (null == response) {
            T.showShort(mContext, "返回为空,登录失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            T.showShort(mContext, "返回为空,登录失败");
            return;
        }
        T.showShort(mContext, response.toString() + "登录成功");
        doComplete((JSONObject) response);
    }

    protected void doComplete(JSONObject values) {

    }

    /**
     * 错误
     *
     * @param e
     */
    @Override
    public void onError(UiError e) {
        T.showShort(mContext, "onError: " + e.errorDetail);

    }

    /**
     * 取消
     */
    @Override
    public void onCancel() {
        T.showShort(mContext, "onCancel: ");

    }
}