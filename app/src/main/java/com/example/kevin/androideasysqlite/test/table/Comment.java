package com.example.kevin.androideasysqlite.test.table;

import com.example.kevin.easysqlite.annotation.BiuniqueColumn;
import com.example.kevin.easysqlite.annotation.ConstraintColumnSql;
import com.example.kevin.easysqlite.annotation.IntSqlColumn;
import com.example.kevin.easysqlite.annotation.StringSqlColumn;
import com.example.kevin.easysqlite.annotation.TableSql;
import com.example.kevin.easysqlite.sqlutils.SupportTable;


/**
 * Created by kevin on 2018/1/24.
 * https://github.com/yinkaiwen
 */

@TableSql("comment")
public class Comment extends SupportTable {

    @ConstraintColumnSql(autoIncrement = true,primaryKey = true)
    @IntSqlColumn
    public static int id;

    @StringSqlColumn
    public static String comment;

    @BiuniqueColumn
    public static News news;

    public static void setComment(String comment) {
        Comment.comment = comment;
    }

    public static void setNews(News news) {
        Comment.news = news;
    }
}
