package com.example.kr.myproject.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by KR on 2015/9/24.
 */
public class AppUtil {

    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle, int compressRate,
                                        Bitmap.CompressFormat compressFormat) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(compressFormat, compressRate, output);

        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle, int compressRate) {
        return bmpToByteArray(bmp, needRecycle, compressRate,
                Bitmap.CompressFormat.JPEG);
    }

    public static void showToast(final Activity activity, final String content) {
        if (content != null) {
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(activity, content,
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }
    }
}
