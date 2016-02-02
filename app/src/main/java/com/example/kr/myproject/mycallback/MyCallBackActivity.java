package com.example.kr.myproject.mycallback;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.mycallback.dialog.MyDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyCallBackActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.but)
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_back);
        ButterKnife.inject(this);
        but.setOnClickListener(this);
        test();
    }

    //测试回调方法
    public static void test() {
        A a = new A();
        //相当于把A和B通过CallBack接口建立了联系
        a.setCallfuc(new B());
        //调用了该方法，B中的接口方法打印
        a.call();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but:
                final MyDialog myDialog2 = new MyDialog(MyCallBackActivity.this, getWindowManager());
                myDialog2.setDialogCallback(new MyDialog.Dialogcallback() {
                    @Override
                    public void dialogdo(String string) {
                        Log.d("mydialog--","在此操作");
                        myDialog2.dismiss();
                    }
                });
                myDialog2.show();
                break;
            default:
                break;
        }
    }
}
