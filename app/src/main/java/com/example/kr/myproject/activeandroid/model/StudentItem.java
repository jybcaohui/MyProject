package com.example.kr.myproject.activeandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by hui.cao on 2016/3/2.
 */
@Table(name = "qc_student")
public class StudentItem extends Model {
    @Column(name = "uid" , index = true)
    public int uid;
    @Column(name = "name")
    public String name;
    @Column(name = "age")
    public int age;
    @Column(name = "addtime")
    public long addtime;
}
