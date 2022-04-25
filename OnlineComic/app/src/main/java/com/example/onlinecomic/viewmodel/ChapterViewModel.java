package com.example.onlinecomic.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.library_base.base.Callback;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.SourceEnum;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.common.ComicSource;
import com.example.library_comic.parse.ManHuaDB;
import com.example.onlinecomic.app.AppCache;
import com.example.onlinecomic.model.ComicModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class ChapterViewModel extends BaseViewModel<ComicModel> {

    public MutableLiveData<List<Chapter>> chapterMutableLiveData = new MutableLiveData<>();

    public ChapterViewModel() {
        model = new ComicModel();
    }

    @Override
    public void destroy() {
    }

    public void requestChapters(Comic comic, Callback<String> callback) {
        Logger.i(comic.toString());
        AppCache.sourceId = comic.sourceId;
        EasyHttp.get(comic.comicUrl)
                .cacheKey(comic.comicUrl).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
                if (callback != null)
                    callback.onFailure(e.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                if (callback != null)
                    callback.onSuccess(s);
                List<Chapter> chapterList = new ArrayList<>();
                Logger.i("____________"+comic.sourceId);
                List<Chapter> list = SourceEnum.getMAP().get(comic.sourceId).parseChapters(s, comic.comicUrl);
                for(Chapter chapter : list) {
                    chapter.comicUrl = comic.comicUrl;
                    chapter.comicName = comic.title;
                    chapterList.add(chapter);
                }
                chapterMutableLiveData.setValue(chapterList);
            }
        });
    }

}
