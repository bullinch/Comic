package com.example.library_comic;

import com.example.library_comic.common.ComicSource;
import com.example.library_comic.parse.ManHuaDB;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author LuQiChuang
 * @desc
 * @date 2021/1/11 23:51
 * @ver 1.0
 */
public enum SourceEnum {
    MAN_HUA_DB(1, "漫画DB");

    private static final Map<Integer, ComicSource> MAP = new LinkedHashMap<>();

    static {
        MAP.put(MAN_HUA_DB.ID, new ManHuaDB());
    }

    public static Map<Integer, ComicSource> getMAP() {
        return MAP;
    }

    public final int ID;
    public final String NAME;

    SourceEnum(int id, String name) {
        this.ID = id;
        this.NAME = name;
    }
}
