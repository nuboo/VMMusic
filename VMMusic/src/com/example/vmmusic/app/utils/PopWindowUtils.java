package com.example.vmmusic.app.utils;



import com.example.vmmusic.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopWindowUtils {
	private Context context;
	private int layoutId;//layout 文件ID
	private Activity activity;
	private LayoutInflater inflater;
	private PopupWindow pop;
	private View view;
	
	public PopWindowUtils(Context context){
		this.context = context;
		activity = (Activity) context;
		inflater = LayoutInflater.from(context);
	}
	
	/**
	 * 评论弹窗
	 */
	public PopupWindow commentPop(){
		pop = new PopupWindow(context);
		view =inflater.inflate(R.layout.items_comment,null);
		  //设置background  和 focusable 以及touchable 使得点击外部popupWindow消失
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		TextView send=(TextView) view.findViewById(R.id.comment_send);
	
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			pop.dismiss();	
			}
		});
		return pop;


	}
}
