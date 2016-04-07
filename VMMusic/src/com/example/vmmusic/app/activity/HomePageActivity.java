package com.example.vmmusic.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.HomeViewPagerAdapter;
import com.example.vmmusic.app.fragment.ChippendaleFragment;
import com.example.vmmusic.app.utils.T;

import java.util.ArrayList;

/**
 * 主页面
 * Created by awx19 on 2016/4/7.
 */
public class HomePageActivity extends FragmentActivity {
    /**
     * fragment管理器
     */
    FragmentManager fragmentManager = getSupportFragmentManager();
    /**
     * ViewPager适配器
     */
    HomeViewPagerAdapter homeViewPagerAdapter;
    ArrayList<Fragment> list = new ArrayList<>();
    /**
     * 音乐馆fragment
     */
    ChippendaleFragment chippendaleFragment = new ChippendaleFragment();
    ViewPager viewPager;

    /**
     * 底部菜单按钮
     */
    RadioGroup public_radio_group;// 底部菜单
    RadioButton chippendale_btn;//音乐馆
    RadioButton special_btn;//专题
    TextView add_btn;//添加音乐
    RadioButton channel_btn;//频道
    RadioButton mine_btn;//我的

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
        public_radio_group = (RadioGroup) findViewById(R.id.public_radio_group);
        chippendale_btn = (RadioButton) findViewById(R.id.chippendale_btn);
        add_btn = (TextView) findViewById(R.id.add_btn);
        special_btn = (RadioButton) findViewById(R.id.special_btn);
        channel_btn = (RadioButton) findViewById(R.id.channel_btn);
        mine_btn = (RadioButton) findViewById(R.id.mine_btn);
        public_radio_group.setOnCheckedChangeListener(onCheckedChangeListener);
        add_btn.setOnClickListener(onClickListener);


        list.add(chippendaleFragment);// 音乐馆


        homeViewPagerAdapter = new HomeViewPagerAdapter(fragmentManager, list);
        viewPager.setAdapter(homeViewPagerAdapter);
        viewPager.setOnPageChangeListener(onPageChangeListener);
    }

    /**
     * ViewPager选择监听
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    chippendale_btn.setChecked(true);
                    break;
                case 1:
                    special_btn.setChecked(true);
                    break;
                case 2:
                    channel_btn.setChecked(true);
                    break;
                case 3:
                    mine_btn.setChecked(true);
                    break;
                default://默认情况下推荐RadioButton选中
                    chippendale_btn.setChecked(true);
                    special_btn.setChecked(false);
                    channel_btn.setChecked(false);
                    mine_btn.setChecked(false);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    /**
     * 添加音乐监听
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    /**
     * 菜单切换监听
     */

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {
            // TODO Auto-generated method stub
            switch (arg1) {
                case R.id.chippendale_btn:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.special_btn:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.channel_btn:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.mine_btn:
                    viewPager.setCurrentItem(3);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 按两次返回键退出程序
     */
    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                T.showShort(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
