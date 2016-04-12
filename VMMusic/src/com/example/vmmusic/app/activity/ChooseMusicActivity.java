package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChooseMusicAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.MediaUtil;

import java.util.List;

/**
 * 选择音乐
 * Created by awx19 on 2016/4/11.
 */
public class ChooseMusicActivity extends Activity {
    RadioGroup radioGroup;
    RadioButton choose_music_single;//单曲
    RadioButton choose_music_singer;//歌手
    RadioButton choose_music_special;//专辑
    ListView choose_music_list_view;//列表
    ChooseMusicAdapter chooseMusicAdapter;
    List<Music> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        initView();
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.choose_music_radio_group);
        choose_music_single = (RadioButton) findViewById(R.id.choose_music_single);
        choose_music_singer = (RadioButton) findViewById(R.id.choose_music_singer);
        choose_music_special = (RadioButton) findViewById(R.id.choose_music_special);
        choose_music_list_view = (ListView) findViewById(R.id.choose_music_list_view);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        MediaUtil mediaUtil = new MediaUtil();
        mediaUtil.doGetMusic(getApplicationContext(), callBack);
        choose_music_list_view.setAdapter(chooseMusicAdapter);
    }

    MediaUtil.CallBack callBack = new MediaUtil.CallBack() {
        @Override
        public void onRequestComplete(List<Music> result) {
            Message message = new Message();
            message.what = 0;
            list = result;
            chooseMusicAdapter = new ChooseMusicAdapter(getApplicationContext(), result);
            handler.sendMessage(message);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (chooseMusicAdapter != null) {
                        choose_music_list_view.setAdapter(chooseMusicAdapter);
                        choose_music_single.setText("单曲" + list.size());
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * radioButton监听
     */
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.choose_music_single:
                    break;
                case R.id.choose_music_singer:
                    break;
                case R.id.choose_music_special:
                    break;
            }
        }
    };
}
