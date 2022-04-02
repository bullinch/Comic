package com.example.onlinecomic.model;

import com.example.library_base.base.BaseModel;
import com.example.library_comic.SourceEnum;
import com.example.library_comic.common.ComicSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SourceModel extends BaseModel{

    public List<ComicSource> getSourceList() {
        List<ComicSource> list = new ArrayList<>();
        Map<Integer, ComicSource> sourceMap = SourceEnum.getMAP();
        Iterator iterator = sourceMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            int sourceId = (int) entry.getKey();
            ComicSource comicSource = (ComicSource) entry.getValue();
            list.add(comicSource);
        }
        return list;
    }
}
