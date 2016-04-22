package com.example.vmmusic.app.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.vmmusic.app.activity.HistoryorCollectionActivity;
import com.example.vmmusic.app.activity.MoreListActivity;
import com.example.vmmusic.app.activity.MusicListActivity;
import com.example.vmmusic.app.customview.LrcTextView;
import com.example.vmmusic.app.model.LrcContent;
import com.example.vmmusic.app.model.LrcProcess;
import com.example.vmmusic.app.model.Music;
import com.tencent.map.b.e;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

/**
 * 播放音乐的service Created by Administrator on 2016/4/11 0011.
 */
public class MusicService extends Service {
	public static final String ISFINISH = "finished";// activity被关闭
	public static final String NEWSONG = "updateInfo";// 通知更新
	public static final String NEXTSONG = "complete";// 完成播放下一首(所有单曲列表)
	public static final String NEXTHIS = "history";// 完成播放下一首(history)

	public static final String NEXT = "notListcomplete";// 完成播放下一首
	public static final String VMMUSIC = "VmMusic";// 开启service
	public static final String LISTSIZE = "listSize";
	public static final String LYRICS = "forLyric";// 查看歌词
	public static final String FROMWHERE = "isSongList";// 来自于哪个activity
	public static final String LASTPLAY = "pauseAt";// 播放进度
	private int where;
	private android.os.Handler handler;
	private Music music;// 当前播放的music
	private MediaPlayer mediaPlayer;// 音乐播放器
	private String path = null;// 当前播放文件路径 用于判断点击歌曲是否是播放中歌曲 也可以使用sid判断
	private int listSize;// 当前播放的list大小
	private int postion;// 播放歌曲在list当中的位置
	private boolean isFinish, isBinded;// 是否为单曲列表,Activity是否已经关闭,是否已绑定
	private ArrayList<Music> list;// 用于后台播放

	private FileUtils fileUtils;// 用于后台播放
	private LrcTextView lrcView;

	private LrcProcess mLrcProcess; // 歌词处理
	private List<LrcContent> lrcList = new ArrayList<LrcContent>(); // 存放歌词列表对象
	private int index = 0; // 歌词检索值
	private int currentTime, duration;// 当前播放进度，歌曲总长度
	MyServiceBinder myServiceBinder = new MyServiceBinder();

	public class MyServiceBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	@Override
	public void onCreate() {

		if (mediaPlayer != null) {// 如果MediaPlayer不为空，则重置并释放数据
			mediaPlayer.reset();
			mediaPlayer.release();
		} else {
			mediaPlayer = new MediaPlayer();// 实例化player
		}
		super.onCreate();
	}

