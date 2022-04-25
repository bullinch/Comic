package com.example.onlinecomic.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.library_base.base.Callback;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.SourceEnum;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Page;
import com.example.library_comic.parse.ManHuaDB;
import com.example.onlinecomic.app.AppCache;
import com.example.onlinecomic.model.ComicModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.GetRequest;

import java.util.List;

public class ReadViewModel extends BaseViewModel<ComicModel> {

    public MutableLiveData<List<Page>> pageMutableLiveData = new MutableLiveData<>();

    public ReadViewModel() {
        model = new ComicModel();
    }

    @Override
    public void destroy() {
    }

    public void a(Chapter chapter, Callback<String> callback) {

    }

    public void requestPages(String url, Callback<String> callback) {
        Logger.i("url:"+url+"__AppCache.sourceId="+AppCache.sourceId);
        GetRequest request = SourceEnum.getMAP().get(AppCache.sourceId).getPagesRequest(url);
        request.execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
                if (callback != null)
                    callback.onFailure(e.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                if (callback != null)
                    callback.onSuccess(s);
                pageMutableLiveData.setValue(SourceEnum.getMAP().get(AppCache.sourceId).parsePages(s));
            }
        });
    }
}
