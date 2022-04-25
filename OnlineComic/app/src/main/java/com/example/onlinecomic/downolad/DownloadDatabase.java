package com.example.onlinecomic.downolad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_base.util.log.Logger;
import com.example.onlinecomic.downolad.bean.DaoMaster;
import com.example.onlinecomic.downolad.bean.Worker;
import com.example.onlinecomic.downolad.bean.WorkerDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DownloadDatabase {
    private volatile static DownloadDatabase instance;
    private WorkerDao mDownloadTaskDao;

    public static DownloadDatabase getInstance() {
        if(instance == null) {
            synchronized (DownloadDatabase.class) {
                if(instance == null) {
                    instance = new DownloadDatabase();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "DownloadDB");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDownloadTaskDao = daoMaster.newSession().getWorkerDao();
    }

    public void insert(Worker worker) {
        mDownloadTaskDao.insertOrReplace(worker);
    }

    public void delete(Worker worker) {
        mDownloadTaskDao.delete(worker);
    }

    public List<Worker> query(String comicUrl) {
        List<Worker> list = mDownloadTaskDao.queryBuilder().list();
        Logger.i(list.size()+"");
        List<Worker> newList = new ArrayList<>();
        for(Worker task : list) {
            if(task.comic.comicUrl.equals(comicUrl)) {
                newList.add(task);
            }
        }
        Collections.sort(newList, new Comparator<Worker>() {
            @Override
            public int compare(Worker t1, Worker t2) {
                return t1.chapter.name.compareTo(t2.chapter.name);
            }
        });
        return newList;
    }

}
