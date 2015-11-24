package com.example.kr.myproject.imageoprate;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CoverChoosePopView {
    public static final int CAPTURE = 0;
    public static final int ALBUM = 1;
    private PopupWindow popup;
    private View mArchor;
    private Context mContext;
    private PopItemSelector mListener;

    @InjectView(R.id.capture)
    Button capture;
    @InjectView(R.id.ablum)
    Button album;
    @InjectView(R.id.cancel)
    Button cancel;

    public CoverChoosePopView(Activity context, View archor, PopItemSelector itenSelectListener) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.pop_cover_choose,
                null);
        this.mListener = itenSelectListener;
        this.mContext = context;
        ButterKnife.inject(this, layout);
        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popup.setOutsideTouchable(true);

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

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose(CAPTURE);
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose(ALBUM);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void choose(int index) {
        if (mListener != null) {
            mListener.select(index);
            dismiss();
        }
    }

    public void show() {
        if (!popup.isShowing()) {
            popup.showAtLocation(mArchor, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    public void dismiss() {
        if (popup.isShowing()) {
            popup.dismiss();
        }
    }

    public interface PopItemSelector {
        void select(int type);
    }
}

