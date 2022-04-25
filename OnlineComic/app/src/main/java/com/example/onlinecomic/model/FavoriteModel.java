package com.example.onlinecomic.model;

import com.example.library_base.base.BaseModel;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.ComicDao;
import com.example.library_comic.database.ComicDatabase;
import com.example.onlinecomic.callback.IExecutor;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

public class FavoriteModel extends BaseModel {
    private final ComicDao mComicDao;

    public FavoriteModel() {
        mComicDao = ComicDatabase.getInstance().getDaoSession().getComicDao();
    }


    public boolean isFavorite(Comic comic) {
        return !mComicDao.queryBuilder()
                .where(ComicDao.Properties.FavoriteStamp.isNotNull())
                .where(ComicDao.Properties.ComicUrl.eq(comic.getComicUrl())).
                        list().isEmpty();
    }

    public void favorite(Comic comic) {
        Logger.i(comic.toString());
        comic.favoriteStamp = System.currentTimeMillis();
        mComicDao.insertOrReplace(comic);
    }

    public void deleteFavorite(Comic comic) {
        Logger.i(comic.toString());
        comic.favoriteStamp = null;
        if (comic.historyStamp == null && comic.downloadStamp == null) {
            mComicDao.delete(comic);
        } else {
            mComicDao.update(comic);
        }
    }

    public void loadAll(IExecutor<List<Comic>> executor) {
        mComicDao.queryBuilder().where(ComicDao.Properties.FavoriteStamp.isNotNull())
                .orderDesc(ComicDao.Properties.FavoriteStamp).rx()
                .list().observeOn(AndroidSchedulers.mainThread())
                .subscribe(executor::success);
    }
}
