package com.example.kr.myproject.activeandroid.dao;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.example.kr.myproject.activeandroid.StudentVo;
import com.example.kr.myproject.activeandroid.model.StudentItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hui.cao on 2016/3/2.
 */
public class StudentDao {
    private static int PAGESIZE = 5;
    /**
     * 取得最大ID
    */
    public static int selectMaxId() {
        List<StudentItem> students = new Select()
                .from(StudentItem.class)
                .orderBy("uid DESC LIMIT 1")
                .execute();
        if (students != null && students.size() > 0) {
            return students.get(0).uid;
        }
        return 0;
    }
    /**
     *增加元素
     */
    public static void insert(String name,int age){
        Date date = new Date();

        StudentItem student = new StudentItem();
        student.uid = selectMaxId()+1;
        student.name = name;
        student.age = age;
        student.addtime = date.getTime();
        student.save();
    }

    /**
     *插入多条数据
     */
    public static void insertStudentVos(List<StudentVo> stuVoList) {
        ActiveAndroid.beginTransaction();
        try {
            for (StudentVo studentVo : stuVoList) {
                StudentItem item = fromStudentVo(studentVo);
                item.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
    /**
     *删除元素
     */
    public static void deleteStudent(int uid) {
        new Delete().from(StudentItem.class).where("uid = ? ",uid).execute();
    }
    /**
     *更改单条数据
     */
    public static void updateStudent(int uid, String name){
        new Update(StudentItem.class)
                .set("name =?", name)
                .where("uid = ? ", uid).execute();
    }
    /**
     *一条数据转换成一个实体
     */
    private static StudentVo fromStudentItem(StudentItem stu) {
        StudentVo student = new StudentVo();
        student.id = stu.uid;
        student.name = stu.name;
        student.age = stu.age;
        student.addtime = stu.addtime;
        return student;
    }
    /**
     *一个实体转换成一条数据
     */
    private static StudentItem fromStudentVo(StudentVo studentVo) {
        StudentItem studentItem = new StudentItem();
        studentItem.uid = studentVo.getId();
        studentItem.name = studentVo.getName();
        studentItem.age = studentVo.getAge();
        studentItem.addtime = studentVo.getAddtime();
        return studentItem;
    }
    /**
     * 查询多条数据
    * */
    public static List<StudentVo> getStudent(String name) {
        List<StudentItem> students = new Select()
                .from(StudentItem.class)
                .where("name = ? ", name)
                .orderBy("uid DESC LIMIT " + PAGESIZE)
                .execute();
        List<StudentVo> stus = new ArrayList<StudentVo>();
        for (int i = 0; i < students.size(); i++) {
            stus.add(fromStudentItem(students.get(students.size() - 1 - i)));
        }
        return stus;
    }

    /**
     * 查询多条数据
     * */
    public static List<StudentVo> getAllStudent() {
        List<StudentItem> students = new Select()
                .from(StudentItem.class)
                .orderBy("uid DESC LIMIT " + 100)
                .execute();
        List<StudentVo> stus = new ArrayList<StudentVo>();
        for (int i = 0; i < students.size(); i++) {
            stus.add(fromStudentItem(students.get(students.size() - 1 - i)));
        }
        return stus;
    }

    /**
     * 判断 是否存在
     * @param uid
     * @return
     */
    public static boolean checkExist(int uid) {
        List<StudentItem> studentItems = new Select()
                .from(StudentItem.class)
                .where("uid = ? ", uid)
                .execute();
        return studentItems.size() > 0;
    }

    /**
     *更新一条数据
     * @param name
     * @param uid
     */
    public static void resetCount(String name, int age ,int uid) {
        new Update(StudentItem.class)
                .set("name =?,age=?"
                        , name, age)
                .where("uid = ? ", uid).execute();
    }
    /**
     * 查询多条数据
     */
    public static List<StudentItem> getAll(int age) {
        return new Select()
                .from(StudentItem.class)
                .where("age =? ", age)
                .execute();
    }
    /**
     * 查询一条数据
     */
    public static StudentItem getItem(int uid, int age) {
        return new Select()
                .from(StudentItem.class)
                .where("uid =? and age = ?", uid, age)
                .executeSingle();
    }
    /**
     * 删除一条数据
     */
    public static void deleteItem(int uid, int age) {
        new Delete()
                .from(StudentItem.class)
                .where("uid =?  and age = ? ", uid, age)
                .execute();
    }
    /**
     * 更新多一条数据
     */
    public static void updateItem(int uid, String name) {
        try {
            StudentItem item = new Select()
                    .from(StudentItem.class)
                    .where("uid = ? ", uid)
                    .executeSingle();
            item.name = name;

            item.save();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新多条数据
     */
    public static void updateList(int uid, int age, String name) {
        List<StudentItem> items = new Select()
                .from(StudentItem.class)
                .where("uid = ? ", uid)
                .execute();
        for (StudentItem item : items) {
            item.age = age;
            item.name = name;
            item.save();
        }
    }

}
