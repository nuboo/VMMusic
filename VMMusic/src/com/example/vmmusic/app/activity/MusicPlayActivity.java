package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.MusicService;

/** 音乐播放界面，歌词界面
 * Created by Administrator on 2016/4/11 0011.
 */
public class MusicPlayActivity extends Activity {
    MusicService myService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }



    @Override
    protected void onDestroy() {

       // myService.onDestroy();
        super.onDestroy();
    }
}
