package com.example.kr.myproject.imageoprate.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil {

	public static InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	public static byte[] InputStream2Bytes(InputStream is) {
		if (is == null) {
			return null;
		}
		String str = "";
		byte[] readByte = new byte[1024];
		try {
			while ((is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static InputStream Bitmap2InputStream(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	public static Bitmap InputStream2Bitmap(InputStream is) {
		if (is == null) {
			return null;
		}
		return BitmapFactory.decodeStream(is);
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	/**
	 * 加载网络图片
	 * 
	 * @param path 路径
	 * @param handler 回调
	 */
	public static void load(final String path, final Handler handler) {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(10000);
					conn.setDoInput(true);
					conn.setUseCaches(false);
					conn.connect();
					InputStream is = conn.getInputStream();
					Bitmap bmp = BitmapUtil.InputStream2Bitmap(is);
					is.close();
					Message message = new Message();
					message.obj = bmp;
					handler.sendMessage(message);
				} catch (IOException e) {
					Log.e("BitmapUtil.load", e.toString());
				}
			}
		}.start();
	}

    /**
     * 根据路径获取 图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap getOriginBitMap(String path) throws IOException {
        Bitmap bmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        try {
            bmp = BitmapFactory.decodeFile(path, opts);
        } catch (OutOfMemoryError e) {
        }
        return bmp;
    }
}
