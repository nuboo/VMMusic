package com.example.vmmusic.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.vmmusic.R;
import com.example.vmmusic.app.activity.MoreAndMoreActivity;
import com.example.vmmusic.app.activity.MusicListActivity;
import com.example.vmmusic.app.activity.MusicLyricPlayActivity;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.customview.HeaderGridView;
import com.example.vmmusic.app.customview.RoundImageView;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.HttpUtils;
import com.example.vmmusic.app.utils.TopSettiings;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by awx19 on 2016/4/8.
 */
public class MineFragment extends Fragment {
    RoundImageView mine_icon;//用户头像
    TextView mine_name;//用户名
    TextView mine_vip;//会员
    TextView mine_fans;//粉丝
    TextView mine_attention;//关注
    TextView mine_voice;//声音
    TextView local_music;//本地音乐
    TextView download_music;//下载音乐
    TextView i_like_music;//我喜欢
    TextView mine_more_and_more;//更多
    TextView mine_lately;//最近播放
    TableRow mine_guess_you_like;//猜你喜欢
    Intent intent;
    public static final String LOCAL="local music";
    private static final String MINEHTTP="http://192.168.15.247:90/api/personage";
    private static final String NUM="18883766232";
    private HashMap<String,String> map;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
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
        topSettiings.setTitle("我的");
        topSettiings.getTextRight().setOnClickListener(onClickListener);
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_mine, null);
        //从header中获得id
        mine_icon = (RoundImageView) header.findViewById(R.id.mine_icon);
        mine_name = (TextView) header.findViewById(R.id.mine_name);
        mine_vip = (TextView) header.findViewById(R.id.mine_vip);
        mine_fans = (TextView) header.findViewById(R.id.mine_fans);
        mine_attention = (TextView) header.findViewById(R.id.mine_attention);
        mine_voice = (TextView) header.findViewById(R.id.mine_voice);
        local_music = (TextView) header.findViewById(R.id.local_music);
        download_music = (TextView) header.findViewById(R.id.download_music);
        i_like_music = (TextView) header.findViewById(R.id.i_like_music);
        mine_more_and_more = (TextView) header.findViewById(R.id.mine_more_and_more);
        mine_lately = (TextView) header.findViewById(R.id.mine_lately);
        mine_guess_you_like = (TableRow) header.findViewById(R.id.mine_guess_you_like);
        local_music.setOnClickListener(onClickListener);
        download_music.setOnClickListener(onClickListener);
        i_like_music.setOnClickListener(onClickListener);
        mine_more_and_more.setOnClickListener(onClickListener);
        mine_lately.setOnClickListener(onClickListener);
        mine_guess_you_like.setOnClickListener(onClickListener);

        //推荐adapter
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(view.getContext(), getdate());
        HeaderGridView grid = (HeaderGridView) view.findViewById(R.id.mine_rewrite_grid_view);
        grid.addHeaderView(header);//添加headerView
        grid.setAdapter(chippendaleAdapter);
        
        map=new HashMap<String, String>();
        map.put("tel", NUM);
        MyTask task=new MyTask();
        task.execute("");
    }

    /**
     * 监听
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.public_top_right:
                	Intent intent = new Intent(getActivity(), MusicLyricPlayActivity.class);
                	getActivity().startActivity(intent);
                    break;
                case R.id.local_music://本地音乐
                	Intent local = new Intent(getActivity(), MusicListActivity.class);
                	local.putExtra(LOCAL, true);
                	getActivity().startActivity(local);	
                    break;
                case R.id.download_music://下载音乐

                    break;
                case R.id.i_like_music://我喜欢

                    break;
                case R.id.mine_more_and_more://更多
                    intent = new Intent(getActivity(), MoreAndMoreActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mine_lately://最近播放

                    break;
                case R.id.mine_guess_you_like://猜你喜欢

                    break;
            }
        }
    };


    /**
     * 假数据
     *
     * @return
     */
    private List<Chippendale> getdate() {
        List<Chippendale> list = new ArrayList<Chippendale>();
        for (int i = 0; i < 6; i++) {
            Chippendale chippendale = new Chippendale();
            chippendale.setImg(R.drawable.pci);
            chippendale.setMusic_name("遥远的她");
            chippendale.setSinger("Eeason");
            list.add(chippendale);
        }
        return list;
    }
    
    class MyTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpUtils httpUtils=new HttpUtils();
			String result=httpUtils.postData(MINEHTTP, map);
			return result;
		}
    	@Override
    	protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub
    		Log.w("resutl",result);
    		super.onPostExecute(result);
    	}
    }

}
