package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.ServiceHelper;

/** 音乐播放界面，歌词界面
 * Created by Administrator on 2016/4/11 0011.
 */
public class MusicLyricPlayActivity extends Activity {
    private ServiceHelper serviceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_lyric);
    }

    private void inni(){

    }

    @Override
    protected void onDestroy() {

       // myService.onDestroy();
        super.onDestroy();
    }
}
