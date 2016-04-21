package com.example.vmmusic.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChannelClassificationAdapter;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.customview.ReWriteGridView;
import com.example.vmmusic.app.model.Channel;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.T;
import com.example.vmmusic.app.utils.TopSettiings;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 频道分类
 * Created by awx19 on 2016/4/13.
 */
public class ChannelClassificationActivity extends Activity {
	GridView gridView;
    ReWriteGridView reWriteGridView;
    private String[] musicName={"Try Everything","You're Not Alone","RAIN","秘密","恋无可恋","陪你度过的漫长岁月","吸引力","Cinderella","Another you","All of me","初初","Wi Ing Wi ling"};
    private String[] singers={"Shakira Trything","Chicago Chicago","金泰妍","金泰妍","古巨基","陈奕迅","金高恩","November real To Reel","Cascada.Everytime We Touch","朴灿烈","古巨基","朴灿烈"};
    private int[] all={R.drawable.channel_all_01,R.drawable.channel_all_02,R.drawable.channel_all_03,R.drawable.channel_all_04,R.drawable.channel_all_05,R.drawable.channel_all_06,R.drawable.channel_all_07,R.drawable.channel_all_08,R.drawable.channel_all_09,R.drawable.channel_all_10,R.drawable.channel_all_11,R.drawable.channel_all_12};
    private String[] title={"全部","伤感","安静","欢快","思恋","慵懒","宣泄"};
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
        TextView left=topSettiings.setLeft(null, getResources().getDrawable(R.drawable.back), false);
        left.setOnClickListener(onClickListener);
        topSettiings.setTitle(getIntent().getStringExtra("name"));
        

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	finish();
        }
    };

    /**
     * 横向gridView
     */
    private void setGridView() {
        gridView = (GridView) findViewById(R.id.classification_horizontal_grid);
        List<Channel> list = getDate();
        int size = list.size();
        int length = 55;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
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
          //  T.showShort(getApplicationContext(), "点击了" + parent);
        }
    };

    /**
     * 假数据
     *
     * @return
     */
    private List<Chippendale> getdate() {
        List<Chippendale> list = new ArrayList<Chippendale>();
        for (int i = 0; i < all.length; i++) {
            Chippendale chippendale = new Chippendale();
            chippendale.setImg(all[i]);
            chippendale.setMusic_name(musicName[i]);
            chippendale.setSinger(singers[i]);
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
        for (int i = 0; i < title.length; i++) {
            Channel channel = new Channel();
            channel.setItem_channel_grid_name(title[i]);
            list.add(channel);
        }
        return list;
    }
}
