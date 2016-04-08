package com.example.vmmusic.app.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class TopSettiings {
    private Context context;
    private LayoutInflater inflater;
    private View view;
    private TextView title,right,left;
    private RadioGroup choice;
    private RadioButton choiceLeft,choiceRight;
     public TopSettiings(Context context){
         this.context=context;
         inflater=LayoutInflater.from(context);
         view=inflater.inflate(R.layout.public_top,null);
         title=(TextView)view.findViewById(R.id.public_top_title);
         right=(TextView)view.findViewById(R.id.public_top_right);
         left=(TextView)view.findViewById(R.id.public_top_left);
         choice=(RadioGroup)view.findViewById(R.id.public_radio_group);
         choiceLeft=(RadioButton)view.findViewById(R.id.public_top_radio_left);
         choiceRight=(RadioButton)view.findViewById(R.id.public_top_radio_right);
     }



    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        this.title.setText(title);
    }

    /**
     * 设置右上角
     * @param rightTitle 右上角文字 default null;
     * @param drawRight 右上角背景   default null;
     * @return  TextView
     */
    public TextView setRight(String rightTitle,Drawable drawRight){
        if(rightTitle!=null&&!rightTitle.equals("")) {
            right.setText(rightTitle);
        }
        if(drawRight!=null) {
            right.setBackgroundDrawable(drawRight);
        }
        return  right;
    }

    /**
     * 设置右上角
     * @param leftTitle 右上角文字 default null;
     * @param drawLeft 右上角背景   default null;
     * @return  TextView
     */
    public TextView setLeft(String leftTitle,Drawable drawLeft){
        if(leftTitle!=null&&!leftTitle.equals("")) {
            left.setText(leftTitle);
        }
        if(drawLeft!=null) {
            left.setBackgroundDrawable(drawLeft);
        }
        return  left;
    }

    /**
     * 设置顶部RadioGroup 同时隐藏标题栏
     * @param left
     * @param right
     */
    public void setTopRadioGroup(String left,String right){
        title.setVisibility(View.GONE);
        if(left!=null&&!left.equals("")){
            choiceLeft.setText(left);
        }
        if(right!=null&&!right.equals("")){
            choiceRight.setText(right);
        }
    }

    /**
     * 获得右边RadioButton
     * @return
     */
    public RadioButton getChoiceRight(){
        return  choiceRight;
    }
    /**
     * 获得左边RadioButton
     * @return
     */
    public RadioButton getChoiceLeft(){
        return  choiceLeft;
    }
}
