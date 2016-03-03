package com.example.kr.myproject.activeandroid;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kr.myproject.R;
import com.example.kr.myproject.activeandroid.dao.StudentDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 *  app gradle中配置：   compile 'com.michaelpardo:activeandroid:3.1.0-SNAPSHOT'
 *  project gradle中配置：   maven { url "http://dl.bintray.com/populov/maven" }
 *                          maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
 * 使用流程：http://www.stormzhang.com/openandroid/android/sqlite/2013/12/20/android-orm-tools-activeandroid/
 * 1、manifest中声明数据库名、版本
 *      <meta-data android:name="AA_DB_NAME" android:value="myproject.db" />
 *      <meta-data android:name="AA_DB_VERSION" android:value="1" />
 * 2、Application继承自： com.activeandroid.app.Application;
 *      onCreate中： ActiveAndroid.initialize(this);  初始化
 *      onTerminate中： ActiveAndroid.dispose();   清理工作
 * 3、映射表（StudentItem）
 * 4、数据库操作工具类（StudentDao）
 */

public class ActiveAndroidActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.add_age)
    EditText addage;
    @InjectView(R.id.add_name)
    EditText addname;
    @InjectView(R.id.add)
    Button add;
    @InjectView(R.id.delete_id)
    EditText deleteid;
    @InjectView(R.id.delete)
    Button delete;
    @InjectView(R.id.change_id)
    EditText changeid;
    @InjectView(R.id.change_name)
    EditText changename;
    @InjectView(R.id.change)
    Button change;
    @InjectView(R.id.select_name)
    EditText selectname;
    @InjectView(R.id.select)
    Button select;
    @InjectView(R.id.select_res)
    TextView selectres;
    @InjectView(R.id.select_all)
    Button selectall;
    @InjectView(R.id.all)
    TextView all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_android);
        ButterKnife.inject(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        change.setOnClickListener(this);
        select.setOnClickListener(this);
        selectall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                String newname = addname.getText().toString();
                int newage = Integer.parseInt(addage.getText().toString());
                StudentDao.insert(newname,newage);
                break;
            case R.id.delete:
                int deleid = Integer.parseInt(deleteid.getText().toString());
                StudentDao.deleteStudent(deleid);
                break;
            case R.id.change:
                int chaid = Integer.parseInt(changeid.getText().toString());
                String chaname = changename.getText().toString();
                StudentDao.updateStudent(chaid,chaname);
                break;
            case R.id.select:
                String selname = selectname.getText().toString();
                List<StudentVo> list = StudentDao.getStudent(selname);
                if(list != null && list.size()>0 ){
                    selectres.setText(list.get(0).getAge()+"--");
                }else{
                    Log.d("list----",list+"---"+list.size());
                }
                break;
            case R.id.select_all:
                String res = "";
                List<StudentVo> lists = StudentDao.getAllStudent();
                if(lists != null && lists.size()>0  ){
                    for(StudentVo vo:StudentDao.getAllStudent()){
                        Log.d("lists----",vo.getId()+"-"+vo.getName()+"-"+vo.getAge()+"-"+vo.getAddtime());
                        res = res+vo.getId()+"--"+vo.getName()+"--"+vo.getAge()+"--"+vo.getAddtime()+"\n";
                        all.setText(res);
                    }
                }else{
                    Log.d("lists----",lists+"=="+lists.size());
                }
                break;
             default:
                 break;
        }
    }
}
