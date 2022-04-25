package com.example.onlinecomic.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.service.DownloadService;
import com.example.onlinecomic.ui.activity.ComicChapterActivity;
import com.example.onlinecomic.ui.activity.ComicReadActivity;

public class IntentUtils {

    public static void intentChapterActivity(Activity from,  Parcelable object) {
        Intent intent = new Intent(from, ComicChapterActivity.class);
        intent.putExtra("comic", object);
        from.startActivity(intent);
    }

    public static void intentChapterActivity(Activity from, Parcelable object, int extra) {
        Intent intent = new Intent(from, ComicChapterActivity.class);
        intent.putExtra("comic", object);
        intent.putExtra("extra", extra);
        from.startActivity(intent);
    }

    public static void intentReadActivity(Activity from,  Parcelable object) {
        Intent intent = new Intent(from, ComicReadActivity.class);
        intent.putExtra("chapter", object);
        from.startActivity(intent);
    }

    public static void intentDownloadService(Activity from, Parcelable object) {
        Intent intent = new Intent(from, DownloadService.class);
        intent.setAction(DownloadService.CREATE);
        intent.putExtra("chapter", object);
        from.startService(intent);
    }

    public static void intentServiceWithTask(Activity from, String action) {
        Intent intent = new Intent(from, DownloadService.class);
        intent.setAction(action);
        from.startService(intent);
    }
}
