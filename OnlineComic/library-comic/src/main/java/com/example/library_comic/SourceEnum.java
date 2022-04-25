package com.example.library_comic;

import com.example.library_comic.common.ComicSource;
import com.example.library_comic.parse.BaiNian;
import com.example.library_comic.parse.CartoonMad;
import com.example.library_comic.parse.MH90;
import com.example.library_comic.parse.ManHuaDB;
import com.example.library_comic.parse.MangaBZ;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author LuQiChuang
 * @desc
 * @date 2021/1/11 23:51
 * @ver 1.0
 */
public enum SourceEnum {
    MAN_HUA_DB(1, "漫画DB", "https://www.manhuadb.com"),
    MANGA_BZ(2, "MangaBZ", "http://www.mangabz.com"),
    MH90(3, "MH90", "http://m.90mh.com/"),
    BAINIAN(4, "BaiNian", "https://m.bnman.net"),
    CARTOONMAD(5, "CartoonMad", "https://www.cartoonmad.com");

    private static final Map<Integer, ComicSource> MAP = new LinkedHashMap<>();

    static {
        MAP.put(MAN_HUA_DB.ID, new ManHuaDB());
        MAP.put(MANGA_BZ.ID, new MangaBZ());
        MAP.put(MH90.ID, new MH90());
        MAP.put(BAINIAN.ID, new BaiNian());
        MAP.put(CARTOONMAD.ID, new CartoonMad());
    }

    public static Map<Integer, ComicSource> getMAP() {
        return MAP;
    }

    public final int ID;
    public final String NAME;
    public final String HOST;

    SourceEnum(int id, String name, String host) {
        this.ID = id;
        this.NAME = name;
        this.HOST = host;
    }
}
