package com.example.vmmusic.app.activity;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.GesturePassword;
import com.example.vmmusic.app.utils.T;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GestureActivity extends Activity {
	LinearLayout layout;
	Button done, reset;
	GesturePassword gesturePassword;
	SharedPreferences sp;
	TextView newPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture);
		inni();
	}

	private void inni() {
		layout = (LinearLayout) findViewById(R.id.gesture);
		gesturePassword = new GesturePassword(this);
		layout.addView(gesturePassword);
		newPass=(TextView) findViewById(R.id.reset_password);
		done = (Button) findViewById(R.id.gesture_done);
		reset = (Button) findViewById(R.id.gesture_reset);
		reset.setOnClickListener(listener);
		newPass.setOnClickListener(listener);
		sp=getSharedPreferences(GesturePassword.SAVESP, Context.MODE_PRIVATE);
		boolean hasPass=sp.getBoolean(GesturePassword.SAVESP, false);
		if(hasPass){
		done.setOnClickListener(passListener);	
		newPass.setVisibility(View.VISIBLE);
		}else{
		
		done.setOnClickListener(listener);
		newPass.setVisibility(View.INVISIBLE);
		}
		
	}
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.gesture_reset:
				gesturePassword.reset();
				break;
			case R.id.gesture_done:
				if(gesturePassword.donePassword()){
				
				inni();
				}
				break;
			case R.id.reset_password:
				gesturePassword.resetPassword();
				inni();
				break;
			default:
				break;
			}
			
		}
	};
	
	OnClickListener passListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(gesturePassword.isPass()){
				finish();
			}else{
				T.showShort(GestureActivity.this, "密码错误");
			}
			
		}
		
	};
}
