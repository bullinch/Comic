package com.example.onlinecomic.downolad.bean;

import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Page;
import com.example.onlinecomic.util.StringUtils;

public class Task extends Page {

    public long tid;
    public String workUrl;
    public String workPath;

    public Task(int index, String title, String imgUrl) {
        super(title, imgUrl, index);
    }

    public Task(int index, String title, String imgUrl, String workUrl, String workPath) {
        super(title, imgUrl, index);
        this.workUrl = workUrl;
        this.workPath = workPath;
        tid = StringUtils.parseStringToLong(imgUrl);
    }

    public Task(Page page, String workUrl, String workPath) {
        super(page.title, page.imgUrl, page.index);
        this.workUrl = workUrl;
        this.workPath = workPath;
        tid = StringUtils.parseStringToLong(imgUrl);
    }

    public Page getPage() {
        Page page = new Page(title, imgUrl, imgLocalPath, index);
        return page;
    }

    @Override
    public String toString() {
        return "Task{" +
                "tid=" + tid +
                ", workUrl='" + workUrl + '\'' +
                ", workPath='" + workPath + '\'' +
                ", imgLocalPath='" + imgLocalPath + '\'' +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", index=" + index +
                '}';
    }
}
