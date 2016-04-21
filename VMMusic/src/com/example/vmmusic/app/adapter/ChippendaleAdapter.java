package com.example.vmmusic.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Chippendale;

import java.util.List;

/**
 * 推荐adapter
 * Created by awx19 on 2016/4/7.
 */
public class ChippendaleAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<Chippendale> mList;

    public ChippendaleAdapter(Context context, List<Chippendale> list) {
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HandView handView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_chippendale_grid_view, null);
            handView = new HandView();
            handView.item_chippendale_grid_img = (ImageView) convertView.findViewById(R.id.item_chippendale_grid_img);
            handView.music_name = (TextView) convertView.findViewById(R.id.music_name);
            handView.singer = (TextView) convertView.findViewById(R.id.singer);
            convertView.setTag(handView);
        } else {
            handView = (HandView) convertView.getTag();
        }


        handView.item_chippendale_grid_img.setBackgroundResource(mList.get(position).getImg());
        if (mList.get(position).getMusic_name().equals("")) {
            handView.music_name.setVisibility(View.GONE);
        } else {
            handView.music_name.setText(mList.get(position).getMusic_name());
        }
        if (mList.get(position).getSinger().equals("")) {
            handView.singer.setVisibility(View.GONE);
        } else {
            handView.singer.setText(mList.get(position).getSinger());
        }
        return convertView;
    }

    class HandView {
        ImageView item_chippendale_grid_img;
        TextView music_name;
        TextView singer;
    }
}
