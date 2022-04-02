package com.example.library_base.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.library_base.base.BaseModel;

public abstract class BaseViewModel<M extends BaseModel> extends ViewModel {

    protected M model;

    public abstract void destroy();
}
