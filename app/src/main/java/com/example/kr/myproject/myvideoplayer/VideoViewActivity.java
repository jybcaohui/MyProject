package com.example.kr.myproject.myvideoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VideoViewActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.full)
    Button full;
    @InjectView(R.id.start)
    Button start;
    @InjectView(R.id.pause)
    Button pause;

    @InjectView(R.id.player)
    VideoView player;
    @InjectView(R.id.jump)
    Button jump;
    @InjectView(R.id.top)
    RelativeLayout top;
    @InjectView(R.id.menu)
    LinearLayout menu;
    private Boolean fullscreen = false;
    private Boolean running = false;
    int width;
    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_player);
        ButterKnife.inject(this);

        width = ScreenUtils.getScreenWidth(this);
        height = width*3/4;
//        ViewGroup.LayoutParams params = player.getLayoutParams();
//        params.height=height;
//        params.width =width;
//        player.setLayoutParams(params);
        RelativeLayout.LayoutParams lp=new  RelativeLayout.LayoutParams(width,height);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        player.setLayoutParams(lp);
        player.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/123.3gp"));
        player.setMediaController(new MediaController(this));
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        jump.setOnClickListener(this);
        full.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                if(!running && player.getResources()==null){
                    Log.d("file", Environment.getExternalStorageDirectory().getPath() + "/123.3gp");
                    player.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/123.3gp"));
                    player.start();
                }else{
                    player.start();
                }
                running=true;
                start.setClickable(false);
                start.setTextColor(getResources().getColor(R.color.gray_text));
                break;
            case R.id.pause:
                if(running){
                    player.pause();
                    running=false;
                    pause.setText("继续");
                }else{
                    player.start();
                    pause.setText("暂停");
                    running=true;
                }
                break;
            case R.id.jump:
                Intent intent=new Intent(this,SurfaceViewActivity.class);
                startActivity(intent);
                break;
            case R.id.full:
                if(!fullscreen){//设置RelativeLayout的全屏模式
                    RelativeLayout.LayoutParams layoutParams=
                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    player.setLayoutParams(layoutParams);
                    top.setVisibility(View.GONE);
                    menu.setVisibility(View.GONE);
                    fullscreen = true;//改变全屏/窗口的标记
                }else{//设置RelativeLayout的窗口模式
                    RelativeLayout.LayoutParams lp=new  RelativeLayout.LayoutParams(width,height);
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    player.setLayoutParams(lp);
                    top.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);
                    fullscreen = false;//改变全屏/窗口的标记
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stopPlayback();
    }
}
