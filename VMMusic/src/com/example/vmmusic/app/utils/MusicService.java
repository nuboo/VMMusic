package com.example.vmmusic.app.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
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
import java.util.ArrayList;


/**播放音乐的service
 * Created by Administrator on 2016/4/11 0011.
 */
public class MusicService extends Service {
    public static final String ISFINISH="finished";//activity被关闭
    public static  final  String NEXTSONG="complete";//完成播放下一首(所有单曲列表)
    public static  final  String NEXT="notListcomplete";//完成播放下一首
    public static final String VMMUSIC="VmMusic";//开启service
    public static  final String LISTSIZE="listSize";
    public static final String ISSONGLIST="isSongList";//是否是单曲列表

    private Music music;
    private MediaPlayer mediaPlayer;//音乐播放器
    private String path=null;//当前播放文件路径  用于判断点击歌曲是否是播放中歌曲 也可以使用sid判断
    private int listSize;//当前播放的list大小
    private int postion;//播放歌曲在list当中的位置
    private boolean isSongList,isFinish;//是否为单曲列表,Activity是否已经关闭
    private ArrayList<Music> list;//用于后台播放
    private FileUtils fileUtils;//用于后台播放



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
            isFinish=bundle.getBoolean(ISFINISH,false);

            if(!isFinish){

                music=(Music)bundle.getSerializable(VMMUSIC);
                postion=music.getId();//ID和listView中位置一致
                listSize=bundle.getInt(LISTSIZE,1);//默认为1
                isSongList=bundle.getBoolean(ISSONGLIST,true);//默认为是
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
                      inniPosition();//刷新播放位置
                    if(!isFinish){//如果activity还在运行

                        Intent intent =new Intent();
                        if(isSongList==true) {
                            intent.setAction(NEXTSONG);
                            intent.putExtra(NEXTSONG, postion);
                        }else {
                            intent.setAction(NEXT);
                            intent.putExtra(NEXT, postion);
                        }
                        sendBroadcast(intent);
                    }else{//如果activity还在不存在，进行后台播放
                        inniMusic();
                        playBackground();
                    }



                  }
              });

          }
    }

    /**
     * 后台播放
     *
     */
    private void playBackground(){
        music=list.get(postion);
        path=music.getPath();//更新当前播放歌曲路径
        if(path!=null&&!path.equals("")){
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    inniPosition();//刷新播放位置
                    playBackground();//继续后台播放
                }
            });
        }
    }



    /**
     * 初始化音乐文件
     */
    private void inniMusic(){
        if(list==null){
            list=new ArrayList<Music>();
            fileUtils=new FileUtils();
            fileUtils.getMediaInfoByResolver(getContentResolver(),list);//获取跟MusicListActivity一样的列表
        }


    }

    /**
     * 刷新播放歌曲位置   顺序和随机2种模式
     */
    private void inniPosition(){
        postion++;//下一首
        postion=postion%listSize;//postion除以listSize的余数刚好为在list中的位置
    }
}
