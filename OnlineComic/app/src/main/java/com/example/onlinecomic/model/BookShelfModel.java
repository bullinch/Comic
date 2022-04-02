package com.example.onlinecomic.model;

import android.annotation.SuppressLint;
import android.telecom.Call;

import com.example.library_base.base.BaseModel;
import com.example.library_base.base.Callback;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.ComicDao;
import com.example.library_comic.database.ComicDatabase;
import com.example.onlinecomic.bean.HistoryComic;
import com.example.onlinecomic.callback.IExecutor;
import com.example.onlinecomic.util.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BookShelfModel extends BaseModel {

    private final ComicDao mComicDao;

    public BookShelfModel() {
        mComicDao = ComicDatabase.getInstance().getDaoSession().getComicDao();
    }

    public void favorite(Comic comic, IExecutor executor) {
        Logger.i("comic-->" + comic.toString());
        mComicDao.queryBuilder().where
                (ComicDao.Properties.ComicUrl.eq(comic.getComicUrl())).
                where(ComicDao.Properties.Title.eq(comic.getTitle())).
                where(ComicDao.Properties.FavoriteStamp.eq(comic.getFavoriteStamp()))
                .rx().list().observeOn(AndroidSchedulers.mainThread()).
                subscribe(comics -> {
                    if (comics.isEmpty())
                        mComicDao.insert(comic);
                    else {
                        mComicDao.delete(comics.get(0));
                    }
                    executor.success();
                });
    }

    public void history(Comic comic, IExecutor executor) {
        Logger.i("history-->" + comic.toString());
        mComicDao.rx().insertOrReplace(comic).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Comic>() {
                    @Override
                    public void call(Comic comic) {
                        executor.success();
                    }
                });
    }

    public void deleteHistory(Comic comic) {
        Logger.i("deleteHistory-->" + comic.toString());
        mComicDao.delete(comic);
    }

    @SuppressLint("CheckResult")
    public List<Comic> refreshFavoriteComics() {
        return mComicDao.queryBuilder().where
                (ComicDao.Properties.FavoriteStamp.isNotNull()).list();
    }

    @SuppressLint("CheckResult")
    public List<HistoryComic> refreshHistoryComics() {
        List<HistoryComic> historyComicList = new ArrayList<>();
        List<Comic> list = mComicDao.queryBuilder().where
                (ComicDao.Properties.HistoryStamp.isNotNull()).
                orderDesc(ComicDao.Properties.HistoryStamp).list();
        Map<String, List<Comic>> map = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String key = TimeUtils.millis2String2(list.get(i).getHistoryStamp());
            List<Comic> everyList = map.get(key);
            if (everyList == null) {
                everyList = new ArrayList<>();
            }
            everyList.add(list.get(i));
            map.put(key, everyList);
        }
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            List<Comic> mapList = (List<Comic>) entry.getValue();
            historyComicList.add(new HistoryComic(true, key));
            for (Comic comic : mapList) {
                historyComicList.add(new HistoryComic(false, comic));
            }
        }
        return historyComicList;
    }

}
