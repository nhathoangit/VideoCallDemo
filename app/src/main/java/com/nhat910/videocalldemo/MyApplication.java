package com.nhat910.videocalldemo;

import android.app.Application;

import com.nhat910.videocalldemo.db.DbHelper;
import com.nhat910.videocalldemo.others.Constant;
import com.quickblox.auth.session.QBSettings;

import kotlin.jvm.Synchronized;

public class MyApplication extends Application {

    private static MyApplication instance;
    private DbHelper dbHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        initCredentials();
    }

    @Synchronized
    public static MyApplication getApplication(){
        return instance;
    }

    private void initCredentials() {
        instance = this;
        dbHelper = new DbHelper(this);
        QBSettings.getInstance().init(getApplicationContext(), Constant.APPLICATION_ID, Constant.AUTH_KEY, Constant.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(Constant.ACCOUNT_KEY);
    }

    @Synchronized
    public DbHelper getDbHelper() {
        return dbHelper;
    }
}
