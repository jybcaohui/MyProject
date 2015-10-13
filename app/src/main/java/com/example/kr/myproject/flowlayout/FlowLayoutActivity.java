package com.example.kr.myproject.flowlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kr.myproject.R;
import com.example.kr.myproject.flowlayout.canclick.ClickFlowLayoutActivity;
import com.example.kr.myproject.flowlayout.canclick.ScrollActivity;

public class FlowLayoutActivity extends Activity {

    private Button click;
    private Button scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        this.click=(Button)findViewById(R.id.canclick);
        this.scroll=(Button)findViewById(R.id.scroll);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FlowLayoutActivity.this, ClickFlowLayoutActivity.class);
                startActivity(intent);
            }
        });
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FlowLayoutActivity.this, ScrollActivity.class);
                startActivity(intent);
            }
        });
    }

}
