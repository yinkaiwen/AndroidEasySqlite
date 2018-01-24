package com.example.kevin.androideasysqlite.easySqlite.sqlutils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.core.Debug;
import com.example.kevin.androideasysqlite.easySqlite.core.EasySqlite;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class SaveInfo {

    private static final String tag = SaveInfo.class.getSimpleName();

    public void save() {
        Class<? extends SaveInfo> cls = this.getClass();
        Field[] fields = cls.getDeclaredFields();
        SQLiteDatabase db = EasySqlite.getmDatabase();
        ContentValues values = new ContentValues();
        for (Field f : fields) {
            String key = f.getName();
            Type type = f.getGenericType();
            Object value = null;
            if (f.isSynthetic()) {
                continue;
            }
            if (CreateTable.UID.equals(key)) {
                continue;
            }

            if (f.getAnnotations()[0] instanceof ConstraintColumnSql) {
                ConstraintColumnSql constraintColumnSql = (ConstraintColumnSql) f.getAnnotations()[0];
                boolean b = constraintColumnSql.autoIncrement();
                if (b)
                    continue;
            }

            try {
                value = f.get(this);
                if (Debug.isDebug)
                    Log.i(tag, String.format("type-->%s,value-->%s", type, value));

                if (value instanceof Integer) {
                    values.put(key, (int) value);
                }
                if (value instanceof String) {
                    values.put(key, (String) value);
                }
                if (value instanceof Float) {
                    values.put(key, (Float) value);
                }
                if (value instanceof Double) {
                    values.put(key, (Double) value);
                }
                if (value instanceof Boolean) {
                    values.put(key, (Boolean) value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.i(tag, e.getMessage());
            }
        }
        CreateTable createTable = new CreateTable();
        String tableName = createTable.getTableName(cls);


        long insertIndex = db.insert(tableName, null, values);
        Log.i(tag, String.format("%s save info,result index is %s",
                this.getClass().getSimpleName(), insertIndex));
    }

}
