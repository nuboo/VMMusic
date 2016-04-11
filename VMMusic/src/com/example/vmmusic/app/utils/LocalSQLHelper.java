package com.example.vmmusic.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 本地数据库hepler 用于缓存 Music和用户数据
 * Created by Administrator on 2016/4/11 0011.
 */
public class LocalSQLHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME="VMplayerLocal";//表格名称
    public static final int VIRVIOSN=1;//版本号
    public LocalSQLHelper(Context context){
        super(context, TABLE_NAME, null, VIRVIOSN);
    }
    @Override
    public void onCreate(SQLiteDatabase arg0) {
   /*     String sql="CREATE TABLE "+TABLE_NAME+"(DIARY_ID varchar[10] not null  PRIMARY KEY, "
                +"WEATHER varchar[140] , "
                +"Date varchar[12] not null)";
        arg0.execSQL(sql);*/
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        String sql="DROP TABLE IS EXISTS "+TABLE_NAME;
        arg0.execSQL(sql);
        onCreate(arg0);
    }

}
