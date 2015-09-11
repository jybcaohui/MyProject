package com.example.kr.myproject.myvideoplayer;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.util.ScreenUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class SurfaceViewActivity extends BaseActivity {



    private final String TAG = "main";
    private SurfaceView sv;
    private Button btn_play, btn_pause, btn_replay, btn_stop, btn_dan;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView txt;
    private int currentPosition = 0;
    private boolean isPlaying;


    //弹幕
    private MyHandler handler;

    //弹幕内容
    private TanmuBean tanmuBean;
    //放置弹幕内容的父组件
    private RelativeLayout containerVG;

    //父组件的高度
    private int validHeightSpace;
    private int i=0;
    private Boolean running=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_surface_view);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        sv = (SurfaceView) findViewById(R.id.sv);

        txt = (TextView) findViewById(R.id.txt);
        containerVG = (RelativeLayout) findViewById(R.id.danmu);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_replay = (Button) findViewById(R.id.btn_replay);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_dan = (Button) findViewById(R.id.btn_dan);

        btn_play.setOnClickListener(click);
        btn_pause.setOnClickListener(click);
        btn_replay.setOnClickListener(click);
        btn_stop.setOnClickListener(click);
        btn_dan.setOnClickListener(click);

        // 为SurfaceHolder添加回调
        sv.getHolder().addCallback(callback);

        // 4.0版本之下需要设置的属性
        // 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面
        // sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(change);


        //弹幕
        tanmuBean = new TanmuBean();
        tanmuBean.setItems(new String[]{"测试一下", "弹幕这东西真不好做啊", "总是出现各种问题~~", "也不知道都是为什么？麻烦！", "哪位大神可以帮帮我啊？", "I need your help.",
                "测试一下", "弹幕这东西真不好做啊", "总是出现各种问题~~", "也不知道都是为什么？麻烦！", "哪位大神可以帮帮我啊？", "I need your help.",
                "测试一下", "弹幕这东西真不好做啊", "总是出现各种问题~~", "也不知道都是为什么？麻烦！", "哪位大神可以帮帮我啊？", "I need your help."});

        handler = new MyHandler(this);
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        // SurfaceHolder被修改的时候回调
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "SurfaceHolder 被销毁");
            // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "SurfaceHolder 被创建");
            if (currentPosition > 0) {
                // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
                play(currentPosition);
                currentPosition = 0;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.i(TAG, "SurfaceHolder 大小被改变");
        }

    };

    private SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                // 设置当前播放的位置
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };

    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_play:
                    play(0);
                    break;
                case R.id.btn_pause:
                    pause();
                    break;
                case R.id.btn_replay:
                    replay();
                    break;
                case R.id.btn_stop:
                    stop();
                    break;
                case R.id.btn_dan:
                    if (i == 0 ) {
                        if(!running){
                            existMarginValues.clear();
                            new Thread(new CreateTanmuThread()).start();
                            running=true;
                            containerVG.setVisibility(View.VISIBLE);
                        }else{
                            containerVG.setVisibility(View.VISIBLE);
                        }
                        i = 1;
                        btn_dan.setText("取消");
                    } else {
                        btn_dan.setText("弹幕");
                        containerVG.setVisibility(View.GONE);
                        i=0;
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /*
     * 停止播放
     */
    protected void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            btn_play.setEnabled(true);
            isPlaying = false;
            btn_pause.setText("暂停");
            seekBar.setProgress(0);
        }
    }

    /**
     * 开始播放
     *
     * @param msec 播放初始位置
     */
    protected void play(final int msec) {
        // 获取视频文件地址
        String path = "/storage/emulated/0/123.3gp";
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            mediaPlayer.setDataSource(file.getAbsolutePath());
            // 设置显示视频的SurfaceHolder
            mediaPlayer.setDisplay(sv.getHolder());
            Log.i(TAG, "开始装载");
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "装载完成");
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(msec);
                    // 设置进度条的最大进度为视频流的最大播放时长
                    seekBar.setMax(mediaPlayer.getDuration());

                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                isPlaying = true;
                                while (isPlaying) {
                                    int current = mediaPlayer
                                            .getCurrentPosition();
                                    seekBar.setProgress(current);
//                                    Time progress = new Time(mediaPlayer.getCurrentPosition());
//                                    Time remaining = new Time(mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition());
//                                    txt.setText(progress.toString() + "/" + remaining.toString());
                                    sleep(500);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    btn_play.setEnabled(false);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    btn_play.setEnabled(true);
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 发生错误重新播放
                    play(0);
                    isPlaying = false;
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 重新开始播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            Toast.makeText(this, "重新播放", Toast.LENGTH_SHORT).show();
            btn_pause.setText("暂停");
            return;
        }
        btn_pause.setText("暂停");
        isPlaying = false;
        play(0);


    }

    /**
     * 暂停或继续
     */
    protected void pause() {
        if (btn_pause.getText().toString().trim().equals("继续")) {
            btn_pause.setText("暂停");
            mediaPlayer.start();
            Toast.makeText(this, "继续播放", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btn_pause.setText("继续");
            Toast.makeText(this, "暂停播放", Toast.LENGTH_SHORT).show();
        }

    }


    //每2s自动添加一条弹幕
    private class CreateTanmuThread implements Runnable {
        @Override
        public void run() {
            int N = tanmuBean.getItems().length;
            for (int i = 0; i < N; i++) {
                handler.obtainMessage(1, i, 0).sendToTarget();
                SystemClock.sleep(2000);
            }
        }
    }

    //需要在主线城中添加组件
    private static class MyHandler extends Handler {
        private WeakReference<SurfaceViewActivity> ref;

        MyHandler(SurfaceViewActivity ac) {
            ref = new WeakReference<>(ac);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                SurfaceViewActivity ac = ref.get();
                if (ac != null && ac.tanmuBean != null) {
                    int index = msg.arg1;
                    //设置弹幕内容、文字大小和颜色
                    String content = ac.tanmuBean.getItems()[index];
                    float textSize = (float) (ac.tanmuBean.getMinTextSize() * (1 + Math.random() * ac.tanmuBean.getRange()));
                    int a=(int)(Math.random()*5);//产生0-5的随机数
                    int textColor;
                    //产生随机字体颜色
                    switch (a){
                        case 0:
                            textColor=Color.RED;
                            break;
                        case 1:
                            textColor=Color.BLUE;
                            break;
                        case 2:
                            textColor=Color.GREEN;
                            break;
                        case 3:
                            textColor=Color.GRAY;
                            break;
                        default:
                            textColor=Color.YELLOW;

                    }
                    ac.showTanmu(content, textSize, textColor);
                }
            }
        }
    }

    private void showTanmu(String content, float textSize, int textColor) {
        final TextView textView = new TextView(this);

        textView.setTextSize(textSize);
        textView.setText(content);
//        textView.setSingleLine();
        textView.setTextColor(textColor);

        int leftMargin = containerVG.getRight() - containerVG.getLeft() - containerVG.getPaddingLeft();
        //计算本条弹幕的topMargin(随机值，但是与屏幕中已有的不重复)
        int verticalMargin = getRandomTopMargin();
        textView.setTag(verticalMargin);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = verticalMargin;

        textView.setLayoutParams(params);
        Animation anim = AnimationHelper.createTranslateAnim(this, leftMargin, -ScreenUtils.getScreenWidth(this));
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //移除该组件
                containerVG.removeView(textView);
                //移除占位
                int verticalMargin = (int) textView.getTag();
                existMarginValues.remove(verticalMargin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(anim);

        containerVG.addView(textView);
    }

    //记录当前仍在显示状态的弹幕的位置（避免重复）
    private Set<Integer> existMarginValues = new HashSet<>();
    private int linesCount;

    private int getRandomTopMargin() {
        //计算用于弹幕显示的空间高度
        if (validHeightSpace == 0) {
            validHeightSpace = containerVG.getBottom() - containerVG.getTop()
                    - containerVG.getPaddingTop() - containerVG.getPaddingBottom();
        }

        //计算可用的行数
        if (linesCount == 0) {
            linesCount = validHeightSpace / ScreenUtils.dp2px(SurfaceViewActivity.this, tanmuBean.getMinTextSize() * (1 + tanmuBean.getRange()));
            if (linesCount == 0) {
                throw new RuntimeException("Not enough space to show text.");
            }
        }

        //检查重叠
        while (true) {
            int randomIndex = (int) (Math.random() * linesCount);
            int marginValue = randomIndex * (validHeightSpace / linesCount);

            if (!existMarginValues.contains(marginValue)) {
                existMarginValues.add(marginValue);
                return marginValue;
            }
        }
    }

}
