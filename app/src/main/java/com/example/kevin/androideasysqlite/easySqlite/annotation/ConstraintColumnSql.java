package com.example.kevin.androideasysqlite.easySqlite.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kevin on 2018/1/22.
 * https://github.com/yinkaiwen
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstraintColumnSql {

    boolean primaryKey() default false;

    boolean autoIncrement() default false;

    boolean notNull() default false;
}
