package com.example.vmmusic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Mp3Info;

import java.util.List;

/**
 * 选择音乐adapter
 * Created by awx19 on 2016/4/12.
 */
public class ChooseMusicAdapter extends BaseAdapter {
    Context mContext;
    List<Mp3Info> mList;
    LayoutInflater inflater;

    public ChooseMusicAdapter(Context context, List<Mp3Info> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeaderView headerView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_choose_music_list_view, null);
            headerView = new HeaderView();
            headerView.item_choose_music_name = (TextView) convertView.findViewById(R.id.item_choose_music_name);
            headerView.item_choose_music_singer = (TextView) convertView.findViewById(R.id.item_choose_music_singer);
            convertView.setTag(headerView);
        } else {
            headerView = (HeaderView) convertView.getTag();
        }
        headerView.item_choose_music_name.setText(mList.get(position).getDisplayName());
        headerView.item_choose_music_singer.setText(mList.get(position).getArtist());
        return convertView;
    }

    class HeaderView {
        TextView item_choose_music_name;
        TextView item_choose_music_singer;
    }
}
