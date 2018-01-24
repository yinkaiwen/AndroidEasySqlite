package com.example.kevin.androideasysqlite.easySqlite.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class EasySqlHelper extends SQLiteOpenHelper {
    private static final String tag = EasySqlite.class.getSimpleName();

    private static EasySqlHelper instance;
    private static EasySqlite mEasySqlite;

    private EasySqlHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    public static EasySqlHelper getInstance(String name, Context context, EasySqlite easySqlite) {
        if (instance == null) {
            synchronized (EasySqlHelper.class) {
                if (instance == null) {
                    instance = new EasySqlHelper(context, name);
                    mEasySqlite = easySqlite;
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> createTableSql = mEasySqlite.getCreateTableSql();
        for (String sql : createTableSql) {
            if (Debug.isDebug) {
                Log.i(tag, "create table sql : " + sql);
            }
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
