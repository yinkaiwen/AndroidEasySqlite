package com.example.kevin.androideasysqlite.test.table;


import com.example.kevin.easysqlite.annotation.BoobSqlColumn;
import com.example.kevin.easysqlite.annotation.ConstraintColumnSql;
import com.example.kevin.easysqlite.annotation.IntSqlColumn;
import com.example.kevin.easysqlite.annotation.RealSqlColumn;
import com.example.kevin.easysqlite.annotation.StringSqlColumn;
import com.example.kevin.easysqlite.annotation.TableSql;
import com.example.kevin.easysqlite.sqlutils.SupportTable;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

@TableSql("person_info")
public class Person extends SupportTable {
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
    @StringSqlColumn
    public static String familyName;

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

    public static void setFamilyName(String familyName) {
        Person.familyName = familyName;
    }
}
