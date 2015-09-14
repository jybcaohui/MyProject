package com.example.kr.myproject.weixinheadimagecut;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.weixinheadimagecut.img.ClipImageLayout;

import java.io.ByteArrayOutputStream;

public class CutImageActivity extends BaseActivity {

    private ClipImageLayout mClipImageLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cut_image);
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cut_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cut:
                Bitmap bitmap = mClipImageLayout.clip();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();

                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtra("bitmap", datas);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
