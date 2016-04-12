package com.example.vmmusic.app.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.vmmusic.app.model.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awx19 on 2016/4/12.
 */
public class MediaUtil {


    /**
     * 回调
     */
    public interface CallBack {

        void onRequestComplete(List<Music> result);
    }

    public void doGetMusic(final Context context, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    List<Music> result = getMusic(context);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 获得手机音乐信息
     *
     * @param context
     * @return
     */
    private List<Music> getMusic(Context context) {
        List<Music> mList = new ArrayList<Music>();
        ContentResolver cr = context.getContentResolver();
        if (cr != null) {
            Cursor cursor = cr.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (null == cursor) {
                return null;
            }
            if (cursor.moveToFirst()) {
                do {
                    Music music = new Music();
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)); // 音乐id
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)); // 音乐标题
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)); // 艺术家
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)); // 专辑
                    String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)); // 时长
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)); // 文件大小
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
                    int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // 是否为音乐
                    if (isMusic != 0) { // 只把音乐添加到集合当中
                        music.setId(id);
                        music.setTitle(title);
                        music.setArtist(artist);
                        music.setAlbum(album);
                        music.setDisplayName(displayName);
                        music.setAlbumId(albumId);
                        music.setDuration(duration);
                        music.setSize(size);
                        music.setUrl(url);
                        mList.add(music);
                    }
                } while (cursor.moveToNext());
            }
        }
        return mList;

    }
}