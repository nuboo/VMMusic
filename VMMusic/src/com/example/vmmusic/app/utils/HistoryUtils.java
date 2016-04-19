package com.example.vmmusic.app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.example.vmmusic.app.model.Music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class HistoryUtils {
	private SQLiteDatabase database;
	private Context context;
	
	private HistorySQLHepler historyHelper;
	private ContentValues contentValues;
	private Music music;

	public HistoryUtils(Context context) {
		this.context = context;
	}

	public SQLiteDatabase getDatabase() {
		historyHelper = new HistorySQLHepler(context);
		database = historyHelper.getWritableDatabase();
		return database;

	}

	public SQLiteDatabase getDatabaseByContext() {
		/*
		 * database=
		 * getApplication().getApplicationContext().openOrCreateDatabase(
		 * LocalSQLHelper.TABLE_NAME, MODE_WORLD_READABLE, null);
		 */
		database = context.openOrCreateDatabase(HistorySQLHepler.TABLE_NAME, Context.MODE_WORLD_READABLE, null);

		return database;

	}
	

	public SQLiteDatabase getDatabaseByStatic() {
		String name = HistorySQLHepler.TABLE_NAME;

		database = SQLiteDatabase
				.openOrCreateDatabase(Environment.getDataDirectory().toString() + File.separator + name, null);
		return database;
	}
	
	/**
	 * 获取播放历史记录
	 * 
	 * @param list
	 * @return
	 */
	public ArrayList<Music> getHistory(ArrayList<Music> list) {
		historyHelper = new HistorySQLHepler(context);
		database = historyHelper.getWritableDatabase();
		Cursor cursor = database.query(HistorySQLHepler.TABLE_NAME, null, null, null, null, null,
				HistorySQLHepler.ADD_TIME + " DESC");

		if (cursor != null) {
			Log.i("cursor", "notNUll");
			while (cursor.moveToNext()) {
				music = new Music();
				int idNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_ID);
				int id = cursor.getInt(idNum);
				int collectNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_COLLECT);
				int collect = cursor.getInt(collectNum);
				int sidNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_SID);
				int sid = cursor.getInt(sidNum);
				int titleNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_NAME);
				String title = cursor.getString(titleNum);
				int albumNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_ALBUM);
				String album = cursor.getString(albumNum);
				int singerNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_SINGER);
				String singer = cursor.getString(singerNum);
				int urlNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_PATH);
				String url = cursor.getString(urlNum);
				int duNum = cursor.getColumnIndex(HistorySQLHepler.MUSIC_DURATION);
				String du = cursor.getString(duNum);
				music.setId(id);
				Log.w("sql", title);
				// 系统ID 歌名 专辑 歌手 时长 路径
				music.setSid(sid);
				music.setName(title);
				music.setAlbum(album);
				music.setSinger(singer);
				music.setTime(du);
				music.setPath(url);
				music.setCollection(collect);
				list.add(music);

			}

		}
		cursor.close();
		return list;
	}
	
	
	/**
	 * 添加播放记录
	 * @param music
	 */
	public void addHistoryData(Music music) {
		historyHelper = new HistorySQLHepler(context);
		database = historyHelper.getWritableDatabase();
		long sid = music.getSid();
		Log.i("music", music.getSid() + "");
		String[] args = { String.valueOf(sid) };
		Cursor cursor = database.query(HistorySQLHepler.TABLE_NAME, null, HistorySQLHepler.MUSIC_SID + "=?", args, null,
				null, null);

		if (cursor == null || !cursor.moveToFirst()) {// 如果结果为空，不存在该历史记录，则添加到数据库中
			contentValues = putValues(music);
			Log.i("music", music.getName());
			database.insert(HistorySQLHepler.TABLE_NAME, null, contentValues);
		}
		cursor.close();
	}
	
	/**
	 * 删除历史记录
	 * 
	 * @param sid
	 */
	public void deleteHistory(long sid) {
		historyHelper = new HistorySQLHepler(context);
		database = historyHelper.getWritableDatabase();
		String[] args = { String.valueOf(sid) };
		database.delete(HistorySQLHepler.TABLE_NAME, HistorySQLHepler.MUSIC_SID + "=?", args);
	}
	
	
	/**
	 * 把music数据放入ContentValues中
	 * 
	 * @param music
	 */
	public ContentValues putValues(Music music) {
		ContentValues contentValues = new ContentValues();
		Date date = new Date();
		String nowTime = date.toString();
		
		contentValues.put(HistorySQLHepler.MUSIC_ID, music.getId());
		contentValues.put(HistorySQLHepler.MUSIC_SID, music.getSid());
		contentValues.put(HistorySQLHepler.MUSIC_ALBUM, music.getAlbum());
		contentValues.put(HistorySQLHepler.MUSIC_DURATION, music.getTime());
		contentValues.put(HistorySQLHepler.MUSIC_NAME, music.getName());
		contentValues.put(HistorySQLHepler.MUSIC_PATH, music.getPath());
		contentValues.put(HistorySQLHepler.MUSIC_SINGER, music.getSinger());
		contentValues.put(HistorySQLHepler.ADD_TIME, nowTime);
		contentValues.put(HistorySQLHepler.MUSIC_COLLECT, music.getCollection());
		
		return contentValues;
	}
}
