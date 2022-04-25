package com.example.onlinecomic.constant;

public class Constants {

    public static final String COMIC_RECENT_UPDATE = "recent_update";
    public static final String COMIC_RANKING = "ranking";
    public static final String COMIC_CLASSIFICATION = "classification";

    public static class SOURCE {

        public static final String MHDB = "https://www.manhuadb.com/";
    }


    public static class DOWNLOAD {
        public static final int IDLE = 0;
        public static final int ANALYZE = 1;
        public static final int QUEUE = 2;
        public static final int ING = 3;
        public static final int PAUSE = 4;
        public static final int COMPLETE = 5;
        public static final int ERROR = -1;
    }

}
