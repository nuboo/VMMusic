package com.example.vmmusic.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.example.vmmusic.app.customview.ReWriteGridView;
import com.example.vmmusic.app.model.Channel;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.HttpUtils;
import com.example.vmmusic.app.utils.T;
import com.example.vmmusic.app.utils.TopSettiings;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 频道
 * Created by awx19 on 2016/4/8.
 */
public class ChannelFragment extends Fragment {
    GridView gridView;
    Activity activity;
    private int[] draws={R.drawable.channel_classify_01,R.drawable.channel_classify_02,R.drawable.proxy,R.drawable.channel_classify_03,R.drawable.channel_classify_01};
    private String[] classify={"音乐类型","场景","情绪","原创","影视"};
    private String[] musicName={"Try Everything","You're Not Alone","RAIN","秘密","恋无可恋","陪你度过的漫长岁月","吸引力","Cinderella","Another you","All of me","初初","Wi Ing Wi ling"};
    private String[] singers={"Shakira Trything","Chicago Chicago","金泰妍","金泰妍","古巨基","陈奕迅","金高恩","November real To Reel","Cascada.Everytime We Touch","朴灿烈","古巨基","朴灿烈"};
    private int[] all={R.drawable.channel_all_01,R.drawable.channel_all_02,R.drawable.channel_all_03,R.drawable.channel_all_04,R.drawable.channel_all_05,R.drawable.channel_all_06,R.drawable.channel_all_07,R.drawable.channel_all_08,R.drawable.channel_all_09,R.drawable.channel_all_10,R.drawable.channel_all_11,R.drawable.channel_all_12};
    ReWriteGridView reWriteGridView;
    private final String HTTP="music";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);
        activity = getActivity();
        setGridView(view);
    
        MyTask task=new MyTask();
        task.execute("");
        return view;
    }


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
        for (int i = 0; i < draws.length ; i++) {
            Channel channel = new Channel();
            channel.setItem_channel_grid_img(draws[i]);
            channel.setItem_channel_grid_name(classify[i]);
            list.add(channel);
        }
        return list;
    }
    
    class MyTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... arg0) {
			
			HttpUtils httpUtils=new HttpUtils();
			HashMap<String, String> map=new HashMap<String, String>();
			String result=httpUtils.NewpostData(HTTP, map);
			return result;
		}
    	@Override
    	protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub
    		Log.i("",result);
    		super.onPostExecute(result);
    	}
    }
}
