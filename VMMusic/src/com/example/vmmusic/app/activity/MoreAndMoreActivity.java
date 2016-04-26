package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.RoundImageView;
import com.example.vmmusic.app.utils.HttpUtils;
import com.example.vmmusic.app.utils.TopSettiings;

/**
 * 更多
 * Created by awx19 on 2016/4/11.
 */
public class MoreAndMoreActivity extends Activity {
    RoundImageView more_img;//用户头像
    TextView more_name;//用户名
    TextView more_vip;//会员
    TextView more_fans;//粉丝
    TextView more_attention;//关注
    TextView more_voice;//声音
    private TopSettiings topSettiings;
    TextView more_theme;//主题
    TextView more_collect;//收藏
    TextView more_setting;//设置
    TextView more_about_vm;//关于VM
    TextView more_update;//版本更新
    TextView more_login_out;//退出登录
    private final static String HTTP="more";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_and_more);
        initView();
        topSetting();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        topSettiings=new TopSettiings(this);
        topSettiings.setTitle("更多");
        TextView topLeft=topSettiings.setLeft(null, getResources().getDrawable(R.drawable.back),false);
      
        more_img = (RoundImageView) findViewById(R.id.more_img);
        more_name = (TextView) findViewById(R.id.more_name);
        more_vip = (TextView) findViewById(R.id.more_vip);
        more_fans = (TextView) findViewById(R.id.more_fans);
        more_attention = (TextView) findViewById(R.id.more_attention);
        more_voice = (TextView) findViewById(R.id.more_voice);
        more_theme = (TextView) findViewById(R.id.more_theme);
        more_collect = (TextView) findViewById(R.id.more_collect);
        more_setting = (TextView) findViewById(R.id.more_setting);
        more_about_vm = (TextView) findViewById(R.id.more_about_vm);
        more_update = (TextView) findViewById(R.id.more_update);
        more_login_out = (TextView) findViewById(R.id.more_login_out);
        more_theme.setOnClickListener(onClickListener);
        more_collect.setOnClickListener(onClickListener);
        more_setting.setOnClickListener(onClickListener);
        more_about_vm.setOnClickListener(onClickListener);
        more_update.setOnClickListener(onClickListener);
        more_login_out.setOnClickListener(onClickListener);
        topLeft.setOnClickListener(onClickListener);


    }

    /**
     * 顶部设置
     */
    private void topSetting() {
        TopSettiings topSettiings = new TopSettiings(this);
        topSettiings.setTitle("更多");
        topSettiings.getTextRight().setOnClickListener(onClickListener);
    }

    /**
     * 监听
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.public_top_right:
                	Intent intent=new Intent(MoreAndMoreActivity.this,MusicLyricPlayActivity.class);
                	startActivity(intent);
                    break;
                case R.id.public_top_left:
                	finish();
                    break;
                case R.id.more_theme://主题
                    break;
                case R.id.more_collect://收藏
                    break;
                case R.id.more_setting://设置
                    break;
                case R.id.more_about_vm://关于VM
                    break;
                case R.id.more_update://更新
                    break;
                case R.id.more_login_out://退出登录
                	System.exit(0);
                    break;


            }
        }
    };
    
    class MyTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpUtils httpUtils=new HttpUtils();
			String result=httpUtils.NewpostData(HTTP, null);
			return result;
		}
    	
    }
}
