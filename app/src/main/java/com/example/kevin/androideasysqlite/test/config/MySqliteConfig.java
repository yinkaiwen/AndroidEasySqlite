package com.example.kevin.androideasysqlite.test.config;

import com.example.kevin.androideasysqlite.easySqlite.config.SqliteConfig;
import com.example.kevin.androideasysqlite.test.table.News;
import com.example.kevin.androideasysqlite.test.table.Person;
import com.example.kevin.androideasysqlite.test.table.TestTable;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class MySqliteConfig implements SqliteConfig {
    public static final String databaseName = "test";
    public static final int version = 1;

    public static final Class<?>[] clss = {
            Person.class,
            TestTable.class,
//            News.class
    };

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Class<?>[] getTableClass() {
        return clss;
    }
}
