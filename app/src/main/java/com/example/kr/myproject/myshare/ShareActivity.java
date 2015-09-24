package com.example.kr.myproject.myshare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.util.AppUtil;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.t.Weibo;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private IWXAPI api;

    private Tencent mTencent;

    private String s = "http://m.qicheng.tv/upload/avatar/030/02/04/04_avatar_small.jpg";
    private ArrayList<String> arrayList;
    private ShareToQQzonePopview shareToQQWeiboPopview;
    private Weibo mTencentWeibo;
    //    private OnShareListener mListener;
    public static final String QQ_SCOPE = "get_user_info,get_simple_userinfo,get_user_profile,get_app_friends,"
            + "add_share,add_topic,add_t,add_pic_t,list_album,upload_pic,add_album,set_user_face,get_vip_info,get_vip_rich_info,get_intimate_friends_weibo,match_nick_tips_weibo";


    Boolean sendqz;
    @InjectView(R.id.root)
    View root;
    @InjectView(R.id.wxshare)
    Button wxshare;
    @InjectView(R.id.wxsharefri)
    Button wxsharefri;
    @InjectView(R.id.qqshare)
    Button qqshare;
    @InjectView(R.id.qqzoneshare)
    Button zoneShare;
    @InjectView(R.id.qqweiboshare)
    Button qqweiboshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.inject(this);
        mTencent = Tencent.createInstance("1104804443", this.getApplicationContext());
        wxshare.setOnClickListener(this);
        wxsharefri.setOnClickListener(this);
        qqshare.setOnClickListener(this);
        zoneShare.setOnClickListener(this);
        qqweiboshare.setOnClickListener(this);

        arrayList = new ArrayList<>();
        arrayList.add(s);
        arrayList.add("http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wxshare:
                sendqz = true;
                shareToWeChat();
                break;
            case R.id.wxsharefri:
                sendqz = false;
                shareToWeChat();
                break;
            case R.id.qqshare:
                onQQBind(false);
                break;
            case R.id.qqzoneshare:
                shareToQzone();
                break;
            case R.id.qqweiboshare:
                onQQBind(true);
                break;
        }
    }

    private void shareToWeChat() {
        String app_id = "wx90aaa93de3644201";
        api = WXAPIFactory.createWXAPI(this, app_id, true);
        api.registerApp(app_id);

        Bitmap loadedImage = null;
        loadedImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.a);
        byte[] imageByte;
        int compressionRatio = 100;
        imageByte = AppUtil.bmpToByteArray(loadedImage, false, compressionRatio);
        int initSize = imageByte.length;
        compressionRatio = 60;
        boolean alterType = false;
        while (imageByte.length > 32 * 1024) {
            if (!alterType) {
                compressionRatio = compressionRatio - 10;
                imageByte = AppUtil.bmpToByteArray(loadedImage, false,
                        compressionRatio); // 默认为 JPEG
            } else {
                // 若非 JPEG 则使用 PNG 压缩
                imageByte = AppUtil.bmpToByteArray(loadedImage, false,
                        compressionRatio, Bitmap.CompressFormat.PNG);
                compressionRatio = compressionRatio - 10;
            }
            if (initSize == imageByte.length) {
                alterType = true;
                compressionRatio = 60;
            }
        }
        WXMediaMessage msg = null;
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";
        msg = new WXMediaMessage(webpage);
        msg.title = "我的分享";
        msg.description = "微信分享内容";
        msg.thumbData = imageByte;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (sendqz) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    private void shareToQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        mTencent.shareToQQ(ShareActivity.this, params, new BaseUiListener() {
            @Override
            protected void doComplete(Object o) {
                Toast.makeText(ShareActivity.this, "分享QQ好友成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void shareToQzone() {
        Log.d("shareToQzone", "分享到空间");
        final Bundle params = new Bundle();
        //分享类型
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayList);
        mTencent.shareToQzone(ShareActivity.this, params, new BaseUiListener() {
            @Override
            protected void doComplete(Object o) {
                Toast.makeText(ShareActivity.this, "分享到空间成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //腾讯QQ 回调Listener
    public abstract class BaseUiListener implements IUiListener {

        protected abstract void doComplete(Object o);

        @Override
        public void onComplete(Object o) {
            doComplete(o);
        }

        @Override
        public void onError(UiError e) {
            Log.d("onError", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            AppUtil.showToast(ShareActivity.this,
                    "分享错误" + "code:" + e.errorCode + ", msg:"
                            + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
//            Toast.makeText(getApplicationContext(), getResources().getString(R.string.auth_cancel),
//                    Toast.LENGTH_LONG).show();
        }
    }


    private void doSendPicWeibo(final String token, final String openId, final String expiresIn) {
        shareToQQWeiboPopview = new ShareToQQzonePopview(this, root, new ShareToQQzonePopview.ShareCallback() {
            @Override
            public void onShare(final String content) {
                QQToken qqToken = new QQToken("1104804443");
                qqToken.setAccessToken(token, expiresIn);
                qqToken.setOpenId(openId);
                qqToken.setAuthSource(QQToken.AUTH_QQ);

                mTencentWeibo = new Weibo(ShareActivity.this, qqToken);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTencentWeibo.sendText(content,
                                new BaseUiListener() {
                                    @Override
                                    protected void doComplete(Object o) {
                                        AppUtil.showToast(ShareActivity.this, "分享成功！");
                                        shareToQQWeiboPopview.dismiss();
                                    }

                                    @Override
                                    public void onError(UiError e) {
                                        AppUtil.showToast(ShareActivity.this, "分享出错" + " code:" + e.errorCode + ", msg:"
                                                + e.errorMessage + ", detail:" + e.errorDetail);
                                        shareToQQWeiboPopview.dismiss();
                                    }

                                    @Override
                                    public void onCancel() {
                                        shareToQQWeiboPopview.dismiss();
                                    }
                                });
                    }
                }).start();
            }

            @Override
            public void popDiss() {

            }
        });
        shareToQQWeiboPopview.setTitle("分享到腾讯微博");
        shareToQQWeiboPopview.show();
    }

    public interface OnShareListener {
        //type 定义在 IConstants  WECHAT = 1;QQ_STR = 2;WEIBO = 3;
        public void onSuccessShare(int type);

        public void onFailShare(int type);

        public void onCancelShare(int type);
    }

    private void onQQBind(final boolean isWeibo) {
        IUiListener listener = new BaseUiListener() {
            @Override
            protected void doComplete(Object o) {
                try {
                    final JSONObject values = new JSONObject(o.toString());
                    QQAccessTokenKeeper.keepAccessToken(ShareActivity.this, values.getString("access_token"),
                            values.getString("openid"), values.getString("expires_in"));
                    final String token = values.getString("access_token");
                    final String openId = values.getString("openid");
                    final String expiresIn = values.getString("expires_in");
                    if (isWeibo) {
                        ShareActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doSendPicWeibo(token, openId, expiresIn);
                            }
                        });
                    } else {
                        shareToQQ();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mTencent.login(ShareActivity.this, QQ_SCOPE, listener);
    }

}
