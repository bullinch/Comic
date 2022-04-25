package com.example.onlinecomic.downolad.bean;

import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.downolad.convert.ChapterBeanConvert;
import com.example.onlinecomic.downolad.convert.ComicBeanConvert;
import com.example.onlinecomic.downolad.convert.TaskListConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Worker {

    @Id(autoincrement = true)
    public Long id;

    @Convert(columnType = String.class,converter = ComicBeanConvert.class)
    public Comic comic;
    private int comicId;
    @Convert(columnType = String.class,converter = ChapterBeanConvert.class)
    public Chapter chapter;
    @Convert(columnType = String.class,converter = TaskListConvert.class)
    public List<Task> taskList = new ArrayList<>();

    public int state;
    public String path;
    public int allSize;
    public int loadSize;
    @Generated(hash = 1000819244)
    public Worker(Long id, Comic comic, int comicId, Chapter chapter,
            List<Task> taskList, int state, String path, int allSize,
            int loadSize) {
        this.id = id;
        this.comic = comic;
        this.comicId = comicId;
        this.chapter = chapter;
        this.taskList = taskList;
        this.state = state;
        this.path = path;
        this.allSize = allSize;
        this.loadSize = loadSize;
    }
    @Generated(hash = 107771754)
    public Worker() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Comic getComic() {
        return this.comic;
    }
    public void setComic(Comic comic) {
        this.comic = comic;
    }
    public Chapter getChapter() {
        return this.chapter;
    }
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    public List<Task> getTaskList() {
        return this.taskList;
    }
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getAllSize() {
        return this.allSize;
    }
    public void setAllSize(int allSize) {
        this.allSize = allSize;
    }
    public int getLoadSize() {
        return this.loadSize;
    }
    public void setLoadSize(int loadSize) {
        this.loadSize = loadSize;
    }
    public int getComicId() {
        return this.comicId;
    }
    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", state=" + state +
                ", path='" + path + '\'' +
                ", allSize=" + allSize +
                ", loadSize=" + loadSize +
                '}';
    }
}
