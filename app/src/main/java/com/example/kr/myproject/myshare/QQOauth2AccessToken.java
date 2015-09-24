package com.example.kr.myproject.myshare;

/**
 * User: ÆôÉù
 * Time: 13-7-29 ÏÂÎç10:47
 */
public class QQOauth2AccessToken {
    private String openId;
    private String token;
    private long expiresIn;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
