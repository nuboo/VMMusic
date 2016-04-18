package com.example.vmmusic.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/** 歌曲属性
 * Created by Administrator on 2016/4/8 0008.
 */
public class Music  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String singer;//歌手
    private String name;//歌曲名
    private String path;//本地路径
    private String url;//网络路径
    private String downTime;//下载时间
    private int collection;//是否收藏  1为收藏 0不为收藏
    private String album;//专辑
    private String time;//时长
    private int id;//数据库ID
    private int sid;//系统ID

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

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

    public int getCollection() {
    	
        return collection;
    }
    /**
     *   1为收藏 0不为收藏
     * @param collection
     */
    public void setCollection(int collection) {
        this.collection = collection;
    }
    public boolean isCollection(){
    	if(collection==1){
    		return true;
    	}else{
    		return false;
    	}
    	
    }

    /**
     * 根据ArrayList<music>  按专辑分类
     * @param list
     * @return  HashMap<String,ArrayList<Music>>;
     */
    public static   HashMap<String,ArrayList<Music>> sortByAlbum(ArrayList<Music> list){
        HashMap<String,ArrayList<Music>> musics =new HashMap<String,ArrayList<Music>>();
        for(Music music:list){
           String album =music.getAlbum();
            if(musics.containsKey(album)){
              musics.get(album).add(music);//如果有该专辑，则加入
            }else{//没有，则添加
                ArrayList<Music> mList=new ArrayList<Music>();
                mList.add(music);
                musics.put(album,mList);
            }
        }

        return musics;
    }

    /**
     * 根据ArrayList<music>  按歌手分类
     * @param list
     * @return  HashMap<String,ArrayList<Music>>;
     */
    public static   HashMap<String,ArrayList<Music>> sortBySinger(ArrayList<Music> list){
        HashMap<String,ArrayList<Music>> musics =new HashMap<String,ArrayList<Music>>();
        for(Music music:list){
            String singer =music.getSinger();
            if(musics.containsKey(singer)){
                musics.get(singer).add(music);//如果有该专辑，则加入
            }else{//没有，则添加
                ArrayList<Music> mList=new ArrayList<Music>();
                mList.add(music);
                musics.put(singer,mList);
            }
        }

        return musics;
    }

}
