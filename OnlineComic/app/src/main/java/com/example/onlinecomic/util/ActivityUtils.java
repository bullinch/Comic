package com.example.onlinecomic.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.ui.activity.ComicReadActivity;

public class ActivityUtils {

    public static void intentActivity(Activity from,  Class to, Parcelable object) {
        Intent intent = new Intent(from, to);
        intent.putExtra("comic", object);
        from.startActivity(intent);

    }

    public static void intentActivity(Activity from,  Class to, Parcelable object, int extra) {
        Intent intent = new Intent(from, to);
        intent.putExtra("comic", object);
        intent.putExtra("extra", extra);
        from.startActivity(intent);
    }
}
