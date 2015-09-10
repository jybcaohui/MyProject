package com.example.kr.myproject.pulltorefereshandrecycleview.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kr.myproject.R;

import java.util.List;

/**
 * Created by KR on 2015/8/10.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<FunctionEntity> mList;

    public MyAdapter(Context mContext, List<FunctionEntity> mList) {
        super();
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder MyViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, viewGroup, false));
        return MyViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        FunctionEntity FunctionEntity = mList.get(i);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) myViewHolder.itemView.getLayoutParams();
        layoutParams.height = FunctionEntity.getItemHeight();
        myViewHolder.itemView.setLayoutParams(layoutParams);
        myViewHolder.itemView.setBackgroundColor(FunctionEntity.getColor());
        myViewHolder.mTv_Text.setText(FunctionEntity.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTv_Text;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTv_Text = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}