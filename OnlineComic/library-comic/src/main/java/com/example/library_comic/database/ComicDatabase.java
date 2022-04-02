package com.example.library_comic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_comic.bean.DaoMaster;
import com.example.library_comic.bean.DaoSession;

public class ComicDatabase {
    private static DaoSession mDaoSession;
    private volatile static ComicDatabase mDatabase;

    public static ComicDatabase getInstance() {
        if (mDatabase == null) {
            synchronized (ComicDatabase.class) {
                if (mDatabase == null) {
                    mDatabase = new ComicDatabase();
                }
            }
        }
        return mDatabase;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "COMICDB");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
