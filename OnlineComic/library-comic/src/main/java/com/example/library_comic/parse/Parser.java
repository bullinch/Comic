package com.example.library_comic.parse;

import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.zhouyou.http.request.GetRequest;

import java.util.List;

public interface Parser {
    GetRequest getChaptersRequest(String url);
    GetRequest getPagesRequest(String url);
    GetRequest getSearchRequest(String keyword, int page);

    List<Chapter> parseChapters(String html);
    List<Page> parsePages(String html);
    List<Comic> parseSearch(String html);
}
