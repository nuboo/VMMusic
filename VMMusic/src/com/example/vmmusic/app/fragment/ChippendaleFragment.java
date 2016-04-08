package com.example.vmmusic.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.ChippendaleAdapter;
import com.example.vmmusic.app.customview.ReWriteGridView;
import com.example.vmmusic.app.model.Chippendale;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 * Created by awx19 on 2016/4/7.
 */
public class ChippendaleFragment extends Fragment {
    ReWriteGridView reWriteGridView;

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
        reWriteGridView = (ReWriteGridView) view.findViewById(R.id.chippendale_grid);
        ChippendaleAdapter chippendaleAdapter = new ChippendaleAdapter(view.getContext(), getdate());
        reWriteGridView.setAdapter(chippendaleAdapter);

    }

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
