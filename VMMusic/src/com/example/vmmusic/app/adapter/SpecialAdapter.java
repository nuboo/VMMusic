package com.example.vmmusic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Special;
import java.util.List;



/**
 *
 * 专题adapter
 * Created by awx19 on 2016/4/8.
 */
public class SpecialAdapter extends BaseAdapter {
    List<Special> mList;
    Context mContext;
    LayoutInflater inflater;

    public SpecialAdapter(Context context, List<Special> list) {
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
        HandleView handleView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_special_list_view, null);
            handleView = new HandleView();
            handleView.special_relative = (RelativeLayout) convertView.findViewById(R.id.special_relative);
            handleView.special_name = (TextView) convertView.findViewById(R.id.special_name);
            handleView.special_type = (TextView) convertView.findViewById(R.id.special_type);
            convertView.setTag(handleView);
        } else {
            handleView = (HandleView) convertView.getTag();
        }
        handleView.special_relative.setBackgroundResource(mList.get(position).getSpecial_img());
        handleView.special_name.setText(mList.get(position).getSpecial_name());
        handleView.special_type.setText(mList.get(position).getSpecial_type());
        return convertView;
    }

    class HandleView {
        RelativeLayout special_relative;
        TextView special_name;
        TextView special_type;
    }
}
