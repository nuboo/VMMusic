
package com.example.vmmusic.app.share;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.vmmusic.app.activity.RegisterLoginActivity;
import com.example.vmmusic.app.utils.Util;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * qq空间分享
 */
public class QZoneShare {
    Activity activity;

    public QZoneShare(Context context) {
        activity = (Activity) context;
    }

    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            Util.toastMessage(activity, "onCancel:test ");
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(activity, "onError: " + e.errorMessage, "e");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(activity, "onComplete: " + response.toString());
        }

    };

    /**
     * 用异步方式启动分享
     *
     * @param params
     */
    public void doShareToQzone(final Bundle params) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != RegisterLoginActivity.mTencent) {
                    RegisterLoginActivity.mTencent.shareToQzone(activity, params, qZoneShareListener);
                }
            }
        });
    }
}
