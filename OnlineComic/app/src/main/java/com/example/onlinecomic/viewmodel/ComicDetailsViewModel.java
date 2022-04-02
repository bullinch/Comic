package com.example.onlinecomic.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.library_base.base.BaseModel;
import com.example.library_base.base.Callback;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.library_comic.parse.ManHuaDB;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

public class ComicDetailsViewModel extends BaseViewModel<BaseModel> {
    private static final String TAG = "ComicDetailsViewModel";

    public MutableLiveData<List<Chapter>> chapterMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Page>> pageMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Comic> readComicMutableLiveData = new MutableLiveData<>();

    public static String HOST_URL = "https://www.manhuadb.com";

    @Override
    public void destroy() {

    }

    public void requestChapters(Comic comic, Callback<String> callback) {
        Log.i(TAG, "requestComicUrl: " + comic.toString());
        EasyHttp.get(comic.getComicUrl())
                .cacheKey(getClass().getSimpleName()).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
                Log.e(TAG, "onError: " + e.getMessage());
                if (callback != null)
                    callback.onFailure(e.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                if (callback != null)
                    callback.onSuccess(s);
                ManHuaDB manHuaDB = new ManHuaDB();
                chapterMutableLiveData.setValue(manHuaDB.parseChapters(s));
            }
        });
    }

    public void requestPages(String url, Callback<String> callback) {
        Log.i(TAG, "requestWordUrl: " + url);
        EasyHttp.get(url)
                .cacheKey(getClass().getSimpleName()).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
                Log.e(TAG, "onError: " + e.getMessage());
                if (callback != null)
                    callback.onFailure(e.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                if (callback != null)
                    callback.onSuccess(s);
                ManHuaDB manHuaDB = new ManHuaDB();
                pageMutableLiveData.setValue(manHuaDB.parsePages(s));
            }
        });
    }

}
