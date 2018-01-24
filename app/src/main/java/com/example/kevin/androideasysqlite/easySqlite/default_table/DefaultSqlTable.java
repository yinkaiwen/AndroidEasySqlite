package com.example.kevin.androideasysqlite.easySqlite.default_table;

import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.annotation.IntSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.StringSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.TableSql;
import com.example.kevin.androideasysqlite.easySqlite.sqlutils.SupportTable;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 * <p>
 * This table save other table's name and version.
 */
@TableSql(DefaultTableUtils.default_table_name)
public class DefaultSqlTable extends SupportTable {

    @ConstraintColumnSql(autoIncrement = true, primaryKey = true)
    @IntSqlColumn
    public static String id;

    @IntSqlColumn
    public static int version;

    @StringSqlColumn
    public static String tableName;

    @StringSqlColumn
    public static String columns;

    public static void setVersion(int version) {
        DefaultSqlTable.version = version;
    }

    public static void setTableName(String tableName) {
        DefaultSqlTable.tableName = tableName;
    }

    public static void setColumns(String columns) {
        DefaultSqlTable.columns = columns;
    }
}
