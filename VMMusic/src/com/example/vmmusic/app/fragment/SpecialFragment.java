package com.example.vmmusic.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.SpecialAdapter;
import com.example.vmmusic.app.model.Special;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题
 * Created by awx19 on 2016/4/7.
 */
public class SpecialFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special, null);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.special_list_view);
        SpecialAdapter specialAdapter = new SpecialAdapter(view.getContext(), getDate());
        listView.setAdapter(specialAdapter);
    }

    /**
     * 假数据
     *
     * @return
     */
    private List<Special> getDate() {
        List<Special> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Special special = new Special();
            special.setSpecial_img(R.drawable.topic);
            special.setSpecial_name("前奏无敌 简单的调调");
            special.setSpecial_type("#摇滚");
            list.add(special);
        }
        return list;
    }
}
