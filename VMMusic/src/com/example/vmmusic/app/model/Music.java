package com.example.vmmusic.app.model;

/**
 * 音乐
 * Created by awx19 on 2016/4/12.
 */
public class Music {
    private long id; // 歌曲ID
    private String title; // 歌曲名称
    private String album; // 专辑
    private long albumId;//专辑ID
    private String displayName; //显示名称
    private String artist; // 歌手名称
    private long duration; // 歌曲时长
    private long size; // 歌曲大小
    private String url; // 歌曲路径
    private String lrcTitle; // 歌词名称
    private String lrcSize; // 歌词大小

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getSize() {
        return size;
    }

    public String getLrcSize() {
        return lrcSize;
    }

    public void setLrcSize(String lrcSize) {
        this.lrcSize = lrcSize;
    }

    public String getLrcTitle() {

        return lrcTitle;
    }

    public void setLrcTitle(String lrcTitle) {
        this.lrcTitle = lrcTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;

    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getArtist() {

        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;

    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
