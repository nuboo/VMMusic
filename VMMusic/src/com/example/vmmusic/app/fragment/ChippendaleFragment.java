package com.example.vmmusic.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.adapter.ChippendaleListAdapter;
import com.example.vmmusic.app.customview.HeaderGridView;
import com.example.vmmusic.app.model.Attention;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.T;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 推荐
 * Created by awx19 on 2016/4/7.
 */
public class ChippendaleFragment extends Fragment {
    RadioGroup radioGroup;
    RadioButton chippendale_attention;//关注
    RadioButton chippendale_recommend;//推荐
    TextView optical_disk;//播放
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
        radioGroup = (RadioGroup) view.findViewById(R.id.chippendale_radio);
        chippendale_attention = (RadioButton) view.findViewById(R.id.chippendale_attention);
        chippendale_recommend = (RadioButton) view.findViewById(R.id.chippendale_recommend);
        optical_disk = (TextView) view.findViewById(R.id.optical_disk);
        chippendale_list_view = (ListView) view.findViewById(R.id.chippendale_list_view);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        optical_disk.setOnClickListener(onClickListener);
        ChippendaleListAdapter chippendaleListAdapter = new ChippendaleListAdapter(getContext(), getListDate());
        chippendale_list_view.setAdapter(chippendaleListAdapter);
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(view.getContext(), getdate());
        View header = LayoutInflater.from(getContext()).inflate(R.layout.hander_chippendale, null);
        grid = (HeaderGridView) view.findViewById(R.id.chippendale_grid);
        grid.addHeaderView(header);//添加headerView
        grid.setAdapter(chippendaleAdapter);
        grid.setOnItemClickListener(onItemClickListener);
    }


    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.chippendale_attention:
                    chippendale_list_view.setVisibility(View.VISIBLE);
                    grid.setVisibility(View.GONE);
                    break;
                case R.id.chippendale_recommend:
                    chippendale_list_view.setVisibility(View.GONE);
                    grid.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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