	/**
	 * 播放音乐
	 * 
	 * @param intent
	 * @param startId
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		if (intent != null && intent.getExtras() != null) {
			Bundle bundle = intent.getExtras();

			isBinded = bundle.getBoolean(LYRICS, false);
			if (isBinded) {

			} else {

				isFinish = bundle.getBoolean(ISFINISH, false);

				if (!isFinish) {

					music = (Music) bundle.getSerializable(VMMUSIC);
					postion = music.getId();// ID和listView中位置一致
					listSize = bundle.getInt(LISTSIZE, 1);// 默认为1
					where = bundle.getInt(FROMWHERE, MusicListActivity.FROM);// 默认为是
					if (music != null && listSize != 0) {
						if (!music.getPath().equals(path)) {// 如果不是同一首歌，则播放

							playMusic(music.getPath());

						} else {
							if (mediaPlayer.isPlaying()) {// 如果正在播放，则暂停
								mediaPlayer.pause();
							} else {
								mediaPlayer.start();//// 如果暂停，则播放
							}
						}

					}

				}

			}

		}

		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		isBinded = true;
		return myServiceBinder;
	}

	/**
	 * 播放对应路径的文件
	 * 
	 * @param path
	 */
	private void playMusic(String path) {

		this.path = path;
		if (path != null && !path.equals("")) {

			mediaPlayer.reset();

			try {
				mediaPlayer.setDataSource(path);
				mediaPlayer.prepare();

				mediaPlayer.start();
				sendMusicInfo();// 发送广播，通知歌词界面更新标题
			} catch (IOException e) {
				e.printStackTrace();
			}
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer player) {
					histroy(music);
					sendNextSong();

				}
			});

		}
	}

	/**
	 * 发送广播，通知歌词界面更新标题
	 */
	private void sendMusicInfo() {
		Intent intent = new Intent();

		intent.setAction(NEWSONG);

		sendBroadcast(intent);

	}

	/**
	 * 发送广播，播放下一首歌
	 */
	private void sendNextSong() {
		if (!isFinish) {// 如果activity还在运行
			inniPosition();// 刷新播放位置
			Intent intent = new Intent();

			switch (where) {
			case MusicListActivity.FROM:// 来自音乐单曲
				intent.setAction(NEXTSONG);
				intent.putExtra(NEXTSONG, postion);
				sendBroadcast(intent);
				break;
			case MoreListActivity.FROM:// 专辑歌手
				intent.setAction(NEXT);
				intent.putExtra(NEXT, postion);
				sendBroadcast(intent);
				break;
			case HistoryorCollectionActivity.FROM:// 专辑歌手
				intent.setAction(NEXTHIS);
				intent.putExtra(NEXT, postion);
				Log.i("send", where + "");
				sendBroadcast(intent);
				break;
			default:
				break;
			}

		} else {// 如果activity还在不存在，进行后台播放
			inniMusic();
			playBackground();
		}
	}

	/**
	 * 后台播放
	 *
	 */
	private void playBackground() {
		music = list.get(postion);
		path = music.getPath();// 更新当前播放歌曲路径
		if (path != null && !path.equals("")) {
			mediaPlayer.reset();
			try {
				mediaPlayer.setDataSource(path);
				mediaPlayer.prepare();
				mediaPlayer.start();
				sendMusicInfo();// 发送广播，通知歌词界面更新标题
			} catch (IOException e) {
				e.printStackTrace();
			}
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
					histroy(music);
					inniPosition();// 刷新播放位置
					playBackground();// 继续后台播放
				}
			});
		}
	}

	/**
	 * 初始化音乐文件
	 */
	private void inniMusic() {
		if (list == null) {
			list = new ArrayList<Music>();

		}
		AlbumImgHelper albumImgHelper = new AlbumImgHelper();
		list = albumImgHelper.getMp3InfosFromSql(this);// 获取跟MusicListActivity一样的列表

	}

	/**
	 * 刷新播放歌曲位置 顺序和随机2种模式
	 */
	private void inniPosition() {
		Log.i("inni", postion + "~~~listSize" + listSize);
		postion++;// 下一首
		postion = postion % listSize;// postion除以listSize的余数刚好为在list中的位置

	}

	/**
	 * 初始化歌词配置
	 */
	public void initLrc(LrcTextView lrcView) {
		this.lrcView = lrcView;

		mLrcProcess = new LrcProcess();
		// 读取歌词文件
		music = getNowPlay();
		mLrcProcess.readLRC(music.getPath());
		// 传回处理后的歌词文件
		lrcList = mLrcProcess.getLrcList();
		lrcView.setmLrcList(lrcList);

		if (handler == null) {
			handler = new android.os.Handler();
		}
		handler.post(mRunnable);

	}

	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {

			lrcView.setIndex(lrcIndex());
			lrcView.invalidate();
			handler.postDelayed(mRunnable, 100);
		}
	};

	/**
	 * 根据时间获取歌词显示的索引值
	 * 
	 * @return
	 */
	public int lrcIndex() {
		if (mediaPlayer.isPlaying()) {
			currentTime = mediaPlayer.getCurrentPosition();
			duration = mediaPlayer.getDuration();

		}
		if (currentTime < duration) {
			for (int i = 0; i < lrcList.size(); i++) {
				if (i < lrcList.size() - 1) {
					if (currentTime < lrcList.get(i).getLrcTime() && i == 0) {
						index = i;
					}
					if (currentTime > lrcList.get(i).getLrcTime() && currentTime < lrcList.get(i + 1).getLrcTime()) {
						index = i;
					}
				}
				if (i == lrcList.size() - 1 && currentTime > lrcList.get(i).getLrcTime()) {
					index = i;
				}
			}
		}

		return index;
	}

	/**
	 * 根据时间获取歌词显示的索引值
	 * 
	 * @return
	 */
	public double roundIndex() {

		double per = ((double) currentTime / (double) duration);
		return per;
	}

	/**
	 * 销毁时记录播放进度
	 */
	@Override
	public void onDestroy() {
		saveAsSp();

		super.onDestroy();
	}

	/**
	 * 播放历史
	 * 
	 * @param historyMusic
	 *            播放完毕的音乐文件
	 */
	private void histroy(Music historyMusic) {

		HistoryUtils sqlUtils = new HistoryUtils(this);
		sqlUtils.addHistoryData(historyMusic);

	}

	/**
	 * 将进度存入SP
	 */
	public void saveAsSp() {
		SharedPreferences sp = getSharedPreferences(LASTPLAY, MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("musicName", music.getName());
		editor.putString("musicPath", music.getPath());
		editor.putString("musicAlbum", music.getAlbum());
		editor.putString("musicSinger", music.getSinger());
		// editor.putLong("musicSid", music.getSid());
		editor.putInt("musicAt", mediaPlayer.getCurrentPosition());
		editor.putString("musicDuration", music.getTime());
		editor.putInt("music_collect", music.getCollection());
		editor.putLong("musicIcon", music.getAlbum_id());
		editor.commit();

	}

	public Music getNowPlay() {
		if (music == null) {
			music = new Music();
		}
		SharedPreferences sp = getSharedPreferences(LASTPLAY, MODE_PRIVATE);
		inniMusic();
		Music defaultMusic = list.get(0);
		if (!mediaPlayer.isPlaying() && sp != null) {// 如果当前没有播放音乐
			if (defaultMusic != null) {
				// 获取上次结束时的播放记录，没有记录则播放文件中第一首歌

				music.setName(sp.getString("musicName", defaultMusic.getName()));
				music.setPath(sp.getString("musicPath", defaultMusic.getPath()));
				music.setAlbum(sp.getString("musicAlbum", defaultMusic.getAlbum()));
				music.setTime(sp.getString("musicDuration", defaultMusic.getTime()));
				music.setAlbum_id(sp.getLong("musicIcon", defaultMusic.getAlbum_id()));
				music.setSinger(sp.getString("musicSinger", defaultMusic.getSinger()));

				// music.setSid(sp.getLong("musicSid", defaultMusic.getSid()));
				music.setCollection(sp.getInt("music_collect", 0));
				mediaPlayer.seekTo(sp.getInt("musicAt", mediaPlayer.getCurrentPosition()));
			} else {// 如果music为空或者defaultmusic为空

				music.setName("当前没有播放音乐");
				music.setSinger("VMplayer");
				music.setPath("none");
				music.setCollection(2);

			}

		}

		return music;
	}
}
