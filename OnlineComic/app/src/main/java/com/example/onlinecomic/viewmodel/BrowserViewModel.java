package com.example.onlinecomic.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.library_base.base.Callback;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.model.ComicBrowserModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BrowserViewModel extends BaseViewModel<ComicBrowserModel> {

    public static String HOST_URL = "https://www.manhuadb.com";

    private CompositeDisposable compositeDisposable;

    public MutableLiveData<List<Comic>> recentUpdateList = new MutableLiveData<>();
    public MutableLiveData<List<Comic>> rankingList = new MutableLiveData<>();
    public MutableLiveData<List<Comic>> searchComicList = new MutableLiveData<>();

    public BrowserViewModel() {
        compositeDisposable = new CompositeDisposable();
        model = new ComicBrowserModel();
    }

    @Override
    public void destroy() {
        model.cancel();
    }

    public void requestHostUrl(String url) {
        model.requestHostUrl(url, new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                recentUpdateList.setValue(model.parseRecentUpdateList(s));
                rankingList.setValue(model.parseRankingList(s));
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    public void requestSearchData(String keyWord) {
        Logger.i("keyWord-->" + keyWord);
        model.requestSearchData(keyWord, new Callback<List<Comic>>() {
            @Override
            public void onSuccess(List<Comic> comics) {
                searchComicList.setValue(comics);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
