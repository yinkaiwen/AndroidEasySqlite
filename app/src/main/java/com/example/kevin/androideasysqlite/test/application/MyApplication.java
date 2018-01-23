package com.example.kevin.androideasysqlite.test.application;

import android.app.Application;

import com.example.kevin.androideasysqlite.easySqlite.EasySqlite;
import com.example.kevin.androideasysqlite.test.config.MySqliteConfig;
import com.facebook.stetho.Stetho;

/**
 * Created by kevin on 2018/1/23.
 * https://github.com/yinkaiwen
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }
}
