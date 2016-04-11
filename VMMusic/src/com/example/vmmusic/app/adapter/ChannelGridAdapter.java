package com.example.vmmusic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.RoundImageView;
import com.example.vmmusic.app.model.Channel;

import java.util.List;

/**
 *
 * 频道分类adapter
 * Created by awx19 on 2016/4/8.
 */
public class ChannelGridAdapter extends BaseAdapter {
    Context context;// 上下文（当前加载页面用来获取view中控件的对象）
    LayoutInflater inflater;// 布局填充器
    List<Channel> data;

    public ChannelGridAdapter() {// 构造器

    }

    public ChannelGridAdapter(List<Channel> data, Context context) {// 构造器
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        HolderView holderView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_channel_horizontal_grid_view, null);
            holderView = new HolderView();
            holderView.item_channel_grid_img = (RoundImageView) convertView.findViewById(R.id.item_channel_grid_img);
            holderView.item_channel_grid_name = (TextView) convertView.findViewById(R.id.item_channel_grid_name);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        holderView.item_channel_grid_img.setImageResource(data.get(position).getItem_channel_grid_img());
        holderView.item_channel_grid_name.setText(data.get(position).getItem_channel_grid_name());
        return convertView;
    }

    class HolderView {
        RoundImageView item_channel_grid_img;
        TextView item_channel_grid_name;
    }
}
