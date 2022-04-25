package com.example.onlinecomic.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Source;
import com.example.library_comic.common.ComicSource;
import com.example.onlinecomic.model.SourceModel;

import java.util.ArrayList;
import java.util.List;

public class SourceViewModel extends BaseViewModel<SourceModel> {

    public MutableLiveData<List<Source>> sourceListLiveData = new MutableLiveData<>();

    public SourceViewModel() {
        model = new SourceModel();
    }

    @Override
    public void destroy() {
    }

    public void requestSourceData() {
        Logger.i("requestSourceData");
        List<Source> list = new ArrayList<>();
        for(ComicSource comicSource : model.getSourceList()) {
            Source source = new Source(comicSource.getSourceId(),
                    comicSource.getSourceName(), comicSource.getSourceHost());
            list.add(source);
        }
        sourceListLiveData.setValue(list);
    }
}
