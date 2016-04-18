package com.example.vmmusic.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.MusicListAdapter;
import com.example.vmmusic.app.adapter.ViewPagerAdapter;
import com.example.vmmusic.app.fragment.MineFragment;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.FileUtils;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.ServiceHelper;
import com.example.vmmusic.app.utils.TopSettiings;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 选择音乐
 * Created by Administrator on 2016/4/8 0008.
 */
public class MusicListActivity extends Activity {
    private TopSettiings topSettiings;
    private ListView songlist, albumlist, singerlist;//歌曲listview，专辑歌手listview
    private RadioButton topRight, topLeft;//全部,本地
    private RadioButton songs, singers, albums;//歌曲，歌手，专辑
    private Music music;
    private int listPostion;//播放位置
    private ServiceHelper serviceHelper;
    private MusicListAdapter songAdapter, singerAdapter, albumAdapter;
    private FileUtils fileUtils;
    private RadioGroup topGroup;
    private ArrayList<Music> aList, sList;//专辑，歌手
    private ViewPager viewPager;
    private boolean local;

    public static final int FROM=1000;


    private HashMap<String, ArrayList<Music>> map, sMap;//专辑，歌手
    private ArrayList<View> viewList = new ArrayList<View>();
    private ArrayList<Music> list = new ArrayList<Music>();//所有歌曲
    private ArrayList<Music> sortlist = new ArrayList<Music>();//单个分类下的歌曲
    public static final String MUSICLIST = "Albums and Songs";
    public static final String TITLE = "new title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.NEXTSONG);
        registerReceiver(musicListReceiver, intentFilter);
        inni();
    }

    /**
     * 初始化界面
     */
    private void inni() {
        topSettiings = new TopSettiings(this);
        topSettiings.setTopRadioGroup(null, null);
        topRight = topSettiings.getChoiceRight();
        topLeft = topSettiings.getChoiceLeft();
        TextView back = topSettiings.setLeft(null, null, false);
        TextView right = topSettiings.setRight(null, null, false);
        right.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        Intent intent=getIntent();
        if(intent!=null){
        	local=intent.getBooleanExtra(MineFragment.LOCAL, false);
        	if(local){
        		topRight.setChecked(true);
        	}
        }else{
        topLeft.setChecked(true);
        }

        topGroup = (RadioGroup) findViewById(R.id.music_list_group);
        songs = (RadioButton) findViewById(R.id.music_list_single);
        singers = (RadioButton) findViewById(R.id.music_list_singer);
        albums = (RadioButton) findViewById(R.id.music_list_cd);
        inniPager();
        getLocalFile();


        map = Music.sortByAlbum(list);
        sMap = Music.sortBySinger(list);

        albums.setText("专辑" + map.size());
        singers.setText("歌手" + sMap.size());


        byAlbums();
        bySingers();

        topGroup.setOnCheckedChangeListener(checkedChangeListener);
        songAdapter = new MusicListAdapter(this, list, true);
        songlist.setAdapter(songAdapter);
        songlist.setOnItemClickListener(itemClickListener);


        albumlist.setOnItemClickListener(albumClickListener);
        singerlist.setOnItemClickListener(singerClickListener);
    }

    /**
     * 设置viewPager
     */
    private void inniPager() {
        viewPager = (ViewPager) findViewById(R.id.music_list_viewpager);
        View view1 = getLayoutInflater().inflate(R.layout.items_pager_list_view, null);
        View view2 = getLayoutInflater().inflate(R.layout.items_pager_list_view, null);
        View view3 = getLayoutInflater().inflate(R.layout.items_pager_list_view, null);
        songlist = (ListView) view1.findViewById(R.id.music_list_view);
        singerlist = (ListView) view2.findViewById(R.id.music_list_view);
        albumlist = (ListView) view3.findViewById(R.id.music_list_view);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new ViewPagerAdapter(viewList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                viewPager.setCurrentItem(i);
                switch (i) {
                    case 0:
                        songs.setChecked(true);
                        break;
                    case 1:
                        singers.setChecked(true);
                        break;
                    case 2:
                        albums.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 专辑分类
     */
    private void byAlbums() {
        aList = new ArrayList<Music>();
        for (String album : map.keySet()) {
            music = new Music();
            music.setName(album);
            aList.add(music);
        }
        albumAdapter = new MusicListAdapter(this, aList, false);
        albumlist.setAdapter(albumAdapter);
    }

    /**
     * 歌手分类
     */
    private void bySingers() {
        sList = new ArrayList<Music>();
        for (String singer : sMap.keySet()) {
            music = new Music();
            music.setName(singer);
            sList.add(music);
        }
        singerAdapter = new MusicListAdapter(this, sList, false);
        singerlist.setAdapter(singerAdapter);
    }

    /**
     * 歌曲点击播放音乐
     */
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            music = list.get(i);
            serviceHelper = new ServiceHelper(MusicListActivity.this);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MusicService.VMMUSIC, music);
            bundle.putInt(MusicService.LISTSIZE, list.size());
            serviceHelper.startMyService(bundle);
            songAdapter.notifyDataSetChanged();
            listPostion = i;
            playMusic(i);
        }
    };

    /**
     * 播放音乐
     *
     * @param postion 点击的位置
     */
    private void playMusic(int postion) {
        music = list.get(postion);
        serviceHelper=new ServiceHelper(MusicListActivity.this);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MusicService.VMMUSIC,music);
        bundle.putInt(MusicService.LISTSIZE,list.size());
        bundle.putInt(MusicService.FROMWHERE,FROM);
        serviceHelper.startMyService(bundle);
    }

    /**
     * 歌手的点击事件
     */
    AdapterView.OnItemClickListener singerClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Music music=sList.get(i);
                    String singer =music.getName();//歌手名
                    if(sMap.containsKey(singer)) {
                        Intent intent =new Intent(MusicListActivity.this,MoreListActivity.class);
                        Bundle bundle=new Bundle();
                        ArrayList<Music> singerList = sMap.get(singer);
                        bundle.putString(TITLE, singer);
                        bundle.putSerializable(MUSICLIST,singerList);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
        }
    };


    /**
     * 专辑的点击事件
     */
    AdapterView.OnItemClickListener albumClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Music music = aList.get(i);
            String album = music.getName();//专辑名
            if (map.containsKey(album)) {
                Intent intent = new Intent(MusicListActivity.this, MoreListActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<Music> albumList = map.get(album);
                bundle.putSerializable(MUSICLIST, albumList);
                bundle.putString(TITLE, album);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    };

    /**
     * 获取本地mp3文件存入ArrayList中  获取单曲数量
     */
    private void getLocalFile() {
        fileUtils = new FileUtils();
        fileUtils.getMediaInfo(this, list);
        songs.setText("单曲" + list.size());
        songs.setChecked(true);

    }

    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.music_list_single:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.music_list_cd:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.music_list_singer:
                    viewPager.setCurrentItem(1);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 单击事件
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.public_top_left:
                    finish();
                    break;
                case R.id.public_top_right:
                    Intent playLyric=new Intent(MusicListActivity.this,MusicLyricPlayActivity.class);
                    startActivity(playLyric);//跳转歌词页面
                    break;
                default:
                    break;

            }

        }
    };
    /**
     * broadcastReceiver  自动播放下一曲
     */
    BroadcastReceiver musicListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(MusicService.NEXTSONG)) {
                listPostion++;
                playMusic(intent.getIntExtra(MusicService.NEXTSONG, listPostion));

            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(musicListReceiver);
        serviceHelper=new ServiceHelper(MusicListActivity.this);
        Bundle bundle=new Bundle();
        bundle.putBoolean(MusicService.ISFINISH, true);
        serviceHelper.startMyService(bundle);
        super.onDestroy();

    }
}
