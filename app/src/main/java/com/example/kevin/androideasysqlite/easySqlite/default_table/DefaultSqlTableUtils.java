package com.example.kevin.androideasysqlite.easySqlite.default_table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kevin.androideasysqlite.easySqlite.config.SqliteConfig;
import com.example.kevin.androideasysqlite.easySqlite.sqlutils.CreateTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class DefaultSqlTableUtils {
    public static final String default_table_name = "default_table_save_table_name";

    public static void saveTableInfo(String tableName, int version, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(DefaultSqlTable.tableName, tableName);
        values.put(DefaultSqlTable.version, version);

        db.insert(default_table_name, null, values);
    }


    /**
     * coding... difficult
     * @param db
     * @param currentVersion
     * @param config
     */
    public static void updateDatabase(SQLiteDatabase db, int currentVersion, SqliteConfig config) {
        List<String> createTableSql = new ArrayList<>();


        String query = String.format("select * from %s", default_table_name);
        Cursor cursor = db.rawQuery(query, null);
        boolean b = cursor.moveToFirst();
        if (!b)
            return;
        int oldVersion = getVersion(cursor);
        if (oldVersion == currentVersion)
            return;

        List<String> oldTableNames = new ArrayList<>();
        oldTableNames.add(getTable(cursor));
        while (cursor.moveToNext()) {
            oldTableNames.add(getTable(cursor));
        }

        Class<?>[] clss = config.getTableClass();
        CreateTable createTable = new CreateTable();
        for (Class c : clss) {
            String currentTableName = createTable.getTableName(c);
            if (!oldTableNames.contains(currentTableName)) {
                createTableSql.add(createTable.getCreateTableSql(c));
            }
        }

//        for (String currentTableName : currentTableNames) {
//            if (!oldTableNames.contains(currentTableName)) {
//                // New table that no save in database.
//
//            }
//        }

    }

    private static int getVersion(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(DefaultSqlTable.version));
    }

    private static String getTable(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(DefaultSqlTable.tableName));
    }

}
