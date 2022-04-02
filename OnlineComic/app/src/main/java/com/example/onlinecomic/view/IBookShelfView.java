package com.example.onlinecomic.view;

import com.example.library_base.base.IBaseView;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.bean.HistoryComic;

import java.util.List;

public interface IBookShelfView extends IBaseView {

    void refreshFavoriteList(List<Comic> list);

    void refreshHistoryList(List<HistoryComic> list);

}
