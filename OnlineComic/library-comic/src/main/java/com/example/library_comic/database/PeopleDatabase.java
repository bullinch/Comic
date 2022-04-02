package com.example.library_comic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.DaoMaster;
import com.example.library_comic.bean.DaoSession;
import com.example.library_comic.bean.People;
import com.example.library_comic.bean.PeopleDao;


import java.util.List;

import io.reactivex.Observable;

public class PeopleDatabase {
    private static DaoSession mDaoSession;
    private volatile static PeopleDatabase mDatabase;

    public static PeopleDatabase getInstance() {
        if (mDatabase == null) {
            synchronized (PeopleDatabase.class) {
                if (mDatabase == null) {
                    mDatabase = new PeopleDatabase();
                }
            }
        }
        return mDatabase;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "PeopleDB");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
