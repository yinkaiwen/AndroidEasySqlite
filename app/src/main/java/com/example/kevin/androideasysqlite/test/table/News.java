package com.example.kevin.androideasysqlite.test.table;

import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.annotation.IntSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.StringSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.TableSql;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

@TableSql("news")
public class News {


    @ConstraintColumnSql(autoIncrement = true, primaryKey = true)
    @IntSqlColumn
    public static final String id = "id";

    @StringSqlColumn
    public static final String content = "content";

    @StringSqlColumn
    public static final String title = "title";

    @IntSqlColumn
    public static final String date = "date";
}
