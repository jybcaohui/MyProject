package com.example.kr.myproject.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hui.cao on 2016/3/1.
 */
public class MyTopBar extends RelativeLayout {
    @InjectView(R.id.ic_back)
    ImageView back;
    @InjectView(R.id.title)
    TextView txt_title;
    @InjectView(R.id.rightAction)
    ImageView rightAction;

    private ActionbarViewListener mListener;
    private boolean isEditable;
    private boolean hasNext;
    public MyTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){ //必须加此判断
            RelativeLayout rl =(RelativeLayout) LayoutInflater.from(context).inflate(R.layout.topbar, this, true);
            ButterKnife.inject(this,rl);

            TypedArray t =context.obtainStyledAttributes(attrs , R.styleable.MyTopBar);
            String title = t.getString(R.styleable.MyTopBar_text);
            txt_title.setText(title == null ? "" : title);
            isEditable = t.getBoolean(R.styleable.MyTopBar_isEditable, false);
            boolean isRightActionAble = t.getBoolean(R.styleable.MyTopBar_isRightActionVisible, false);
            if (!isRightActionAble) {
                rightAction.setVisibility(GONE);
            }
            boolean isLeftActionVisible = t.getBoolean(R.styleable.MyTopBar_isLeftActionVisible, true);
            if (!isLeftActionVisible) {
                back.setVisibility(GONE);
            }
            t.recycle();

            back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onBackClicked();
                    }

                }
            });

            rightAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onRightActionClicked();
                    }

                }
            });

        }
    }

    public ActionbarViewListener getmListener() {
        return mListener;
    }

    public void setmListener(ActionbarViewListener mListener) {
        this.mListener = mListener;
    }

    public void setRightActionVisible(boolean isVisible)
    {
        rightAction.setVisibility(isVisible?VISIBLE:GONE);
    }
    /**
     * 设置文本
     */
    public void setText(String text) {
        txt_title.setText(text);
    }


    public interface ActionbarViewListener{
        void onBackClicked();
        void onRightActionClicked();
    }
}
