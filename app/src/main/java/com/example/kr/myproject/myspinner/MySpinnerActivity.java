package com.example.kr.myproject.myspinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MySpinnerActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.gradeSpinner)
    Spinner gradeSpinner;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.jump)
    Button but;

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spinner);
        ButterKnife.inject(this);
        but.setOnClickListener(this);
        setData();
        gradeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                /* 将所选mySpinner 的值带入myTextView 中*/
                text.setText("您选择的是：" + adapter.getItem(arg2));
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                text.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setData() {
        final List<String> gradeList = new ArrayList<>();
        gradeList.add("一年级");
        gradeList.add("二年级");
        gradeList.add("三年级");
        gradeList.add("四年级");
        gradeList.add("五年级");
        adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_checked_text, gradeList) {

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.spinner_item_layout, parent,
                        false);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);
                ImageView check = (ImageView) view
                        .findViewById(R.id.spinner_item_checked_image);
                label.setText(gradeList.get(position));
                if (gradeSpinner.getSelectedItemPosition() == position) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_green));
                    check.setImageResource(R.drawable.selected);
                } else {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_light_green));
                    check.setImageResource(R.drawable.unselected);
                }

                return view;
            }

        };
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        gradeSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump:
                Intent intent=new Intent(MySpinnerActivity.this,SpinnerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
