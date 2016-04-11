package com.example.vmmusic.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 *
 * 主页ViewPager adapter
 * Created by awx19 on 2016/4/7.
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public HomeViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {

        super(fm);
        this.list = list;

    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return (list == null || list.size() == 0) ? null : list.get(arg0);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

}
