package com.example.kr.myproject.placechoose;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.kr.myproject.placechoose.data.Province;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kison
 * Date on 2014/6/11
 * Time on 16:58
 */
public class PlacePraser {

    /**
     * 解析地址的Json列表
     */
    public static List<Province> prasePlace(Context mContext) {
        List<Province> provinces = new ArrayList<Province>();
        try {
            InputStream is = mContext.getAssets().open("places.json");
            provinces = JSON.parseArray(ConvertStream2Json(is), Province.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return provinces;
    }

    private static String ConvertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
