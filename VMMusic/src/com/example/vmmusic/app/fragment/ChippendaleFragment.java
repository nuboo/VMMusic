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
     public ListView chippendale_list_view;//关注listView
     public HeaderGridView grid;//推荐gridView

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
     * item监听
     */

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           
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
        Attention attention = new Attention();
        attention.setIcon(R.drawable.vm);
        attention.setName("VM");
        attention.setTime(format.format(date.getTime()));
        attention.setBackground(R.drawable.attention_one);
        attention.setSong_name("傲慢的上校");
        attention.setSinger("朴树");
        attention.setContent("鲫鱼");
        attention.setThumb_up("2万");
        attention.setComments("1.5万");
        attention.setShare("5万");
        list.add(attention);
        Attention attention_one = new Attention();
        attention_one.setIcon(R.drawable.jiyu);
        attention_one.setName("鲫鱼");
        attention_one.setTime(format.format(date.getTime()));
        attention_one.setBackground(R.drawable.attention_two);
        attention_one.setSong_name("星星");
        attention_one.setSinger("Vitas");
        attention_one.setContent("没有别人聪明，就要比别人勤奋");
        attention_one.setThumb_up("万");
        attention_one.setComments("712");
        attention_one.setShare("1281");
        list.add(attention_one);
        Attention attention_two = new Attention();
        attention_two.setIcon(R.drawable.top);
        attention_two.setName("top");
        attention_two.setTime(format.format(date.getTime()));
        attention_two.setBackground(R.drawable.attention_three);
        attention_two.setSong_name("if you");
        attention_two.setSinger("BigBang");
        attention_two.setContent("BOOM SHAKALAKA");
        attention_two.setThumb_up("2023");
        attention_two.setComments("1123");
        attention_two.setShare("101");
        list.add(attention_two);
        Attention attention_three = new Attention();
        attention_three.setIcon(R.drawable.xiaolu);
        attention_three.setName("小路");
        attention_three.setTime(format.format(date.getTime()));
        attention_three.setBackground(R.drawable.attention_four);
        attention_three.setSong_name("LOSER");
        attention_three.setSinger("BigBang");
        attention_three.setContent("今天天气不错，你觉得呢？");
        attention_three.setThumb_up("1253");
        attention_three.setComments("785");
        attention_three.setShare("78");
        list.add(attention_three);
        return list;
    }

    /**
     * 假数据
     *
     * @return
     */
    private List<Chippendale> getdate() {
        List<Chippendale> list = new ArrayList<Chippendale>();
        Chippendale chippendale = new Chippendale();
        chippendale.setImg(R.drawable.pci);
        chippendale.setMusic_name("遥远的她");
        chippendale.setSinger("Eeason");
        list.add(chippendale);
        Chippendale chippendale_one = new Chippendale();
        chippendale_one.setImg(R.drawable.pci_e);
        chippendale_one.setMusic_name("陪你度过漫长岁月");
        chippendale_one.setSinger("Eeason");
        list.add(chippendale_one);
        Chippendale chippendale_two = new Chippendale();
        chippendale_two.setImg(R.drawable.pci_one);
        chippendale_two.setMusic_name("Call Me Maybe");
        chippendale_two.setSinger("Carly Rae Jepsen");
        list.add(chippendale_two);
        Chippendale chippendale_three = new Chippendale();
        chippendale_three.setImg(R.drawable.pci_two);
        chippendale_three.setMusic_name("Crazy dog");
        chippendale_three.setSinger("Moon");
        list.add(chippendale_three);
        Chippendale chippendale_four = new Chippendale();
        chippendale_four.setImg(R.drawable.pci_three);
        chippendale_four.setMusic_name("Bruises");
        chippendale_four.setSinger("Chairlift");
        list.add(chippendale_four);
        Chippendale chippendale_five = new Chippendale();
        chippendale_five.setImg(R.drawable.pci_four);
        chippendale_five.setMusic_name("We Are The World");
        chippendale_five.setSinger("迈克尔");
        list.add(chippendale_five);
        Chippendale chippendale_six = new Chippendale();
        chippendale_six.setImg(R.drawable.pci_five);
        chippendale_six.setMusic_name("I'M Yours 1");
        chippendale_six.setSinger("Jason Mraz");
        list.add(chippendale_six);
        return list;
    }
}
