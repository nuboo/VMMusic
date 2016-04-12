package com.example.vmmusic.app.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.vmmusic.app.model.Music;

import java.io.File;
import java.util.ArrayList;

/** mp3文件工具类
 * Created by Administrator on 2016/4/11 0011.
 */
public class FileUtils {
    private String fileName = null;
    private String filePath = null;
    private Music music;



    /**
     * 获取音乐文件信息    歌曲名，歌手，专辑，文件大小，文件路径，播放时长
     * @param  contentResolver context.getgetContentResolver();
     * @param list ArrayList<Music>用于存储music
     *
     */
    public void getMediaInfo( ContentResolver contentResolver,ArrayList<Music> list){
        int i=0;

        Cursor cursor =contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if(cursor!=null){

            while (cursor.moveToNext()){
                music =new Music();

                int id=i;
                int titleNum=cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                String title=cursor.getString(titleNum);
                int albumNum=cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                String album=cursor.getString(albumNum);
                int singerNum=cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                String singer=cursor.getString(singerNum);
                int urlNum=cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                String url=cursor.getString(urlNum);
                music.setId(id);
                i++;
                Log.i("id",id+"");
                music.setName(title);
                music.setAlbum(album);
                music.setSinger(singer);
                Log.w("url",url);
                music.setPath(url);
                list.add(music);
            }
        }

    }




    /**
     * 获取SD卡内所有mp3格式文件
     *
     * @param list 用于存储Music的Arraylist
     * @return ArrayList<Music>;
     */
    public void getAllMusicFiles(ArrayList<Music> list) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//如果有SD卡

            File path = Environment.getExternalStorageDirectory();//SD卡根目录；
            File[] files = path.listFiles();//获取所有文件路径；
            setFileName(files, list);
        }

    }

    /**
     * 获取指定文件夹内的mp3格式文件
     *
     * @param list  用于存储Music的Arraylist
     * @param files 指定的文件夹名
     * @return ArrayList<Music>;
     */
    public void setFileName(File[] files, ArrayList<Music> list) {
        if(files!=null){
            for (File file : files) {
                if (!file.isFile()) {//如果是文件夹

                    setFileName(file.listFiles(), list);

                } else {//如果是文件
                    music = getMusic(file);
                    if(music!=null) {
                        list.add(music);
                    }
                }

            }

        }

    }

    /**
     * 获取mp3格式的Music 对象
     *
     * @param file
     * @return Music，如果没有结果，则返回null；
     */
    public Music getMusic(File file) {

        String name=file.getName();

        if (name.endsWith(".mp3")) {//如果是mp3文件
            music = new Music();
            fileName = name;

            filePath = file.getAbsolutePath();
            music.setName(fileName);
            music.setPath(filePath);
            Log.i("success", "");
        }

        return music;
    }


}