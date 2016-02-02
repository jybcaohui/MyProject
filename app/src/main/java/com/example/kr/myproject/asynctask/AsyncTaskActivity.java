package com.example.kr.myproject.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;

/**
 * http://embed.21ic.com/software/android/201403/31550.html
 */
public class AsyncTaskActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    Button download;
    ProgressBar pb;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        pb = (ProgressBar) findViewById(R.id.pb);
        tv = (TextView) findViewById(R.id.tv);
        download = (Button) findViewById(R.id.download);
        //    弹出要给ProgressDialog
        progressDialog = new ProgressDialog(AsyncTaskActivity.this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载中，请稍后......");
        //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为圆圈的形式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask dTask = new DownloadTask();
                dTask.execute(100);
            }
        });
    }

    class DownloadTask extends AsyncTask {
        //后面尖括号内分别是参数(例子里是线程休息时间)，进度(publishProgress用到)，返回值 类型

        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
            //    在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //第二个执行方法,onPreExecute()执行完后执行
            for (int i = 0; i <= 100; i++) {
                pb.setProgress(i);
                publishProgress(i);
                try {
                    Thread.sleep((Integer)params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "执行完毕";
        }


        @Override
        protected void onProgressUpdate(Object[] progress) {
            //这个函数在doInBackground调用publishProgress时触发，虽然调用时只有一个参数
            //但是这里取到的是一个数组,所以要用progesss[0]来取值
            //第n个参数就用progress[n]来取值
            tv.setText(progress[0] + "%");
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Object result) {
            //doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
            //这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕"
            setTitle(result.toString());
            super.onPostExecute(result);
            //    使ProgressDialog框消失
            progressDialog.dismiss();
        }

    }

}
