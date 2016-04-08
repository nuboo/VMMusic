package com.example.vmmusic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Music;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class MusicListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Music> list;
    private LayoutInflater inflater;

    public MusicListAdapter(){

    }
    public  MusicListAdapter(Context context,ArrayList<Music> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            view=inflater.inflate(R.layout.activity_music_list_items,null);
            holder=new Holder();
            holder.name=(TextView)view.findViewById(R.id.music_list_item_name);
            holder.singer=(TextView)view.findViewById(R.id.music_list_item_singer);
            view.setTag(holder);
        }
        holder=(Holder)view.getTag();
        Music music=list.get(i);
        holder.name.setText(music.getName());
        holder.singer.setText(music.getSinger());
        return view;
    }
    class Holder{
        TextView name;
        TextView singer;
    }
}
