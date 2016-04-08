package com.example.vmmusic.app.model;

/** 歌曲属性
 * Created by Administrator on 2016/4/8 0008.
 */
public class Music {
    private String singer;//歌手
    private String name;//歌曲名
    private String path;//本地路径
    private String url;//网络路径
    private String downTime;//下载时间
    private boolean collection;//是否收藏

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }
}
