package com.example.vmmusic.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class SQLUtils {
    SQLiteDatabase database;
    Context context;
    LocalSQLHelper helper;
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

}
