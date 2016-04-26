package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ViewPagerAdapter;
import com.example.vmmusic.app.customview.LrcTextView;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.AlbumImgHelper;
import com.example.vmmusic.app.utils.AndroidShare;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.SQLUtils;
import com.example.vmmusic.app.utils.ServiceHelper;
import com.example.vmmusic.app.utils.T;

/** 音乐播放界面，歌词界面
 * Created by Administrator on 2016/4/11 0011.
 */
public class MusicLyricPlayActivity extends Activity {
    private ServiceHelper serviceHelper;
    private MusicService myService;
    private  LrcTextView lrcView,lrcViewNow,lrcViewChange;
    private TextView title,singer,back,more;
    private TextView collect ,share,donwLoad,playType;//收藏，分享，下载，随机播放
    private IntentFilter intentFilter;
    private Music music;
    private SQLUtils sqlUtils;
    private ViewPager viewPager;
    private ArrayList<View> viewList,lrcList;
    private ArrayList<Music> musics;
    private AlbumImgHelper imgHelper;
    private int count;
    private View view;
    private boolean first=true;//是否第一次更新
    private int po;
    boolean playMode;
    boolean play=true;
    public static final int FROM=5002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_lyric);
        inni();

    }
    /**
     * 初始化控件和页面
     */
    private void inni(){
    	if(sqlUtils==null){
    		sqlUtils=new SQLUtils(this);
    	}
    	if(musics==null){
    		musics=new ArrayList<Music>();
    	}
    	if(imgHelper==null){
    		imgHelper=new AlbumImgHelper();
    	}
    	if(viewList==null){
    		viewList=new ArrayList<View>();
    	}
    	if(lrcList==null){
    		lrcList=new ArrayList<View>();
    	}
    	viewPager=(ViewPager) findViewById(R.id.playing_view_pager);
    	
        intentFilter=new IntentFilter();
        intentFilter.addAction(MusicService.NEWSONG);
        registerReceiver(updateReceiver, intentFilter);//注册广播，更新
        musics=imgHelper.getMp3InfosFromSql(this);
        more=(TextView)findViewById(R.id.lyrics_top_right);
        title=(TextView)findViewById(R.id.lyrics_song);
        title.setSelected(true);//设置标题滚动
        
        singer=(TextView)findViewById(R.id.lyrics_singer);
        singer.setSelected(true);
        back=(TextView)findViewById(R.id.lyrics_top_left);
        collect=(TextView)findViewById(R.id.lyrics_collection);
        share=(TextView)findViewById(R.id.lyrics_share);
        donwLoad=(TextView)findViewById(R.id.lyrics_download);
        playType=(TextView)findViewById(R.id.lyrics_play_type);
        SharedPreferences sp=getSharedPreferences("playType", Context.MODE_PRIVATE);
        playMode=sp.getBoolean("playType", false);
        inniPager();
        bindMyservice();
        back.setOnClickListener(clickListener);
        more.setOnClickListener(clickListener);
        playType.setSelected(playMode);
        
        collect.setOnClickListener(clickListener);
        share.setOnClickListener(clickListener);
        playType.setOnClickListener(clickListener);
        donwLoad.setOnClickListener(clickListener);
       
        


    }
    
    /**
     * 初始化ViewPager
     */
    private void inniPager(){
    	count=(musics.size()+2);
    	
    	for(int i=0;i<count;i++){
    		view=getLayoutInflater().inflate(R.layout.item_lyrics, null);
    		lrcView=(LrcTextView) view.findViewById(R.id.playing_lyrics);
    		lrcView.setClickable(true);
    	
    		lrcList.add(lrcView);
    		viewList.add(view);
    		
    	}
    
    	viewPager.setAdapter(new ViewPagerAdapter(viewList));
    	viewPager.setOnPageChangeListener(pageListener);
    }
    
    
    
    OnPageChangeListener pageListener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			int where=switchPosition(arg0);
			
			
			if(where!=arg0){
			viewPager.setCurrentItem(where,false);
			
			}
				
			lrcViewNow=(LrcTextView) lrcList.get(arg0);
			music=musics.get(where-1);
			playMusic(where-1);
			po=where-1;
			inniSong(music);
			
			myService.initLrc(lrcViewNow,music);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			/*int width=getWindowManager().getDefaultDisplay().getWidth();
			int position;
			if(0.4f<arg1&&arg1<0.6f){
				if(arg2>(float)(width*0.4)){
					if(arg2>(float)(width*0.6)){
						position=switchPosition(arg0-1);
					}else{
						position=switchPosition(arg0+1);
					}
					inniSong(musics.get(position));
				}
			}*/
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		
		}
	};
    
    
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
                case R.id.lyrics_collection:
                	inniSelected();
                	break;
                case R.id.lyrics_share:
                	shareMusic();
                	break;
                case R.id.lyrics_play_type:
                	myService.playPause();
                	
                	break;
                case R.id.lyrics_download:
                	T.showShort(MusicLyricPlayActivity.this, "歌曲已存在");
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

       

        intent.putExtras(bundle);
        this.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }
   
	/**
     * 随机播放和顺序播放
     */
    protected void changePlayMode() {
    	 SharedPreferences sp=getSharedPreferences("playType", Context.MODE_PRIVATE);
    	
         SharedPreferences.Editor editor=sp.edit();
         if(playMode){
        	 editor.putBoolean("playType", false);
        	 playType.setSelected(false);
         }else{
        	 editor.putBoolean("playType", true);
        	 playType.setSelected(true);
         }
         editor.commit();
		
	}
	/**
     * 分享音乐
     */
    protected void shareMusic() {
    	
		AndroidShare share=new AndroidShare(this);
		share.show();
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
            if(first){//设置初始页面
            int i=myService.getPosition();
            po=i;
            viewPager.setCurrentItem(i+1);
           
            first=false;
            }
          
           
           
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

     
        unregisterReceiver(updateReceiver);
        unbindMyService();
     
        	
        
    
       
        super.onDestroy();
    }

    BroadcastReceiver updateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MusicService.NEWSONG)){
               
               
                int i=myService.getPosition();//获取歌曲列表的位置
                viewPager.setCurrentItem(i+1);
              
             /*   music=myService.getNowPlay();
                inniSong(music);*/
               
               
            }
        }
    };
    
    /**
     * 更新标题 和收藏状态
     * @param music
     */
    private void inniSong(Music music){
    	
    	 singer.setText(music.getSinger());
         title.setText(music.getName());
         if(music.getCollection()!=2){
        	 boolean collected=sqlUtils.isCollection(music);
        	 collect.setSelected(collected);
         }
         SharedPreferences sp=getSharedPreferences("playType", Context.MODE_PRIVATE);
         boolean playMode=sp.getBoolean("playType", false);
         playType.setSelected(playMode);
    }
    
    /**
     * 收藏，取消收藏
     */
    private void inniSelected(){
    
    	boolean se=collect.isSelected();
    	if(se){
    		music.setCollection(0);
    		sqlUtils.upDate(music);
    		
    		collect.setSelected(false);
    		
    		
    	}else{
    	
    		music.setCollection(1);
    		sqlUtils.upDate(music);
    		collect.setSelected(true);
    		T.showShort(this, "已添加到我的收藏");
    		
    	}
    }
    
    /**
     * 播放歌曲
     * @param postion
     */
    private void playMusic(int postion) {

        music = musics.get(postion);


        serviceHelper=new ServiceHelper(MusicLyricPlayActivity.this);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MusicService.VMMUSIC,music);
        bundle.putInt(MusicService.LISTSIZE,musics.size());
       
        bundle.putInt(MusicService.FROMWHERE,MusicListActivity.FROM);
        
        	
       
        
        
        bundle.putInt(MusicService.NOWPOSITION,postion);
        serviceHelper.startMyService(bundle);
    }
    /**
     * 换算歌曲列表和viewpager的对应位置
     * @param arg0
     * @return
     */
    private int switchPosition(int arg0){
    	int where;
    	if(arg0==0){
			where=musics.size();
		}else if(arg0==(musics.size()+1)){
			where=1;
		}else{
			where=arg0;
		}
		return where;
    }
    
   
}
