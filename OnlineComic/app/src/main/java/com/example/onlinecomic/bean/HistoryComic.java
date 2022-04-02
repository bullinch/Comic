package com.example.onlinecomic.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;
import com.example.library_comic.bean.Comic;

public class HistoryComic extends JSectionEntity {

    private Object object;
    private String readChapterIndex;
    private String readChapterUrl;
    private Long timeStamp;
    private boolean isHeader;

    public HistoryComic(boolean isHeader, Object object) {
        this.object = object;
        this.isHeader = isHeader;
    }

    public HistoryComic(boolean isHeader, Object object, String readChapterIndex, String readChapterUrl) {
        this.object = object;
        this.readChapterIndex = readChapterIndex;
        this.readChapterUrl = readChapterUrl;
        this.isHeader = isHeader;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReadChapterIndex() {
        return readChapterIndex;
    }

    public void setReadChapterIndex(String readChapterIndex) {
        this.readChapterIndex = readChapterIndex;
    }

    public String getReadChapterUrl() {
        return readChapterUrl;
    }

    public void setReadChapterUrl(String readChapterUrl) {
        this.readChapterUrl = readChapterUrl;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }
}
