package com.example.kevin.androideasysqlite.test.table;


import com.example.kevin.easysqlite.annotation.BiuniqueColumn;
import com.example.kevin.easysqlite.annotation.ConstraintColumnSql;
import com.example.kevin.easysqlite.annotation.IntSqlColumn;
import com.example.kevin.easysqlite.annotation.StringSqlColumn;
import com.example.kevin.easysqlite.annotation.TableSql;
import com.example.kevin.easysqlite.sqlutils.SupportTable;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

@TableSql("news")
public class News extends SupportTable {


    @ConstraintColumnSql(autoIncrement = true, primaryKey = true)
    @IntSqlColumn
    public static String id;

    @StringSqlColumn
    public static String content;

    @StringSqlColumn
    public static String title;

    @IntSqlColumn
    public static String date;

    @StringSqlColumn
    public static String comment;

    @BiuniqueColumn
    public static Introduction introduction;

    public static void setContent(String content) {
        News.content = content;
    }

    public static void setTitle(String title) {
        News.title = title;
    }

    public static void setDate(String date) {
        News.date = date;
    }

    public static void setComment(String comment) {
        News.comment = comment;
    }

    public static void setIntroduction(Introduction i) {
        introduction = i;
    }
}
