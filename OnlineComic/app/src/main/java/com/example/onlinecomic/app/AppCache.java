package com.example.onlinecomic.app;

public class AppCache {
    private volatile static AppCache instance;

    public static int sourceId;

    public static AppCache get() {
        if (instance == null) {
            synchronized (AppCache.class) {
                if (instance == null) {
                    instance = new AppCache();
                }
            }
        }
        return instance;
    }

}
