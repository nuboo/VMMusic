package com.example.vmmusic.app.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.vmmusic.app.model.Music;

import java.io.File;
import java.util.ArrayList;

/**
 * mp3文件工具类
 * Created by Administrator on 2016/4/11 0011.
 */
public class FileUtils {
    private String fileName = null;
    private String filePath = null;
    private Music music;


    /**
     * 获取音乐文件信息    歌曲名，歌手，专辑，文件大小，文件路径，播放时长
     *
     * @param context context.getContentResolver();
     * @param list    ArrayList<Music>用于存储music
     */
    public void getMediaInfo(Context context, ArrayList<Music> list) {
        int i = 0;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media._ID);
        SQLUtils sqlUtils=new SQLUtils(context);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                music = new Music();

                int id = i;
                int sidNum = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int sid = cursor.getInt(sidNum);
                int titleNum = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                String title = cursor.getString(titleNum);
                int albumNum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                String album = cursor.getString(albumNum);
                int singerNum = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                String singer = cursor.getString(singerNum);
                int urlNum = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                String url = cursor.getString(urlNum);
                int duNum = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                String du = cursor.getString(duNum);
               
                music.setId(id);
                i++;
                //系统ID  歌名   专辑  歌手  时长  路径
                music.setSid(sid);
                music.setName(title);
                music.setAlbum(album);
                music.setSinger(singer);
                music.setTime(du);
                music.setPath(url);
               
                music= sqlUtils.addData(music);//如果第一次添加。添加成功则设置收藏不为零
                list.add(music);
               

            }
            cursor.close();
        }

    }


    /**
     * 获取音乐文件信息    歌曲名，歌手，专辑，文件大小，文件路径，播放时长
     *
     * @param contentResolver context.getContentResolver();
     * @param list            ArrayList<Music>用于存储music
     */
    public void getMediaInfoByResolver(ContentResolver contentResolver, ArrayList<Music> list) {
        int i = 0;

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media._ID);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                music = new Music();

                int id = i;
                int sidNum = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int sid = cursor.getInt(sidNum);
                int titleNum = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                String title = cursor.getString(titleNum);
                int albumNum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                String album = cursor.getString(albumNum);
                int singerNum = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                String singer = cursor.getString(singerNum);
                int urlNum = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                String url = cursor.getString(urlNum);
                int duNum = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                String du = cursor.getString(duNum);
                music.setId(id);
                i++;
                //系统ID  歌名   专辑  歌手  时长  路径
                music.setSid(sid);
                music.setName(title);
                music.setAlbum(album);
                music.setSinger(singer);
                music.setTime(du);
                music.setPath(url);
               
                list.add(music);


            }
            cursor.close();
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
        if (files != null) {
            for (File file : files) {
                if (!file.isFile()) {//如果是文件夹
                    setFileName(file.listFiles(), list);
                } else {//如果是文件
                    music = getMusic(file);
                    if (music != null) {
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
        String name = file.getName();
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