package com.example.vmmusic.app.activity;

import java.util.ArrayList;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.UploadAdapter;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.FileUtils;
import com.example.vmmusic.app.utils.TopSettiings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class UploadActivity extends Activity{
	private GridView gridView;
	private UploadAdapter adapter;
	private ArrayList<Music> list;
	private Music music;
	private FileUtils fileUtils;
	private TopSettiings topSettings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		inni();
	}
	/**
	 * 初始化页面
	 */
	private void inni() {
		if(list==null){
			list=new ArrayList<Music>();
		}
		gridView=(GridView) findViewById(R.id.upload_girdview);
		fileUtils=new FileUtils();
		topSettings=new TopSettiings(this);
		topSettings.setTitle("选择要上传的音乐");
		TextView right=topSettings.setRight("完成", null, true);
		TextView left=topSettings.setLeft(null, getResources().getDrawable(R.drawable.back), false);
		left.setOnClickListener(listener);
		right.setOnClickListener(listener);
		fileUtils.getMediaInfo(this, list);
		adapter=new UploadAdapter(this, list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				adapter.inni();
				adapter.getMap().put(arg2, true);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.public_top_right:
				
				break;
			case R.id.public_top_left:
				finish();
				break;

			default:
				break;
			}
			
		}
	};
}
