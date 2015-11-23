package com.example.kr.myproject.flowlayout.canclick;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.R;
import com.example.kr.myproject.flowlayout.FlowLayout;

import java.util.Set;

public class ClickFlowLayoutActivity extends Activity {

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};


    private TagFlowLayout layout1;
    private TagFlowLayout layout2;
    private TagFlowLayout layout3;

    private TagFlowLayout layout5;
    private TagAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_flow_layout);
        this.layout1 = (TagFlowLayout) findViewById(R.id.id_flowlayout1);
        this.layout2 = (TagFlowLayout) findViewById(R.id.id_flowlayout2);
        this.layout3 = (TagFlowLayout) findViewById(R.id.id_flowlayout3);

        this.layout5 = (TagFlowLayout) findViewById(R.id.id_flowlayout5);
        setData1();//设置第1个layout数据
        setData2();//设置第2个layout数据
        setData3();//设置第3个layout数据

        setData5();//设置第5个layout数据
    }

    public void setData1() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        layout1.setMaxSelectCount(3);//设置最多可选数
        layout1.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        layout1, false);
                tv.setText(s);
                return tv;
            }
        });

        layout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(ClickFlowLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        layout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                ClickFlowLayoutActivity.this.setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

    public void setData2() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        layout2.setMaxSelectCount(3);
        layout2.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        layout2, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    public void setData3() {

        final LayoutInflater mInflater = LayoutInflater.from(this);
        layout3.setMaxSelectCount(3);

        layout3.setAdapter(mAdapter = new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        layout3, false);
                tv.setText(s);
                return tv;
            }
        });
        mAdapter.setSelectedList(1, 3, 5, 7, 8, 9);
        layout3.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(ClickFlowLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                view.setVisibility(View.GONE);
                return true;
            }
        });


        layout3.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                ClickFlowLayoutActivity.this.setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

    public void setData5() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        //mFlowLayout.setMaxSelectCount(3);
        layout5.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        layout5, false);
                tv.setText(s);
                return tv;
            }
        });

        layout5.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(ClickFlowLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        layout5.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                ClickFlowLayoutActivity.this.setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

}
