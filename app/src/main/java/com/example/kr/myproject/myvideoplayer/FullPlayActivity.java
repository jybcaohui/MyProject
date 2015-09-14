package com.example.kr.myproject.myvideoplayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;


import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FullPlayActivity extends Activity {

    @InjectView(R.id.video_view)
    SurfaceView video;
    @InjectView(R.id.seekBar)
    SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private String path;
    private int progress;
    private Boolean isPlaying=false;
    private int currentPosition = 0;
    private int max = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_play);
        ButterKnife.inject(this);
        // 为SurfaceHolder添加回调
        video.getHolder().addCallback(callback);
        progress = getIntent().getIntExtra("currentpos", 0);
        max = getIntent().getIntExtra("max", 0);
        path = getIntent().getStringExtra("uri");
        currentPosition=progress;
        play(progress);
    }

    protected void play(final int msec) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            //网络文件
            mediaPlayer.setDataSource(path);
            // 设置显示视频的SurfaceHolder
            mediaPlayer.setDisplay(video.getHolder());
            Log.d("---", "开始装载");
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("---", "装载完成");
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(msec);
                    // 设置进度条的最大进度为视频流的最大播放时长
                    seekBar.setMax(max);

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
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
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

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        // SurfaceHolder被修改的时候回调
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i("---", "SurfaceHolder 被销毁");
            // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i("---", "SurfaceHolder 被创建");
            if (currentPosition > 0) {
                // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
                play(currentPosition);
                currentPosition = 0;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.i("---", "SurfaceHolder 大小被改变");
        }

    };

    private SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            Log.d("---",mediaPlayer.getCurrentPosition()+"");
            Log.d("---",mediaPlayer.getDuration()+"");
            Log.d("---",seekBar.getProgress()+"");
            Log.d("---",seekBar.getMax()+"");
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

}
