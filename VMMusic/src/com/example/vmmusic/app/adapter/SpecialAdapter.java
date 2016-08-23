package com.example.vmmusic.app.adapter;

import android.content.Context;

import com.example.vmmusic.R;
import com.example.vmmusic.app.model.Special;

import java.util.List;



/**
 *
 * 专题adapter
 * Created by awx19 on 2016/4/8.
 */
public class SpecialAdapter extends BaseRecyclerAdapter<Special> {

    public SpecialAdapter(Context ctx, List<Special> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_special_list_view;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Special item) {
        holder.setBackground(R.id.special_relative, item.getSpecial_img());
        holder.setText(R.id.special_name, item.getSpecial_name());
        holder.setText(R.id.special_type, item.getSpecial_type());
    }
}
