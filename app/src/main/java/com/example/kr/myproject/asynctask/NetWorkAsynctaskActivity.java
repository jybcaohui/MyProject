package com.example.kr.myproject.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kr.myproject.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class NetWorkAsynctaskActivity extends Activity {
    private ImageView m_imageView;
    private Button m_button;
    private Button m_but;
    private ProgressBar m_proBar;
    Task task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_asynctask);


        m_imageView = (ImageView)findViewById(R.id.imageView);
        m_button = (Button)findViewById(R.id.download_btn);
        m_proBar = (ProgressBar)findViewById(R.id.progressBar);
        m_but = (Button)findViewById(R.id.cancel_btn);

        task = null;
        m_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                task = new Task();
                task.execute("http://www.baidu.com/img/shouye_b5486898c692066bd2cbaeda86d74448.gif");
            }

        });
        m_but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                task.cancel(false);
            }
        });
    }

    class Task extends AsyncTask<String,Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {//处理后台执行的任务，在后台线程执行
            // TODO Auto-generated method stub

            publishProgress(0);//将会调用onProgressUpdate(Integer... progress)方法
            HttpClient hc = new DefaultHttpClient();
            publishProgress(30);
            HttpGet hg = new HttpGet(params[0]);
            Bitmap bm = null;

            HttpResponse hr = null;

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if(isCancelled())
                return null;

            try {
                hr = hc.execute(hg);
                bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(isCancelled())
                return null;
            publishProgress(0);
            publishProgress(100);
            return bm;
        }

        protected void onProgressUpdate(Integer... progress) {//在调用publishProgress之后被调用，在ui线程执行
            m_proBar.setProgress(progress[0]);
        }

        protected void onPostExecute(Bitmap result) {//后台任务执行完之后被调用，在ui线程执行
            if(result != null) {
                Toast.makeText(NetWorkAsynctaskActivity.this, "成功获取图片", Toast.LENGTH_LONG).show();
                m_imageView.setImageBitmap(result);
            }else {
                Toast.makeText(NetWorkAsynctaskActivity.this, "获取图片失败", Toast.LENGTH_LONG).show();
                m_proBar.setProgress(0);
            }

        }

        protected void onPreExecute () {//在 doInBackground(Params...)之前被调用，在ui线程执行
            m_imageView.setImageBitmap(null);
            m_proBar.setProgress(0);//进度条复位
        }

        protected void onCancelled () {//在ui线程执行
            m_proBar.setProgress(0);//进度条复位
            Toast.makeText(NetWorkAsynctaskActivity.this, "取消加载", Toast.LENGTH_LONG).show();
        }

    };
}
