package com.example.kr.myproject.customview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.widget.Toast;

import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyTopBarActivity extends Activity {

//    @InjectView(R.id.topbar)
    MyTopBar topBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_top_bar);
//        ButterKnife.inject(this);
        topBar = (MyTopBar)findViewById(R.id.topbar);
        topBar.setmListener(new MyTopBar.ActionbarViewListener() {
            @Override
            public void onBackClicked() {
                Toast.makeText(MyTopBarActivity.this,"向左",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightActionClicked() {
                Toast.makeText(MyTopBarActivity.this,"向右",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
