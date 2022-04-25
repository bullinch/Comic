package com.example.onlinecomic.model;

import com.example.library_base.base.BaseModel;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.ComicDao;
import com.example.library_comic.database.ComicDatabase;
import com.example.onlinecomic.callback.IExecutor;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class HistoryModel extends BaseModel {
    private final ComicDao mComicDao;

    public HistoryModel() {
        mComicDao = ComicDatabase.getInstance().getDaoSession().getComicDao();
    }

    public void history(Comic comic) {
        Logger.i(comic.toString());
        comic.historyStamp = System.currentTimeMillis();
        mComicDao.insertOrReplace(comic);
    }

    public void deleteHistory(Comic comic) {
        Logger.i(comic.toString());
        comic.historyStamp = null;
        if (comic.favoriteStamp == null && comic.downloadStamp == null) {
            mComicDao.delete(comic);
        } else {
            mComicDao.update(comic);
        }
    }

    public void deleteAll() {
        mComicDao.queryBuilder().where(ComicDao.Properties.FavoriteStamp.isNotNull())
                .orderDesc(ComicDao.Properties.FavoriteStamp).rx()
                .list().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Comic>>() {
                    @Override
                    public void call(List<Comic> comics) {
                        for(Comic comic: comics) {
                            deleteHistory(comic);
                        }
                    }
                });
    }

    public void loadAll(IExecutor<List<Comic>> executor) {
        mComicDao.queryBuilder().where(ComicDao.Properties.HistoryStamp.isNotNull())
                .orderDesc(ComicDao.Properties.HistoryStamp).rx()
                .list().observeOn(AndroidSchedulers.mainThread())
                .subscribe(executor::success);
    }
}
