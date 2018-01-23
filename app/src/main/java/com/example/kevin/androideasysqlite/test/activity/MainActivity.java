package com.example.kevin.androideasysqlite.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kevin.androideasysqlite.R;
import com.example.kevin.androideasysqlite.easySqlite.core.EasySqlite;
import com.example.kevin.androideasysqlite.test.config.MySqliteConfig;

public class MainActivity extends AppCompatActivity {

    private static final String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasySqlite init = EasySqlite.init(this, new MySqliteConfig());

    }
}
