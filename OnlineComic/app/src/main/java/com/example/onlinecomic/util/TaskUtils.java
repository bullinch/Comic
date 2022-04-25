package com.example.onlinecomic.util;

import com.example.library_comic.bean.Page;
import com.example.onlinecomic.constant.Constants;
import com.example.onlinecomic.downolad.bean.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {

    public static List<Page> parseTasksToPages(List<Task> list) {
        List<Page> pageList = new ArrayList<>();
        for(Task task:list) {
            Page page = task.getPage();
            pageList.add(page);
        }
        return pageList;
    }

    public static String parseDownloadState(int state) {
        String stateS = "";
        switch (state) {
            case Constants.DOWNLOAD.QUEUE:
                stateS = "队列中...";
                break;
            case Constants.DOWNLOAD.ING:
                stateS = "正在下载";
                break;
            case Constants.DOWNLOAD.ANALYZE:
                stateS = "正在解析";
                break;
            case Constants.DOWNLOAD.PAUSE:
                stateS = "已暂停";
                break;
            case Constants.DOWNLOAD.COMPLETE:
                stateS = "已完成";
                break;
        }
        return stateS;
    }
}
