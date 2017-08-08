package com.example.kr.myproject.jswithandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by jyblc on 2017/8/7.
 */
public class Contact {
    private Activity activity;
    private WebView webView;

    public Contact(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    //JavaScript调用此方法拨打电话
    @JavascriptInterface
    public void call(String phone,String s) {
//            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
        Toast.makeText(activity, phone, Toast.LENGTH_LONG).show();
        sendSMS(phone,s);
    }

    //Html调用此方法传递数据
    @JavascriptInterface
    public void showcontacts() {
        handler.sendEmptyMessage(0);
    }

    @JavascriptInterface
    public void toast(String str) {
        Toast.makeText(activity, "aaaaaaaaaaaa  --- " + str, Toast.LENGTH_LONG).show();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
                    // 调用JS中的方法
                    webView.loadUrl("javascript:show('" + json + "')");
                    break;
                default:
                    break;
            }
        }
    };

    // 重写addJavaScriptInteface getClass方法，防止类对象漏洞攻击
    public Object getClass(Object o) {
        return null;
    }

    /**
     * 发短信
     * @param smsBody 内容
     * @param num 发送到手机号
     */
    private void sendSMS(String num,String smsBody){
        Uri smsToUri = Uri.parse("smsto:"+num);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        activity.startActivity(intent);
    }
}