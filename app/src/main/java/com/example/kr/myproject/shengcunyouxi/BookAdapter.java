package com.example.kr.myproject.shengcunyouxi;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kr.myproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by KR on 2015/5/4.
 */
public class BookAdapter extends BaseAdapter {

    WindowManager windowManager;

    private Activity context;
    private List<BookInfo> list;

    public BookAdapter(Activity context, List<BookInfo> list) {
        this.context = context;
        this.list = list;
        if (context != null) {
            windowManager = context.getWindowManager();
        }
    }

    @Override
    public int getCount() {
        return list != null && !list.isEmpty() ? list.size() : 0;
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
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;
        final BookInfo itemInfo = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.bookitem_layout, null);
            viewHolder.book = (TextView) view.findViewById(R.id.bookname);
            viewHolder.intro = (TextView) view.findViewById(R.id.intro);
            viewHolder.auth = (TextView) view.findViewById(R.id.auth);
            viewHolder.sub = (TextView) view.findViewById(R.id.sub);
            viewHolder.uv = (TextView) view.findViewById(R.id.uv);
            viewHolder.integral = (TextView) view.findViewById(R.id.integral);
            viewHolder.cover = (ImageView) view.findViewById(R.id.cover);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.book.setText(itemInfo.getBookName());
        viewHolder.intro.setText(itemInfo.getBookIntro());
        viewHolder.auth.setText(itemInfo.getAuthName());
        viewHolder.sub.setText("订阅"+itemInfo.getSubNum());
        viewHolder.uv.setText("UV"+itemInfo.getUvNum());
        viewHolder.integral.setText("积分"+itemInfo.getIntegral());
        String coverUrl = itemInfo.getCover().trim();
        if (!coverUrl.isEmpty()) {
            Picasso.with(context).load(coverUrl).into(viewHolder.cover);
        } else {
            Picasso.with(context).load(R.drawable.sy_book_cover).into(viewHolder.cover);
        }
        viewHolder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView book;
        TextView intro;
        TextView auth;
        TextView sub;
        TextView uv;
        TextView integral;
        ImageView cover;
    }

}
