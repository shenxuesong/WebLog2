package com.shenxuesong.weblog.Gilde;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.usher.greendao_demo.greendao.gen.DaoMaster;
import com.usher.greendao_demo.greendao.gen.DaoSession;


public class MyAPP extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyAPP instances;
        @Override  
        public void onCreate() {  
            super.onCreate();  
            Fresco.initialize(this);
           AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
            instances = this;
            setDatabase();
            Config.DEBUG = true;
           UMShareAPI.get(this);
            //umeng推送
            PushAgent mPushAgent = PushAgent.getInstance(this);
//开启推送并设置注册的回调处理
            mPushAgent.register(new IUmengRegisterCallback() {
                @Override
                public void onSuccess(String s) {
                    Log.d("device_token", s);
                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        }


    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "e62ec51733740462c3c5fb90ffce5524");
        PlatformConfig.setQQZone("1106423171", "K3NEXOgW84ZeFH3M");
        PlatformConfig.setSinaWeibo("", "", "");

    }
    public static MyAPP getInstances(){
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


    }  