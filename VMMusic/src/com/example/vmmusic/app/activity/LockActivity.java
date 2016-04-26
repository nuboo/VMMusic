package com.example.vmmusic.app.activity;

import java.util.ArrayList;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.LrcTextView;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.AlbumImgHelper;
import com.example.vmmusic.app.utils.MusicService;
import com.example.vmmusic.app.utils.ServiceHelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * 锁屏
 * 
 * @author Administrator
 *
 */
public class LockActivity extends Activity {
	private TextView title, last, next, sing;// 标题，上一首，下一首
	private LrcTextView lrc;// 歌词
	private ArrayList<Music> list;// 歌曲列表
	private Music music;
	private AlbumImgHelper imgHelper;
	private MusicService myService;
	private int i;
	private SeekBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_lock);
		inni();
	}

	/**
	 * 初始化控件
	 */
	private void inni() {
		title = (TextView) findViewById(R.id.lock_title);
		next = (TextView) findViewById(R.id.lock_next);
		last = (TextView) findViewById(R.id.lock_last);
		lrc = (LrcTextView) findViewById(R.id.lock_lyrics);
		sing = (TextView) findViewById(R.id.lock_song);
		bar = (SeekBar) findViewById(R.id.lock_progress);
		imgHelper = new AlbumImgHelper();
		list = imgHelper.getMp3InfosFromSql(this);
		bindMyservice();

		next.setOnClickListener(listener);
		last.setOnClickListener(listener);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (bar.getProgress() == 100) {
					Intent intent = new Intent(LockActivity.this, HomePageActivity.class);
					startActivity(intent);
					unregisterReceiver(updateReceiver);
					unbindMyService();
					finish();
				}

			}
		});
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.lock_last:
				i++;
				Log.w("i+++++", i + "");
				playMusic(i);

				break;
			case R.id.lock_next:
				if (i == 0) {
					i = list.size();
					Log.w("i----", i + "");
				}
				i--;
				Log.w("------i----", i + "");
				playMusic(i);

				break;
			case R.id.lock_song:

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
	public void bindMyservice() {
		Intent intent = new Intent(this, MusicService.class);

		Bundle bundle = new Bundle();
		bundle.putBoolean(MusicService.LYRICS, true);

		intent.putExtras(bundle);
		this.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 设置serviceConnection
	 */
	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			myService = null;

		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			myService = ((MusicService.MyServiceBinder) arg1).getService();

			i = myService.getPosition();
			music = list.get(i);
			inniSong(music);

			myService.initLrc(lrc, music);
			myService.inniSeek(bar);

		}
	};

	/**
	 * 解绑服务
	 */

	public void unbindMyService() {
		if (conn != null) {
			this.unbindService(conn);
		}
	}

	@Override
	protected void onDestroy() {

		unregisterReceiver(updateReceiver);
		unbindMyService();

		super.onDestroy();
	}

	BroadcastReceiver updateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(MusicService.NEWSONG)) {

				int i = myService.getPosition();// 获取歌曲列表的位置
				music = list.get(i);
				inniSong(music);

				myService.initLrc(lrc, music);
				myService.inniSeek(bar);
				/*
				 * music=myService.getNowPlay(); inniSong(music);
				 */

			}
		}
	};

	/**
	 * 更新歌名歌手
	 * 
	 * @param music
	 */
	private void inniSong(Music music) {
		title.setText(music.getName());
		sing.setText(music.getSinger());
	}

	/**
	 * 播放歌曲
	 * 
	 * @param postion
	 */
	private void playMusic(int postion) {
		int j = (postion % (list.size()));
		music = list.get(j);

		ServiceHelper serviceHelper = new ServiceHelper(LockActivity.this);
		Bundle bundle = new Bundle();
		bundle.putSerializable(MusicService.VMMUSIC, music);
		bundle.putInt(MusicService.LISTSIZE, list.size());
		bundle.putInt(MusicService.FROMWHERE, MusicListActivity.FROM);
		bundle.putInt(MusicService.NOWPOSITION, postion);
		serviceHelper.startMyService(bundle);
		inniSong(list.get(i));

		myService.initLrc(lrc, music);
		myService.inniSeek(bar);
	}

}
