package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChannelClassificationAdapter;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.customview.ReWriteGridView;
import com.example.vmmusic.app.model.Channel;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.T;
import com.example.vmmusic.app.utils.TopSettiings;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道分类
 * Created by awx19 on 2016/4/13.
 */
public class ChannelClassificationActivity extends Activity {
    GridView gridView;
    ReWriteGridView reWriteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_classification);
        setGridView();
        topSetting();

    }

    /**
     * 顶部设置
     */
    private void topSetting() {
        TopSettiings topSettiings = new TopSettiings(this);
        topSettiings.getTextLeft().setBackgroundResource(R.drawable.back);
        topSettiings.setTitle(getIntent().getStringExtra("name"));
        topSettiings.getTextRight().setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    /**
     * 横向gridView
     */
    private void setGridView() {
        gridView = (GridView) findViewById(R.id.classification_horizontal_grid);
        List<Channel> list = getDate();
        int size = list.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(-105); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数
        ChannelClassificationAdapter channelClassificationAdapter = new ChannelClassificationAdapter(list, getApplicationContext());
        gridView.setAdapter(channelClassificationAdapter);

/**
 * gridView
 */
        reWriteGridView = (ReWriteGridView) findViewById(R.id.classification_grid);
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(getApplicationContext(), getdate());
        reWriteGridView.setAdapter(chippendaleAdapter);
        reWriteGridView.setOnItemClickListener(onItemClickListener);

    }

    /**
     * gridView item监听
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            T.showShort(getApplicationContext(), "点击了" + parent);
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
        for (int i = 0; i < 10; i++) {
            Channel channel = new Channel();
            channel.setItem_channel_grid_name("思恋");
            list.add(channel);
        }
        return list;
    }
}
