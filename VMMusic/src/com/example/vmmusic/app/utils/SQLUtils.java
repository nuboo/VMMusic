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

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class SQLUtils {
	private SQLiteDatabase database;
	private Context context;
	private LocalSQLHelper helper;
	private HistorySQLHepler historyHelper;
	private ContentValues contentValues;
	private Music music;
	private boolean isLocal;
	public SQLUtils(Context context) {
		this.context = context;
	}

	public SQLiteDatabase getDatabase() {
		helper = new LocalSQLHelper(context);
		database = helper.getWritableDatabase();
		return database;

	}

	public SQLiteDatabase getDatabaseByContext() {
		/*
		 * database=
		 * getApplication().getApplicationContext().openOrCreateDatabase(
		 * LocalSQLHelper.TABLE_NAME, MODE_WORLD_READABLE, null);
		 */
		database = context.openOrCreateDatabase(LocalSQLHelper.TABLE_NAME, Context.MODE_WORLD_READABLE, null);

		return database;

	}

	public SQLiteDatabase getDatabaseByStatic() {
		String name = LocalSQLHelper.TABLE_NAME;

		database = SQLiteDatabase
				.openOrCreateDatabase(Environment.getDataDirectory().toString() + File.separator + name, null);
		return database;
	}

	/**
	 * 添加数据库
	 * 
	 * @param music
	 */
	public Music addData(Music music) {
	
		database = getDatabase();
		int sid = music.getSid();
		Log.i("music", music.getSid() + "");
		String[] args = { String.valueOf(sid) };
		Cursor cursor = database.query(LocalSQLHelper.TABLE_NAME, null, LocalSQLHelper.MUSIC_SID + "=?", args, null,
				null, null);

		if (cursor == null || !cursor.moveToFirst()) {// 如果结果为空，不存在该记录，则添加到数据库中
			music.setCollection(0);
			contentValues = putValues(music);
			Log.i("music", music.getName());
			database.insert(LocalSQLHelper.TABLE_NAME, null, contentValues);
			
		}
		cursor.close();
		return music;
	}
	

	/**
	 * 更新数据库
	 * 
	 * @param music
	 */
	public void upDate(Music music) {
		database = getDatabase();
		ContentValues contentValues = putValues(music);
		String[] args = { String.valueOf(music.getId()) };
		database.update(LocalSQLHelper.TABLE_NAME, contentValues, LocalSQLHelper.MUSIC_ID + "=?", args);
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
		
		contentValues.put(LocalSQLHelper.MUSIC_ID, music.getId());
		contentValues.put(LocalSQLHelper.MUSIC_SID, music.getSid());
		contentValues.put(LocalSQLHelper.MUSIC_ALBUM, music.getAlbum());
		contentValues.put(LocalSQLHelper.MUSIC_DURATION, music.getTime());
		contentValues.put(LocalSQLHelper.MUSIC_NAME, music.getName());
		contentValues.put(LocalSQLHelper.MUSIC_PATH, music.getPath());
		contentValues.put(LocalSQLHelper.MUSIC_SINGER, music.getSinger());
		contentValues.put(LocalSQLHelper.ADD_TIME, nowTime);
		contentValues.put(LocalSQLHelper.MUSIC_COLLECT, music.getCollection());
		return contentValues;
	}

	/**
	 * 获取播放历史记录
	 * 
	 * @param list
	 * @return
	 */
	public ArrayList<Music> getAllFromSQL(ArrayList<Music> list) {
		database = getDatabase();
		Cursor cursor = database.query(LocalSQLHelper.TABLE_NAME, null, null, null, null, null,
				LocalSQLHelper.MUSIC_SID);

		if (cursor != null) {

			while (cursor.moveToNext()) {
				music = new Music();
				int idNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_ID);
				int id = cursor.getInt(idNum);
				int collectNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_COLLECT);
				int collect = cursor.getInt(collectNum);
				int sidNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_SID);
				int sid = cursor.getInt(sidNum);
				int titleNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_NAME);
				String title = cursor.getString(titleNum);
				int albumNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_ALBUM);
				String album = cursor.getString(albumNum);
				int singerNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_SINGER);
				String singer = cursor.getString(singerNum);
				int urlNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_PATH);
				String url = cursor.getString(urlNum);
				int duNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_DURATION);
				String du = cursor.getString(duNum);
				music.setId(id);

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
	 * 获取我的收藏
	 * 
	 * @param list
	 * @return
	 */
	public ArrayList<Music> getCollection(ArrayList<Music> list) {
		database = getDatabase();
		String[] args = { String.valueOf(1) };
		Cursor cursor = database.query(LocalSQLHelper.TABLE_NAME, null, LocalSQLHelper.MUSIC_COLLECT + "=?", args, null,
				null, null);

		if (cursor != null) {
			Log.i("cursor", "notNUll");
			while (cursor.moveToNext()) {
				music = new Music();
				int idNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_ID);
				int id = cursor.getInt(idNum);
				int collectNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_COLLECT);
				int collect = cursor.getInt(collectNum);
				int sidNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_SID);
				int sid = cursor.getInt(sidNum);
				int titleNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_NAME);
				String title = cursor.getString(titleNum);
				int albumNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_ALBUM);
				String album = cursor.getString(albumNum);
				int singerNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_SINGER);
				String singer = cursor.getString(singerNum);
				int urlNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_PATH);
				String url = cursor.getString(urlNum);
				int duNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_DURATION);
				String du = cursor.getString(duNum);
				music.setId(id);

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
	 * 删除历史记录
	 * 
	 * @param sid
	 */
	public void deleteBySid(int sid) {
		database = getDatabase();

		String[] args = { String.valueOf(sid) };
		database.delete(LocalSQLHelper.TABLE_NAME, LocalSQLHelper.MUSIC_SID + "=?", args);
	}

	
	public void updateCollection(int sid, int collection) {
		database = getDatabase();
		String[] args = { String.valueOf(sid) };
		Log.i("sid", args+"-------sid"+sid);
		contentValues = new ContentValues();

		contentValues.put(LocalSQLHelper.MUSIC_COLLECT, collection);
		database.update(LocalSQLHelper.TABLE_NAME, contentValues, LocalSQLHelper.MUSIC_SID + "=?", args);
	}
	/**
	 * 该歌曲是否已经被收藏
	 * @param music
	 * @return
	 */
	public boolean isCollection(Music music){
		boolean isNot=false;
		database = getDatabase();
		String[] args = { String.valueOf(music.getSid()) };
		Cursor cursor = database.query(LocalSQLHelper.TABLE_NAME, null, LocalSQLHelper.MUSIC_SID + "=?", args, null,
				null, null);
	
			while (cursor.moveToNext()) {
				
				
				int collectNum = cursor.getColumnIndex(LocalSQLHelper.MUSIC_COLLECT);
				int collect = cursor.getInt(collectNum);
				Log.i("isCollec", "d"+collect);
				if(collect==1){
					isNot=true;
				}
			}
		
		return isNot;
	}
	
	 /**
     * 
     * @param music
     * @return
     */
    public boolean isLocalMusic(Music music){
    	database=getDatabase();
    	int sid = music.getSid();
		Log.i("music", music.getSid() + "");
		String[] args = { String.valueOf(sid) };
    	Cursor cursor = database.query(LocalSQLHelper.TABLE_NAME, null, LocalSQLHelper.MUSIC_SID + "=?", args, null,
				null, null);
    	if(cursor.moveToFirst()){//如果数据库不存在
    		isLocal=true;
    	}else{
    		isLocal=false;
    	}
    	return isLocal;
    }
}
