package com.example.vmmusic.app.utils;

import android.os.Environment;
import android.util.Log;

import com.example.vmmusic.app.model.Music;

import java.io.File;
import java.util.ArrayList;

/** mp3文件工具类
 * Created by Administrator on 2016/4/11 0011.
 */
public class FileUtils {
    String fileName = null;
    String filePath = null;
    Music music;



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