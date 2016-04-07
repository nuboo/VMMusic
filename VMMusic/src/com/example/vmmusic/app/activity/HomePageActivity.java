package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.vmmusic.R;

/**
 * 主页面
 * Created by awx19 on 2016/4/7.
 */
public class HomePageActivity extends Activity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
    }
}
