package com.example.kr.myproject.analyhtml;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kr.myproject.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class AnalyHtmlActivity extends Activity {

    private WebView web;
    Document doc;
    WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analy_html);
        web = (WebView) findViewById(R.id.web);
        webSettings = web.getSettings();
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(false);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        web.requestFocus();
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("erik", "loadUrl ,url = " + url);
                view.loadUrl(url);
                return true;
            }
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                if (failingUrl.contains("#")) {
                    Log.d("erik", "failingurl = " + failingUrl);
                    String[] temp;
                    temp = failingUrl.split("#");
                    view.loadUrl(temp[0]); // load page without internal
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    view.goBack();
                    view.goBack();
                }
            }
        });
        editHtml();
        web.loadUrl("file:///sdcard/tchstd.html");
    }

    public void editHtml() {
        Log.d("file--", Environment.getExternalStorageDirectory().getPath() + "/tchstd.html");
        File input = new File("/sdcard/tchstd.html");
        try {
            doc = Jsoup.parse(input, "utf-8");

            Log.d("element---", doc.toString());//文章整体
            Elements es = doc.getElementsByClass("page");
            Log.d("element---0", es.toString());//page页

            String selfname = "stage_004";
            String title = "第四章 测试增加内容";
            String fontSrc = "#stage_001";
            String content = "10.雨淅淅沥沥下了整晚，清晨屋里充满了清新潮湿的味道。我松开拽着被单的手，转过身看了看妮妮。她睡着正香。今天要与好友航海出游，相约上午10点在码头集合出发，搭乘南希的高富帅男友。";
            String moreSrc = "#stage_005";
            String moreTxt = "返回第五段";
            String more ="<a href=\"" + moreSrc + "\" class=\"btn_01\">" + moreTxt + "</a>";
            String moreList=more+more+more;

            String addstr = "<div id=\"" + selfname + "\"><h5>" + title + "<span><a href=\"" + fontSrc + "\"><font>上一步</font> <em>←</em></a></span></h5><p>" + content + "</p>"+moreList+"</div>";
            String insteadstr = "<h5>" + title + "<span><a href=\"" + fontSrc + "\"><font>上一步</font> <em>←</em></a></span></h5><p>" + content + "</p>"+moreList;

            for (Element e : es) {//遍历page头div的每个子节点
//                Map<String, String> map = new HashMap<String, String>();
//                Log.d("element---1", e.toString());
//                Log.d("element---2", e.getElementsByTag("h4").toString());//固定某一个节点
//                Log.d("element---2", e.getElementsByTag("h4").text());//固定某节点的所有文字内容
//                if(e.getElementsByTag("h5").hasAttr("stage_008")){
//                    Log.d("stage_008", e.toString());
//                }
            }
            Element e = doc.getElementById(selfname);

            //增加或替换元素
            if (e == null) {
                es.append(addstr);//为空，则在末尾增加新元素
            }else{
                Log.d("element==4",e.toString());
                e.html(insteadstr);//不为空，则清除原有内容，替换为新内容（替换元素内的内容，不包括头标签）
                Log.d("element==24", e.toString());
            }

            //更新文章头部信息（作者，创建时间，更新时间）
            Element h4=doc.getElementsByTag("h4").first();
            Log.d("h4", h4.toString());
            SimpleDateFormat sDateFormat =new SimpleDateFormat("yyyy-MM-dd    HH:mm:ss"); //HH:24小时制，hh:12小时制
            String  date=sDateFormat.format(new java.util.Date());
            String authName=" 睡梦落人";
            String update="<span><font>创作 2015-5-20  18:20:12</font><font>更新"+date+"</font><font>作者"+authName+"</font></span>";
            h4.html(update);


            Element h1=doc.getElementsByTag("h1").first();
            Log.d("h1", h1.toString());
            String bookName="逃出灰色天地2";
            h1.text(bookName);
             /*
              * Jsoup只是解析，不能保存修改，所以要在这里保存修改。
              */
            input.delete(); //清除原有内容，重新写入
            FileOutputStream fos = new FileOutputStream(new File("/sdcard/tchstd.html"), true);
            fos.write(doc.html().getBytes());
            fos.close();
            Log.d("element---0", es.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
    }
}
