package com.example.vmmusic.app.model;

/**
 * 频道结构体
 * Created by awx19 on 2016/4/8.
 */
public class Channel {
    int item_channel_grid_img;//横向gridView的img
    String item_channel_grid_name;//横向gridView的分类

    public int getItem_channel_grid_img() {
        return item_channel_grid_img;
    }

    public String getItem_channel_grid_name() {
        return item_channel_grid_name;
    }

    public void setItem_channel_grid_img(int item_channel_grid_img) {
        this.item_channel_grid_img = item_channel_grid_img;
    }

    public void setItem_channel_grid_name(String item_channel_grid_name) {
        this.item_channel_grid_name = item_channel_grid_name;
    }
}
