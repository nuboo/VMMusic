package com.example.vmmusic.app.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.vmmusic.app.model.Music;

/** 绑定service 工具类
 * Created by Administrator on 2016/4/11 0011.
 */
public class ServiceHelper {
    private  MusicService myService;
    private  Context context;

    /**
     * 关闭服务
     */
    public ServiceHelper(Context context){
        this.context=context;
    }
    public void destroyMyService(){
        Intent intent =new Intent(context,MusicService.class);

        context.stopService(intent);
    }

    /**
     * 开启服务
     * @param bundle
     */
    public void startMyService(Bundle bundle){
        Intent intent =new Intent(context,MusicService.class);


        intent.putExtras(bundle);
        context.startService(intent);
    }


    /**
     * 绑定服务
     * @param music  music对象
     */
    public void bindMyservice(Bundle bundle){
        Intent intent =new Intent(context,MusicService.class);



        intent.putExtras(bundle);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 设置serviceConnection
     */
    ServiceConnection conn =new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub
            myService=null;
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            // TODO Auto-generated method stub
            myService=((MusicService.MyServiceBinder)arg1).getService();

        }
    };

    /**
     * 解绑服务
     */

    public void unbindMyService(){
        if(conn!=null){
            context.unbindService(conn);
        }
    }

}
