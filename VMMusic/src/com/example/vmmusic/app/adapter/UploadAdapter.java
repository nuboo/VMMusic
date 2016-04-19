package com.example.vmmusic.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Music;
import com.example.vmmusic.app.utils.AlbumImgHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 上传音乐
 * @author Administrator
 *
 */
public class UploadAdapter extends BaseAdapter {
	private ArrayList<Music> list;
	private Context context;
	private LayoutInflater inflater;
	private HashMap<Integer, Boolean> map;
	
	public UploadAdapter() {
		
	}
	
	public UploadAdapter(Context context,ArrayList<Music> list) {
		this.list=list;
		this.context=context;
		this.inflater=LayoutInflater.from(context);
		inni();
	}
	
	public void inni(){
		
		for(int i=0;i<list.size();i++){
			getMap().put(i, false);
		}
	}
	public HashMap<Integer, Boolean> getMap() {
		if(map==null){
			map=new HashMap<Integer, Boolean>();
		}
		return map;
	}

	public void setMap(HashMap<Integer, Boolean> map) {
		this.map = map;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder holder;
		if(arg1==null){
			arg1=inflater.inflate(R.layout.activity_upload_items, null);
			holder=new Holder();
			holder.album=(ImageView)arg1.findViewById(R.id.grid_view_img);
			holder.name=(TextView)arg1.findViewById(R.id.grid_view_name);
			arg1.setTag(holder);
		}
		holder=(Holder)arg1.getTag();
		Music music=list.get(arg0);
		holder.name.setText(music.getName());
		holder.name.setSelected(getMap().get(arg0));
		Bitmap bm=AlbumImgHelper.getArtwork(context, music.getSid(), music.getAlbum_id(), true, true);
		holder.album.setImageBitmap(bm);
		return arg1;
	}
	
	class Holder{
		ImageView album;
		TextView name;
	}
}
