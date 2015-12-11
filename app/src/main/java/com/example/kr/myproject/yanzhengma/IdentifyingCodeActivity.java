package com.example.kr.myproject.yanzhengma;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kr.myproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class IdentifyingCodeActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.img)
    ImageView img;
    @InjectView(R.id.show)
    TextView show;
    @InjectView(R.id.change)
    Button but;

    private Code code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifying_code);
        ButterKnife.inject(this);
        but.setOnClickListener(this);
        setData();
    }

    public void setData(){
        code=new Code();
        img.setImageBitmap(code.getInstance().createBitmap());
        show.setText(code.getInstance().getCode());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                img.setImageBitmap(code.getInstance().createBitmap());
                show.setText(code.getInstance().getCode());
                break;
            default:
                break;
        }
    }
}
