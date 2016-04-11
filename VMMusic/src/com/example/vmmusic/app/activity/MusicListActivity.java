package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.vmmusic.R;

import com.example.vmmusic.app.adapter.MusicListAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.FileUtils;
import com.example.vmmusic.app.utils.ServiceHelper;

import com.example.vmmusic.app.utils.TopSettiings;


import java.util.ArrayList;
import java.util.HashMap;

/**选择音乐
 * Created by Administrator on 2016/4/8 0008.
 */
public class MusicListActivity extends Activity {
    private TopSettiings topSettiings;
    private ListView listView;
    private RadioButton topRight,topLeft;//全部,本地
    private RadioButton songs,singers,albums;//歌曲，歌手，专辑
    private  Music music;
    private ServiceHelper serviceHelper;
    private MusicListAdapter adapter;
    private FileUtils fileUtils;
    private RadioGroup topGroup;
    private  ArrayList<Music>  mList;

    private   HashMap<String,ArrayList<Music>>  map;

    private  ArrayList<Music> list =new ArrayList<Music>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        inni();
    }

    /**
     * 初始化界面
     */
    private  void inni(){
        topSettiings=new TopSettiings(this);

        topSettiings.setTopRadioGroup(null,null);
        topRight=topSettiings.getChoiceRight();
        topLeft=topSettiings.getChoiceLeft();
        topLeft.setChecked(true);

       listView=(ListView)findViewById(R.id.music_list_view);
        topGroup=(RadioGroup)findViewById(R.id.music_list_group);
        songs=(RadioButton)findViewById(R.id.music_list_single);
        singers=(RadioButton)findViewById(R.id.music_list_singer);
        albums=(RadioButton)findViewById(R.id.music_list_cd);


       getLocalFile();
        map = Music.sortByAlbum(list);
        albums.setText("专辑"+map.size());
        topGroup.setOnCheckedChangeListener(checkedChangeListener);
        adapter=new MusicListAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);




    }

    /**
     * 点击播放音乐
     */
    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                music=list.get(i);
            serviceHelper=new ServiceHelper(MusicListActivity.this);
            serviceHelper.startMyService(music);
            Log.i("VMmusic",music.getPath());

        }
    };
    /**
     * 测试数据
     */
    private void testData(){
       for(int i=0;i<10;i++){
         music=new Music();
           music.setName("this is war");
           music.setSinger("30 seconds to mars");
           list.add(music);
       }
    }

    /**
     * 获取本地mp3文件存入ArrayList中  获取单曲数量
     */
    private void getLocalFile(){
        fileUtils=new FileUtils();
        fileUtils.getAllMusicFiles(list);
        songs.setText("单曲"+list.size());
        songs.setChecked(true);
    }

    RadioGroup.OnCheckedChangeListener checkedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.music_list_single:
                    all();
                    break;
                case R.id.music_list_cd:
                    albums();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 专辑分类
     */
    private void albums(){
        if(mList==null) {//只有第一次才实例化进行添加数据
            mList = new ArrayList<Music>();

            for (String album : map.keySet()) {
                music = new Music();
             //   if(album!=null&&!album.equals("")) {
                    music.setName(album);
                    mList.add(music);
              //  }
            }
        }
            adapter=new MusicListAdapter(this,mList);
            listView.setAdapter(adapter);

            adapter.notifyDataSetChanged();


    }

    /**
     * 所有单曲
     */
    private void all(){
        adapter=new MusicListAdapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
