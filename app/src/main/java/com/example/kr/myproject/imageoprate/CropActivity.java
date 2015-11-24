package com.example.kr.myproject.imageoprate;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.imageoprate.view.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CropActivity extends BaseActivity{
    public static final String USER_TMP_AVATAR = "/qicheng/tmp/avatar.jpg";
    public static final String APP_TMP_FILE = "/qicheng/tmp/";
    final int IMAGE_MAX_SIZE = 1024;
    private static final String TAG = "CropImage";
    // Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final int DEFAULT_OUTPUT_SIZE = 200;
    private static final int DEFAULT_OUTPUT_SIZE_Y = 200;
    public static final String IMAGE_PATH = "image-path";
    public static final String SAVE_PATH = "save-path";
    public static final String ASPECT_RATIO_X = "aspectX";
    public static final String ASPECT_RATIO_Y = "aspectY";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    public static final String RETURN_DATA = "return-data";
    public static final String RETURN_DATA_AS_BITMAP = "data";
    public static final String ACTION_INLINE_DATA = "inline-data";
    private static final int ON_TOUCH = 1;
    private int mOutputX;
    private int mOutputY;
    private Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;

    // Instance variables
    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    Bitmap croppedImage;
    TextView cancel;
    TextView confirm;
    private ContentResolver mContentResolver;
    private Uri mSaveUri = null;
    private Bitmap mBitmap;
    private String mImagePath;
    private String mSavePath;
    private File tmp = new File(Environment.getExternalStorageDirectory() + APP_TMP_FILE);
    private File imageFile = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        mContentResolver = getContentResolver();
        tmp.mkdirs();
        imageFile = new File(
                Environment.getExternalStorageDirectory()
                        + USER_TMP_AVATAR);
        mOutputX = DEFAULT_OUTPUT_SIZE;
        mOutputY = DEFAULT_OUTPUT_SIZE_Y;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAspectRatioX = extras.getInt(ASPECT_RATIO_X);
            mAspectRatioY = extras.getInt(ASPECT_RATIO_Y);
            mOutputX = extras.getInt(OUTPUT_X);
            mOutputY = extras.getInt(OUTPUT_Y);

            mImagePath = extras.getString(IMAGE_PATH);
            mSavePath = extras.getString(SAVE_PATH);
            if (!TextUtils.isEmpty(mSavePath)) {
                imageFile = new File(mSavePath);
            }
            mSaveUri = getImageUri(imageFile.getAbsolutePath());
            mBitmap = getBitmap(mImagePath);
            Log.d(TAG, "x:y  " + mAspectRatioX + ":" + mAspectRatioY);
        }



        if (mBitmap == null) {
            Log.d(TAG, "finish!!!");
            finish();
            return;
        }

        // Initialize components of the app
        final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setImageBitmap(mBitmap);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setAspectRatio(mAspectRatioX, mAspectRatioY);

        confirm = (TextView) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                croppedImage = cropImageView.getCroppedImage();
                /* Scale the image to the required dimensions */
                //scale 缩小
                croppedImage = transform(new Matrix(),
                        croppedImage, mOutputX, mOutputY, true);
                saveOutput(croppedImage);
            }
        });

        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ASPECT_RATIO_X, mAspectRatioX);
        outState.putInt(ASPECT_RATIO_Y, mAspectRatioY);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAspectRatioX = savedInstanceState.getInt(ASPECT_RATIO_X);
        mAspectRatioY = savedInstanceState.getInt(ASPECT_RATIO_Y);
    }

    private Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private Bitmap getBitmap(String path) {
        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = mContentResolver.openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            //旋转图片 动作
            Matrix matrix = new Matrix();
            int angle =getPictureDegree(path);
            matrix.postRotate(angle);
            System.out.println("angle2=" + angle);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(b, 0, 0,
                    b.getWidth(), b.getHeight(), matrix, true);

            return resizedBitmap;
            //return b;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file " + path + " not found");
        } catch (IOException e) {
            Log.e(TAG, "file " + path + " not found");
        }
        return null;
    }


    private void saveOutput(Bitmap croppedImage) {

        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ex) {

                Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
                setResult(RESULT_CANCELED);
                finish();
                return;
            } finally {
                if (outputStream == null) return;
                try {
                    outputStream.close();
                } catch (Throwable t) {
                    // do nothing
                }
            }

            Bundle extras = new Bundle();
            Intent intent = new Intent(mSaveUri.toString());
            intent.putExtras(extras);
            intent.putExtra(IMAGE_PATH, mImagePath);
            //intent.putExtra(ORIENTATION_IN_DEGREES, Util.getOrientationInDegree(this));
            setResult(RESULT_OK, intent);
        } else {
            Log.e(TAG, "not defined image url");
        }
        croppedImage.recycle();
        finish();
    }




    public static Bitmap transform(Matrix scaler,
                                   Bitmap source,
                                   int targetWidth,
                                   int targetHeight,
                                   boolean scaleUp) {

        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            /*
             * In this case the bitmap is smaller, at least in one dimension,
             * than the target.  Transform it by placing as much of the image
             * as possible into the target and leaving the top/bottom or
             * left/right (or both) black.
             */
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
                    Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(
                    deltaXHalf,
                    deltaYHalf,
                    deltaXHalf + Math.min(targetWidth, source.getWidth()),
                    deltaYHalf + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(
                    dstX,
                    dstY,
                    targetWidth - dstX,
                    targetHeight - dstY);
            c.drawBitmap(source, src, dst, null);
            return b2;
        }
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }

        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to mFilter here.
            b1 = Bitmap.createBitmap(source, 0, 0,
                    source.getWidth(), source.getHeight(), scaler, true);
        } else {
            b1 = source;
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(
                b1,
                dx1 / 2,
                dy1 / 2,
                targetWidth,
                targetHeight);

        if (b1 != source) {
            b1.recycle();
        }

        return b2;
    }
    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


}