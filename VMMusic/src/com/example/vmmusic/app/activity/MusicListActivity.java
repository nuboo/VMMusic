package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.MusicListAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.T;
import com.example.vmmusic.app.utils.TopSettiings;

import java.util.ArrayList;

/**选择音乐
 * Created by Administrator on 2016/4/8 0008.
 */
public class MusicListActivity extends Activity {
    private TopSettiings topSettiings;
    private ListView listView;
    private RadioButton topRight,topLeft;//全部,本地
    private RadioButton songs,singers,albums;//歌曲，歌手，专辑
    private  Music music;
    private ArrayList<Music> list=new ArrayList<Music>();
    private MusicListAdapter adapter;
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
        listView=(ListView)findViewById(R.id.music_list_view);
        songs=(RadioButton)findViewById(R.id.music_list_single);
        singers=(RadioButton)findViewById(R.id.music_list_singer);
        albums=(RadioButton)findViewById(R.id.music_list_cd);
        testData();
        adapter=new MusicListAdapter(this,list);
        listView.setAdapter(adapter);
    }


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
}
