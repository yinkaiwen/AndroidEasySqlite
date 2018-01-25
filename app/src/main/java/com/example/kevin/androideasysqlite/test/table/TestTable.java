package com.example.kevin.androideasysqlite.test.table;


import com.example.kevin.easysqlite.annotation.ConstraintColumnSql;
import com.example.kevin.easysqlite.annotation.IntSqlColumn;
import com.example.kevin.easysqlite.annotation.StringSqlColumn;
import com.example.kevin.easysqlite.annotation.TableSql;
import com.example.kevin.easysqlite.sqlutils.SupportTable;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

@TableSql("donwload")
public class TestTable extends SupportTable {
    @ConstraintColumnSql(autoIncrement = true, primaryKey = true)
    @IntSqlColumn
    public static String id;

    @StringSqlColumn
    public static String first;

    public static void setFirst(String first) {
        TestTable.first = first;
    }
}
