package com.example.onlinecomic.downolad.convert;

import android.text.TextUtils;

import com.example.library_comic.bean.Comic;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

public class ComicBeanConvert implements PropertyConverter<Comic, String> {

    @Override
    public Comic convertToEntityProperty(String databaseValue) {
        if(TextUtils.isEmpty(databaseValue)) {
            return null;
        } else {
            return new Gson().fromJson(databaseValue, Comic.class);
        }
    }

    @Override
    public String convertToDatabaseValue(Comic entityProperty) {
        if(entityProperty == null) {
            return null;
        } else {
            String str = new Gson().toJson(entityProperty, Comic.class);
            return str;
        }
    }
}
