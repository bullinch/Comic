package com.example.onlinecomic.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.library_base.util.log.Logger;

public class TestViewModel extends AndroidViewModel {

    public MutableLiveData<String> test = new MutableLiveData<>();

    public TestViewModel(@NonNull Application application) {
        super(application);
    }

    public void a() {
        Logger.i("------------");
    }

}
