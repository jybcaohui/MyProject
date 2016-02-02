package com.example.kr.myproject.shengcunyouxi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kr.myproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FragmentLocal extends Fragment {

    @InjectView(R.id.local_book_list)
    PullToRefreshListView list;

    private int position = 0;
    private boolean flag = true;
    private String authName = "花落谁家";
    private String intro = "生存游戏，为生存游戏者而发。生存游戏，为生存游戏者而发。生存游戏，为生存游戏者而发。";
    private BookAdapter adapter;
    private BookInfo itemInfo;
    private List<BookInfo> showlist = new ArrayList<>();

    public FragmentLocal() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.inject(this, view);
        list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
//                        if (list.getRefreshableView().getLastVisiblePosition() == (list.getRefreshableView().getCount() - 1)) {
//                        }
                        // 判断滚动到顶部
//                        if(list.getRefreshableView().getFirstVisiblePosition() == 0){
//                        }
                        list.setMode(PullToRefreshBase.Mode.BOTH);
                        break;
                    //因惯性滑动
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        list.setMode(PullToRefreshBase.Mode.DISABLED);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && !flag) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                modify();
            }
        });
        initData();
        return view;
    }

    public void initData() {
        for(int i=0;i<15;i++){
            BookInfo info = new BookInfo(i,"生存游戏",intro,authName,i+100,i+157,i+187,"http://pic1.win4000.com/wallpaper/7/50cd72ebd3389.jpg");
            showlist.add(info);
        }
        modify();
    }

    public void modify() {

        if (adapter == null) {
            adapter = new BookAdapter(getActivity(), showlist);
            list.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        if (list.isRefreshing()) {
            list.postDelayed(new Runnable() {
                @Override
                public void run() {
                    list.onRefreshComplete();
                    list.getRefreshableView().setSelection(position);
                }
            }, 500);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
