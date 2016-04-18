package com.example.vmmusic.app.listener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.vmmusic.R;
import com.example.vmmusic.app.activity.RegisterLoginActivity;
import com.example.vmmusic.app.utils.AndroidShare;
import com.example.vmmusic.app.utils.T;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by awx19 on 2016/4/18.
 */
public class ChippendaleListListenter implements View.OnClickListener {

    Context mContext;
    Bundle mBundle;

    public ChippendaleListListenter(Context context, Bundle bundle) {
        mContext = context;
        mBundle = bundle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_chippendale_list_collection://收藏
                break;
            case R.id.item_chippendale_list_thumb_up://赞
                break;
            case R.id.item_chippendale_list_comments://评论
                break;
            case R.id.item_chippendale_list_share://分享
                AndroidShare as = new AndroidShare(mContext, "哈哈---超方便的分享！！！来自allen", "http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
                as.show();
                break;
        }
    }


}
