package com.example.onlinecomic.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.bean.HistoryComic;
import com.example.onlinecomic.model.BookShelfModel;

import java.util.List;

public class BookShelfViewModel extends BaseViewModel<BookShelfModel> {

    public MutableLiveData<List<Comic>> favComicsMLD = new MutableLiveData<>();
    public MutableLiveData<List<HistoryComic>> historyComicsMLD = new MutableLiveData<>();

    public BookShelfViewModel() {
        model = new BookShelfModel();
    }

    @Override
    public void destroy() {
        model.cancel();
    }

    public void favorite(Comic comic) {
        model.favorite(comic, this::refreshFavoriteComics);
    }

    public void history(Comic comic) {
        model.history(comic, this::refreshHistoryComics);
    }

    public void deleteHistory(Comic comic) {
        Logger.i("history-->" + comic.toString());
        model.deleteHistory(comic);
        refreshHistoryComics();
    }

    @SuppressLint("CheckResult")
    public void refreshFavoriteComics() {
        Logger.i("refreshFavComics");
        favComicsMLD.setValue(model.refreshFavoriteComics());
    }

    @SuppressLint("CheckResult")
    public void refreshHistoryComics() {
        Logger.i("refreshHistoryComics");
        historyComicsMLD.setValue(model.refreshHistoryComics());
    }
}
