package com.example.kr.myproject.myvideoplayer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.ImageView;
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

public class VideoViewActivity extends BaseActivity implements View.OnClickListener {

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
    @InjectView(R.id.cover)
    ImageView cover;
    @InjectView(R.id.jump)
    Button jump;
    @InjectView(R.id.onlyvoice)
    Button voice;
    @InjectView(R.id.top)
    RelativeLayout top;
    @InjectView(R.id.menu)
    LinearLayout menu;
    private Boolean fullscreen=false;
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
        height = width * 3 / 4;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        player.setLayoutParams(lp);
//        player.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/123.3gp"));
        //videoView播放网络视频，唯一需要改变的只有URI（莫忘网络权限）
        player.setVideoURI(Uri.parse("http://m.qicheng.tv/upload//upload_files/f/0/f2ec6a76b8718cb7cc076598ac568930_480p.mp4"));
        player.setMediaController(new MediaController(this));
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        jump.setOnClickListener(this);
        full.setOnClickListener(this);
        voice.setOnClickListener(this);
        //确定当前横竖屏，横竖屏切换时重建activity
        if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            top.setVisibility(View.GONE);
            menu.setVisibility(View.GONE);
            fullscreen=true;
        }else{
            top.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);
            fullscreen=false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                cover.setVisibility(View.GONE);
                if (!running && player.getResources() == null) {
                    Log.d("file", Environment.getExternalStorageDirectory().getPath() + "/123.3gp");
                    player.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/123.3gp"));
                    player.start();
                } else {
                    player.stopPlayback();
                    player.setVideoURI(Uri.parse("http://m.qicheng.tv/upload//upload_files/f/0/f2ec6a76b8718cb7cc076598ac568930_480p.mp4"));
                    player.start();
                }
                running = true;
                start.setClickable(false);
                start.setTextColor(getResources().getColor(R.color.gray_text));
                break;
            case R.id.pause:
                if (running) {
                    player.pause();
                    running = false;
                    pause.setText("继续");
                } else {
                    player.start();
                    pause.setText("暂停");
                    running = true;
                }
                break;
            case R.id.jump:
                Intent intent = new Intent(this, SurfaceViewActivity.class);
                startActivity(intent);
                break;
            case R.id.onlyvoice:
                //切换到只播放音频
                start.setClickable(true);
                start.setTextColor(getResources().getColor(R.color.black));
                cover.setVisibility(View.VISIBLE);
                player.stopPlayback();
                player.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/111.mp3"));
                player.start();
                break;
            case R.id.full:
                if (!fullscreen) {//设置RelativeLayout的全屏模式

//                    RelativeLayout.LayoutParams layoutParams =
//                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
//                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    player.setLayoutParams(layoutParams);
//                    top.setVisibility(View.GONE);
//                    menu.setVisibility(View.GONE);

                    fullscreen = true;//改变全屏/窗口的标记
                    //切换横屏显示，重新加载activity，在onSaveInstanceState中保存所需数据
                    //在activity重建时onRestoreInstanceState取得上次保存的数据
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {//设置RelativeLayout的窗口模式

//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//                    player.setLayoutParams(lp);
//                    top.setVisibility(View.VISIBLE);
//                    menu.setVisibility(View.VISIBLE);

                    fullscreen = false;//改变全屏/窗口的标记
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stopPlayback();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存播放进度
        int sec = player.getCurrentPosition();
        outState.putInt("time", sec);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        //重新加载时取得上次的播放进度
        int sec = outState.getInt("time");
        player.seekTo(sec);
        player.start();
        super.onRestoreInstanceState(outState);
    }
}
