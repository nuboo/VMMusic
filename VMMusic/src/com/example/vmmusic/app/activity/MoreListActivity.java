package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.MusicListAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.ServiceHelper;
import com.example.vmmusic.app.utils.TopSettiings;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class MoreListActivity extends Activity {
    private TopSettiings topSettiings;
    private ArrayList<Music> musics;
    private Boolean aBoolean;
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
        TextView back=topSettiings.setLeft("返回", null,true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(bundle!=null){
            String title=bundle.getString(MusicListActivity.TITLE,null);
            topSettiings.setTitle(title);
         //   musics=bundle.getParcelableArrayList(MusicListActivity.MUSICLIST);
            musics=(ArrayList<Music>)bundle.getSerializable(MusicListActivity.MUSICLIST);
        }

        ListView listView=(ListView)findViewById(R.id.music_list_view);
        listView.setAdapter(new MusicListAdapter(this,musics,false));
        listView.setOnItemClickListener(itemClickListener);

    }
    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Music music=musics.get(i);
           ServiceHelper serviceHelper=new ServiceHelper(MoreListActivity.this);
            serviceHelper.startMyService(music);

        }
    };
}
