package com.example.kr.myproject.pulltorefereshandrecycleview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.kr.myproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Fragmentb extends Fragment {


    @InjectView(R.id.listView)
    PullToRefreshGridView listView;

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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragmentb, container, false);
        ButterKnife.inject(this, view);
        initListView();
        notifyAdapter();
        return view;
    }

    public void initListView(){
        listView.setMode(PullToRefreshBase.Mode.BOTH);//下拉刷新，上拉加载
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                notifyAdapter();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                notifyAdapter();
            }
        });
    }

    /**
     * 更新adapter
     */
    private void notifyAdapter() {
//        listView.setAdapter(null);//加载网络数据时，防止GridView缓存view对布局的影响，更换布局样式先应先清掉缓存view
        if (adapter == null) {
            adapter = new ItemAdapter(getActivity(),activityInfos);
            listView.setAdapter(adapter);
            listView.getRefreshableView().setNumColumns(2);//设置列数，，item不居中设置item之间间距padding
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
