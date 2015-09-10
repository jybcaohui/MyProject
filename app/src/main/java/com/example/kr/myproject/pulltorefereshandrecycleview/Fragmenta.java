package com.example.kr.myproject.pulltorefereshandrecycleview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kr.myproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Fragmenta extends Fragment {

    @InjectView(R.id.listView)
    PullToRefreshListView listView;

    private ItemAdapter adapter;
    private List<ItemInfo> activityInfos = new ArrayList<ItemInfo>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemInfo info=new ItemInfo(R.drawable.act,"单列下拉刷新测试");
        int i=0;
        while(i<10){
            activityInfos.add(info);
            i++;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragmenta, container, false);
        ButterKnife.inject(this, view);
        initListView();
        notifyAdapter();
        return view;
    }

    public void initListView(){
        listView.setMode(PullToRefreshBase.Mode.BOTH);//下拉刷新，上拉加载
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                notifyAdapter();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                notifyAdapter();
            }
        });
    }

    /**
     * 更新adapter
     */
    private void notifyAdapter() {
        if (adapter == null) {
            adapter = new ItemAdapter(getActivity(),activityInfos);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
