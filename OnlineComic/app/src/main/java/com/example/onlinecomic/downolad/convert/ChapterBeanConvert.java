package com.example.onlinecomic.downolad.convert;

import android.text.TextUtils;

import com.example.library_comic.bean.Chapter;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

public class ChapterBeanConvert implements PropertyConverter<Chapter, String> {

    @Override
    public Chapter convertToEntityProperty(String databaseValue) {
        if(TextUtils.isEmpty(databaseValue)) {
            return null;
        } else {
            return new Gson().fromJson(databaseValue, Chapter.class);
        }
    }

    @Override
    public String convertToDatabaseValue(Chapter entityProperty) {
        if(entityProperty == null) {
            return null;
        } else {
            String str = new Gson().toJson(entityProperty, Chapter.class);
            return str;
        }
    }
}
