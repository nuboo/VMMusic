package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.MusicListAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.ServiceHelper;
import com.example.vmmusic.app.utils.TopSettiings;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class MoreListActivity extends Activity {
    private TopSettiings topSettiings;
    private ArrayList<Music> musics;
    private int where;//当前播放位置
    public static final int FROM=1001;
    private ServiceHelper serviceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_more);
        inni();
    }

    /**
     * 初始化界面
     */
    private  void inni(){
        topSettiings=new TopSettiings(this);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        TextView back=topSettiings.setLeft(null, getResources().getDrawable(R.drawable.back),false);
        TextView forMore=topSettiings.setRight(null,null,false);
        forMore.setOnClickListener(listener);
        back.setOnClickListener(listener);
        if(bundle!=null){
            String title=bundle.getString(MusicListActivity.TITLE,null);
            topSettiings.setTitle(title);
         //   musics=bundle.getParcelableArrayList(MusicListActivity.MUSICLIST);
            musics=(ArrayList<Music>)bundle.getSerializable(MusicListActivity.MUSICLIST);
        }

        ListView listView=(ListView)findViewById(R.id.music_list_view);
        listView.setAdapter(new MusicListAdapter(this,musics,false));
        listView.setOnItemClickListener(itemClickListener);
        IntentFilter filter=new IntentFilter();
        registerReceiver(moreListReceiver,filter);
    }

    /**
     * 点击播放对应歌曲
     */
    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            playByPosition(i);

        }
    };

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.public_top_right:
                    Intent intent=new Intent(MoreListActivity.this,MusicLyricPlayActivity.class);
                    startActivity(intent);

                    break;
                case R.id.public_top_left:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 播放选中位置
     * @param position
     */
    private void playByPosition(int position){
        Music music=musics.get(position);
        serviceHelper=new ServiceHelper(MoreListActivity.this);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MusicService.VMMUSIC,music);
        bundle.putInt(MusicService.LISTSIZE, musics.size());
        bundle.putInt(MusicService.FROMWHERE, FROM);
        bundle.putInt(MusicService.NOWPOSITION,position);
        serviceHelper.startMyService(bundle);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(moreListReceiver);
        //通知service activity已销毁
        serviceHelper=new ServiceHelper(MoreListActivity.this);
        Bundle bundle=new Bundle();

        bundle.putBoolean(MusicService.ISFINISH,true);
        serviceHelper.startMyService(bundle);
        super.onDestroy();
    }

    BroadcastReceiver moreListReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MusicService.NEXT)){
               where++;

                playByPosition(intent.getIntExtra(MusicService.NEXTSONG, where));
            }
        }
    };


}
