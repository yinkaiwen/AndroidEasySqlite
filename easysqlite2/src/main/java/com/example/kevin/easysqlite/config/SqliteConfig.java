package com.example.kevin.easysqlite.config;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public interface SqliteConfig {
    String getDatabaseName();
    int getVersion();
    Class<?>[] getTableClass();
}
