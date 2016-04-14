package com.example.vmmusic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Channel;

import java.util.HashMap;
import java.util.List;

/**
 * 频道分类adapter
 * Created by awx19 on 2016/4/13.
 */
public class ChannelClassificationAdapter extends BaseAdapter {
    Context context;// 上下文（当前加载页面用来获取view中控件的对象）
    LayoutInflater inflater;// 布局填充器
    List<Channel> data;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个

    public ChannelClassificationAdapter(List<Channel> data, Context context) {// 构造器
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        HolderView holderView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_channel_classification_horizontal_grid_view, null);
            holderView = new HolderView();
            holderView.classification_radio_btn = (RadioButton) convertView.findViewById(R.id.classification_radio_btn);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        holderView.classification_radio_btn.setText(data.get(position).getItem_channel_grid_name());

        final RadioButton radio = (RadioButton) convertView.findViewById(R.id.classification_radio_btn);
        holderView.classification_radio_btn = radio;
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        holderView.classification_radio_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), radio.isChecked());
                ChannelClassificationAdapter.this.notifyDataSetChanged();
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;
        holderView.classification_radio_btn.setChecked(res);


        return convertView;
    }

    class HolderView {
        RadioButton classification_radio_btn;
    }
}
