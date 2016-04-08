package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.vmmusic.R;

/**  入口空白页 用于判断是否是第一次登陆，然后跳转首页或者引导页
 * Created by Administrator on 2016/4/8 0008.
 */
public class FirstPage  extends Activity{
    private Intent intent;
    private   boolean first;//是否第一次启动APP
    private boolean loginState;//是否已经登陆
    public static final String  IS_LOGIN="VMlogin";
    public static final String IS_FIRST="VMfirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        inni();
    }

    private void inni(){
        first=isFirst();
        loginState=isLogin();
        Log.i("first", first+"1");
        if(first){
            intent=new Intent(this,GuidePage.class);
            startActivity(intent);
        }else{
         /* if(loginState) {//如果已经登陆*/
             intent = new Intent(this, RegisterLoginActivity.class);
             startActivity(intent);
           /*  }*/
        }
    }
    /**
     * 是否第一次启动APP
     * @return
     */
    private boolean isFirst(){
        SharedPreferences sp=getApplicationContext().getSharedPreferences(IS_FIRST,MODE_PRIVATE);
        return sp.getBoolean(IS_FIRST,true);

    }
    /**
     * 是否登陆
     * @return
     */
    private boolean isLogin(){
        SharedPreferences sp=getApplicationContext().getSharedPreferences(IS_LOGIN,MODE_PRIVATE);
        return sp.getBoolean(IS_LOGIN,false);

    }
}
