package com.example.kevin.androideasysqlite.easySqlite.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kevin.androideasysqlite.easySqlite.config.SqliteConfig;
import com.example.kevin.androideasysqlite.easySqlite.sqlutils.CreateTable;
import com.example.kevin.androideasysqlite.easySqlite.default_table.DefaultSqlTable;
import com.example.kevin.androideasysqlite.easySqlite.default_table.DefaultSqlTableUtils;

import java.util.ArrayList;
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
    private static CreateTable mCreateTable = new CreateTable();

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
        saveTableInfo();
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
        createTableSqlList.add(mCreateTable.getCreateTableSql(DefaultSqlTable.class));
    }

    public  static void saveTableInfo(){
        Class<?>[] clss = mConfig.getTableClass();
        List<String> tableNameList = new ArrayList<>();

        if (clss == null || clss.length == 0) {
            throw new IllegalArgumentException("Class should not be null or length = 0");
        }

        for (Class c : clss) {
            tableNameList.add(mCreateTable.getTableName(c));
        }

        for (String tableName : tableNameList){
//            DefaultSqlTableUtils.saveTableInfo(tableName,mConfig.getVersion(),mDatabase);
        }
    }

    public static SQLiteDatabase getmDatabase() {
        if(mDatabase == null)
            throw new RuntimeException("You should use init() before use this method");
        return mDatabase;
    }
}
