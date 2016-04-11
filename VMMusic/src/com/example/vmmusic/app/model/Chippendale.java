package com.example.vmmusic.app.model;

/**
 * 推荐结构体
 * Created by awx19 on 2016/4/7.
 */
public class Chippendale {
    int img;
    String music_name;
    String singer;

    public int getImg() {
        return img;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setImg(int img) {

        this.img = img;
    }
}
