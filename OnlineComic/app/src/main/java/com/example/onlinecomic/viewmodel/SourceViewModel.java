package com.example.onlinecomic.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.common.ComicSource;
import com.example.onlinecomic.model.SourceModel;

import java.util.List;

public class SourceViewModel extends BaseViewModel<SourceModel> {

    public MutableLiveData<List<ComicSource>> sourceListLiveData = new MutableLiveData<>();

    public SourceViewModel() {
        model = new SourceModel();
    }

    @Override
    public void destroy() {
    }

    public void requestSourceData() {
        Logger.i("requestSourceData");
        sourceListLiveData.setValue(model.getSourceList());
    }
}
