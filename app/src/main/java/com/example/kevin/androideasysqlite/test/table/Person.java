package com.example.kevin.androideasysqlite.test.table;

import com.example.kevin.androideasysqlite.easySqlite.annotation.BoobSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.annotation.IntSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.RealSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.StringSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.TableSql;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

@TableSql("person_info")
public class Person {
    @ConstraintColumnSql(primaryKey = true, autoIncrement = true, notNull = true)
    @IntSqlColumn
    public static final String id = "id";
    @StringSqlColumn
    public static final String age = "age";
    @RealSqlColumn
    public static final String height = "height";
    @BoobSqlColumn
    public static final String gender = "gender";
    @StringSqlColumn
    public static final String name = "name";
}
