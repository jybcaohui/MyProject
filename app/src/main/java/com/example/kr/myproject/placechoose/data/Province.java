package com.example.kr.myproject.placechoose.data;

import java.util.List;

/**
 * User: kison.chen
 * Date: 13-6-25
 * Time: 下午5:14
 */
public class Province {
    private List<CityUnit> city;
    private int key;
    private String province;

    public List<CityUnit> getCity() {
        return city;
    }

    public void setCity(List<CityUnit> city) {
        this.city = city;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "Province{" +
                "city=" + city +
                ", key=" + key +
                ", province='" + province + '\'' +
                '}';
    }
}
