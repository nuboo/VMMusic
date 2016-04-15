package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.LrcTextView;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.ServiceHelper;
import com.example.vmmusic.app.utils.T;

/** 音乐播放界面，歌词界面
 * Created by Administrator on 2016/4/11 0011.
 */
public class MusicLyricPlayActivity extends Activity {
    private ServiceHelper serviceHelper;
    private MusicService myService;
    private  LrcTextView lrcView;
    private TextView title,singer,back,more;
    private TextView collect ,share,donwLoad,playType;//收藏，分享，下载，随机播放
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_lyric);
        inni();

    }

    private void inni(){

        intentFilter=new IntentFilter();
        intentFilter.addAction(MusicService.NEWSONG);
        registerReceiver(updateReceiver, intentFilter);//注册广播，更新
        lrcView=(LrcTextView)findViewById(R.id.playing_lyrics);
        more=(TextView)findViewById(R.id.lyrics_top_right);
        title=(TextView)findViewById(R.id.lyrics_song);
        title.setSelected(true);//设置标题滚动

        singer=(TextView)findViewById(R.id.lyrics_singer);
        singer.setSelected(true);
        back=(TextView)findViewById(R.id.lyrics_top_left);
        bindMyservice();
        back.setOnClickListener(clickListener);
        more.setOnClickListener(clickListener);
        collect=(TextView)findViewById(R.id.lyrics_collection);
        share=(TextView)findViewById(R.id.lyrics_share);
        donwLoad=(TextView)findViewById(R.id.lyrics_download);
      //  playType=(TextView)findViewById(R.id.lyrics_play_type);

        collect.setSelected(true);
        share.setSelected(true);



    }


    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lyrics_top_left:
                    finish();
                    break;
                case R.id.lyrics_top_right:
                    Intent intent=new Intent(MusicLyricPlayActivity.this,MoreAndMoreActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 绑定服务
     *
     */
    public void bindMyservice(){
        Intent intent =new Intent(this,MusicService.class);

        Bundle bundle=new Bundle();
        bundle.putBoolean(MusicService.LYRICS,true);

        Log.e("null","not");

        intent.putExtras(bundle);
        this.bindService(intent, conn, Context.BIND_AUTO_CREATE);
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
            myService.initLrc(lrcView,title,singer);
        }
    };

    /**
     * 解绑服务
     */

    public void unbindMyService(){
        if(conn!=null){
            this.unbindService(conn);
        }
    }


    @Override
    protected void onDestroy() {

       unbindMyService();
        unregisterReceiver(updateReceiver);
        super.onDestroy();
    }

    BroadcastReceiver updateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MusicService.NEWSONG)){
                Music music=(Music)intent.getSerializableExtra(MusicService.NEWSONG);
                title.setText(music.getName());
                singer.setText(music.getSinger());
                myService.initLrc(lrcView,title,singer);//更换歌词
            }
        }
    };
}
