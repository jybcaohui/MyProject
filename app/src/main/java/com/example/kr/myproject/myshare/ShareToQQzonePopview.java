package com.example.kr.myproject.myshare;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kr.myproject.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * author:qisheng.chen
 * time:2014/7/15.9:49
 */
public class ShareToQQzonePopview {
    private PopupWindow popup;
    private View mArchor;
    private Context mContext;
    ShareCallback listener;


    @InjectView(R.id.cover)
    ImageView cover;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.send)
    RelativeLayout send;
    @InjectView(R.id.title)
    TextView titleText;

    public ShareToQQzonePopview(final Activity context, View archor, final ShareCallback listener) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popview_weibo_share,
                null);
        this.listener = listener;
        this.mContext = context;
        ButterKnife.inject(this, layout);
        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popup.setOutsideTouchable(true);
        // popup.setAnimationStyle(R.style.PopupWindowAnimation);

        // Some offset to align the popup a bit to the right, and a bit down,
        // relative to button's position.

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        // Displaying the popup at the specified location, + offsets.
        mArchor = archor;

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                if (listener != null) {
                    listener.popDiss();
                }
            }
        });

        //增加分享广告

        Picasso.with(mContext).load(R.drawable.act).into(cover);
        content.setText("http://www.qicheng.tv");

//        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);
//        if (accessToken.isSessionValid()) {
//            statusesAPI = new StatusesAPI(mContext, accessToken);
//        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onShare(content.getText().toString().trim());
            }
        });
    }

    public void show() {
        if (!popup.isShowing()) {
            //popup.showAsDropDown(mArchor, 0, 0);
            popup.showAtLocation(mArchor, Gravity.CENTER, 0, 0);
        }
    }

    public void dismiss() {
        if (popup.isShowing()) {
            popup.dismiss();
        }
    }


    public interface ShareCallback {
        void onShare(String s);

        void popDiss();
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }
}
