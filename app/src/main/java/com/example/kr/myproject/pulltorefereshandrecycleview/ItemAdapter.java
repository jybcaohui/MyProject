package com.example.kr.myproject.pulltorefereshandrecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kr.myproject.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * Created by KR on 2015/5/4.
 */
public class ItemAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ItemInfo> list;

    public ItemAdapter(Context context, List<ItemInfo> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null && !list.isEmpty()?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.item_layout,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        final ItemInfo activityInfo = list.get(position);

        viewHolder.intro.setText(activityInfo.getTxt());
        viewHolder.cover.setImageResource(activityInfo.getId());
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.item)
        LinearLayout item;
        @InjectView(R.id.cover)
        ImageView cover;
        @InjectView(R.id.intro)
        TextView intro;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
