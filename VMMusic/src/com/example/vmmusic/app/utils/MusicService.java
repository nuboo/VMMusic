package com.example.vmmusic.app.utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.vmmusic.app.model.Music;

import java.io.IOException;


/**播放音乐的service
 * Created by Administrator on 2016/4/11 0011.
 */
public class MusicService extends Service {
    public static final String VMMUSIC="VmMusic";
    private Music music;
    private MediaPlayer mediaPlayer;//音乐播放器

    MyServiceBinder myServiceBinder=new MyServiceBinder();


    public class MyServiceBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        if(mediaPlayer!=null){//如果MediaPlayer不为空，则重置并释放数据
            mediaPlayer.reset();
            mediaPlayer.release();
        }else{
            mediaPlayer=new MediaPlayer();//实例化player
        }
        super.onCreate();
    }

    /**
     * 播放音乐
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            music=(Music)bundle.getSerializable(VMMUSIC);
            Log.w("path",music.getPath().toString());
            if(music!=null){
                playMusic(music.getPath());
            }
        }

        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return myServiceBinder;
    }

    /**
     * 播放对应路径的文件
     * @param path
     */
    private void playMusic(String path){
          if(path!=null&&!path.equals("")) {

              mediaPlayer.reset();
                Log.w("playing","");
              try {
                  mediaPlayer.setDataSource(path);
                  mediaPlayer.prepare();
                  mediaPlayer.start();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  @Override
                  public void onCompletion(MediaPlayer player) {
                    mediaPlayer.release();
                  }
              });
          }
    }
}
