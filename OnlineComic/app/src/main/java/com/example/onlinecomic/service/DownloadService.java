package com.example.onlinecomic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Chapter;
import com.example.onlinecomic.downolad.DownloadManager;

public class DownloadService extends Service {

    public static final String CREATE = "create_download";
    public static final String START = "start_download";
    public static final String PAUSE = "pause_download";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("onCreate");
        DownloadManager.getInstance().init(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_STICKY;
    }

    private void handleIntent(Intent intent) {
        String action = null;
        if(intent != null) {
            action = intent.getAction();
        }
        if(!TextUtils.isEmpty(action)) {
            switch (action) {
                case CREATE:
                    Chapter chapter = intent.getParcelableExtra("chapter");
                    DownloadManager.getInstance().createDownload(chapter);
                    break;
                case PAUSE:
//                    DownloadManager.getInstance().pauseDownload();
                    break;
                case START:
//                    DownloadManager.getInstance().startDownload();
                    break;
            }
        }
    }
}
