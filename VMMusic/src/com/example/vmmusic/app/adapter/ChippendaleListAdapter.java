package com.example.vmmusic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.RoundImageView;
import com.example.vmmusic.app.model.Attention;

import java.util.List;

/**
 * 关注adapter
 * Created by awx19 on 2016/4/12.
 */
public class ChippendaleListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<Attention> mList;

    public ChippendaleListAdapter(Context context, List<Attention> list) {
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

    HeaderView headerView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_chippendale_list_view, null);
            headerView = new HeaderView();
            headerView.item_chippendale_list_img = (RoundImageView) convertView.findViewById(R.id.item_chippendale_list_img);
            headerView.item_chippendale_list_name = (TextView) convertView.findViewById(R.id.item_chippendale_list_name);
            headerView.item_chippendale_list_time = (TextView) convertView.findViewById(R.id.item_chippendale_list_time);
            headerView.item_chippendale_list_background = (ImageView) convertView.findViewById(R.id.item_chippendale_list_background);
            headerView.item_chippendale_list_collection = (TextView) convertView.findViewById(R.id.item_chippendale_list_collection);
            headerView.item_chippendale_list_song_name = (TextView) convertView.findViewById(R.id.item_chippendale_list_song_name);
            headerView.item_chippendale_list_singer = (TextView) convertView.findViewById(R.id.item_chippendale_list_singer);
            headerView.item_chippendale_list_play = (CheckBox) convertView.findViewById(R.id.item_chippendale_list_play);
            headerView.item_chippendale_list_seek_bar = (SeekBar) convertView.findViewById(R.id.item_chippendale_list_seek_bar);
            headerView.item_chippendale_list_content = (TextView) convertView.findViewById(R.id.item_chippendale_list_content);
            headerView.item_chippendale_list_thumb_up = (TextView) convertView.findViewById(R.id.item_chippendale_list_thumb_up);
            headerView.item_chippendale_list_comments = (TextView) convertView.findViewById(R.id.item_chippendale_list_comments);
            headerView.item_chippendale_list_share = (TextView) convertView.findViewById(R.id.item_chippendale_list_share);
            convertView.setTag(headerView);
        } else {
            headerView = (HeaderView) convertView.getTag();
        }
        headerView.item_chippendale_list_img.setBackgroundResource(mList.get(position).getIcon());
        headerView.item_chippendale_list_name.setText(mList.get(position).getName());
        headerView.item_chippendale_list_time.setText(mList.get(position).getTime());
        headerView.item_chippendale_list_background.setBackgroundResource(mList.get(position).getBackground());
        headerView.item_chippendale_list_song_name.setText(mList.get(position).getSong_name());
        headerView.item_chippendale_list_singer.setText(mList.get(position).getSinger());
        headerView.item_chippendale_list_content.setText(mList.get(position).getContent());
        headerView.item_chippendale_list_thumb_up.setText(mList.get(position).getThumb_up());
        headerView.item_chippendale_list_comments.setText(mList.get(position).getComments());
        headerView.item_chippendale_list_share.setText(mList.get(position).getShare());

        //设置监听
        headerView.item_chippendale_list_collection.setOnClickListener(onClickListener);
        headerView.item_chippendale_list_thumb_up.setOnClickListener(onClickListener);
        headerView.item_chippendale_list_comments.setOnClickListener(onClickListener);
        headerView.item_chippendale_list_share.setOnClickListener(onClickListener);
        headerView.item_chippendale_list_play.setOnCheckedChangeListener(onCheckedChangeListener);
        headerView.item_chippendale_list_seek_bar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        return convertView;
    }

    /**
     * 监听事件
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_chippendale_list_collection://收藏
                    break;
                case R.id.item_chippendale_list_thumb_up://赞
                    break;
                case R.id.item_chippendale_list_comments://评论
                    break;
                case R.id.item_chippendale_list_share://分享
                    break;
            }
        }
    };
    /**
     * CheckBox选择监听
     */
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {//播放音乐

            } else {//暂停

            }
        }
    };
    /**
     * 进度条监听
     */
    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        /**
         *  seekBar滑块滑动时调用
         */

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        /**
         * 开始滑动时调用
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        /**
         * 停止滑动时调用
         * @param seekBar
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    class HeaderView {
        RoundImageView item_chippendale_list_img;
        TextView item_chippendale_list_name;
        TextView item_chippendale_list_time;
        ImageView item_chippendale_list_background;
        TextView item_chippendale_list_collection;
        TextView item_chippendale_list_song_name;
        TextView item_chippendale_list_singer;
        CheckBox item_chippendale_list_play;
        SeekBar item_chippendale_list_seek_bar;
        TextView item_chippendale_list_content;
        TextView item_chippendale_list_thumb_up;
        TextView item_chippendale_list_comments;
        TextView item_chippendale_list_share;

    }


}
