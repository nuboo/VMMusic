package com.example.vmmusic.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.customview.HeaderGridView;
import com.example.vmmusic.app.model.Chippendale;
import com.example.vmmusic.app.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 * Created by awx19 on 2016/4/7.
 */
public class ChippendaleFragment extends Fragment {
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
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(view.getContext(), getdate());
        View header = LayoutInflater.from(getContext()).inflate(R.layout.hander_chippendale, null);
        HeaderGridView grid = (HeaderGridView) view.findViewById(R.id.chippendale_grid);
        grid.addHeaderView(header);//添加headerView
        grid.setAdapter(chippendaleAdapter);
        grid.setOnItemClickListener(onItemClickListener);
    }


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
    private List<Chippendale> getdate() {
        List<Chippendale> list = new ArrayList<>();
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
