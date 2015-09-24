package com.example.kr.myproject.myshare;

import android.content.Context;
import android.content.SharedPreferences;


public class QQAccessTokenKeeper {
    private static final String PREFERENCES_NAME = "com_tencent_sdk_android";


    /**
     * 保存accesstoken到SharedPreferences
     *
     * @param context Activity 上下文环境
     * @param token   Oauth2AccessToken
     */
    public static void keepAccessToken(Context context, String token, String openId, String expiresIn) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.putLong("expiresTime", System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000);
        editor.putString("openId", openId);
        editor.commit();
    }

    public static void keepAccessToken(Context context, String token, String openId, int expiresIn) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.putLong("expiresTime", 1000l*expiresIn);
        editor.putString("openId", openId);
        editor.commit();
    }

    public static void bindUsername(Context context, String username) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public static String getUsername(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return pref.getString("username", "");
    }

    /**
     * 清空sharepreference
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 从SharedPreferences读取accessstoken
     *
     * @param context
     * @return Oauth2AccessToken
     */
    public static QQOauth2AccessToken readAccessToken(Context context) {
        QQOauth2AccessToken token = new QQOauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setToken(pref.getString("token", ""));
        token.setExpiresIn(pref.getLong("expiresTime", 0));
        token.setOpenId(pref.getString("openId", ""));
        return token;
    }
}
