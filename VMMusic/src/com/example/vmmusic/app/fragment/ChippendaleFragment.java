package com.example.vmmusic.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.vmmusic.R;
import com.example.vmmusic.app.activity.MusicListActivity;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.adapter.ChippendaleListAdapter;
import com.example.vmmusic.app.customview.HeaderGridView;
import com.example.vmmusic.app.model.Attention;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.T;
import com.example.vmmusic.app.utils.TopSettiings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 推荐
 * Created by awx19 on 2016/4/7.
 */
public class ChippendaleFragment extends Fragment {
    ListView chippendale_list_view;//关注listView
    HeaderGridView grid;//推荐gridView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_chippendale, null);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view 视图
     */
    private void initView(View view) {
        //顶部设置
        TopSettiings topSettiings = new TopSettiings(view);
        topSettiings.setTopRadioGroup("关注", "推荐");
        topSettiings.getTextLeft().setVisibility(View.GONE);
        topSettiings.getChoiceRight().setChecked(true);
        topSettiings.getChoice().setOnCheckedChangeListener(onCheckedChangeListener);
        topSettiings.getTextRight().setOnClickListener(onClickListener);


        //设置listView
        chippendale_list_view = (ListView) view.findViewById(R.id.chippendale_list_view);
        ChippendaleListAdapter chippendaleListAdapter = new ChippendaleListAdapter(getContext(), getListDate());
        chippendale_list_view.setAdapter(chippendaleListAdapter);


        //设置gridView
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(view.getContext(), getdate());
        View header = LayoutInflater.from(getContext()).inflate(R.layout.hander_chippendale, null);
        grid = (HeaderGridView) view.findViewById(R.id.chippendale_grid);
        grid.addHeaderView(header);//添加headerView
        grid.setAdapter(chippendaleAdapter);
        grid.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 选择监听
     */
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.public_top_radio_left:
                    chippendale_list_view.setVisibility(View.VISIBLE);
                    grid.setVisibility(View.GONE);
                    break;
                case R.id.public_top_radio_right:
                    chippendale_list_view.setVisibility(View.GONE);
                    grid.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    /**
     * 顶部右边监听
     */

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent intent = new Intent(getActivity(), MusicListActivity.class);
        	getActivity().startActivity(intent);
        }
    };


    /**
     * item监听
     */

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            T.showShort(getContext(), "点击了" + position);
        }
    };

    /**
     * 假数据
     *
     * @return
     */
    private List<Attention> getListDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd  HH-mm");
        Date date = new Date();
        List<Attention> list = new ArrayList<Attention>();
        for (int i = 0; i < 20; i++) {
            Attention attention = new Attention();
            attention.setIcon(R.drawable.icon);
            attention.setName("VM");
            attention.setTime(format.format(date.getTime()));
            attention.setBackground(R.drawable.attention_background);
            attention.setSong_name("傲慢的上校");
            attention.setSinger("朴树");
            attention.setContent("鲫鱼");
            attention.setThumb_up("2万");
            attention.setComments("1.5万");
            attention.setShare("5万");
            list.add(attention);
        }
        return list;
    }

    /**
     * 假数据
     *
     * @return
     */
    private List<Chippendale> getdate() {
        List<Chippendale> list = new ArrayList<Chippendale>();
        for (int i = 0; i < 20; i++) {
            Chippendale chippendale = new Chippendale();
            chippendale.setImg(R.drawable.pci);
            chippendale.setMusic_name("遥远的她");
            chippendale.setSinger("Eeason");
            list.add(chippendale);
        }
        return list;
    }
}
