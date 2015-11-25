package com.example.kr.myproject.imageoprate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.imageoprate.util.BitmapUtil;
import com.example.kr.myproject.imageoprate.util.CircleTransform;
import com.example.kr.myproject.util.AppUtil;
import com.example.kr.myproject.util.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChangeImageActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.avatar)
    ImageView avatar;
    @InjectView(R.id.but)
    Button but;

    CoverChoosePopView avatarChoosePop;
    public static final int CHOOSE_PHOTO = 0;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final String USER_TMP_AVATAR = "/survivalgame/tmp/bookcover.jpg";
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);
        ButterKnife.inject(this);
        but.setOnClickListener(this);
        Picasso.with(this).load(R.drawable.a).transform(new CircleTransform()).into(avatar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but:
                avatarChoosePop = new CoverChoosePopView(this, findViewById(android.R.id.content), new CoverChoosePopView.PopItemSelector() {
                    @Override
                    public void select(int type) {
                        switch (type) {
                            case CoverChoosePopView.CAPTURE:
                                takePhoto();
                                break;
                            case CoverChoosePopView.ALBUM:
                                choosePhoto();
                                break;
                        }
                    }
                });
                avatarChoosePop.show();
                break;

        }
    }


    //从相册选择
    private void choosePhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //<19
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CHOOSE_PHOTO);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image*//*");
            startActivityForResult(intent, CHOOSE_PHOTO);
        }
    }

    //拍照
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + USER_TMP_AVATAR)));
        startActivityForResult(intent, TAKE_PHOTO);
    }



    //图片裁剪
    private void cropImage(Uri uri, int requestCode) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.putExtra(CropActivity.IMAGE_PATH, AppUtil.getPath(this, uri));
        intent.putExtra(CropActivity.ASPECT_RATIO_X, 81);//裁剪框比例100:81
        intent.putExtra(CropActivity.ASPECT_RATIO_Y, 100);
        intent.putExtra(CropActivity.OUTPUT_X, 108);//输出108*132px的图片
        intent.putExtra(CropActivity.OUTPUT_Y, 132);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO:
                    cropImage(data.getData(), CROP_PHOTO);
                    break;
                case TAKE_PHOTO:
                    cropImage(Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                            + USER_TMP_AVATAR)), CROP_PHOTO);
                    break;
                case CROP_PHOTO:
                    try {
                        bitmap = BitmapUtil.getOriginBitMap(Environment.getExternalStorageDirectory()
                                + USER_TMP_AVATAR);
                    } catch (IOException e) {
                        Log.e("---", "oom exception");
                        e.printStackTrace();
                        return;
                    }
                    new AlertDialog.Builder(ChangeImageActivity.this).setTitle("提示").setMessage("确定修改头像吗")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("=====","===="+bitmap);
                                    avatar.setImageBitmap(bitmap);

                                }
                            }).setNegativeButton("取消", null).show();
                    break;

            }
        }
    }

}
