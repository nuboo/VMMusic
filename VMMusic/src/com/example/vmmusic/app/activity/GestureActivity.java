package com.example.vmmusic.app.activity;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.GesturePassword;

import android.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class GestureActivity extends Activity {
	LinearLayout layout;
	Button done, reset;
	GesturePassword gesturePassword;

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
		done = (Button) findViewById(R.id.gesture_done);
		reset = (Button) findViewById(R.id.gesture_reset);
		done.setOnClickListener(listener);
		reset.setOnClickListener(listener);
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
				finish();
				}
				break;

			default:
				break;
			}
			
		}
	};
}
