package com.example.vmmusic.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** 本地数据库hepler 用于缓存 Music和用户数据 
 * Created by Administrator on 2016/4/11 0011.
 */
public class LocalSQLHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME="FINAL_VM_SQL";//表格名称
    public static final int VIRVIOSN=1;//版本号


    public static final String MUSIC_ID="music_id";//id
    public static final String MUSIC_SID="music_sid";//系统ID
    public static final String MUSIC_NAME="music_name";//歌名
    public static final String MUSIC_PATH="music_path";//路径
    public static final String MUSIC_SINGER="music_singer";//歌手
    public static final String MUSIC_ALBUM="music_album";//专辑
    public static final String MUSIC_DURATION="music_duration";//时长
    public static final String MUSIC_COLLECT="music_collect";//收藏
    public static final String ADD_TIME="add_time";//添加时间

    public LocalSQLHelper(Context context){
        super(context, TABLE_NAME, null, VIRVIOSN);
    }
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        String sql="CREATE TABLE "+TABLE_NAME+" ( "+MUSIC_ID+" int not null  PRIMARY KEY, "

                +MUSIC_SID+" int , "
                 +MUSIC_COLLECT+" int , "
                +MUSIC_NAME+" varchar(140) , "
                +MUSIC_PATH+" varchar(140) , "
                +MUSIC_SINGER+" varchar(140) , "
                +MUSIC_ALBUM+" varchar(140) , "
                +ADD_TIME+" varchar(140) , "
                +MUSIC_DURATION+" varchar(140) )";


        arg0.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        String sql="DROP TABLE IS EXISTS "+TABLE_NAME;
        arg0.execSQL(sql);
        onCreate(arg0);
    }

}
