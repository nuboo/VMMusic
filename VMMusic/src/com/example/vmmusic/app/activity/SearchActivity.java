package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.customview.XCFlowLayout;
import com.example.vmmusic.app.utils.KeyBoardUtils;
import com.example.vmmusic.app.utils.T;

/**
 * 搜索页面
 * Created by awx19 on 2016/4/11.
 */
public class SearchActivity extends Activity {
    /**
     * 标签数据
     */
    private String mNames[] = {"陈奕迅", "if you", "其实"};
    private String mName[] = {"傲慢的上校", "六月飞霜", "维斯塔"};
    private XCFlowLayout mFlowLayout;

    RadioGroup radioGroup;
    EditText search_view;//搜索输入
    TextView hot_search;
    TextView search_cancel;
    RadioButton search_hot;
    RadioButton search_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        initChildViews(mNames);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.search_radio);
        search_view = (EditText) findViewById(R.id.search_view);
        hot_search = (TextView) findViewById(R.id.hot_search);
        search_cancel = (TextView) findViewById(R.id.search_cancel);
        search_hot = (RadioButton) findViewById(R.id.search_hot);
        search_history = (RadioButton) findViewById(R.id.search_history);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        search_cancel.setOnClickListener(onClickListener);
        search_view.setOnKeyListener(onKeyListener);


    }

    /**
     * 输入法键盘监听
     */
    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // 先隐藏键盘
                KeyBoardUtils keyBoardUtils = new KeyBoardUtils();
                keyBoardUtils.closeKeybord(search_view, getApplicationContext());
                //进行搜索操作的方法，在该方法中可以加入非空判断
                search(search_view.getText().toString().trim());
            }
            return false;
        }
    };

    /**
     * 搜索功能
     */
    private void search(String searchContext) {
        if (TextUtils.isEmpty(searchContext)) {
            T.showShort(this, "输入框为空，请输入");
        } else {// 调用搜索的API方法


        }
    }

    /**
     * 取消
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * radioButton 选择监听
     */
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.search_hot://热门搜索
                    hot_search.setText("热门搜索");
                    hot_search.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.hot_search), null, null, null);
                    mFlowLayout.removeAllViews();//清空标签
                    search_view.setText("");//清空输入框
                    initChildViews(mNames);
                    break;
                case R.id.search_history://历史搜索
                    hot_search.setText("历史搜索");
                    hot_search.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.history), null, null, null);
                    mFlowLayout.removeAllViews();//清空标签
                    search_view.setText("");//清空输入框
                    initChildViews(mName);
                    break;
            }
        }
    };


    /**
     * 设置标签
     *
     * @param mNames
     */
    private void initChildViews(String[] mNames) {
        // TODO Auto-generated method stub
        mFlowLayout = (XCFlowLayout) findViewById(R.id.flow_layout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 28, 30, 0);//设置margin
        for (int i = 0; i < mNames.length; i++) {
            TextView view = new TextView(this);
            view.setText(mNames[i]);//设置内容
            view.setTextColor(getResources().getColor(R.color.green));//设置字体颜色
            view.setBackgroundResource(R.drawable.text_view_label);//设置边框
            view.setId(i);//设置id
            mFlowLayout.addView(view, lp);
            view.setOnClickListener(new Listener(mNames));
        }
    }

    /**
     * 标签监听
     */

    class Listener implements View.OnClickListener {
        String[] name;

        /**
         * 构造器
         *
         * @param mName 标签数组
         */
        public Listener(String[] mName) {
            name = mName;

        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < name.length; i++) {
                if (i == v.getId()) {
                    search(name[i]);//调用搜索方法
                }
            }

        }
    }

}
