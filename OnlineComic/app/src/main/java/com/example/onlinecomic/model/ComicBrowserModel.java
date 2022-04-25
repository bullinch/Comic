package com.example.onlinecomic.model;

import com.example.library_base.base.BaseModel;
import com.example.library_base.base.Callback;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.common.ComicSource;
import com.example.library_comic.parse.ManHuaDB;
import com.example.library_comic.parse.Parser;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class ComicBrowserModel extends BaseModel {

    public static String HOST_URL = "";

    public ComicBrowserModel() {
    }

    public void requestHostUrl(String url, Callback<String> callback) {
        Logger.i("requestHostUrl: " + url);
        HOST_URL = url;
        Disposable disposable = EasyHttp.get(url).cacheMode(CacheMode.NO_CACHE)
                .cacheKey(url).headers("Referer", "http://www.cartoonmad.com").
                        headers("User-Agent",
                "Mozilla/5.0 (Linux; Android 7.0;) Chrome/58.0.3029.110 Mobile").execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Logger.i("onError: " + e.getMessage());
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(s);
                    }
                });
        addDisposable(disposable);
    }

    public void requestSearchData(String keyWord, Callback<List<Comic>> callback) {
        Logger.i("keyWord-->"+keyWord);
        Parser parser = new ManHuaDB();
        parser.getSearchRequest(keyWord, -1).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
            }

            @Override
            public void onSuccess(String s) {
                callback.onSuccess(parser.parseSearch(s));
            }
        });
    }

    public List<Comic> parseRecentUpdateList(ComicSource source, String html) {
        return source.parseRecentUpdateList(html);
    }

    public List<Comic> parseRankingList(ComicSource source, String html) {
        return source.parseRankingList(html);
    }
}
