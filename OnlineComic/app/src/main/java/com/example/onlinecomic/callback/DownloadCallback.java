package com.example.onlinecomic.callback;

import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;

public abstract class DownloadCallback extends CallBack {

    public abstract void onComplete(String var1);

    public void onCompleted() {
    }
    public void onError(ApiException e) {
    }
    public void onSuccess(Object o) {
    }
}
