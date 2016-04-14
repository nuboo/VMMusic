package com.example.vmmusic.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.vmmusic.R;
import com.example.vmmusic.app.activity.ChannelClassificationActivity;
import com.example.vmmusic.app.activity.MusicListActivity;
import com.example.vmmusic.app.adapter.ChannelGridAdapter;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.customview.HeaderGridView;
import com.example.vmmusic.app.customview.ReWriteGridView;
import com.example.vmmusic.app.model.Channel;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.T;
import com.example.vmmusic.app.utils.TopSettiings;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道
 * Created by awx19 on 2016/4/8.
 */
public class ChannelFragment extends Fragment {
    GridView gridView;
    Activity activity;
    ReWriteGridView reWriteGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);
        activity = getActivity();
        setGridView(view);
        topSetting(view);
        return view;
    }

    /**
     * 顶部设置
     *
     * @param view
     */
    private void topSetting(View view) {
        TopSettiings topSettiings = new TopSettiings(view);
        topSettiings.getTextLeft().setVisibility(View.GONE);
        topSettiings.setTitle("频道");
        topSettiings.getTextRight().setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent intent = new Intent(getActivity(), MusicListActivity.class);
        	getActivity().startActivity(intent);
        }
    };

    /**
     * 横向gridView
     *
     * @param view
     */
    private void setGridView(View view) {
        gridView = (GridView) view.findViewById(R.id.horizontal_grid);
        List<Channel> list = getDate();
        int size = list.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数
        ChannelGridAdapter channelGridAdapter = new ChannelGridAdapter(list, activity);
        gridView.setAdapter(channelGridAdapter);
        gridView.setOnItemClickListener(listener);
/**
 * 全部分类gridView
 */
        reWriteGridView = (ReWriteGridView) view.findViewById(R.id.channel_grid);
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(view.getContext(), getdate());
        reWriteGridView.setAdapter(chippendaleAdapter);
        reWriteGridView.setOnItemClickListener(onItemClickListener);

    }


    /**
     * 横向gridView item监听
     */
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), ChannelClassificationActivity.class);
            intent.putExtra("name", getDate().get(position).getItem_channel_grid_name());
            startActivity(intent);
        }
    };

    /**
     * 全部分类gridView item监听
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            T.showShort(getContext(), "点击了" + position);
        }
    };

    /**
     * 假数据
     *
     * @return
     */
    private List<Chippendale> getdate() {
        List<Chippendale> list = new ArrayList<Chippendale>();
        for (int i = 0; i < 20; i++) {
            Chippendale chippendale = new Chippendale();
            chippendale.setImg(R.drawable.pci);
            chippendale.setMusic_name("遥远的她");
            chippendale.setSinger("Eeason");
            list.add(chippendale);
        }
        return list;
    }

    /**
     * 假数据
     *
     * @return
     */
    private List<Channel> getDate() {
        List<Channel> list = new ArrayList<Channel>();
        for (int i = 0; i < 6; i++) {
            Channel channel = new Channel();
            channel.setItem_channel_grid_img(R.drawable.proxy);
            channel.setItem_channel_grid_name("音乐分类");
            list.add(channel);
        }
        return list;
    }
}
