package com.example.kr.myproject.placechoose.data;

/**
 * User: kison.chen
 * Date: 13-6-25
 * Time: 下午5:14
 */
public class CityUnit {
    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CityUnit{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
