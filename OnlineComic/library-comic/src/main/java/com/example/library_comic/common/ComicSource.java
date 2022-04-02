package com.example.library_comic.common;

import com.example.library_comic.parse.Parser;

public abstract class ComicSource implements Parser {
    public abstract int getSourceId();
    public abstract String getSourceName();
    public abstract String getSourceHost();
}
