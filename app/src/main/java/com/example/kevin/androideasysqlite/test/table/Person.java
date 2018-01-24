package com.example.kevin.androideasysqlite.test.table;

import com.example.kevin.androideasysqlite.easySqlite.annotation.BoobSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.annotation.IntSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.RealSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.StringSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.TableSql;
import com.example.kevin.androideasysqlite.easySqlite.sqlutils.SaveInfo;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

@TableSql("person_info")
public class Person extends SaveInfo {
    @ConstraintColumnSql(primaryKey = true, autoIncrement = true, notNull = true)
    @IntSqlColumn
    public static int id;
    @StringSqlColumn
    public static int age;
    @RealSqlColumn
    public static float height;
    @BoobSqlColumn
    public static boolean gender;
    @StringSqlColumn
    public static String name;

    public static void setAge(int age) {
        Person.age = age;
    }

    public static void setHeight(float height) {
        Person.height = height;
    }

    public static void setGender(boolean gender) {
        Person.gender = gender;
    }

    public static void setName(String name) {
        Person.name = name;
    }
}
