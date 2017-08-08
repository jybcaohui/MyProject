package com.example.kr.myproject.jswithandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class JSAndroidActivity extends AppCompatActivity {

    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.wb)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsandroid);
        ButterKnife.inject(this);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //支持App内部JavaScript交互
        webview.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.addJavascriptInterface(new Contact(this, webview), "contact");
        webview.getSettings().setLoadWithOverviewMode(true);
        //设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //设置是否出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        webview.loadUrl("file:///android_asset/demo.html");
    }

    @JavascriptInterface
    public void toast(String str) {
        Toast.makeText(JSAndroidActivity.this, "aaaaaaaaaaaa  --- " + str, Toast.LENGTH_LONG).show();
    }
}
