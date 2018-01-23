package com.example.kevin.androideasysqlite.easySqlite.createtable;

import android.text.TextUtils;
import android.util.Log;

import com.example.kevin.androideasysqlite.easySqlite.annotation.BoobSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.ConstraintColumnSql;
import com.example.kevin.androideasysqlite.easySqlite.annotation.IntSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.NullSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.RealSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.StringSqlColumn;
import com.example.kevin.androideasysqlite.easySqlite.annotation.TableSql;
import com.example.kevin.androideasysqlite.easySqlite.Debug;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

public class CreateTable {
    private static final String tag = CreateTable.class.getSimpleName();
    private static final String UID = "serialVersionUID";

    public String getCreateTableSql(Class<?> cls) {
        StringBuilder sql = new StringBuilder();

        String tableName = getTableName(cls);
        LinkedHashMap<String, String> columns = getColumnNameAndType(cls);

        sql.append(String.format("create table if not exists %s (", tableName));
        int index = 0;
        for (HashMap.Entry entry : columns.entrySet()) {
            String columnName = (String) entry.getKey();
            String constraint = (String) entry.getValue();
            if (index != 0) {
                sql.append(",");
            }
            index++;
            sql.append(String.format("%s %s", columnName, constraint));
        }
        sql.append(")");

        if (Debug.isDebug) {
            Log.i(tag, sql.toString());
        }
        return sql.toString();
    }


    private String getTableName(Class<?> cls) {
        if (cls == null)
            throw new IllegalArgumentException("Class should not be null");
        String tableName;
        TableSql tableSql = cls.getAnnotation(TableSql.class);
        if (tableSql == null) {
            throw new IllegalArgumentException("Class must use @TableSql");
        }
        tableName = tableSql.value();
        return tableName;
    }


    private LinkedHashMap<String, String> getColumnNameAndType(Class<?> cls) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (cls == null)
            throw new IllegalArgumentException("Class should not be null");
        Field[] fields = cls.getFields();
        if (Debug.isDebug) {
            Log.i(tag, String.format("fields.length:%s", fields.length));
        }

        Object obj = null;
        try {
            obj = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (obj == null)
            throw new IllegalArgumentException("Class must use no field construct");

        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (f.isSynthetic())
                continue;
            if (UID.equals(f.getName()))
                continue;

            Annotation[] annotation = f.getAnnotations();
            if (Debug.isDebug) {
                try {
                    Log.i(tag, String.format("annotation.length:%s,fieldName:%s", annotation.length, f.get(obj)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            int length = annotation.length;

            //error
            if (length == 0) {
                throw new IllegalArgumentException("Class's field must use @xxxSqlColumn");
            }
            //Normal Column
            else if (length == 1) {
                Annotation a = annotation[0];
                String columnName = null;
                String constraint = null;

                columnName = getColumnName(f, obj);
                constraint = getConstraint(a);

                map.put(columnName, constraint);
            }
            // more constraint column,one annotation use @xxxSqlColumn,other use @ConstraintColumnSql
            else {
                checkMoreConstrain(annotation[0]);

                ConstraintColumnSql columnSql = (ConstraintColumnSql) annotation[0];
                String columnName = null;
                StringBuilder constraint = null;
                Annotation a = annotation[1];

                columnName = getColumnName(f, obj);
                constraint = new StringBuilder(getConstraint(a));

                if (columnSql.primaryKey()) {
                    constraint.append(" primary key ");
                    if (!(a instanceof IntSqlColumn)) {
                        throw new IllegalArgumentException("primary key only use @IntegerSqlColumn");
                    }
                }
                if (columnSql.autoIncrement()) {
                    constraint.append(" autoincrement");
                }
                if (columnSql.notNull()) {
                    constraint.append(" not null");
                }
                map.put(columnName, constraint.toString());
            }
        }

        return map;
    }

    private String getConstraint(Annotation a) {
        String constraint = null;
        if (a instanceof BoobSqlColumn) {
            constraint = ((BoobSqlColumn) a).value();
        }
        if (a instanceof IntSqlColumn) {
            constraint = ((IntSqlColumn) a).value();
        }
        if (a instanceof NullSqlColumn) {
            constraint = ((NullSqlColumn) a).value();
        }
        if (a instanceof RealSqlColumn) {
            constraint = ((RealSqlColumn) a).value();
        }
        if (a instanceof StringSqlColumn) {
            constraint = ((StringSqlColumn) a).value();
        }

        if (TextUtils.isEmpty(constraint)) {
            throw new IllegalArgumentException("If only one Annotation in field,Should use @xxxSqlColumn");
        }
        return constraint;
    }

    private String getColumnName(Field f, Object obj) {
        String columnName = null;
        try {
            columnName = (String) f.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(columnName)) {
            throw new IllegalArgumentException("Class's field should be defaulted");
        }

        return columnName;
    }

    private void checkMoreConstrain(Annotation a) {
        if (!(a instanceof ConstraintColumnSql)) {
            throw new IllegalArgumentException(
                    "The model is" +
                    "@ConstraintColumnSql(xxxxxx)" +
                    "@xxxSqlColumn" +
                    "");
        }
    }

}
