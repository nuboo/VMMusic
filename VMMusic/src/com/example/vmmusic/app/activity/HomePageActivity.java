package com.example.vmmusic.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.HomeViewPagerAdapter;
import com.example.vmmusic.app.fragment.ChannelFragment;
import com.example.vmmusic.app.fragment.ChippendaleFragment;
import com.example.vmmusic.app.fragment.MineFragment;
import com.example.vmmusic.app.fragment.SpecialFragment;
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
    ArrayList<Fragment> list = new ArrayList<Fragment>();
    /**
     * fragment
     */
    ChippendaleFragment chippendaleFragment = new ChippendaleFragment();
    SpecialFragment specialFragment = new SpecialFragment();
    ChannelFragment channelFragment = new ChannelFragment();
    MineFragment mineFragment = new MineFragment();
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
        list.add(specialFragment);//专题
        list.add(channelFragment);//频道
        list.add(mineFragment);//我的
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
     * 监听弹出Dialog
     */

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog dialog = new Dialog(HomePageActivity.this, R.style.Transparent);
            dialog.setContentView(R.layout.dielog_upload_search);
            WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();
            setParams(lay);
            dialog.show();
            dialogInitView(dialog);
        }
    };

    /**
     * 初始化弹出框里面的控件
     *
     * @param dialog
     */
    private void dialogInitView(Dialog dialog) {
        TextView dialog_upload = (TextView) dialog.findViewById(R.id.dialog_upload);
        TextView dialog_search = (TextView) dialog.findViewById(R.id.dialog_search);
        TextView dialog_cancel = (TextView) dialog.findViewById(R.id.dialog_cancel);
        dialog_upload.setOnClickListener(new Listener(dialog));
        dialog_search.setOnClickListener(new Listener(dialog));
        dialog_cancel.setOnClickListener(new Listener(dialog));
    }

    /**
     * 弹出框监听
     */
    class Listener implements View.OnClickListener {
        Dialog mDialog;

        public Listener(Dialog dialog) {
            mDialog = dialog;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_upload:
                	 Intent upload = new Intent(HomePageActivity.this, UploadActivity.class);
                     startActivity(upload);
                    break;
                case R.id.dialog_search:
                    Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.dialog_cancel:
                    mDialog.dismiss();
                    break;
            }
        }

    }

    /**
     * 设置控件宽高
     *
     * @param lay
     */
    private void setParams(WindowManager.LayoutParams lay) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect rect = new Rect();
        View view = getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(rect);
        lay.height = dm.heightPixels - rect.top;
        lay.width = dm.widthPixels;
    }

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
