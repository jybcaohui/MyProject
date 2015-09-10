package com.example.kr.myproject.pulltorefereshandrecycleview.recycleview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kr.myproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Fragmentc extends Fragment {

    @InjectView(R.id.recy_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swiperefresh_view)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<FunctionEntity> mEntities = new ArrayList<FunctionEntity>();
    private MyAdapter myAdapter;
    private int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.GRAY};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragmentc, container, false);
        ButterKnife.inject(this,view);
        initview();
        getData();
        setAdapter();
        return view;
    }

    public void setAdapter() {
        //流式布局
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new MyAdapter(getActivity(), mEntities);
        mRecyclerView.setAdapter(myAdapter);
    }


    public void getData() {
        for (int i = 0; i <= 100; i++) {
            FunctionEntity FunctionEntity = new FunctionEntity();
            int itemHeight = 200 + (int) (Math.random() * 300);//随机高度
            FunctionEntity.setItemHeight(itemHeight);
            FunctionEntity.setName("" + i);
            switch (i % 4) {//随机颜色
                case 0:
                    FunctionEntity.setColor(colors[0]);
                    break;
                case 1:
                    FunctionEntity.setColor(colors[1]);
                    break;
                case 2:
                    FunctionEntity.setColor(colors[2]);
                    break;
                case 3:
                    FunctionEntity.setColor(colors[3]);
                    break;
            }
            mEntities.add(FunctionEntity);
        }
    }

    public void initview() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEntities.clear();
                        getData();
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "refresh successful", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
