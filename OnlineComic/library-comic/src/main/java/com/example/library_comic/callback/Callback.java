package com.example.library_comic.callback;

public interface Callback<T> {
    void onSuccess(T t);
    void onFailure(String msg);
}
