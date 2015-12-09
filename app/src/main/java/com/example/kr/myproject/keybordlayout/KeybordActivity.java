package com.example.kr.myproject.keybordlayout;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.R;

public class KeybordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laybord);
        KeyboardLayout mainView = (KeyboardLayout) findViewById(R.id.keyboardLayout1);
        final TextView tv = (TextView) findViewById(R.id.testone_tv);
        mainView.setOnkbdStateListener(new KeyboardLayout.onKybdsChangeListener() {

            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case KeyboardLayout.KEYBOARD_STATE_HIDE:
                        tv.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "软键盘隐藏", Toast.LENGTH_SHORT).show();
                        break;
                    case KeyboardLayout.KEYBOARD_STATE_SHOW:
                        tv.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "软键盘弹起", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
