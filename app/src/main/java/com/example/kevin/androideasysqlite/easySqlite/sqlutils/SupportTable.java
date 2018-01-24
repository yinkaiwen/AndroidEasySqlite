package com.example.kevin.androideasysqlite.easySqlite.sqlutils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.core.Debug;
import com.example.kevin.androideasysqlite.easySqlite.core.EasySqlite;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class SupportTable {

    private static final String tag = SupportTable.class.getSimpleName();

    /**
     * insert info into database
     */
    public void save() {
        Class<? extends SupportTable> cls = this.getClass();
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


    /**
     * update info
     *
     * @param values
     * @param whereClause
     */
    public void update(ContentValues values, HashMap<String, String> whereClause) {
        ArrayList params = prepareUpdateOrDelete(whereClause);
        SQLiteDatabase db = (SQLiteDatabase) params.get(0);
        String tableName = (String) params.get(1);
        StringBuilder sb = (StringBuilder) params.get(2);
        String[] args = (String[]) params.get(3);

        if (Debug.isDebug)
            Log.i(tag, String.format("whereClause-->%s,args-->%s", sb.toString(), args.toString()));

        int updateIndex = db.update(tableName, values, sb.toString(), args);
        Log.i(tag, String.format("updateIndex-->%s", updateIndex));
    }

    /**
     * Delete info
     * @param whereClause
     */
    public void delete(HashMap<String, String> whereClause) {
        ArrayList params = prepareUpdateOrDelete(whereClause);
        SQLiteDatabase db = (SQLiteDatabase) params.get(0);
        String tableName = (String) params.get(1);
        StringBuilder sb = (StringBuilder) params.get(2);
        String[] args = (String[]) params.get(3);

        if(Debug.isDebug)
            Log.i(tag,String.format("deleteSql-->%s,args-->%s",sb.toString(),args));

        int deleteIndex = db.delete(tableName, sb.toString(), args);
        Log.i(tag,String.format("deleteIndex-->%s",deleteIndex));
    }

    private ArrayList prepareUpdateOrDelete(HashMap<String, String> whereClause){
        ArrayList<Object> params = new ArrayList<>();

        SQLiteDatabase db = EasySqlite.getmDatabase();
        String tableName = getTableName();

        int size = whereClause.size();
        String[] args = new String[size];
        StringBuilder sb = new StringBuilder();
        initWhereClauses(whereClause, args, sb);

        params.add(db);
        params.add(tableName);
        params.add(sb);
        params.add(args);

        return params;
    }


    private void initWhereClauses(HashMap<String, String> whereClause, String[] args, StringBuilder sb) {
        int i = 0;
        for (HashMap.Entry e : whereClause.entrySet()) {
            String clause = (String) e.getKey();
            String arg = (String) e.getValue();
            args[i] = arg;
            sb.append(String.format("%s = ?", clause));
            if (i < args.length - 1) {
                sb.append(" and ");
            }
            i++;
        }
    }


    private String getTableName() {
        Class<? extends SupportTable> cls = this.getClass();
        CreateTable createTable = new CreateTable();
        return createTable.getTableName(cls);
    }

}
























