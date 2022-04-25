package com.example.onlinecomic.downolad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;

import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.library_comic.parse.ManHuaDB;
import com.example.onlinecomic.callback.DownloadCallback;
import com.example.onlinecomic.callback.IExecutor;
import com.example.onlinecomic.constant.Constants;
import com.example.onlinecomic.downolad.bean.Task;
import com.example.onlinecomic.downolad.bean.Worker;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class DownloadManager {

    private volatile static DownloadManager instance;
    private ExecutorService mExecutorService;
    private LongSparseArray<Pair<Task, Future>> mTaskArray;
    private final List<Worker> chapterWorkerList = new ArrayList<>();
    private final List<Task> allTaskList = new ArrayList<>();
    private Worker mCurrentWorker;
    private static final String rootPath = "/sdcard/Pictures/";

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mExecutorService = Executors.newFixedThreadPool(3);
        mTaskArray = new LongSparseArray<>();
    }

    @SuppressLint("CheckResult")
    public void createDownload(Chapter chapter) {
        if (chapter == null) {
            return;
        }
        Logger.i(chapter.toString());
        Worker worker = new Worker();
        worker.comic = new Comic(chapter.comicName, chapter.comicUrl, "");
        worker.chapter = chapter;
        worker.path = rootPath + chapter.name;
        worker.state = Constants.DOWNLOAD.ANALYZE;
        EventBus.getDefault().post(worker);
        DownloadDatabase.getInstance().insert(worker);
        chapterWorkerList.add(worker);
        parseTaskList(worker, new IExecutor<List<Task>>() {
            @Override
            public void success(List<Task> list) {
                worker.taskList.addAll(list);
                worker.allSize = list.size();
                worker.state = Constants.DOWNLOAD.QUEUE;
                allTaskList.addAll(list);
                EventBus.getDefault().post(worker);
                DownloadDatabase.getInstance().insert(worker);
                for (int i = 0; i < list.size(); i++) {
                    Future future = mExecutorService.submit(new DownCallable(worker, list.get(i)));
                    mTaskArray.put(list.get(i).tid, Pair.create(list.get(i), future));
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void parseTaskList(Worker worker, IExecutor<List<Task>> executor) {
        String url = worker.chapter.getHrefUrl();
        Observable<String> observable = EasyHttp.get(url)
                .cacheKey(url).execute(String.class);
        observable.map(new Function<String, List<Task>>() {
            @Override
            public List<Task> apply(@NonNull String s) throws Exception {
//                List<Page> pages = new ManHuaDB().parsePages(s);
                List<Task> list = new ArrayList<>();
//                for (int i = 0; i < pages.size(); i++) {
//                    Task task = new Task(pages.get(i), url, worker.path);
//                    list.add(task);
//                }
                return list;
            }
        }).subscribe(new Consumer<List<Task>>() {
            @Override
            public void accept(List<Task> tasks) throws Exception {
                executor.success(tasks);
            }
        });
    }

    public void startDownload(Worker worker) {
        if(worker == null) {
            Logger.e("----worker == null---");
        }
        mTaskArray.clear();
        if(worker.taskList.isEmpty()) {
            Logger.e("----下载出错---未解析到数据---");
        }
        for (int i = worker.loadSize; i < worker.taskList.size(); i++) {
            Future future = mExecutorService.submit(new DownCallable(worker, worker.taskList.get(i)));
            mTaskArray.put(worker.taskList.get(i).tid,
                    Pair.create(worker.taskList.get(i), future));
        }
        worker.state = Constants.DOWNLOAD.QUEUE;
        notifyDownloadState(worker);
    }

    public void pauseDownload(Worker worker) {
        Logger.i(worker.toString());
        for (int i = 0; i < mTaskArray.size(); i++) {
            if(worker.taskList.contains(mTaskArray.valueAt(i).first)) {
                mTaskArray.valueAt(i).second.cancel(true);
            }
        }
        worker.state = Constants.DOWNLOAD.PAUSE;
        notifyDownloadState(worker);
    }

    public void cancelDownload() {

    }

    public void startDownloadAll() {

    }

    public void pauseDownloadAll() {

    }

    public void cancelDownloadAll() {

    }

    private void requestDownloadAndWrite(Worker worker, Task task) {
        DownloadRequest request = new DownloadRequest(task.imgUrl)
                .savePath(task.workPath)
                .saveName(task.index + "")
                .cacheKey(task.imgUrl);
        request.execute(new DownloadCallback() {
            @Override
            public void onComplete(String path) {
                onTaskCompleted(worker, task, path);
            }

            @Override
            public void onStart() {
                worker.state = Constants.DOWNLOAD.ING;
                notifyDownloadState(worker);
            }
        });
    }

    private void onTaskStart() {
    }

    private void onTaskPause() {
    }

    private void onTaskCompleted(Worker worker, Task task, String path) {
        allTaskList.remove(task);
        int index = worker.taskList.indexOf(task);
        task.imgLocalPath = path;
        worker.taskList.set(index, task);
        mTaskArray.remove(task.tid);
        if (worker != null) {
            worker.loadSize++;
            if (worker.loadSize >= worker.allSize) {
                worker.state = Constants.DOWNLOAD.COMPLETE;
                notifyDownloadState(worker);
                mCurrentWorker =  getNextWorker();
                startDownload(mCurrentWorker);
                return;
            }
            notifyDownloadState(worker);
        } else {
            Logger.e("---current worker is null---");
        }
    }

    private Worker getNextWorker() {
        int position = -1;
        for(int i=0;i<chapterWorkerList.size();i++) {
            if(chapterWorkerList.get(i).id.longValue() == mCurrentWorker.id.longValue()) {
                position = i;
                break;
            }
        }
        position++;
        if(position < chapterWorkerList.size()) {
            return chapterWorkerList.get(position);
        }
        return null;
    }

    private class DownCallable implements Callable<String> {
        private final Task mTask;
        private final Worker mWorker;

        DownCallable(Worker worker, Task task) {
            mTask = task;
            mWorker = worker;
        }

        @SuppressLint("CheckResult")
        @Override
        public String call() throws Exception {
            requestDownloadAndWrite(mWorker, mTask);
            return null;
        }
    }

    private void notifyDownloadState(Worker worker) {
        EventBus.getDefault().post(worker);
        DownloadDatabase.getInstance().insert(worker);
    }

}
