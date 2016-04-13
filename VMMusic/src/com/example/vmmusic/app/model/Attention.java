package com.example.vmmusic.app.model;

/**
 * 关注结构体
 * Created by awx19 on 2016/4/12.
 */
public class Attention {
    int icon;//用户头像
    String name;//用户名
    String time;//时间
    int background;//歌曲背景图
    String song_name;//歌名
    String singer;//歌手
    int seekBar;//歌曲进度
    String content;//关注内容
    String thumb_up;//赞
    String comments;//评论
    String share;//分享

    public int getBackground() {
        return background;
    }

    public int getIcon() {
        return icon;
    }

    public int getSeekBar() {
        return seekBar;
    }

    public String getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getShare() {
        return share;
    }

    public String getSinger() {
        return singer;
    }

    public String getSong_name() {
        return song_name;
    }

    public String getThumb_up() {
        return thumb_up;
    }

    public String getTime() {
        return time;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeekBar(int seekBar) {
        this.seekBar = seekBar;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public void setThumb_up(String thumb_up) {
        this.thumb_up = thumb_up;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
