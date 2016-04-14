package com.example.vmmusic.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.activity.MusicListActivity;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class TopSettiings {
    private Context context;
    private Activity activity;
    private View view;
    private TextView title, right, left;
    private RadioGroup choice;

    private RadioButton choiceLeft,choiceRight;

     public TopSettiings(Context context){
         this.context=context;
          activity=(Activity)context;

         title=(TextView)activity.findViewById(R.id.public_top_title);
         right=(TextView)activity.findViewById(R.id.public_top_right);
         left=(TextView)activity.findViewById(R.id.public_top_left);
         choice=(RadioGroup)activity.findViewById(R.id.public_top_radio);
         choiceLeft=(RadioButton)activity.findViewById(R.id.public_top_radio_left);
         choiceRight=(RadioButton)activity.findViewById(R.id.public_top_radio_right);
     }




    public TopSettiings(View view) {
        this.view = view;
        title = (TextView) view.findViewById(R.id.public_top_title);
        right = (TextView) view.findViewById(R.id.public_top_right);
        left = (TextView) view.findViewById(R.id.public_top_left);
        choice = (RadioGroup) view.findViewById(R.id.public_top_radio);
        choiceLeft = (RadioButton) view.findViewById(R.id.public_top_radio_left);
        choiceRight = (RadioButton) view.findViewById(R.id.public_top_radio_right);
    }

    /**
<<<<<<< HEAD
     * 设置标题  并默认设置左上角跳转选择音乐界面  如需添加其他点击事件，可以通过setLeft获得textView对象
=======
     * 设置标题
     *
>>>>>>> origin/feature/liuchunsheng
     * @param title
     */
    public void setTitle(String title) {
        this.title.setText(title);
        this.title.setSelected(true);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MusicListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 设置右上角
     *
     * @param rightTitle 右上角文字 default null;
     * @param drawRight  右上角背景   default null;
     * @param change     是否改变背景图片设置为空  true;false
     * @return TextView
     */
    public TextView setRight(String rightTitle, Drawable drawRight, Boolean change) {

        if (rightTitle != null && !rightTitle.equals("")) {
            right.setText(rightTitle);
        }
        if (drawRight != null) {
            right.setBackgroundDrawable(drawRight);
        }
        if (change == true) {
            right.setBackgroundDrawable(null);
        }
        return right;
    }

    /**
     * 设置左上角
     *
     * @param leftTitle 右上角文字 default null;
     * @param drawLeft  右上角背景   default null;
     * @param change    是否改变背景图片设置为空  true;false/null
     * @return TextView
     */
    public TextView setLeft(String leftTitle, Drawable drawLeft, Boolean change) {

        if (leftTitle != null && !leftTitle.equals("")) {
            left.setText(leftTitle);
        }
        if (drawLeft != null) {
            left.setBackgroundDrawable(drawLeft);
        }
        if (change == true) {
            left.setBackgroundDrawable(null);
        }
        return left;
    }

    /**
     * 设置顶部RadioGroup 同时隐藏标题栏
     *
     * @param left
     * @param right
     */
    public void setTopRadioGroup(String left, String right) {

        title.setVisibility(View.GONE);
        choice.setVisibility(View.VISIBLE);
        if (left != null && !left.equals("")) {
            choiceLeft.setText(left);
        }
        if (right != null && !right.equals("")) {
            choiceRight.setText(right);
        }
    }

    /**
     * 获得RadioGroup
     *
     * @return
     */
    public RadioGroup getChoice() {

        return choice;
    }

    /**
     * 获得右边RadioButton
     *
     * @return
     */
    public RadioButton getChoiceRight() {

        return choiceRight;
    }

    /**
     * 获得左边RadioButton
     *
     * @return
     */
    public RadioButton getChoiceLeft() {

        return choiceLeft;
    }

    /**
     * 获得左边TextView
     *
     * @return
     */
    public TextView getTextLeft() {

        return left;
    }

    /**
     * 获得左边TextView
     *
     * @return
     */
    public TextView getTextRight() {

        return right;
    }
}
