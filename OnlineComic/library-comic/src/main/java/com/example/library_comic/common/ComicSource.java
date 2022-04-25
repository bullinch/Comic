package com.example.library_comic.common;

import com.example.library_comic.bean.Comic;
import com.example.library_comic.parse.Parser;

import java.util.List;

public abstract class ComicSource implements Parser {
    public abstract int getSourceId();
    public abstract String getSourceName();
    public abstract String getSourceHost();
    public abstract List<Comic> parseRecentUpdateList(String html);
    public abstract List<Comic> parseRankingList(String html);
}
