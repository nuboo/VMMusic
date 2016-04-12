package com.example.vmmusic.app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.vmmusic.app.model.Music;

import java.io.File;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class SQLUtils {
    SQLiteDatabase database;
    Context context;
    LocalSQLHelper helper;
    ContentValues contentValues;
    public SQLUtils(Context context){
        this.context=context;
    }

    public SQLiteDatabase getDatabase(){
        helper=new LocalSQLHelper(context);
        database=helper.getWritableDatabase();
        return database;

    }
    public SQLiteDatabase getDatabaseByContext(){
       /* database=
                   getApplication().getApplicationContext().openOrCreateDatabase(LocalSQLHelper.TABLE_NAME, MODE_WORLD_READABLE, null);
       */
        database=
                context.openOrCreateDatabase(LocalSQLHelper.TABLE_NAME, Context.MODE_WORLD_READABLE, null);

        return database;

    }
    public SQLiteDatabase getDatabaseByStatic(){
        String name = LocalSQLHelper.TABLE_NAME;

        database = SQLiteDatabase
                .openOrCreateDatabase(Environment.getDataDirectory().toString() + File.separator + name, null);
        return database;
    }

    /**
     * 添加数据库
     * @param music
     */
    public void addData(Music music){
        database=getDatabase();
        int sid=music.getSid();
        String[] args={String.valueOf(sid)};
        Cursor cursor=database.query(LocalSQLHelper.TABLE_NAME, null, LocalSQLHelper.MUSIC_SID + "=?", args, null, null, null);
        if(cursor==null) {
            contentValues = putValues(music);
            Log.i("music",music.getName());
            database.insert(LocalSQLHelper.TABLE_NAME, null, contentValues);
        }
    }

    /**
     * 更新数据库
     * @param music
     */
    public void upDate(Music music){
        database=getDatabase();
        ContentValues contentValues= putValues(music);
        String[] args = {String.valueOf(music.getId())};
        database.update(LocalSQLHelper.TABLE_NAME,contentValues,LocalSQLHelper.MUSIC_ID+"=?",args);
    }


    /**
     * 把music数据放入ContentValues中
     * @param music
     */
    public ContentValues putValues(Music music){
        ContentValues contentValues=new ContentValues();
        contentValues.put(LocalSQLHelper.MUSIC_ID,music.getId());
        contentValues.put(LocalSQLHelper.MUSIC_SID,music.getSid());
        contentValues.put(LocalSQLHelper.MUSIC_ALBUM,music.getAlbum());
        contentValues.put(LocalSQLHelper.MUSIC_DURATION,music.getTime());
        contentValues.put(LocalSQLHelper.MUSIC_NAME,music.getName());
        contentValues.put(LocalSQLHelper.MUSIC_PATH,music.getPath());
        contentValues.put(LocalSQLHelper.MUSIC_SINGER,music.getSinger());
        return  contentValues;
    }
}
