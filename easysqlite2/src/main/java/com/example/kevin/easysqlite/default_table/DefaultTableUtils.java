package com.example.kevin.easysqlite.default_table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.kevin.easysqlite.config.SqliteConfig;
import com.example.kevin.easysqlite.sqlutils.CreateTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class DefaultTableUtils {
    public static final String default_table_name = "default_table_save_table_name";

    /**
     * Save table info in default database @default_table_save_table_name,such as : tableName and All columns.
     * If there is a new table be created,save info in @default_table_save_table_name.
     * If there is new columns be created,alter new columns in old table and
     * update the info in @default_table_save_table_name.
     *
     * @param mConfig
     * @param mCreateTable
     * @param mDatabase
     */
    public static void saveSomeInfo(SqliteConfig mConfig, CreateTable mCreateTable, SQLiteDatabase mDatabase) {
        Class<?>[] clss = mConfig.getTableClass();

        if (clss == null || clss.length == 0) {
            throw new IllegalArgumentException("Class should not be null or length = 0");
        }
        DefaultSqlTable table = new DefaultSqlTable();

        for (Class c : clss) {
            String tableName = mCreateTable.getTableName(c);

            String selectSql = String.format("select * from %s where tableName = ?",
                    DefaultTableUtils.default_table_name);
            Cursor cursor = mDatabase.rawQuery(selectSql, new String[]{tableName});
            if (cursor.moveToFirst()) {
                //The table has exists,look up columns.

                //The table has updated.
                int tableVersion = cursor.getInt(cursor.getColumnIndex("version"));
                if (tableVersion == mConfig.getVersion())
                    continue;

                //Check the table columns
                String columnsString = cursor.getString(cursor.getColumnIndex("columns"));
                String[] split = columnsString.split(",");
                List<String> columns = Arrays.asList(split);
                LinkedHashMap<String, String> columnNameAndType = mCreateTable.getColumnNameAndType(c);
                StringBuilder sb = new StringBuilder();
                for (HashMap.Entry e : columnNameAndType.entrySet()) {
                    String currentColumn = (String) e.getKey();
                    sb.append(currentColumn + ",");
                    if (!columns.contains(currentColumn)) {
                        //New Column
                        String constraint = (String) e.getValue();
                        String alterSql = String.format("alter table %s add column %s %s", tableName, currentColumn, constraint);
                        mDatabase.execSQL(alterSql);
                    }
                }

                //Update the default_table_save_table_name
                ContentValues values = new ContentValues();
                values.put("version", mConfig.getVersion());
                values.put("columns", sb.toString());
                HashMap<String, String> whereClause = new HashMap<>();
                whereClause.put("tableName", tableName);
                table.update(values, whereClause);
            } else {
                //create new table
                String tableSql = mCreateTable.getCreateTableSql(c);
                mDatabase.execSQL(tableSql);

                LinkedHashMap<String, String> map = mCreateTable.getColumnNameAndType(c);
                StringBuilder sb = new StringBuilder();
                for (HashMap.Entry e : map.entrySet()) {
                    sb.append(e.getKey() + ",");
                }

                //Update the default table @default_table_save_table_name
                table.setVersion(mConfig.getVersion());
                table.setTableName(tableName);
                table.setColumns(sb.toString());
                table.save();
            }
        }
    }
}
