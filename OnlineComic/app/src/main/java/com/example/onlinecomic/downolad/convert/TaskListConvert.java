package com.example.onlinecomic.downolad.convert;

import com.example.onlinecomic.downolad.bean.Task;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

public class TaskListConvert implements PropertyConverter<List<Task>, String> {

    @Override
    public List<Task> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        String[] list_str = databaseValue.split("~");
        List<Task> list = new ArrayList<>();
        for (String s : list_str) {
            list.add(new Gson().fromJson(s, Task.class));
        }
        return list;

    }

    @Override
    public String convertToDatabaseValue(List<Task> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Task array : entityProperty) {
                String str = new Gson().toJson(array,Task.class);
                sb.append(str);
                sb.append("~");
            }
            return sb.toString();
        }
    }
}
