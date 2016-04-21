package com.example.vmmusic.app.customview;

import java.util.ArrayList;

import com.example.vmmusic.R;
import com.example.vmmusic.app.utils.T;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GesturePassword  extends View{
	public static final String SAVESP="gesturePassword";
	float width;//屏幕宽度
	float height;//屏幕高度
	float radius;//圆半径
	Paint mPaint;
	Paint paint;
	float marginX;//左上圆距离左边的距离
	float marginY;//左上圆距离右边的距离
	float downX,downY,moveX,moveY,upX,upY;//按下，移动，抬手
	float lineX,lineY;//直线的起点
	boolean resetNot,moving;//是否重置（设置为超过4个圆，需要重置，不超过，点击屏幕重置），是否在移动（防止点击下去添加起点后，再点击第二个圆，绘制一直直线）
	Context context;
	StringBuilder pass;//密码
	Point[] points=new Point[9];//圆心
	ArrayList<Integer> password;//密码
	public GesturePassword(Context context) {
		
		super(context);
		this.context=context;
		password=new ArrayList<Integer>();
		resetNot=true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		radius=(width/10);//圆半径
		marginX = (width / 5);// 圆心离边界的x距离
		marginY = (height / 5);// 圆心离边界的Y距离
		paint=new Paint();
		mPaint=new Paint();
		paint.setColor(getResources().getColor(R.color.black));
		mPaint.setColor(getResources().getColor(R.color.green));
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3.5f);
		drawCircle(canvas,mPaint);
		if(moving){
		drawMovingLine(canvas, paint);
		}
		if(password.size()>1){
		drawLines(canvas,paint);
		}
		super.onDraw(canvas);
	}
	/**
	 * 绘制最初的圆
	 * @param canvas
	 * @param mPaint2
	 */
	private void drawCircle(Canvas canvas, Paint mPaint2) {
		for(int i=0;i<9;i++){
				if (password.contains((i+1))){
				
					mPaint2.setColor(getResources().getColor(R.color.green));
					mPaint2.setStrokeWidth(2.5f);
					}else{
						mPaint2.setColor(getResources().getColor(R.color.black));
						mPaint2.setStrokeWidth(2.0f);
					}
				
				int cx=(int)(marginX+((radius+marginX)*(i%3)));
				int cy=(int)(marginY+((radius+marginY)*(i/3)));
				
				canvas.drawCircle(cx, cy, radius, mPaint2);
				points[i]=new Point(cx, cy);
		}
		
	}
	/**
	 * 绘制移动中的线条
	 * @param canvas
	 * @param paint
	 */
	private void drawMovingLine(Canvas canvas,Paint paint){
		
		canvas.drawLine(lineX, lineY, moveX, moveY, paint);
	
		
	}
	/**
	 * 绘制选中的线条
	 * @param canvas
	 * @param paint
	 */
	private void drawLines(Canvas canvas,Paint paint) {
		
		for(int i=1;i<password.size();i++){
			int start=password.get(i-1);
			int end=password.get(i);
			int startX=points[start-1].x;
			int startY= points[start-1].y;
			int endX=points[end-1].x;
			int endY=points[end-1].y;
	
					canvas.drawLine(startX,startY,endX,endY, paint);
				
			
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(resetNot){
			pass=new StringBuilder();
			
			
			reset();
			
			downX=event.getX();
			downY=event.getY();
			
			addNum(downX, downY);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(resetNot){
			moving=true;
			
			moveX=event.getX();
			moveY=event.getY();
			addNum(moveX, moveY);
			if(password.size()==9){//如果已经选中了所有的圆，就不再绘制直线
				moveX=lineX;
				moveY=lineY;
			}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(resetNot){
			moving=false;
			upX=event.getX();
			upY=event.getY();
			
			addNum(upX, upY);
			moveX=lineX;
			moveY=lineY;
			if(password.size()>3){
				resetNot=false;//需要重置后才能绘图
				}
			}
			
			break;
		default:
			break;
		}
		return true;
	}
	/**
	 * 添加选中圆
	 * @param actionX
	 * @param actionY
	 */
	private boolean addNum(float actionX,float actionY){
		boolean circle=false;
		for (int i = 0; i < 9; i++) {
			double range = Math.hypot(actionX - points[i].x, actionY - points[i].y);
			if (range < (radius)) {// 在圆心内
				if(!password.contains(i+1)){
					if(password.size()>0){
				int j=	password.get((password.size()-1))-1;
				float middleX=(points[i].x+points[j].x)/2;
				float middleY=(points[i].y+points[j].y)/2;
				addNum(middleX, middleY);
					}
					
				password.add(i + 1);
				
				lineX=points[i].x;
				lineY=points[i].y;
			
				pass.append(i+1);
				
				circle=true;
				}
			}
			
		}
		invalidate();
		return circle;
	}

	
	/**
	 * 保存密码
	 * 
	 */
	public boolean donePassword(){
		if(password!=null){
			
		if(password.size()<4){
			T.showShort(context, "请选择至少4个圆");
			return false;
		}else{
			savePassword();
			return true;
		}
		
		}
		return false;
	}
	/**
	 * 保存密码
	 */
	private void savePassword() {
		SharedPreferences sp=context.getSharedPreferences(SAVESP, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putString(SAVESP, pass.toString());
		editor.putBoolean(SAVESP, true);
		editor.commit();
	}

	/**
	 * 重置手势密码
	 */
	public void reset() {
		if(password!=null){
		password.clear();
		addNum(0, 0);
		resetNot=true;
		}
		
	}
	
}
