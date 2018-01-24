package com.example.kevin.androideasysqlite.easySqlite.default_table;

import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.annotation.IntSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.StringSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.TableSql;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 *
 * This table save other table's name and version.
 */
@TableSql(DefaultSqlTableUtils.default_table_name)
public class DefaultSqlTable {

    @ConstraintColumnSql(autoIncrement = true, primaryKey = true)
    @IntSqlColumn
    public static final String id = "id";

    @IntSqlColumn
    public static final String version = "database_version";

    @StringSqlColumn
    public static final String tableName = "table_name";


}
