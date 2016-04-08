package com.example.vmmusic.app.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义GridView
 *
 * @author Administrator
 */
public class ReWriteGridView extends GridView {

    public ReWriteGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ReWriteGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
