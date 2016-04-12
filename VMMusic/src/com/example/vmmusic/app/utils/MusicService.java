package com.example.vmmusic.app.utils;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
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
    private String path=null;
    private int listSize=0;
    public static  final String LISTSIZE="listSize";
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
            listSize=bundle.getInt(LISTSIZE,0);
            if(music!=null&&listSize!=0){
                if(!music.getPath().equals(path)) {//如果不是同一首歌，则播放


                        playMusic(music.getPath());


                }/*else{
                    if(mediaPlayer.isPlaying()){//如果正在播放，则暂停
                        mediaPlayer.pause();
                    }else{
                        mediaPlayer.start();////如果暂停，则播放
                    }
                }*/
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
        this.path = path;
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
