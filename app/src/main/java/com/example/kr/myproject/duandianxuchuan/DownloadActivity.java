package com.example.kr.myproject.duandianxuchuan;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.R;
import com.example.kr.myproject.duandianxuchuan.adapter.DownLoadAdapter;
import com.example.kr.myproject.duandianxuchuan.data.LoadInfo;
import com.example.kr.myproject.duandianxuchuan.util.Downloader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadActivity extends ListActivity {
    // 固定下载的资源路径，这里可以设置网络上的地址
//    private static final String URL = "http://10.0.1.163/shine/";
    private static final String URL = "http://114.55.9.239/androidteam/";
//    private static final String URL = "http://114.55.9.239/androidteam/survivalgame/repository/archive.zip";
//    private static final String URL = "http://114.55.9.239/androidteam/chengxingv2/repository/archive.zip";
//    private static final String URL = "http://114.55.9.239/androidteam/sodaplayer/repository/archive.zip";
//    private static final String URL = "http://114.55.9.239/androidteam/qicheng/repository/archive.zip";
    // 固定存放下载的音乐的路径：SD卡目录下
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath()+"/myproject/";
    // 存放各个下载器
    private Map<String, Downloader> downloaders = new HashMap<String, Downloader>();
    // 存放与下载器对应的进度条
    private Map<String, ProgressBar> ProgressBars = new HashMap<String, ProgressBar>();
    /**
     * 利用消息处理机制适时更新进度条
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String url = (String) msg.obj;
                int length = msg.arg1;
                ProgressBar bar = ProgressBars.get(url);
                if (bar != null) {
                    // 设置进度条按读取的length长度更新
                    bar.incrementProgressBy(length);
                    if (bar.getProgress() == bar.getMax()) {
                        LinearLayout layout = (LinearLayout) bar.getParent();
                        TextView resouceName=(TextView)layout.findViewById(R.id.tv_resouce_name);
                        Toast.makeText(DownloadActivity.this, "[" + resouceName.getText() + "]下载完成！", Toast.LENGTH_SHORT).show();
                        // 下载完成后清除进度条并将map中的数据清空
                        layout.removeView(bar);
                        ProgressBars.remove(url);
                        downloaders.get(url).delete(url);
                        downloaders.get(url).reset();
                        downloaders.remove(url);

                        Button btn_start=(Button)layout.findViewById(R.id.btn_start);
                        Button btn_pause=(Button)layout.findViewById(R.id.btn_pause);
                        btn_pause.setVisibility(View.GONE);
                        btn_start.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        File file = new File(SD_PATH);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        showListView();
    }
    // 显示listView，这里可以随便添加
    private void showListView() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "qicheng/repository/archive.zip");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "survivalgame/repository/archive.zip");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "chengxingv2/repository/archive.zip");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "sodaplayer/repository/archive.zip");
        data.add(map);
        DownLoadAdapter adapter=new DownLoadAdapter(this,data);
        setListAdapter(adapter);

    }
    /**
     * 响应开始下载按钮的点击事件
     */
    public void startDownload(View v) {
        // 得到textView的内容
        LinearLayout layout = (LinearLayout) v.getParent();
        String resouceName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();
        String urlstr = URL + resouceName;
        resouceName = resouceName.replace("/", "");

        String localfile = SD_PATH + resouceName;
        //设置下载线程数为4，这里是我为了方便随便固定的
        String threadcount = "4";
        DownloadTask downloadTask=new DownloadTask(v);
        downloadTask.execute(urlstr,localfile,threadcount);

    };
    class DownloadTask extends AsyncTask<String, Integer, LoadInfo> {
        Downloader downloader=null;
        View v=null;
        TextView txt;
        String urlstr=null;
        public DownloadTask(final View v){
            this.v=v;
            txt=(TextView)((View)v.getParent()).findViewById(R.id.txt);
        }
        @Override
        protected void onPreExecute() {
            Button btn_start=(Button)((View)v.getParent()).findViewById(R.id.btn_start);
            Button btn_pause=(Button)((View)v.getParent()).findViewById(R.id.btn_pause);
            btn_start.setVisibility(View.GONE);
            btn_pause.setVisibility(View.VISIBLE);
        }
        @Override
        protected LoadInfo doInBackground(String... params) {
            urlstr=params[0];
            String localfile=params[1];
            int threadcount=Integer.parseInt(params[2]);
            // 初始化一个downloader下载器
            downloader = downloaders.get(urlstr);
            if (downloader == null) {
                downloader = new Downloader(urlstr, localfile, threadcount, DownloadActivity.this, mHandler);
                downloaders.put(urlstr, downloader);
            }
            if (downloader.isdownloading())
                return null;
            // 得到下载信息类的个数组成集合
            return downloader.getDownloaderInfors();
        }
        @Override
        protected void onPostExecute(LoadInfo loadInfo) {
            if(loadInfo!=null){
                // 显示进度条
                showProgress(loadInfo, urlstr, v);
                txt.setText(loadInfo.getComplete()+"/"+loadInfo.getFileSize());
                // 调用方法开始下载
                downloader.download();
            }
        }

    };
    /**
     * 显示进度条
     */
    private void showProgress(LoadInfo loadInfo, String url, View v) {
        ProgressBar bar = ProgressBars.get(url);
        if (bar == null) {
            bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            bar.setMax(loadInfo.getFileSize());
            bar.setProgress(loadInfo.getComplete());
            ProgressBars.put(url, bar);
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, 15);
            ((LinearLayout) ((LinearLayout) v.getParent()).getParent()).addView(bar, params);
        }
    }
    /**
     * 响应暂停下载按钮的点击事件
     */
    public void pauseDownload(View v) {
        LinearLayout layout = (LinearLayout) v.getParent();
        String resouceName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();
        String urlstr = URL + resouceName;
        downloaders.get(urlstr).pause();
        Button btn_start=(Button)((View)v.getParent()).findViewById(R.id.btn_start);
        Button btn_pause=(Button)((View)v.getParent()).findViewById(R.id.btn_pause);
        btn_pause.setVisibility(View.GONE);
        btn_start.setVisibility(View.VISIBLE);
    }
}
