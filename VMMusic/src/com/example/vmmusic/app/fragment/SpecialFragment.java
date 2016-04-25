package com.example.vmmusic.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.activity.MusicListActivity;
import com.example.vmmusic.app.adapter.SpecialAdapter;
import com.example.vmmusic.app.model.Special;
import com.example.vmmusic.app.utils.TopSettiings;

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
      //  topSetting(view);
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
  
    /**
     * 假数据
     *
     * @return
     */
    private List<Special> getDate() {
        List<Special> list = new ArrayList<Special>();
        Special special = new Special();
        special.setSpecial_img(R.drawable.topic);
        special.setSpecial_name("前奏无敌 简单的调调");
        special.setSpecial_type("#摇滚");
        list.add(special);
        Special special_one = new Special();
        special_one.setSpecial_img(R.drawable.topci_one);
        special_one.setSpecial_name("疯狂动物城，听完不舍离场的片尾曲");
        special_one.setSpecial_type("#电影");
        list.add(special_one);
        Special special_two = new Special();
        special_two.setSpecial_img(R.drawable.topci_two);
        special_two.setSpecial_name("磁性女声英翻");
        special_two.setSpecial_type("#摇滚");
        list.add(special_two);
        Special special_three = new Special();
        special_three.setSpecial_img(R.drawable.topci_five);
        special_three.setSpecial_name("动听女生-电音Mire");
        special_three.setSpecial_type("#爵士");
        list.add(special_three);
        Special special_four = new Special();
        special_four.setSpecial_img(R.drawable.topci_three);
        special_four.setSpecial_name("那些独立的小清新");
        special_four.setSpecial_type("#情绪");
        list.add(special_four);
        Special special_five = new Special();
        special_five.setSpecial_img(R.drawable.topci_four);
        special_five.setSpecial_name("那些好听的明星翻唱");
        special_five.setSpecial_type("#流行");
        list.add(special_five);
        return list;
    }
}
