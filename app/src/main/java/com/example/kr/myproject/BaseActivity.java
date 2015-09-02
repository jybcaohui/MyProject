package com.example.kr.myproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;


public class BaseActivity extends FragmentActivity {
    InputMethodManager imm;
    protected Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 提示
     */
    public void toast(int resId) {
        Toast t = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
//        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    /**
     * 提示
     */
    public void toast(String s) {
        Toast t = Toast.makeText(this, s, Toast.LENGTH_SHORT);
//        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    /**
     * 使控件获取焦点并弹出软键盘
     */
    public void requestFocus(View et) {
        et.requestFocus();
        imm.showSoftInput(et, 0);
    }

    public void showProgressDialog() {
        loadingDialog = createLoadingDialog(this);
        loadingDialog.show();
    }

    /**
     * 关闭等待对话框
     */
    public void closeProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }
}
