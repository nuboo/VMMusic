package com.example.vmmusic.app.customview;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.LrcContent;

import java.util.ArrayList;
import java.util.List;

/** 歌词文本
 * Created by Administrator on 2016/4/13 0013.
 */
public class LrcTextView extends TextView {
    private float width;        //歌词视图宽度
    private float height;       //歌词视图高度
    private Paint currentPaint; //当前画笔对象
    private Paint notCurrentPaint;  //非当前画笔对象
    private float donwY;//点击下去时候的Y值
    private float textSize = 26;        //文本大小
    private int index = 0;      //list集合下标
    private int nowIndex;//当前播放进度
    private boolean noLyrics=false;//没有歌词
    private  int DY = 50; // 每一行的间隔
    private boolean notDown=true;//没有按下
    float now;//当前滑动位置
    private Canvas canvas;
    private List<LrcContent> mLrcList = new ArrayList<LrcContent>();

    public void setmLrcList(List<LrcContent> mLrcList) {
        this.mLrcList = mLrcList;
        Log.w("list", mLrcList.size()+"~~~~~");
       
    }

    public LrcTextView(Context context) {
        super(context);
        init();
    }
    public LrcTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LrcTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setFocusable(true);     //设置可对焦

        //高亮部分
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);    //设置抗锯齿，让文字美观饱满
        currentPaint.setTextAlign(Paint.Align.CENTER);//设置文本对齐方式

        //非高亮部分
        notCurrentPaint = new Paint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 绘画歌词
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvas == null) {
        	
            return;
        }
        this.canvas=canvas;
        currentPaint.setColor(getResources().getColor(R.color.green));//播放中文字
        notCurrentPaint.setColor(getResources().getColor(R.color.font_name));

        currentPaint.setTextSize(textSize);
        currentPaint.setTypeface(Typeface.SERIF);

        notCurrentPaint.setTextSize(textSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);

        drawLine(index);
        moveLine(nowIndex);
    }
    /**
     * 滑动显示歌词
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	switch (event.getAction()) {
    	 case MotionEvent.ACTION_DOWN:
    		 
    		 donwY = event.getY();//点击下去的位置
    		 nowIndex=index;
    			 notDown=false;
    		
    		 break;
		case MotionEvent.ACTION_MOVE:
			now=event.getY();//移动中的位置
			if(now>donwY){//向下拉动
				//移动距离除以行间距
				nowIndex=nowIndex-(int)((now-donwY)/(DY+textSize));
				
			}else{//向上拉动
				
				nowIndex=nowIndex+(int)((donwY-now)/(DY+textSize));
			}
			break;
		case MotionEvent.ACTION_UP:
			notDown=true;//重新根据播放进度绘制歌词
			break;
		default:
			break;
		}
    	return true;
    }
    
    /**
     * 手势滑动歌词时
     * @param index
     */
    private void moveLine(int index){
    	if(!notDown){
    		if(mLrcList.size()==0){
    			 canvas.drawText("未找到歌词文件", width / 2, height / 2, notCurrentPaint);
    			 Log.w("", "=======0");
    			 return;
    		}
    		if(index<0){//如果已经到顶部
    			index=0;
    			
    		}else if(index>mLrcList.size()){
    			index=mLrcList.size()-1;
    		}
    		
            setText("");
            canvas.drawText(mLrcList.get(index).getLrcStr(), width / 2, height / 2, currentPaint);

        	float tempY = height / 2;
        	
        		//画出本句之前的句子
                for(int i = index - 1; i >= 0; i--) {
                    //向上推移
                    tempY = tempY - DY;
                    canvas.drawText(mLrcList.get(i).getLrcStr(), width / 2, tempY, notCurrentPaint);
                }
               
        	
        	
            //画出本句之后的句子
            if(index!=mLrcList.size()-1){//如果不是最后一行
            	
            	 tempY = height / 2;
            for(int i = index + 1; i < mLrcList.size(); i++) {
                //往下推移
                tempY = tempY + DY;

                canvas.drawText(mLrcList.get(i).getLrcStr(), width / 2, tempY, notCurrentPaint);
            	}
            }


    	
    	}
    		     

    }
    
    /**
     * 最初的绘制歌词
     * @param index
     */
    private void drawLine(int index){
    	if(notDown){//如果没有被按下时
    		try {
                setText("");
                canvas.drawText(mLrcList.get(index).getLrcStr(), width / 2, height / 2, currentPaint);

            	float tempY = height / 2;
                //画出本句之前的句子
                for(int i = index - 1; i >= 0; i--) {
                    //向上推移
                    tempY = tempY - DY;
                    canvas.drawText(mLrcList.get(i).getLrcStr(), width / 2, tempY, notCurrentPaint);
                }
                tempY = height / 2;
                //画出本句之后的句子
                for(int i = index + 1; i < mLrcList.size(); i++) {
                    //往下推移
                    tempY = tempY + DY;

                    canvas.drawText(mLrcList.get(i).getLrcStr(), width / 2, tempY, notCurrentPaint);
                }
                
               
            } catch (Exception e) {
                
                canvas.drawText("未找到歌词文件", width / 2, height / 2, notCurrentPaint);
                
            }

    	}
    }
    /**
     * 当view大小改变的时候调用的方法
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public  int getDy() {
        return DY;
    }
  
  
    
	
    
}
