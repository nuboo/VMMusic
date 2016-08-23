package com.example.vmmusic.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmmusic.R;
import com.example.vmmusic.app.adapter.SpecialAdapter;
import com.example.vmmusic.app.customview.layout.SwipeRefreshLayoutFinal;
import com.example.vmmusic.app.model.Special;
import com.example.vmmusic.app.myInterface.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;

/**
 * 专题
 * Created by awx19 on 2016/4/7.
 */
public class SpecialFragment extends Fragment {
    List<Special> list = new ArrayList<>();
    @InjectView(R.id.special_list_view)
    RecyclerViewFinal specialListView;
    Integer[] img = new Integer[]{R.drawable.topic, R.drawable.topci_one, R.drawable.topci_two, R.drawable.topci_five, R.drawable.topci_three, R.drawable.topci_four};
    String[] name = new String[]{"前奏无敌 简单的调调", "疯狂动物城，听完不舍离场的片尾曲", "磁性女声英翻", "动听女生-电音Mire", "那些独立的小清新", "那些好听的明星翻唱"};
    String[] type = new String[]{"#摇滚", "#电影", "#摇滚", "#爵士", "#情绪", "#流行"};
    @InjectView(R.id.swipe_ly)
    SwipeRefreshLayoutFinal swipeLy;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special, null);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        SpecialAdapter specialAdapter = new SpecialAdapter(getContext(), getDate());
        //设置布局样式LayoutManager
        specialListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        specialListView.setAdapter(specialAdapter);
        swipeLy.setColorSchemeResources(R.color.refresh);
        specialListView.setHasLoadMore(true);
        setSwipeRefreshInfo();
    }


    private void setSwipeRefreshInfo() {
        swipeLy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(Constant.CONSTANT101, 2000);
            }
        });
        specialListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mHandler.sendEmptyMessageDelayed(Constant.CONSTANT102, 2000);
            }
        });
        swipeLy.autoRefresh();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Constant.CONSTANT101:
                    swipeLy.onRefreshComplete();
                    break;
                case Constant.CONSTANT102:
                    specialListView.onLoadMoreComplete();
                    break;
            }
        }
    };

    /**
     * /**
     * 假数据
     *
     * @return
     */
    private List<Special> getDate() {
        for (int i = 0; i < img.length; i++) {
            Special special = new Special();
            special.setSpecial_img(img[i]);
            special.setSpecial_name(name[i]);
            special.setSpecial_type(type[i]);
            list.add(special);
        }
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
