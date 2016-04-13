package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChooseMusicAdapter;
import com.example.vmmusic.app.model.Mp3Info;
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
    List<Mp3Info> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        initView();
        topSetting();
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.choose_music_radio_group);
        choose_music_single = (RadioButton) findViewById(R.id.choose_music_single);
        choose_music_singer = (RadioButton) findViewById(R.id.choose_music_singer);
        choose_music_special = (RadioButton) findViewById(R.id.choose_music_special);
        choose_music_list_view = (ListView) findViewById(R.id.choose_music_list_view);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        list = MediaUtil.getMp3Infos(getApplicationContext());
        ChooseMusicAdapter chooseMusicAdapter = new ChooseMusicAdapter(getApplicationContext(), list);
        choose_music_single.setText("单曲" + list.size());
        choose_music_list_view.setAdapter(chooseMusicAdapter);
    }

    /**
     * 顶部设置
     */
    private void topSetting() {
        TextView back = (TextView) findViewById(R.id.back);
        RadioGroup radio_group = (RadioGroup) findViewById(R.id.top_radio_group);
        RadioButton radio_btn = (RadioButton) findViewById(R.id.radio_btn);
        RadioButton radio_button = (RadioButton) findViewById(R.id.radio_button);
        TextView optical_disk = (TextView) findViewById(R.id.top_optical_disk);
        radio_btn.setText("全部");
        radio_button.setText("本地");
        back.setOnClickListener(onClickListener);
        optical_disk.setOnClickListener(onClickListener);
        radio_group.setOnCheckedChangeListener(checkedChangeListener);
    }

    /**
     * 公共顶部Button选择监听
     */
    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.radio_btn:

                    break;
                case R.id.radio_button:
                    break;

            }
        }


    };

    /**
     * 公共顶部监听
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.top_optical_disk:
                    break;
            }
        }
    };


    /**
     * 分类radioButton监听
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
