package com.example.vmmusic.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmmusic.R;

/**
 * 推荐
 * Created by awx19 on 2016/4/7.
 */
public class ChippendaleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_chippendale, null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
