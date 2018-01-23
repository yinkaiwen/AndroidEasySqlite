package com.example.kevin.androideasysqlite.easySqlite.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kevin.androideasysqlite.easySqlite.config.SqliteConfig;
import com.example.kevin.androideasysqlite.easySqlite.createtable.CreateTable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

public class EasySqlite {

    private static SqliteConfig mConfig = null;
    private static EasySqlHelper mEasySqlHelper;
    private static EasySqlite instance;
    private static SQLiteDatabase mDatabase;

    private CreateTable mCreateTable = new CreateTable();
    private List<String> createTableSqlList = new LinkedList<>();

    private EasySqlite() {
    }

    public static EasySqlite init(Context context, SqliteConfig config) {
        if (instance == null) {
            synchronized (EasySqlite.class) {
                if (instance == null) {
                    instance = new EasySqlite();
                    mConfig = config;
                }
            }
        }
        mEasySqlHelper = EasySqlHelper.getInstance(config.getDatabaseName(), context, instance);
        mDatabase = mEasySqlHelper.getWritableDatabase();
        return instance;
    }


    public List<String> getCreateTableSql() {
        createTable();
        return createTableSqlList;
    }


    private void createTable() {
        Class<?>[] clss = mConfig.getTableClass();

        if (clss == null || clss.length == 0) {
            throw new IllegalArgumentException("Class should not be null or length = 0");
        }

        for (Class c : clss) {
            createTableSqlList.add(mCreateTable.getCreateTableSql(c));
        }
    }
}
