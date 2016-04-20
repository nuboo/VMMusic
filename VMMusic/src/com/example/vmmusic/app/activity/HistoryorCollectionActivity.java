package com.example.vmmusic.app.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.MusicListAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.HistoryUtils;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.SQLUtils;
import com.example.vmmusic.app.utils.ServiceHelper;
import com.example.vmmusic.app.utils.TopSettiings;

import java.util.ArrayList;

/**
 * 播放历史和我的收藏
 *
 * @author Administrator
 */
public class HistoryorCollectionActivity extends Activity {
    private ListView listView;
    private TopSettiings topSettiings;
    private ArrayList<Music> list;
    private SQLUtils sqlUtils;
    private HistoryUtils historyUtils;
    private MusicListAdapter adapter;
    private ServiceHelper serviceHelper;
    private Music music;
    private int listPosition;
    private int clickPosition;
    private AlertDialog dialog;
    public static final int FROM = 1024;
    private Intent intent;
    public static final String COLLECT = "forCollect";// 查看收藏歌曲
    private int isCollection;// 是否显示收藏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        inni();
    }

    /**
     * 初始化页面
     */
    private void inni() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.NEXTHIS);
        registerReceiver(musicListReceiver, intentFilter);

        if (getIntent() != null) {
            intent = getIntent();
            isCollection = intent.getIntExtra(COLLECT, 0);
        }
        if (list == null) {
            list = new ArrayList<Music>();
        }
        topSettiings = new TopSettiings(this);
        if (sqlUtils == null) {
            sqlUtils = new SQLUtils(this);
        }
        if (historyUtils == null) {
            historyUtils = new HistoryUtils(this);
        }
        TextView right = topSettiings.setRight(null, null, false);
        TextView back = topSettiings.setLeft(null, null, false);
        back.setOnClickListener(listener);
        right.setOnClickListener(listener);
        listView = (ListView) findViewById(R.id.history_list_view);
        if (isCollection == 0) {
            historyPage();
            listView.setOnItemLongClickListener(longListener);
        } else {
            collectionPage();
        }

        listView.setOnItemClickListener(itemListener);

    }

    /**
     * 播放历史页面
     */
    private void historyPage() {
        topSettiings.setTitle("播放历史");

        list = historyUtils.getHistory(list);

        if (list != null && list.size() > 0) {
            adapter = new MusicListAdapter(this, list, true);
            listView.setAdapter(adapter);
        }

    }

    /**
     * 收藏页面
     */
    private void collectionPage() {
        topSettiings.setTitle("我的收藏");

        list = sqlUtils.getCollection(list);

        if (list != null && list.size() > 0) {
            adapter = new MusicListAdapter(this, list, true);
            listView.setAdapter(adapter);
        }

    }

    /**
     * 点击播放
     */
    OnItemClickListener itemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            music = list.get(arg2);
            serviceHelper = new ServiceHelper(HistoryorCollectionActivity.this);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MusicService.VMMUSIC, music);
            bundle.putInt(MusicService.LISTSIZE, list.size());
            serviceHelper.startMyService(bundle);

            listPosition = arg2;
            playMusic(arg2);

        }
    };
    /**
     * 长按删除
     */
    OnItemLongClickListener longListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            clickPosition = arg2;
            showHistoryDialog();
            return true;
        }
    };
    OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.public_top_left:
                    finish();// 返回
                    break;
                case R.id.public_top_right:
                    // 进入播放歌词页面
                    Intent intent = new Intent(HistoryorCollectionActivity.this, MusicLyricPlayActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 播放音乐
     *
     * @param position 点击的位置
     */
    private void playMusic(int postion) {

        music = list.get(postion);

        serviceHelper = new ServiceHelper(HistoryorCollectionActivity.this);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MusicService.VMMUSIC, music);
        bundle.putInt(MusicService.LISTSIZE, list.size());
        bundle.putInt(MusicService.FROMWHERE, FROM);

        serviceHelper.startMyService(bundle);
    }

    /**
     * broadcastReceiver 自动播放下一曲
     */
    BroadcastReceiver musicListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(MusicService.NEXTHIS)) {

                listPosition++;

                listPosition = (listPosition % (list.size()));

                playMusic(listPosition);

            }
        }
    };

    @Override
    protected void onDestroy() {

        unregisterReceiver(musicListReceiver);

        serviceHelper = new ServiceHelper(HistoryorCollectionActivity.this);
        Bundle bundle = new Bundle();

        bundle.putBoolean(MusicService.ISFINISH, true);
        serviceHelper.startMyService(bundle);
        super.onDestroy();

    }

    /**
     * 弹出dialog删除记录
     */
    private void showHistoryDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("删除该历史记录？");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                music = list.get(clickPosition);
                list.remove(clickPosition);
                if (clickPosition <= listPosition) {
                    listPosition = listPosition - 1;
                }
                adapter.notifyDataSetChanged();
                historyUtils.deleteHistory(music.getSid());

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog.dismiss();

            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
