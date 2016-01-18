package com.example.kr.myproject.mycallback.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kr.myproject.R;

/**
 * Created by KR on 2015/10/16.
 */
public class MyDialog {
    Context context;
    Dialogcallback dialogcallback;
    Dialog dialog;
    TextView sure;
    TextView cancle;
    TextView title;
    EditText editText;
    TextView hint;
    WindowManager windowManager;

    /**
     * init the dialog
     *
     * @return
     */
    public MyDialog(Context con, WindowManager windowManager) {
        this.context = con;
        this.windowManager=windowManager;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_my);
        title = (TextView) dialog.findViewById(R.id.title);
        sure = (TextView) dialog.findViewById(R.id.button1);
        cancle = (TextView) dialog.findViewById(R.id.button2);
        editText = (EditText) dialog.findViewById(R.id.editText);
        hint = (TextView) dialog.findViewById(R.id.hint);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogcallback.dialogdo(editText.getText().toString());
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void setDialogCallback(Dialogcallback dialogcallback) {
        this.dialogcallback = dialogcallback;
    }

    /**
     * @category Set The Content of the TextView
     */
    public void setContent(String content) {
        title.setText(content);
    }

    /**
     * Get the Text of the EditText
     */
    public String getText() {
        return editText.getText().toString();
    }

    public void setFullscreen() {
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public void show() {
        dialog.show();
        setFullscreen();
    }

    public void hide() {
        dialog.hide();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void hidehint() {
        hint.setVisibility(View.GONE);
    }

    public void setShowText(String text) {
        editText.setText(text);
    }

    /**
     * 设定一个interfack接口,使mydialog可以處理activity定義的事情
     *
     * @author sfshine
     */
    public interface Dialogcallback {
        public void dialogdo(String string);
    }
}
