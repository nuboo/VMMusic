package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 * Created by awx19 on 2016/4/6.
 */
public class GuidePage extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    //引导图片资源
    private final int[] pics = {R.drawable.whatsnew_00,
            R.drawable.whatsnew_01, R.drawable.whatsnew_02};

    //底部小店图片
    private ImageView[] dots;

    //记录当前选中位置
    private int currentIndex;

    TextView title;
    TextView text;
    TextView done;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        title = (TextView) findViewById(R.id.guide_title);
        text = (TextView) findViewById(R.id.guide_text);

        done=(TextView)findViewById(R.id.guide_done);//底部文字
        views = new ArrayList<View>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


     /*   // 停留2秒跳转到主页面
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Intent intent = new Intent(GuidePage.this, RegisterLoginActivity.class);
                startActivity(intent);
                finish();
            }

        }).start();*/



        //初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        vp = (ViewPager) findViewById(R.id.view_pager);
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.setOnPageChangeListener(this);
        //初始化底部小点
        initDots();

    }

    private void initDots() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guide_linear);
        dots = new ImageView[pics.length];
        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) linearLayout.getChildAt(i);
            dots[i].setEnabled(true);//都设为true
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置选中状态
    }

    /**
     * 设置当前页面的文字
     *
     * @param position
     */
    private void setText(int position) {
        if (position == 0) {
            title.setText("VM微享音乐");
            text.setText("这是一个分享声音的App拒绝枯燥乏味的文字心情，用音乐来表达你的内心吧");
        } else if (position == 1) {
            title.setText("微享30s");
            text.setText("30s的微声音剪辑，分享你最爱的片段，会员加时剪辑");
        } else {
            title.setText("VM钻石会员");
            text.setText("高品质音乐和无损音乐海量下载，微声音由30s增加到45s-60s");
        }


    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);

    }

    /**
     * 这只当前引导小点的选中
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = positon;
        if(positon==(pics.length-1)){//设置底部文字
          
            done.setTextColor(getResources().getColor(R.color.green));
      
            done.setText("立 即 体 验");
            done.setTextSize(20f);
            done.setGravity(Gravity.CENTER);
            done.setOnClickListener(listener);
        }
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);
        //设置文字变换
        setText(arg0);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);

    }


    View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //跳转登陆注册页面  并且设置sharedprefenrece不为第一次登陆
            SharedPreferences sp=getApplicationContext().getSharedPreferences(FirstPage.IS_FIRST, MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean(FirstPage.IS_FIRST,false);
            editor.commit();
            Intent intent=new Intent(GuidePage.this,RegisterLoginActivity.class);
            startActivity(intent);
            finish();
        }
    };
}

