package com.example.onlinecomic.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.bean.HistoryComic;
import com.example.onlinecomic.callback.IExecutor;
import com.example.onlinecomic.model.BookShelfModel;
import com.example.onlinecomic.model.FavoriteModel;
import com.example.onlinecomic.model.HistoryModel;

import java.util.List;

public class BookShelfViewModel extends BaseViewModel<BookShelfModel> {

    public MutableLiveData<List<Comic>> favComicsMLD = new MutableLiveData<>();
    public MutableLiveData<List<HistoryComic>> historyComicsMLD = new MutableLiveData<>();
    public MutableLiveData<List<Comic>> downloadComicsMLD = new MutableLiveData<>();
    public MutableLiveData<Boolean> favMLD = new MutableLiveData<>();

    public MutableLiveData<Comic> mCurrentReadComic = new MutableLiveData<>();
    public MutableLiveData<Chapter> mCurrentReadChapter = new MutableLiveData<>();

    private FavoriteModel mFavoriteModel;
    private HistoryModel mHistoryModel;

    public BookShelfViewModel() {
        model = new BookShelfModel();
        mHistoryModel = new HistoryModel();
        mFavoriteModel = new FavoriteModel();
    }

    @Override
    public void destroy() {
        model.cancel();
    }

    public void favorite(Comic comic) {
        mFavoriteModel.favorite(comic);
        refreshFavoriteComics();
    }

    public void deleteFavorite(Comic comic) {
        mFavoriteModel.deleteFavorite(comic);
        refreshFavoriteComics();
    }

    public void history(Comic comic) {
        mHistoryModel.history(comic);
        refreshHistoryComics();
    }

    public void deleteHistory(Comic comic) {
        mHistoryModel.deleteHistory(comic);
        refreshHistoryComics();
    }

    public void download(Comic comic, boolean isDownload) {
        if(isDownload) {
            // 下载
            model.download(comic, null);
        } else {
            model.deleteDownload(comic);
        }
    }

    @SuppressLint("CheckResult")
    public void refreshFavoriteComics() {
        Logger.i("refreshFavComics");
        mFavoriteModel.loadAll(new IExecutor<List<Comic>>() {
            @Override
            public void success(List<Comic> comics) {
                favComicsMLD.setValue(model.refreshFavoriteComics());
            }
        });
    }

    @SuppressLint("CheckResult")
    public void refreshHistoryComics() {
        Logger.i("refreshHistoryComics");
        historyComicsMLD.setValue(model.refreshHistoryComics());
    }

    @SuppressLint("CheckResult")
    public void refreshDownloadComics() {
        Logger.i("refreshHistoryComics");
        downloadComicsMLD.setValue(model.refreshDownloadComics());
    }
}
