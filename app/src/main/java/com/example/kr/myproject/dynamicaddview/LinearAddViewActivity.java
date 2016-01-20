package com.example.kr.myproject.dynamicaddview;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LinearAddViewActivity extends BaseActivity implements View.OnClickListener{

    private List<ContentText> list = new ArrayList<>();
    private LayoutInflater inflater;
    private int i = 0;
    @InjectView(R.id.but)
    Button button;
    @InjectView(R.id.linear)
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_add_view);
        ButterKnife.inject(this);
        button.setOnClickListener(this);
        inflater = LayoutInflater.from(this);
        list.add(new ContentText(0, "第一段"));
//        list.add(new ContentText(1, "第2段"));
        setData();
    }

    public RelativeLayout.LayoutParams getParams(View v) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, v.getId());
        return params;
    }

    public View getFocusView(ViewGroup group) {
        View v = group.getFocusedChild();
        if (v.getClass().getName().equals("android.widget.EditText")) {
            Log.d("edittext--", "焦点在editText上--" + v.getId());
            return v;
        } else {
            Log.d("edittext--11", "焦点不" +
                    "在editText上");
            return group;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setData() {

//        for (ContentText item : list) {
        for (int i=0;i<list.size();i++) {
            final int position=i;
            ContentText item=list.get(i);
            if (item.getType() == 0) {//0:文字；1:图片
                EditText ed = new EditText(this);
                ed.setId(ed.generateViewId());
                ed.setText(item.getText());
                ed.requestFocus();
                addView(linear, ed);
                ed.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        list.get(position).setText(s.toString());
                    }
                });
            } else {
                final RelativeLayout layout = (RelativeLayout) inflater.inflate(
                        R.layout.hello, null).findViewById(R.id.hellolayout);
                layout.setId(layout.generateViewId());
                layout.setBackgroundColor(this.getResources().getColor(R.color.color2));
                TextView lv = (TextView) layout.findViewById(R.id.url);
                lv.setTextColor(Color.RED);
                Button but = (Button) layout.findViewById(R.id.delete);
                addView(linear, layout);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeView(linear, layout);
                    }
                });
            }
        }

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but:
                int childnum=linear.getChildCount();
                Log.d("views--", childnum + "");
                View view=linear.getChildAt(childnum-1);//最后一个控件
                View focusView=linear.getFocusedChild();
                if (focusView.getClass().getName().equals("android.widget.EditText")){//获得焦点的控件是EditText
                    if (view.getClass().getName().equals("android.widget.EditText") && view.hasFocus()){
                        //最后一个为Edittext并且焦点在此处，则在最后添加图片，同时添加一个EditText
                        final RelativeLayout layout = (RelativeLayout) inflater.inflate(
                                R.layout.hello, null).findViewById(R.id.hellolayout);
                        layout.setId(layout.generateViewId());
                        layout.setBackgroundColor(this.getResources().getColor(R.color.color2));
                        TextView lv = (TextView) layout.findViewById(R.id.url);
                        lv.setTextColor(Color.RED);
                        Button but = (Button) layout.findViewById(R.id.delete);
                        addView(linear, layout);

                        final EditText ed = new EditText(this);
                        ed.setId(ed.generateViewId());
                        ed.setText("新增加的段落----" + i);
                        ed.requestFocus();
                        addView(linear, ed);

                        list.add(new ContentText(1, lv.getText().toString()));
                        list.add(new ContentText(0, ed.getText().toString()));
                        but.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeView(linear, layout);
                                removeView(linear, ed);
                            }
                        });
                        ed.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
//                                list.get(list.size()-1).setText(s.toString());//list.size()-1更改为焦点所在位置
                            }
                        });
                    }else{
                        //指定位置插入
                        EditText editText=(EditText)focusView;
                        String s=editText.getText().toString();
                        Log.d("views--","指定位置插入--"+s);
                        list.add(2,new ContentText(1,"new image"));//替换2为具体位置
                        linear.removeAllViews();
                        setData();
                    }
                }else{
                    Toast.makeText(this, "请把光标放在指定插入图片的位置", Toast.LENGTH_SHORT).show();
                }
                for(ContentText text:list){
                    Log.d("item--",text.getText()+"\n");
                }
                i++;
                break;
            default:
                break;
        }
    }

//    private void addEditText(ViewGroup vg, View view)

    private void addView(ViewGroup vg, View view) {//添加View
        if (null == vg || null == view)
            return;
        vg.addView(view);
    }

    private void addViewloc(ViewGroup vg, View view, ViewGroup.LayoutParams params) {//添加View,指定位置
        if (null == vg || null == view)
            return;
        vg.addView(view, params);
    }

    private void removeView(ViewGroup vg, View view) {//删除View
        if (null == vg || null == view)
            return;
        vg.removeView(view);
    }
}
