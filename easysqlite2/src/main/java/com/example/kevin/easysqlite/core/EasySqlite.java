package com.example.kevin.easysqlite.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.kevin.easysqlite.config.SqliteConfig;
import com.example.kevin.easysqlite.default_table.DefaultSqlTable;
import com.example.kevin.easysqlite.default_table.DefaultTableUtils;
import com.example.kevin.easysqlite.sqlutils.CreateTable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 * <p>
 * You can use init() method in Application,then the database is created and you can use this object in anytime.
 */

public class EasySqlite {

    private static SqliteConfig mConfig = null;
    private static EasySqlHelper mEasySqlHelper;
    private static EasySqlite instance;
    private static SQLiteDatabase mDatabase;
    private static CreateTable mCreateTable = new CreateTable();

    private static List<String> createTableSqlList = new LinkedList<>();


    private EasySqlite() {
    }

    /**
     * First of all,you should use this method and initialize SQLiteOpenHelper.
     *
     * @param context
     * @param config
     * @return
     */
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
        saveTableInfo();
        return instance;
    }


    public List<String> getCreateTableSql() {
        createAllTable();
        return createTableSqlList;
    }

    private void createAllTable() {
        createTable(mConfig.getTableClass());
    }

    private static void createTable(Class<?>[] clss) {
        if (clss == null || clss.length == 0) {
            throw new IllegalArgumentException("Class should not be null or length = 0");
        }

        for (Class c : clss) {
            createTableSqlList.add(mCreateTable.getCreateTableSql(c));
        }
        createTableSqlList.add(mCreateTable.getCreateTableSql(DefaultSqlTable.class));
    }

    public static void saveTableInfo() {
        DefaultTableUtils.saveSomeInfo(mConfig, mCreateTable, mDatabase);
    }

    public static SQLiteDatabase getmDatabase() {
        if (mDatabase == null)
            throw new RuntimeException("You should use init() before use this method");
        return mDatabase;
    }
}
