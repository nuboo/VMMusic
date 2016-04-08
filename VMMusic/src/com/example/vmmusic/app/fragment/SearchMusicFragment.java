package com.example.vmmusic.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.vmmusic.R;

/**  音乐 热门和历史搜索
 * Created by Administrator on 2016/4/8 0008.
 */
public class SearchMusicFragment extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton hot,history;//热门，历史
    private EditText search,hot_his;
    private ImageView cancle;
    private View view ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view =inflater.inflate(R.layout.fragment_search_music,null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     *初始化
     */
    private  void inni(View view){
        radioGroup=(RadioGroup)view.findViewById(R.id.search_music);
        hot=(RadioButton)view.findViewById(R.id.search_hot);
        hot_his=(EditText)view.findViewById(R.id.search_hot_or_history);
        search=(EditText)view.findViewById(R.id.search_blank);
        history=(RadioButton)view.findViewById(R.id.search_history);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
        cancle=(ImageView)view.findViewById(R.id.cancle_xx);
        cancle.setOnClickListener(listener);
        hot.setChecked(true);//默认热门搜索选中

    }

    /**
     * radiogrounp监听
     */
    RadioGroup.OnCheckedChangeListener checkedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.search_hot:
                    hot_his.setText("热门搜索");
                    hot_his.setCompoundDrawables(getResources().getDrawable(R.drawable.hot),null,null,null);
                    break;
                case R.id.search_history:
                    hot_his.setText("历史搜索");
                    hot_his.setCompoundDrawables(getResources().getDrawable(R.drawable.history),null,null,null);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 按钮点击监听
     */
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
